package com.anonymous.chat.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OTPService {
    @Autowired
    private JavaMailSender mailSender;

    private Map<String, String> otpStorage = new HashMap<>();

    public String generateOTP(String email) {
        String otp = String.format("%04d", new Random().nextInt(10000)); // Generate 4-digit OTP
        otpStorage.put(email, otp);
        sendOTPEmail(email, otp);
        return otp;
    }

    private void sendOTPEmail(String email, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Your OTP Code");
            message.setText("Your OTP is: " + otp);

            // Log sending
            System.out.println("Sending OTP to: " + email + " | OTP: " + otp);

            mailSender.send(message);
            System.out.println("✅ OTP email sent successfully!");
        } catch (MailException e) {
            System.err.println("❌ Error sending OTP email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean validateOTP(String email, String otp) {
        return otpStorage.containsKey(email) && otpStorage.get(email).equals(otp);
    }
}

