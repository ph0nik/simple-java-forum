package com.phonik.simpleforum.controller;

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
public class UserController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private UserDao userDao;


    @GetMapping(value = "user/create")
    public ModelAndView createNewUser(ModelAndView modelAndView) {
        modelAndView.setViewName("create_user");
        return modelAndView;
    }

    @PostMapping(value = "user/create")
    public ModelAndView registerUser(@RequestParam (value = "username") final String username,
                                     @RequestParam (value = "pass") final String password,
                                     @RequestParam (value = "email") final String email,
                                     ModelAndView modelAndView) {

        try {
            GeneralUser newUser = userService.createNewUser(username, password, email);
            System.out.println(userService.getUserWithId(newUser.getUserId()));


        } catch (EmptyFieldsException | EmailExistException e) {
            System.out.println(e.getMessage());
            modelAndView.addObject("errorMessage", e.getMessage());
            modelAndView.setViewName("create");
        }
        return modelAndView;
    }

}
