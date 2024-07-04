package com.example.InvoiceSystemCB.invoiceCreator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class PaymentTermsSection implements PdfSection {
    private final String nameOwnerCompany;
    private final String IBAN;
    private final String BIC;
    private final Font fontArialBold;
    private final Font fontArial;

    public PaymentTermsSection(String nameOwnerCompany, String IBAN, String BIC, Font fontArialBold, Font fontArial) {
        this.nameOwnerCompany = nameOwnerCompany;
        this.IBAN = IBAN;
        this.BIC = BIC;
        this.fontArialBold = fontArialBold;
        this.fontArial = fontArial;
    }

    @Override
    public void addSection(Document document) throws DocumentException {
        PdfPTable paymentTable = new PdfPTable(1);
        paymentTable.setWidthPercentage(100);
        paymentTable.setHorizontalAlignment(Element.ALIGN_LEFT);

        PdfPCell cell = createCell("Zahlungsbedingung", fontArialBold);
        cell.setBorder(Rectangle.TOP);
        paymentTable.addCell(cell);

        paymentTable.addCell(createCell("Bitte überweisen Sie den ausstehenden Betrag innerhalb von 14 Tagen ab Rechnungsdatum.", fontArial));
        paymentTable.addCell(createCell(" ", fontArialBold));
        paymentTable.addCell(createCell("    Umsatzsteuerbefreit gemäß § 6 Abs. 1 Z 27 UStG 1994.", fontArial));
        paymentTable.addCell(createCell("    (= umsatzsteuerbefreit aufgrund Kleinunternehmerregelung)", fontArial));
        paymentTable.addCell(createCell(" ", fontArialBold));
        paymentTable.addCell(createCell("    Unsere Kontoinformation:", fontArialBold));
        paymentTable.addCell(createCell("    Institut: Raiffeisenbank Pregarten", fontArial));
        paymentTable.addCell(createCell("    Inhaber: " + nameOwnerCompany, fontArial));
        paymentTable.addCell(createCell("    IBAN: " + IBAN, fontArial));
        paymentTable.addCell(createCell("    BIC: " + BIC, fontArial));
        paymentTable.addCell(createCell(" ", fontArialBold));

        document.add(paymentTable);
    }

    private PdfPCell createCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}
