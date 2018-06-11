package com.phonik.simpleforum.controller;

import com.phonik.simpleforum.dao.ForumElementDao;
import com.phonik.simpleforum.elements.ForumSection;
import com.phonik.simpleforum.elements.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

@Controller
public class SectionController {


    @Autowired
    ForumElementDao forumElementDao;


    @Autowired
    SectionService sectionService;


    /**
    * showing selected section from database
    * */
    @GetMapping(value = "section/{id}")
    public ModelAndView showSelectedSection(@PathVariable("id") final int id, ModelAndView model, RedirectAttributes ra) {
        ForumSection forumSection = null;
        try {
            forumSection = forumElementDao.getForumSection(id);
            model.addObject("section", forumSection);
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
        }
        model.setViewName("section");
        return model;
    }

    /**
     * Showing form to insert new section
     * */
    @GetMapping(value = "new_section")
    public String newSectionForm() {
        return "new_section";
    }

    @PostMapping(value = "new_section")
    public String submitNewSection(@RequestParam(value = "title") final String title,
                                   @RequestParam(value = "description") final String description,
                                   ModelAndView model) {
        ForumSection section = (ForumSection) model.getModel().get("section");
        System.out.println(description);
        return "redirect:/section/" + "id of newly added section";
    }

    @GetMapping(value = "welcome")
    public String hello() {

        return "login";
    }

}
