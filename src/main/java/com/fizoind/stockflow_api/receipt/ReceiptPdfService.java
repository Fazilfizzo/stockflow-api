package com.fizoind.stockflow_api.receipt;

import com.fizoind.stockflow_api.order.entity.CustomerOrder;
import com.fizoind.stockflow_api.orderItem.entity.OrderItem;
import org.openpdf.text.*;
import org.openpdf.text.Font;
import org.openpdf.text.Image;
import org.openpdf.text.Rectangle;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import org.openpdf.text.pdf.draw.LineSeparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@Service
public class ReceiptPdfService {

        @Async("invoiceExecutor")
        public CompletableFuture<byte[]> generateReceipt(CustomerOrder order) {

            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();

                Document document = new Document(PageSize.A4);
                PdfWriter.getInstance(document, out);

                document.open();

                // ================= HEADER =================
//                Font titleFont = new Font(Font.HELVETICA, 20, Font.BOLD);
                Image logo = Image.getInstance("src/main/resources/logoipsum-417.png");
                logo.scaleToFit(100, 100);
                logo.setAlignment(Element.ALIGN_RIGHT);
                document.add(logo);

//                Paragraph title = new Paragraph("STOCKFLOW INVOICE", titleFont);
//                title.setAlignment(Element.ALIGN_CENTER);
//                document.add(title);

                document.add(new Paragraph(" "));

                // ================= COMPANY INFO =================
                Font small = new Font(Font.HELVETICA, 10);
                document.add(new Paragraph("Stockflow API System", small));
                document.add(new Paragraph("Email: support@stockflow.com", small));
                document.add(new Paragraph(" "));


                // ========== PHRASE ============
                Font titleFont = new Font(Font.HELVETICA, 10, Font.BOLD, Color.WHITE);

                PdfPCell titleCell = new PdfPCell(new Phrase("INVOICE", titleFont));
                titleCell.setBackgroundColor(new Color(41, 128, 185));
                titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                titleCell.setPadding(10);
                titleCell.setBorder(Rectangle.NO_BORDER);
                document.add(titleCell);

                LineSeparator line = new LineSeparator();
                line.setLineColor(new Color(189, 195, 199));
                document.add(line);

                // ================= ORDER INFO TABLE =================
                PdfPTable infoTable = new PdfPTable(2);
                infoTable.setWidthPercentage(90);
                infoTable.setWidths(new float[]{1.5f, 2.5f});

                infoTable.addCell("Invoice ID");

                infoTable.addCell(String.valueOf(order.getId()));

                infoTable.addCell("Date");
                infoTable.addCell(order.getCreatedAt().toString());

                infoTable.addCell("Customer ID");
                infoTable.addCell(String.valueOf(order.getCustomer().getId()));

                document.add(infoTable);

                document.add(new Paragraph(" "));

                // ================= ITEM TABLE =================
                PdfPTable table = new PdfPTable(4);
                table.setWidthPercentage(90);

                // Header row styling
                String[] headers = {"Product", "Quantity", "Price", "Total"};
                Font header_font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, new Color(80, 80, 80));
                Font value_font = FontFactory.getFont(FontFactory.HELVETICA, 11, Color.BLACK);
                for (String h : headers) {
                    PdfPCell cell = new PdfPCell(new Phrase(h, header_font));
                    cell.setBackgroundColor(Color.LIGHT_GRAY);
                    table.addCell(cell);
                }

               BigDecimal grandTotal = BigDecimal.ZERO;

                for (OrderItem item : order.getOrderItems()) {

                    table.addCell(item.getProduct().getName());
                    table.addCell(String.valueOf(item.getQuantity()));
                    table.addCell(String.valueOf(item.getProduct().getPrice()));

                    BigDecimal total = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                    table.addCell(String.valueOf(total));

                    grandTotal = grandTotal.add(total);
                }

                document.add(table);

                document.add(new Paragraph(" "));

                // ================= TOTAL SECTION =================
                Font totalFont = new Font(Font.HELVETICA, 14, Font.BOLD);

                Paragraph total = new Paragraph(
                        "TOTAL AMOUNT: " + grandTotal,
                        totalFont
                );

                total.setAlignment(Element.ALIGN_RIGHT);
                document.add(total);

                document.add(new Paragraph(" "));

                // ================= FOOTER =================
                Font footerFont = new Font(Font.HELVETICA, 9, Font.ITALIC);

                Paragraph footer = new Paragraph(
                        "Thank you for using Stockflow. This is a system-generated invoice.",
                        footerFont
                );

                footer.setAlignment(Element.ALIGN_CENTER);
                document.add(footer);

                document.close();

                return CompletableFuture.completedFuture(out.toByteArray());

            } catch (Exception e) {
                throw new RuntimeException("Failed to generate receipt PDF", e);
            }
        }
    }

