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
    public void handle(
            PaymentCompletedEvent event
    ) {


        receiptPdfService
                .generateReceipt(
                        event.getOrder()
                )
                .thenAccept(pdfBytes -> {


                    emailService.sendInvoice(
                            event.getOrder()
                                    .getCustomer()
                                    .getEmail(),

                            pdfBytes
                    );

                });

    }

}
