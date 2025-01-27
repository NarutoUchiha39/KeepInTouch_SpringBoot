package com.BootProject.Project.Classes;

public interface EmailInterface {

    String sendEmail(EmailDetails emailDetails);
    String sendEmailWithAttachment(EmailDetails emailDetails);
}
