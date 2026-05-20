package com.fizoind.stockflow_api.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async("emailExecutor")
    public void sendOrderConfirmation(String to, String customerName, Long orderId) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(to);
        mailMessage.setSubject("Order confirmation");
        mailMessage.setText(
                "Hello " + customerName + ",\n\n" + "Your order has been placed successfully. \n" +
                        "Order ID: " + orderId + "\n\n" +
                        "Thanks for using our app."
        );

        mailSender.send(mailMessage);
    }
}
