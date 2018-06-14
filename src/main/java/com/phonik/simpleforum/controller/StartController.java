package com.phonik.simpleforum.controller;

import com.phonik.simpleforum.elements.ForumSection;
import com.phonik.simpleforum.elements.service.SectionService;
import com.phonik.simpleforum.exceptions.EmailExistException;
import com.phonik.simpleforum.exceptions.EmptyFieldsException;
import com.phonik.simpleforum.exceptions.UserPrivilegesException;
import com.phonik.simpleforum.users.GeneralUser;
import com.phonik.simpleforum.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StartController {

    @Autowired
    private UserService userService;

    @Autowired
    private SectionService sectionService;

    private boolean hasAdmin = false;
    private String message;

    /**
     * Starting point of the whole forum, method checks for two elements that must be present to render the view,
     * administrator user and root section. If any of them are absent user is prompted to create either one of them.
     * */

    @GetMapping(value = "forum")
    public ModelAndView initForum(ModelAndView modelAndView) {
        // check if there's user with type admin
        ForumSection root = sectionService.getRootSection();
        if (root != null) {
            modelAndView.addObject("root", root);
            modelAndView.setViewName("section");
        } else {
            boolean adminPresent = userService.adminCheck();
            if (adminPresent) {
                // login as admin
                message = "Please login as administrator";
                modelAndView.addObject("message", message);

//                System.out.println(model.asMap().get("userName"));
                modelAndView.setViewName("login");
            } else {
                // register administrator
                message = "Register as administrator";
                modelAndView.addObject("message", message);
                modelAndView.setViewName("create_admin");
            }
        }
        return modelAndView;
    }


    @PostMapping(value = "admin/create")
    public ModelAndView registerUser(@RequestParam(value = "username") final String username,
                                     @RequestParam(value = "pass") final String password,
                                     @RequestParam(value = "email") final String email,
                                     ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        try {
            GeneralUser newUser = userService.createNewAdmin(username, password, email);
            redirectAttributes.addFlashAttribute("userName", newUser);
            modelAndView.setViewName("redirect:/forum");
        } catch (EmptyFieldsException | EmailExistException e) {
            System.out.println(e.getMessage());
            modelAndView.addObject("errorMessage", e.getMessage());
            modelAndView.setViewName("create_admin");
        }
        return modelAndView;
    }

    /**
     * Opens root element creation form -> create_root.jsp
     * */
    @GetMapping(value = "root/create")
    public String getRootCreateForm(ModelAndView modelAndView) {
        return "create_root";
    }

    // creates temp admin user
    private GeneralUser getTestAdmin() throws EmptyFieldsException, EmailExistException {
        return userService.createNewAdmin("testAdmin", "adminpass", "adminemail");
    }

    // root forum element creation method
    @PostMapping(value = "root/create")
    public ModelAndView createRootSection(@RequestParam(value = "title") String rootTitle,
                                           @RequestParam(value = "description") String rootDescription,
                                           ModelAndView modelAndView) {
        GeneralUser admin = null;
        try {
            // create sample admin user and main forum element
            admin = getTestAdmin();
            sectionService.createRoot(rootTitle, rootDescription, admin);
        } catch (EmptyFieldsException | EmailExistException e) {
            e.printStackTrace();
        } catch (UserPrivilegesException e) {
            e.printStackTrace();
        }
        modelAndView.setViewName("redirect:/forum");
        return modelAndView;
    }

}
