package ua.edu.chmnu.network.java;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RestController
public class EmailSenderController {

    private final JavaMailSender mailSender;

    public EmailSenderController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @RequestMapping("/send-email")
    public String sendEmail(){
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("denvanzhula@gmail.com");
            message.setTo("denvanzhula@gmail.com");
            message.setSubject("Simple text email from Denis Vanzhula");
            message.setText("This is a simple email for the first email!");

            mailSender.send(message);
            return "success!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/send-email-with-attachment")
    public String sendEmailWithAttachment() {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("denvanzhula@gmail.com");
            //helper.setTo("denvanzhula@gmail.com");

            String[] recipients = {"denvanzhula@gmail.com", "denis.vanzhula@gmail.com"};
            helper.setTo(recipients);

            helper.setSubject("Java email with attachment | From Denis Vanzhula");
            helper.setText("Please find the attached documents below");

            helper.addAttachment("presentation.pptx", new File("presentation.pptx"));

            mailSender.send(message);
            return "success!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/send-html-email")
    public String sendHtmlEmail() {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("denvanzhula@gmail.com");
            helper.setTo("denvanzhula@gmail.com");
            helper.setSubject("Java email with attachment | From Denis Vanzhula");

            try (var inputStream = Objects.requireNonNull(EmailSenderController.class.getResourceAsStream("/templates/email-attachment.html"))) {
                helper.setText(
                        new String(inputStream.readAllBytes(), StandardCharsets.UTF_8),
                        true
                );
            }
            mailSender.send(message);
            return "success!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
