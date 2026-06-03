package com.fizoind.stockflow_api.order.listener;

import com.fizoind.stockflow_api.email.EmailService;
import com.fizoind.stockflow_api.order.event.OrderCreatedEvent;
import com.fizoind.stockflow_api.receipt.ReceiptPdfService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderEmailInvoiceListener {

    private final EmailService emailService;
    private final ReceiptPdfService receiptPdfService;

    public OrderEmailInvoiceListener(EmailService emailService, ReceiptPdfService receiptPdfService) {
        this.emailService = emailService;
        this.receiptPdfService = receiptPdfService;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(OrderCreatedEvent event) {
        receiptPdfService.generateReceipt(event.getCustomerOrder()).thenAccept(pdfBytes -> {
            emailService.sendInvoice(event.getCustomerOrder().getCustomer().getEmail(), pdfBytes);
        });
    }
}
