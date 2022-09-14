package com.gatineau.TwitterLike_UMS.controllers;

import com.gatineau.TwitterLike_UMS.AuthenticationUtil;
import com.gatineau.TwitterLike_UMS.beans.User;
import com.gatineau.TwitterLike_UMS.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;


@RestController

public class UserController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    AuthenticationUtil authenticationUtil;

    @GetMapping("/")
    public ModelAndView getUserLoginPage(ModelMap model) {
        if (authenticationUtil.isAuthenticated()) {
            return new ModelAndView("redirect:/user", model);
        }
        return new ModelAndView("/", model);
    }

    @GetMapping("/user")
    public ResponseEntity<User> user() {
        Optional<User> user = Optional.ofNullable(
                userRepo.findUserByGitHubID(
                        authenticationUtil.getAuthToken().getGitHubID()));
        if (user.isPresent()) {
            user.get();
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return createUser();
        }
    }


    @GetMapping(path = "/user/" + "{gitHubID}")
    public ResponseEntity<User> getByUserName(@PathVariable String gitHubID) throws Exception {
        System.out.println(gitHubID);
        Optional<User> user = Optional.ofNullable(userRepo.findUserByGitHubID(gitHubID));
        if (user.isPresent()) {
            System.out.println(gitHubID);
            user.get();

        } else {
            throw new Exception();
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @GetMapping("users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("roles")
    public List<String> getRoles() {
        return mongoTemplate.query(User.class).distinct("roles").as(String.class).all();
    }

    private ResponseEntity<User> createUser() {
        try {
            User _user = userRepo.save(new User(
                    authenticationUtil.getAuthToken().getId(),
                    authenticationUtil.getAuthToken().getName(),
                    authenticationUtil.getAuthToken().getGitHubID(),
                    authenticationUtil.getAuthToken().getAuthToken()));
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}