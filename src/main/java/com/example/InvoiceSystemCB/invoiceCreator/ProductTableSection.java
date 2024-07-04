package com.example.InvoiceSystemCB.invoiceCreator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.util.List;


public class ProductTableSection implements PdfSection{
    private final String invoiceNumber;
    private final List<String> productNames;
    private final List<Integer> amountOfSoldProduct;
    private final List<Double> productPrices;
    private final List<Double> sellingPrices;
    private final Font fontArial;
    private final Font fontArialBold;
    private final Double UST;

    public ProductTableSection(String invoiceNumber, List<String> productNames, List<Integer> amountOfSoldProduct, List<Double> productPrices,
                               List<Double> sellingPrices, Font fontArial, Font fontArialBold, Double UST) {
        this.invoiceNumber = invoiceNumber;
        this.productNames = productNames;
        this.amountOfSoldProduct = amountOfSoldProduct;
        this.productPrices = productPrices;
        this.sellingPrices = sellingPrices;
        this.fontArial = fontArial;
        this.fontArialBold = fontArialBold;
        this.UST = UST;
    }

    @Override
    public void addSection(Document document) throws DocumentException {
        boolean showDiscountAndSellingPrice = checkPriceDifferences(productPrices, sellingPrices);

        int columns = showDiscountAndSellingPrice ? 7 : 5;
        PdfPTable table = new PdfPTable(columns);
        table.setWidthPercentage(100);
        if (showDiscountAndSellingPrice) {
            table.setWidths(new float[]{1, 3, 2, 2, 2, 2, 2});
        } else {
            table.setWidths(new float[]{1, 8, 2, 2, 2});
        }

        Paragraph title = new Paragraph();
        title.setAlignment(Element.ALIGN_LEFT);
        title.add(new Phrase("\n\n\nRechnung ", fontArialBold));
        title.add(new Phrase(invoiceNumber, fontArial));
        document.add(title);

        document.add(new Paragraph("\n"));

        addTableHeaders(table, showDiscountAndSellingPrice);

        double sumOfNetPrices = 0;

        for (int i = 0; i < productNames.size(); i++) {
            addTableRow(table, i, productNames.get(i), amountOfSoldProduct.get(i), productPrices.get(i), sellingPrices.get(i), showDiscountAndSellingPrice);
            double netPrice = sellingPrices.get(i) * amountOfSoldProduct.get(i);
            sumOfNetPrices += netPrice;
        }
        document.add(table);

        document.add(new Paragraph("\n\n"));

        addSummaryTable(document, sumOfNetPrices);
    }

    private void addTableHeaders(PdfPTable table, boolean showDiscountAndSellingPrice) {
        PdfPCell headerCell;
        headerCell = new PdfPCell(new Phrase("Nr.", fontArialBold));
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        headerCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(headerCell);

        headerCell = new PdfPCell(new Phrase("Bezeichnung", fontArialBold));
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        headerCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(headerCell);

        headerCell = new PdfPCell(new Phrase("Menge", fontArialBold));
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        headerCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(headerCell);

        if (showDiscountAndSellingPrice) {
            headerCell = new PdfPCell(new Phrase("Listenpreis [€]", fontArialBold));
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            headerCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(headerCell);

            headerCell = new PdfPCell(new Phrase("Rabatt [%]", fontArialBold));
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            headerCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(headerCell);

            headerCell = new PdfPCell(new Phrase("Preis [€]", fontArialBold));
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            headerCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(headerCell);
        } else {
            headerCell = new PdfPCell(new Phrase("Preis [€]", fontArialBold));
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            headerCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(headerCell);
        }

        headerCell = new PdfPCell(new Phrase("Netto [€]", fontArialBold));
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        headerCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(headerCell);
    }

    private void addTableRow(PdfPTable table, int index, String productName, int amount, double productPrice, double sellingPrice, boolean showDiscountAndSellingPrice) {
        PdfPCell cell;

        cell = new PdfPCell(new Phrase(String.valueOf(index + 1), fontArial));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(productName, fontArial));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(String.valueOf(amount), fontArial));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(String.format("%.2f", productPrice), fontArial));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        if (showDiscountAndSellingPrice) {
            double discount = 100 - (sellingPrice / productPrice) * 100;
            cell = new PdfPCell(new Phrase(String.format("%.2f", discount), fontArial));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.format("%.2f", sellingPrice), fontArial));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
        }

        double netPrice = sellingPrice * amount;
        cell = new PdfPCell(new Phrase(String.format("%.2f", netPrice), fontArial));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
    }

    private void addSummaryTable(Document document, double sumOfNetPrices) throws DocumentException {
        PdfPTable summaryTable = new PdfPTable(4);
        summaryTable.setWidthPercentage(100);
        summaryTable.setWidths(new float[]{10, 2, 1, 2});
        summaryTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

        summaryTable.addCell(createSummaryCell("", fontArial));
        summaryTable.addCell(createSummaryCell("Nettosumme", fontArialBold));
        summaryTable.addCell(createSummaryCell("EUR", fontArial));
        summaryTable.addCell(createSummaryCell(String.format("%.2f", sumOfNetPrices), fontArial));

        summaryTable.addCell(createSummaryCell("", fontArial));
        summaryTable.addCell(createSummaryCell("Umsatzsteuer", fontArialBold));
        summaryTable.addCell(createSummaryCell(UST.intValue() + "%", fontArial));
        summaryTable.addCell(createSummaryCell(String.format("%.2f", sumOfNetPrices * (UST / 100)), fontArial));

        summaryTable.addCell(createSummaryCell("", fontArial));
        summaryTable.addCell(createSummaryCell("Bruttosumme", fontArialBold));
        summaryTable.addCell(createSummaryCell("EUR", fontArial));
        summaryTable.addCell(createSummaryCell(String.format("%.2f", sumOfNetPrices * (1 + (UST / 100))), fontArial));

        document.add(summaryTable);
    }

    private PdfPCell createSummaryCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private boolean checkPriceDifferences(List<Double> productPrices, List<Double> sellingPrices) {
        for (int i = 0; i < productPrices.size(); i++) {
            if (!productPrices.get(i).equals(sellingPrices.get(i))) {
                return true;
            }
        }
        return false;
    }
}
