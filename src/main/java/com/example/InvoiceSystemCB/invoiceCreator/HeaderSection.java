package com.example.InvoiceSystemCB.invoiceCreator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.IOException;

public class HeaderSection implements PdfSection {
    private final String pathLogo;
    private final String nameOwnerCompany;
    private final String nameStreetCompany;
    private final String nameTownCompany;
    private final String phoneNumberCompany;
    private final String eMailCompany;
    private final Font fontArialBold;

    public HeaderSection(String pathLogo, String nameOwnerCompany, String nameStreetCompany, String nameTownCompany,
                         String phoneNumberCompany, String eMailCompany, Font fontArialBold) {
        this.pathLogo = pathLogo;
        this.nameOwnerCompany = nameOwnerCompany;
        this.nameStreetCompany = nameStreetCompany;
        this.nameTownCompany = nameTownCompany;
        this.phoneNumberCompany = phoneNumberCompany;
        this.eMailCompany = eMailCompany;
        this.fontArialBold = fontArialBold;
    }

    @Override
    public void addSection(Document document) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1, 2});

        Image imgLogo = Image.getInstance(pathLogo);
        imgLogo.scaleToFit(170, 170);
        PdfPCell logoCell = new PdfPCell(imgLogo);
        logoCell.setBorder(Rectangle.NO_BORDER);
        logoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(logoCell);

        PdfPCell textCell = new PdfPCell();
        textCell.setBorder(Rectangle.NO_BORDER);
        textCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        textCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        paragraph.add(new Phrase(nameOwnerCompany + "\n", fontArialBold));
        paragraph.add(new Phrase(nameStreetCompany + "\n", fontArialBold));
        paragraph.add(new Phrase(nameTownCompany + "\n", fontArialBold));
        paragraph.add(new Phrase(phoneNumberCompany + "\n", fontArialBold));
        paragraph.add(new Phrase(eMailCompany, fontArialBold));

        textCell.addElement(paragraph);
        table.addCell(textCell);

        PdfPCell emptyCell = new PdfPCell(new Phrase(""));
        emptyCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(emptyCell);

        document.add(table);
        document.add(new Paragraph("\n"));
    }

}
