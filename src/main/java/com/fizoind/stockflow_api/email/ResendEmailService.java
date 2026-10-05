package com.fizoind.stockflow_api.email;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.Attachment;
import com.resend.services.emails.model.CreateEmailOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResendEmailService {

    private final Resend resend;

    @Value("${resend.from}")
    private String from;


    public void sendEmail(
            String to,
            String subject,
            String html,
            Attachment attachment
    ) throws ResendException {

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from(from)
                .to(to)
                .subject(subject)
                .html(html)
                .attachments(attachment)
                .build();

        resend.emails().send(params);
    }
}