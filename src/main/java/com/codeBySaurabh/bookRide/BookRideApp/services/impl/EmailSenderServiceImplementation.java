package com.codeBySaurabh.bookRide.BookRideApp.services.impl;

import com.codeBySaurabh.bookRide.BookRideApp.services.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderServiceImplementation implements EmailSenderService {
    private final JavaMailSender javaMailSender;
    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage mailMessage=new SimpleMailMessage();

            mailMessage.setTo(toEmail);
            mailMessage.setSubject(subject);
            mailMessage.setText(body);

            javaMailSender.send(mailMessage);
            log.info("Email sent successfully");

        }catch (Exception e){
            log.info("Cannot send emil "+e.getMessage());

        }


    }

    @Override
    public void sendEmail(String[] toEmail, String subject, String body) {
        try {
            SimpleMailMessage mailMessage=new SimpleMailMessage();

            mailMessage.setTo("saurabhmohite255@gmail.com");
            mailMessage.setBcc(toEmail);
            mailMessage.setSubject(subject);
            mailMessage.setText(body);

            javaMailSender.send(mailMessage);
            log.info("Email sent successfully");

        }catch (Exception e){
            log.info("Cannot send emil "+e.getMessage());

        }
    }
}
