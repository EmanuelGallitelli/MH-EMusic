package com.emusic.school.services;

import com.emusic.school.dtos.MerchTicketDTO;
import com.emusic.school.models.Ticket;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PDFGeneratorService {


    @Autowired
    MerchService merchService;
    @CrossOrigin
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.black);
        cell.setPadding(4);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.WHITE);

//        cell.setPhrase(new Phrase("ID PRODUCT", font));
//
//        table.addCell(cell);

        cell.setPhrase(new Phrase("QUANTITY", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("NAME", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("PRICE FOR UNIT", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("AMOUNT", font));
        table.addCell(cell);
    }
    private void writeTableData(PdfPTable table, Ticket ticket) throws BadElementException {
        Font fontCells = FontFactory.getFont(FontFactory.TIMES);
        fontCells.setSize(14);
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.pink);

        ticket.getPurchaseOrder().forEach(order -> {
            cell.setPhrase(new Phrase(String.valueOf(order.getQuantity()), fontCells));
            table.addCell(cell);

            cell.setPhrase(new Phrase(order.getMerch().getType(), fontCells));
            table.addCell(cell);

            cell.setPhrase(new Phrase("$" + order.getMerch().getPrice(), fontCells));
            table.addCell(cell);

            cell.setPhrase(new Phrase("$" +(order.getQuantity() * order.getMerch().getPrice()), fontCells));
            table.addCell(cell);

//            table.addCell(String.valueOf(order.getQuantity()));
//            table.addCell(order.getMerch().getType());
//            table.addCell(String.valueOf(order.getMerch().getPrice()));
//            table.addCell(String.valueOf(order.getQuantity() * order.getMerch().getPrice()));
        });
        ticket.getCourseTickets().forEach(courseTicket -> {
            cell.setPhrase(new Phrase("1", fontCells));
            table.addCell(cell);
            cell.setPhrase(new Phrase("Curse:"+ courseTicket.getCourse().getName(), fontCells));
            table.addCell(cell);
            cell.setPhrase(new Phrase("$" + courseTicket.getCourse().getPrice(), fontCells));
            table.addCell(cell);
            cell.setPhrase(new Phrase("$" + courseTicket.getCourse().getPrice(), fontCells));
            table.addCell(cell);


            //            table.addCell(String.valueOf(1));
//            table.addCell( "CURSE:"+ courseTicket.getCourse().getName());
//            table.addCell(String.valueOf(courseTicket.getCourse().getPrice()));
//            table.addCell(String.valueOf(courseTicket.getCourse().getPrice()));
        });
        Font fontTotal = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTotal.setSize(13);
        fontTotal.setColor(Color.WHITE);
        Paragraph total = new Paragraph("TOTAL", fontTotal);
        total.setAlignment(Paragraph.ALIGN_CENTER);
        PdfPCell cellTotal = new PdfPCell(total);
        cellTotal.setBackgroundColor(Color.BLACK);
        cellTotal.setColspan(3);
        table.addCell(cellTotal);

//
//        table.addCell(total);
//        table.addCell("");
//        table.addCell("");
        Paragraph totalNumber = new Paragraph("$" + (ticket.getTotalPrice()), fontTotal);
        PdfPCell cellTotalNumber = new PdfPCell(totalNumber);
        cellTotalNumber.setBackgroundColor(Color.BLACK);
        table.addCell(cellTotalNumber);



//            table.addCell(String.valueOf(user.getId()));
//            table.addCell(user.getEmail());
//            table.addCell(user.getFullName());
//            table.addCell(user.getRoles().toString());
//            table.addCell(String.valueOf(user.isEnabled()));
//
    }

    public void export(HttpServletResponse response, Ticket ticket) throws IOException, DocumentException, URISyntaxException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Path path = Paths.get(ClassLoader.getSystemResource("../resources/static/assets/logo-01.png").toURI());
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        img.scalePercent(20f);
        img.setAlignment(Element.ALIGN_CENTER);
        document.add(img);
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(25);

        fontTitle.setColor(Color.black);
        Paragraph paragraph1 = new Paragraph("E-MusicSchool", fontTitle);
        paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
//        String imageFile = "../resources/static/assets/logo-01.png";
//        ImageData data = ImageDataFactory.create(imageFile);
        document.add(paragraph1);

        Font fontTicket = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTicket.setSize(20);
        Paragraph paragraph = new Paragraph("Ticket #" + ticket.getId(), fontTicket);
        paragraph.setAlignment(Paragraph.ALIGN_LEFT);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
//        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(8);
        writeTableHeader(table);
        writeTableData(table, ticket);

        document.add(paragraph);
        document.add(new Paragraph(""));
        document.add(new Paragraph(""));
        document.add(table);
        document.close();
    }
}
