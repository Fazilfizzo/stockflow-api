package com.fizoind.stockflow_api.order.listener;

import com.fizoind.stockflow_api.email.EmailService;
import com.fizoind.stockflow_api.invoice.InvoicePdfService;
import com.fizoind.stockflow_api.order.event.OrderCreatedEvent;
import com.fizoind.stockflow_api.receipt.ReceiptPdfService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderEmailInvoiceListener {

    private final EmailService emailService;
    private final InvoicePdfService invoicePdfService;

    private String filename = "invoice.pdf";
    private String subject = "StockFlow invoice";
    private String html = """
                    <p>Hello,</p> <p>Thank you for your order.</p> <p>Your invoice is attached to this email.</p> <p>Regards,<br>StockFlow</p> 
                    """;

    public OrderEmailInvoiceListener(EmailService emailService, InvoicePdfService invoicePdfService) {
        this.emailService = emailService;
        this.invoicePdfService = invoicePdfService;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(OrderCreatedEvent event) {
        invoicePdfService.generateInvoice(event.getCustomerOrder()).thenAccept(pdfBytes -> {
            emailService.sendInvoice(event.getCustomerOrder().getCustomer().getEmail(), filename, subject, html, pdfBytes);
        });
    }
}
