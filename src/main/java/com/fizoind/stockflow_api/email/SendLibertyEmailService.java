package com.fizoind.stockflow_api.email;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class SendLibertyEmailService {

    @Value("${sendLiberty.api-key}")
    private String sendLibertyKey;

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendEmail(String filename, String toEmail, String subject, String messageHtml, String base64Attachment) {
        try {
            // 1. Create the root JSON object
            ObjectNode payloadJson = objectMapper.createObjectNode();
            payloadJson.put("from", "StockFlow <fadhilimajala97@gmail.com>");
            payloadJson.put("to", toEmail);
            payloadJson.put("subject", subject);
            payloadJson.put("html", "<p>Hello " + ",</p><p>" + messageHtml + "</p>");

            // 2. Create the attachments array and object
            ArrayNode attachmentsArray = payloadJson.putArray("attachments");
            ObjectNode attachmentObj = attachmentsArray.addObject();
            attachmentObj.put("filename", filename);
            attachmentObj.put("content", base64Attachment);

            // 3. Convert the JSON object to a String payload
            String payload = objectMapper.writeValueAsString(payloadJson);

            // 4. Build and send the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://sendliberty.com/api/send"))
                    .header("Authorization", sendLibertyKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());
            response.body();

        } catch (Exception e) {
            throw new RuntimeException("Error occurred" + e.getMessage());
        }
    }
}