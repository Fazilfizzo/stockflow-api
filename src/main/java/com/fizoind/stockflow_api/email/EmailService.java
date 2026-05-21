package com.fizoind.stockflow_api.email;

import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

    @Async("emailExecutor")
    public void sendInvoice(String to, byte[] pdfBytes) {

        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setFrom("f4194257@gmail.com", "Stockflow");
            helper.setTo(to);
            helper.setSubject("Stockflow Invoice");

            helper.setText("Your invoice is attached.");

            helper.addAttachment(
                    "invoice.pdf",
                    new ByteArrayResource(pdfBytes), "application/pdf"
            );

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
