package com.fizoind.stockflow_api.email;

import com.resend.services.emails.model.Attachment;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Base64;

@Service
public class EmailService {

    @Value("${default.from}")
    String from;

    private final JavaMailSender mailSender;
    private final ResendEmailService resendEmailService;
    private final SendLibertyEmailService sendLibertyEmailService;

    public EmailService(JavaMailSender mailSender, ResendEmailService resendEmailService, SendLibertyEmailService sendLibertyEmailService) {
        this.mailSender = mailSender;
        this.resendEmailService = resendEmailService;
        this.sendLibertyEmailService = sendLibertyEmailService;
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

//    @Async("invoiceExecutor")
//    public void sendEmail(String filename, String subject, String html, String to, byte[] pdfBytes) {
//
//        try {
//
//            String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);
//
//            Attachment attachment = Attachment.builder()
//                    .fileName(filename)
//                    .content(base64Pdf)
//                    .build();
//
//            resendEmailService.sendEmail(to, subject, html, attachment);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Async("emailExecutor")
    public void sendEmail(String filename, String subject, String html, String to, byte[] pdfBytes) {

        try {

            String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

            sendLibertyEmailService.sendEmail(filename, to, subject, html, base64Pdf);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
