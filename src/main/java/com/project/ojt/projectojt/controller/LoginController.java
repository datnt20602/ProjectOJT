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
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    OtpUtil otpUtil;

    @Autowired
    EmailUtil emailUtil;



    private final UserRepo userRepo;

    public LoginController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/login")
    public ModelAndView getLogin() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("loginDto", new LoginDTO());
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView postLogin(@ModelAttribute("loginDto") LoginDTO loginDTO,
                                  HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("login");
        User user = userRepo.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (user == null) {
            modelAndView.addObject("loginError", "Tài khoản hoặc mật khẩu không đúng!");
        } else if (!user.isActive()) {
            modelAndView.addObject("loginError", "Chưa active, vui lòng check mail active tài khoản");
            String otp = otpUtil.generateOtp();
            try {
                emailUtil.sendOtpEmail(loginDTO.getEmail(), otp);
            } catch (MessagingException e) {
                throw new RuntimeException("Unable to send otp please try again");
            }
            user.setOtp(otp);
            user.setOtpGeneratedTime(LocalDateTime.now());
            userRepo.save(user);
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            modelAndView.addObject("loginError", "Đăng nhập thành công");
            modelAndView = new ModelAndView("redirect:/home");
        }
        return modelAndView;
    }
//    @PutMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginDTO loginDto) {
//        return new ResponseEntity<>(userService.login(loginDto), HttpStatus.OK);
//    }
}
