package com.fizoind.stockflow_api.payment.listener;


import com.fizoind.stockflow_api.email.EmailService;
import com.fizoind.stockflow_api.payment.event.PaymentCompletedEvent;
import com.fizoind.stockflow_api.receipt.ReceiptPdfService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


@Component
public class PaymentCompletedListener {


    private final EmailService emailService;
    private final ReceiptPdfService receiptPdfService;

    private String filename = "receipt.pdf";
    private String subject = "StockFlow Receipt";
    private String html = """
                    <p>Hello,</p> <p>Thank you for payment.</p> <p>Your receipt is attached to this email.</p> <p>Regards,<br>StockFlow</p> 
                    """;


    public PaymentCompletedListener(
            EmailService emailService,
            ReceiptPdfService receiptPdfService
    ) {

        this.emailService = emailService;
        this.receiptPdfService = receiptPdfService;

    }



    @Async
    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(PaymentCompletedEvent event) {
        receiptPdfService.generateReceipt(event.getOrder())
                .thenAccept(pdfBytes -> {
                    emailService.sendEmail(event.getOrder().getCustomer().getEmail(), filename, subject, html, pdfBytes);
                });

    }

}
