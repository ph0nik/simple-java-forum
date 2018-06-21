package com.phonik.simpleforum.controller;

import com.phonik.simpleforum.dao.ForumElementDao;
import com.phonik.simpleforum.elements.ForumSection;
import com.phonik.simpleforum.elements.service.SectionService;
import com.phonik.simpleforum.exceptions.UserPrivilegesException;
import com.phonik.simpleforum.users.GeneralUser;
import com.phonik.simpleforum.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import java.util.NoSuchElementException;

@Controller
public class SectionController {

//
//    @Autowired
//    private ForumElementDao forumElementDao;

    @Autowired
    private UserService userService;

    @Autowired
    private SectionService sectionService;

    /**
    * showing selected section from database
    * */
    @GetMapping(value = "section/{id}")
    public ModelAndView showSelectedSection(@PathVariable("id") final int id, ModelAndView model, RedirectAttributes ra) {
        ForumSection forumSection = sectionService.getSection(id);
        model.addObject("section", forumSection);
//        try {
//            forumSection = forumElementDao.getForumSection(id);
//            model.addObject("section", forumSection);
//        } catch (NoSuchElementException ex) {
//            System.out.println(ex.getMessage());
//        }
        System.out.println(forumSection.getTitle());
        model.setViewName("section");
        return model;
    }

    /**
     * Showing form to insert new section
     * */
    @GetMapping(value = "section/new")
    public ModelAndView newSectionForm(@RequestParam(value = "parent") final long parentId,
                                 ModelAndView model) {
        // passing forward parent element id
        model.addObject("parentSection", parentId);
        model.setViewName("new_section");
        return model;
    }

    private GeneralUser testUser() {
        return userService.getUserWithId(1);
    }

    // TODO user object passed as parameter to create new element collides with user existing in db
    @PostMapping(value = "section/new")
    public String submitNewSection(@RequestParam(value = "title") final String title,
                                   @RequestParam(value = "description") final String description,
                                   @RequestParam(value = "parent") final long parentId,
                                   ModelAndView model) throws UserPrivilegesException {
        System.out.println(testUser());
        ForumSection forumSection = sectionService.addNewSection(title, description, testUser(), parentId);
//        ForumSection section = (ForumSection) model.getModel().get("section");
//
//        System.out.println(description);
        return "redirect:/section/" + forumSection.getId();
    }

    @GetMapping(value = "welcome")
    public String hello() {

        return "login";
    }

}
