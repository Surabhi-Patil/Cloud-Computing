/**
 * Amitha_Murali, 001643826, murali.a@husky.neu.edu
 * Jyoti Sharma, 001643410, sharma.j@husky.neu.edu
 * Surabhi Patil, 001251860, patil.sur@husky.neu.edu
 **/

package com.csye6225.demo.controllers;


import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;



import com.csye6225.demo.User;
import com.csye6225.demo.UserRepository;
import com.csye6225.demo.Helper;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/user") // This means URL's start with /user (after Application path)

public class RegisterUserController {

    @Autowired // This means to get the bean called userRepository which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;


    @Autowired
    private Helper helper;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //@GetMapping(path="/register") // Map ONLY GET Requests
    @RequestMapping(value="/register",method=RequestMethod.POST,produces="application/json")
    public @ResponseBody String addNewUser (HttpServletRequest request, HttpServletResponse response){
        //@RequestParam String email, @RequestParam String password) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if(helper.validateUserEmail(email) == null) {
            User newUser = new User();
            //newUser.setName(name);
            newUser.setEmail(email);   //
            newUser.setPassword(bCryptPasswordEncoder.encode(password));
            userRepository.save(newUser);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", "User has been created successfully.");
            //jsonObject.addProperty("name",newUser.getName());
            jsonObject.addProperty("email",newUser.getEmail());
            jsonObject.addProperty("password",newUser.getPassword());
            return jsonObject.toString();
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "User already exists.");
        return jsonObject.toString();
    }


    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }
}