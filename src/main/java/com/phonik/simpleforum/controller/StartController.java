package com.phonik.simpleforum.controller;

import com.phonik.simpleforum.elements.ForumSection;
import com.phonik.simpleforum.elements.service.SectionService;
import com.phonik.simpleforum.exceptions.EmailExistException;
import com.phonik.simpleforum.exceptions.EmptyFieldsException;
import com.phonik.simpleforum.users.GeneralUser;
import com.phonik.simpleforum.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StartController {

    @Autowired
    private UserService userService;

    @Autowired
    private SectionService sectionService;

    private boolean hasAdmin = false;

    /**
     * Starting point of the whole forum, method checks for two elements that must be present to render the view,
     * administrator user and root section. If any of them are absent user is prompted to create either one of them.
     * */

    @GetMapping(value = "forum")
    public ModelAndView initForum(ModelAndView modelAndView) {
        // check if there's user with type admin
        boolean asd = userService.adminCheck();
        if (asd) {
            System.out.println(asd);
            // get the root section of forum
            ForumSection rootSection = sectionService.getRootSection();

            // add root object to model so it can be rendered within jsp
            modelAndView.addObject("root", rootSection);

            // set the file name to render
            modelAndView.setViewName("section");
//            modelAndView.setViewName("redirect:section/1");
        } else {
            // admin not found, redirect to create new user
            modelAndView.setViewName("create_admin");
        }
        // check if admin exist, if not prompt to create admin
        // check if forum root exists, if not prompt to create root section
        return modelAndView;
    }

//    @GetMapping(value = "admin/create")
//    public String createAdmin() {
//        return "create_admin";
//    }

    @PostMapping(value = "admin/create")
    public ModelAndView registerUser(@RequestParam(value = "username") final String username,
                                     @RequestParam(value = "pass") final String password,
                                     @RequestParam(value = "email") final String email,
                                     ModelAndView modelAndView) {
        try {
            GeneralUser newUser = userService.createNewAdmin(username, password, email);
            System.out.println(newUser);

            System.out.println("admin chk: " + userService.adminCheck());
            modelAndView.addObject("user", newUser);
            modelAndView.setViewName("redirect:root/create");
        } catch (EmptyFieldsException | EmailExistException e) {
            System.out.println(e.getMessage());
            modelAndView.addObject("errorMessage", e.getMessage());
            modelAndView.setViewName("create_admin");
        }
        return modelAndView;
    }

    @GetMapping(value = "root/create")
    public String getRootCreateForm(ModelAndView modelAndView) {
        System.out.println(modelAndView.getModel().get("user"));
        return "create_root";
    }

    @PostMapping(value = "root/create")
    public String createRootSection() {
        return "";
    }

}
