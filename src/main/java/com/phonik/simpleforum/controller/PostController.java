package com.phonik.simpleforum.controller;

import com.phonik.simpleforum.elements.ForumPost;
import com.phonik.simpleforum.elements.service.PostService;
import com.phonik.simpleforum.elements.service.SectionService;
import com.phonik.simpleforum.exceptions.UserPrivilegesException;
import com.phonik.simpleforum.users.GeneralUser;
import com.phonik.simpleforum.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PostController {

    @Autowired
    private UserService userService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private PostService postService;

    private GeneralUser testUser() {
        return userService.getUserWithId(1);
    }

    /**
     * show post
     */
    @GetMapping(value = "post/{id}")
    public ModelAndView showPost(@PathVariable("id") final int id, ModelAndView mav) {
        ForumPost post = postService.getPost(id);
        mav.addObject("post", post);
        mav.setViewName("post");
        return mav;
    }

    /**
     * show add new post page
     * */
    @GetMapping(value = "post/new")
    public ModelAndView newPostForm(@RequestParam(value = "parentId") final int parentId,
                                @RequestParam(value = "parentName") final String parentName,
                                ModelAndView mav) {
        mav.addObject("parentId", parentId);
        mav.addObject("parentName", parentName);
        mav.setViewName("new_post");
        return mav;
    }

    /**
     * add new post
     * */
    @PostMapping(value = "post/new")
    public String addNewPost(@RequestParam(value = "title") final String title,
                                   @RequestParam(value = "parentId") final int parentId,
                                   @RequestParam(value = "message") final String message) throws UserPrivilegesException {
        ForumPost forumPost = postService.addNewPost(title, message, testUser(), parentId);
        return "redirect:/post/" + forumPost.getId();
    }
}
