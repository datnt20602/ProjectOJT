package com.project.ojt.projectojt.controller;

import com.project.ojt.projectojt.dto.request.LoginDTO;
import com.project.ojt.projectojt.dto.request.RegisterDTO;

import com.project.ojt.projectojt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public ModelAndView getRegister() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("registerDto", new RegisterDTO());
        return modelAndView;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register (@ModelAttribute("registerDto") RegisterDTO registerDTO){

        return new ResponseEntity<>(userService.register(registerDTO), HttpStatus.OK);
    }



    @GetMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestParam String email, @RequestParam String otp){
        return new ResponseEntity<>(userService.verifyAccount(email,otp), HttpStatus.OK);
    }

    @GetMapping("/regenerate-otp")
    public ResponseEntity<String> regenerateOtp(@RequestParam String email){
        return new ResponseEntity<>(userService.regenerateOtp(email), HttpStatus.OK);
    }


}
