package com.example.InvoiceSystemCB.invoiceCreator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class InvoiceDetailsSection implements PdfSection {
    private final String companyName;
    private final String firstName;
    private final String secondName;
    private final String street;
    private final String streetNr;
    private final String plz;
    private final String town;
    private final String invoiceNumber;
    private final String formattedDateRegular;
    private final String getFormattedDateRegularPlus14Days;
    private final String GLN;
    private final Font fontArialBold;
    private final Font fontArial;

    public InvoiceDetailsSection(String companyName, String firstName, String secondName, String street, String streetNr,
                                 String plz, String town, String invoiceNumber, String formattedDateRegular,
                                 String getFormattedDateRegularPlus14Days, String GLN,
                                 Font fontArialBold, Font fontArial) {
        this.companyName = companyName;
        this.firstName = firstName;
        this.secondName = secondName;
        this.street = street;
        this.streetNr = streetNr;
        this.plz = plz;
        this.town = town;
        this.invoiceNumber = invoiceNumber;
        this.formattedDateRegular = formattedDateRegular;
        this.getFormattedDateRegularPlus14Days = getFormattedDateRegularPlus14Days;
        this.GLN = GLN;
        this.fontArialBold = fontArialBold;
        this.fontArial = fontArial;
    }

    @Override
    public void addSection(Document document) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1, 2});

        PdfPCell recipientCell = new PdfPCell();
        recipientCell.setBorder(Rectangle.NO_BORDER);
        recipientCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        recipientCell.setVerticalAlignment(Element.ALIGN_TOP);

        Paragraph recipientParagraph = new Paragraph();
        recipientParagraph.add(new Phrase("Rechnungsempfänger:\n", fontArialBold));
        if (!companyName.isEmpty()) {
            recipientParagraph.add(new Phrase(companyName + "\n", fontArial));
        }
        recipientParagraph.add(new Phrase(firstName + " " + secondName + "\n", fontArial));
        recipientParagraph.add(new Phrase(street + " " + streetNr + "\n", fontArial));
        recipientParagraph.add(new Phrase(plz + " " + town, fontArial));

        recipientCell.addElement(recipientParagraph);
        table.addCell(recipientCell);

        PdfPCell invoiceCell = new PdfPCell();
        invoiceCell.setBorder(Rectangle.NO_BORDER);
        invoiceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        invoiceCell.setVerticalAlignment(Element.ALIGN_TOP);

        Paragraph invoiceParagraph = new Paragraph();
        invoiceParagraph.setAlignment(Element.ALIGN_RIGHT);
        invoiceParagraph.add(new Phrase("Rechnungsnummer: " + invoiceNumber + "\n", fontArial));
        invoiceParagraph.add(new Phrase("Rechnungsdatum: " + formattedDateRegular + "\n", fontArial));
        invoiceParagraph.add(new Phrase("Fälligkeitsdatum: " + getFormattedDateRegularPlus14Days + "\n", fontArial));
        invoiceParagraph.add(new Phrase("GLN: " + GLN + "\n", fontArial));
        invoiceParagraph.add(new Phrase("Liefer-/Leistungszeitraum: " + formattedDateRegular + " - " + getFormattedDateRegularPlus14Days, fontArial));

        invoiceCell.addElement(invoiceParagraph);
        table.addCell(invoiceCell);

        PdfPCell emptyCell = new PdfPCell(new Phrase(""));
        emptyCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(emptyCell);

        document.add(table);
    }
}
