package com.BootProject.Project.Config;


import com.BootProject.Project.Classes.EmailDetails;
import com.BootProject.Project.Classes.EmailInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailInterface {

    @Autowired
    JavaMailSender mailSender;
    @Override
    public String sendEmail(EmailDetails emailDetails) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setText(emailDetails.getBody());
            message.setTo(emailDetails.getTo());
            message.setSubject(emailDetails.getSubject());
            mailSender.send(message);
            return "Email Sent Successfully";
        }catch (Exception e){
            return "Cannot send mail";
        }
    }

    @Override
    public String sendEmailWithAttachment(EmailDetails emailDetails) {
        return "";
    }
}
