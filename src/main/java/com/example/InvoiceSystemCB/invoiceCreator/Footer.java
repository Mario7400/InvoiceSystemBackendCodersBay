package com.example.InvoiceSystemCB.invoiceCreator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class Footer extends PdfPageEventHelper {
    Font fontArial = FontFactory.getFont("Arial", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED, 8, Font.NORMAL);

    private int totalPages;
    private final String owner;
    private final String GLN;

    public Footer(String nameOwnerCompany, String gln) {
        this.owner = nameOwnerCompany;
        this.GLN = gln;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfPTable footerTable = new PdfPTable(1);
        footerTable.setWidthPercentage(100);

        PdfPCell cell;

        cell = new PdfPCell(new Phrase(owner+" | "+"GLN: "+GLN, fontArial));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        footerTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Seite " + writer.getPageNumber() + " von " + totalPages, fontArial));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        footerTable.addCell(cell);

        footerTable.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
        footerTable.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(), writer.getDirectContent());
    }
}
