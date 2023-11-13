package com.project.ojt.projectojt.service;

import com.project.ojt.projectojt.dto.request.LoginDTO;
import com.project.ojt.projectojt.dto.request.RegisterDTO;
import com.project.ojt.projectojt.entity.User;
import com.project.ojt.projectojt.repository.UserRepo;
import com.project.ojt.projectojt.util.EmailUtil;
import com.project.ojt.projectojt.util.OtpUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class UserService {
    @Autowired
    private OtpUtil otpUtil;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private UserRepo userRepository;

    public String register(RegisterDTO registerDto) {

        // Kiểm tra xem mật khẩu có khớp với nhập lại mật khẩu hay không
        if (!registerDto.getPassword().equals(registerDto.getRePassword())) {
            // Xử lý khi mật khẩu không khớp, ví dụ: đưa ra thông báo lỗi

        }

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
        return "User registration successful";
    }

    public String verifyAccount(String email, String otp) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        if (user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(),
                LocalDateTime.now()).getSeconds() < (1 * 240)) {
            user.setActive(true);
            userRepository.save(user);
            return "OTP verified you can login";
        }
        return "Please regenerate otp and try again";
    }

    public String regenerateOtp(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(email, otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "Email sent... please verify account within 3 minute";
    }



//    public String login(LoginDTO loginDto) {
//        User user = userRepository.findByEmail(loginDto.getEmail())
//                .orElseThrow(
//                        () -> new RuntimeException("User not found with this email: " + loginDto.getEmail()));
//        if (!loginDto.getPassword().equals(user.getPassword())) {
//            return "Password is incorrect";
//        } else if (!user.isActive()) {
//            return "Your account is not verified";
//        }
//        return "Login successful";
//    }
}
