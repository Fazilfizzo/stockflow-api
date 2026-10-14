package com.fizoind.stockflow_api.invoice;

import com.fizoind.stockflow_api.order.entity.CustomerOrder;
import com.fizoind.stockflow_api.orderItem.entity.OrderItem;
import org.openpdf.text.*;
import org.openpdf.text.Font;
import org.openpdf.text.Rectangle;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@Service
public class InvoicePdfService {


        private static final Color PRIMARY =
                new Color(41,128,185);


        private static final Color LIGHT_GRAY =
                new Color(245,245,245);


        @Async("invoiceExecutor")
        public CompletableFuture<byte[]> generateInvoice(
                CustomerOrder order
        ) {


            try {
                ByteArrayOutputStream output = new ByteArrayOutputStream();

                Document document = new Document(PageSize.A4);

                PdfWriter.getInstance(
                        document,
                        output
                );

                document.open();

                addHeader(document);

                addCompanyInformation(document);

                addInvoiceTitle(document);

                addOrderInformation(
                        document,
                        order
                );

                BigDecimal total =
                        addItemsTable(
                                document,
                                order
                        );

                addTotalSection(
                        document,
                        total
                );

                addFooter(document);

                document.close();

                return CompletableFuture.completedFuture(
                        output.toByteArray()
                );

            } catch(Exception e){

                throw new RuntimeException(
                        "Failed to generate receipt PDF",
                        e
                );

            }

        }





        private void addHeader(
                Document document
        ) throws Exception {

            org.openpdf.text.Image logo = org.openpdf.text.Image.getInstance(
                    getClass().getResource(
                            "/logoipsum-417.png"
                    )
            );


            logo.scaleToFit(
                    90,
                    90
            );

            logo.setAlignment(
                    Element.ALIGN_RIGHT
            );

            document.add(logo);

        }


        private void addCompanyInformation(Document document) throws DocumentException {

            org.openpdf.text.Font font = FontFactory.getFont(
                    FontFactory.HELVETICA,
                    10
            );


            document.add(new Paragraph(
                            "StockFlow Inventory System",
                            FontFactory.getFont(
                                    FontFactory.HELVETICA_BOLD,
                                    12
                            )
                    )
            );

            document.add(
                    new Paragraph(
                            "Email: support@stockflow.com",
                            font
                    )
            );

            document.add(new Paragraph(" "));

        }


        private void addInvoiceTitle(Document document) throws DocumentException {

            PdfPTable table = new PdfPTable(1);

            table.setWidthPercentage(100);

            org.openpdf.text.Font font = new org.openpdf.text.Font(
                    org.openpdf.text.Font.HELVETICA,
                    18,
                    org.openpdf.text.Font.BOLD,
                    Color.WHITE
            );

            PdfPCell cell = new PdfPCell(new Phrase(
                    "PAYMENT INVOICE",
                    font
            )
            );

            cell.setBackgroundColor(PRIMARY);

            cell.setHorizontalAlignment(Element.ALIGN_CENTER);

            cell.setPadding(12);

            cell.setBorder(Rectangle.NO_BORDER);

            table.addCell(cell);

            document.add(table);

            document.add(new Paragraph(" "));

        }


        private void addOrderInformation(Document document, CustomerOrder order) throws DocumentException {

            PdfPTable table = new PdfPTable(2);

            table.setWidthPercentage(100);

            addInfoRow(
                    table,
                    "Invoice ID",
                    "#" + order.getId()
            );

            addInfoRow(
                    table,
                    "Customer",
                    order.getCustomer()
                            .getName()
            );

            addInfoRow(
                    table,
                    "Order Date",
                    order.getCreatedAt()
                            .toString()
            );

            addInfoRow(
                    table,
                    "Status",
                    order.getStatus()
                            .name()
            );

            document.add(table);

            document.add(new Paragraph(" "));

        }

        private BigDecimal addItemsTable(Document document, CustomerOrder order) throws DocumentException {

            PdfPTable table = new PdfPTable(4);

            table.setWidthPercentage(100);

            table.setWidths(
                    new float[]{
                            3,
                            1,
                            1.5f,
                            1.5f
                    }
            );

            String[] headers = {"Product", "Qty", "Price", "Total"};

            for(String header: headers){

                PdfPCell cell =
                        new PdfPCell(
                                new Phrase(
                                        header,
                                        FontFactory.getFont(
                                                FontFactory.HELVETICA_BOLD,
                                                10
                                        )
                                )
                        );


                cell.setBackgroundColor(LIGHT_GRAY);

                cell.setPadding(8);

                table.addCell(cell);
            }

            BigDecimal grandTotal = BigDecimal.ZERO;

            for(OrderItem item : order.getOrderItems()){

                BigDecimal itemTotal =
                        item.getProduct()
                                .getPrice()
                                .multiply(
                                        BigDecimal.valueOf(
                                                item.getQuantity()
                                        )
                                );


                table.addCell(
                        item.getProduct()
                                .getName()
                );


                table.addCell(
                        String.valueOf(
                                item.getQuantity()
                        )
                );


                table.addCell(
                        formatMoney(
                                item.getProduct()
                                        .getPrice()
                        )
                );


                table.addCell(
                        formatMoney(
                                itemTotal
                        )
                );


                grandTotal =
                        grandTotal.add(
                                itemTotal
                        );

            }

            document.add(table);

            return grandTotal;

        }

        private void addTotalSection(Document document, BigDecimal total) throws DocumentException {

            Paragraph paragraph =
                    new Paragraph(
                            "TOTAL TO BE PAID: "
                                    +
                                    formatMoney(total),

                            new org.openpdf.text.Font(
                                    org.openpdf.text.Font.HELVETICA,
                                    14,
                                    org.openpdf.text.Font.BOLD
                            )
                    );


            paragraph.setAlignment(Element.ALIGN_RIGHT);

            document.add(paragraph);

            document.add(new Paragraph(" "));
        }


        private void addFooter(Document document) throws DocumentException {

            Paragraph footer =
                    new Paragraph(
                            "Thank you for choosing StockFlow.\n"
                                    +
                                    "This invoice was automatically generated.",
                            new org.openpdf.text.Font(
                                    org.openpdf.text.Font.HELVETICA,
                                    9,
                                    Font.ITALIC
                            )
                    );


            footer.setAlignment(Element.ALIGN_CENTER);

            document.add(footer);
        }

        private void addInfoRow(PdfPTable table, String key, String value){

            table.addCell(key);
            table.addCell(value);

        }

        private String formatMoney(BigDecimal amount){

            return NumberFormat
                    .getCurrencyInstance(
                            Locale.US
                    )
                    .format(amount);

        }

    }
