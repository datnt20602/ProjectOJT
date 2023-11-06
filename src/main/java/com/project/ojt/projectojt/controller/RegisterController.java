package com.project.ojt.projectojt.controller;

import com.project.ojt.projectojt.dto.request.LoginDTO;
import com.project.ojt.projectojt.dto.request.RegisterDTO;

import com.project.ojt.projectojt.entity.User;
import com.project.ojt.projectojt.repository.UserRepo;
import com.project.ojt.projectojt.service.UserService;
import com.project.ojt.projectojt.util.EmailUtil;
import com.project.ojt.projectojt.util.OtpUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@RestController
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private OtpUtil otpUtil;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private UserRepo userRepository;

    @GetMapping("/register")
    public ModelAndView getRegister() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("registerDto", new RegisterDTO());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register (@ModelAttribute("registerDto") RegisterDTO registerDto,
                                  HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("register");
        // Kiểm tra xem mật khẩu có khớp với nhập lại mật khẩu hay không
        if (!registerDto.getPassword().equals(registerDto.getRePassword())) {
            // Xử lý khi mật khẩu không khớp, ví dụ: đưa ra thông báo lỗi
            modelAndView.addObject("registerError", "Nhập lại mk không khớp!");
        } else {
            String otp = otpUtil.generateOtp();
            try {
                emailUtil.sendOtpEmail(registerDto.getEmail(), otp);
            } catch (MessagingException e) {
                throw new RuntimeException("Unable to send otp please try again");
            }
            User user = new User();
            user.setName(registerDto.getName());
            user.setEmail(registerDto.getEmail());
            user.setPassword(registerDto.getPassword());
            user.setOtp(otp);
            user.setOtpGeneratedTime(LocalDateTime.now());
            userRepository.save(user);
        }
        return modelAndView;
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
