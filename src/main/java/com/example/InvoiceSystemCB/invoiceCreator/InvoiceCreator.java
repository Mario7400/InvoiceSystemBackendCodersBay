package com.example.InvoiceSystemCB.invoiceCreator;

import com.example.InvoiceSystemCB.model.Invoice;
import com.example.InvoiceSystemCB.model.Product;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


@Component
public class InvoiceCreator {
    private String pathTarget;
    private String pathLogo;
    private String nameOwnerCompany;
    private String nameStreetCompany;
    private String nameTownCompany;
    private String phoneNumberCompany;
    private String eMailCompany;
    private String GLN;
    private String IBAN;
    private String BIC;
    private Double UST;
    private Font fontArial;
    private Font fontArialBold;
    private String formattedDate;
    private String formattedDateRegular;
    private String getFormattedDateRegularPlus14Days;

    public InvoiceCreator() {
        this.init();
    }

    private void init() {
        loadConfigurationFromFile("C:\\Users\\brand\\dataInvoiceProject\\invoice_config.txt");
        initializeOtherFields();
    }

    private void loadConfigurationFromFile(String filePath) {
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(line -> {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    switch (key) {
                        case "pathTarget":
                            pathTarget = value;
                            break;
                        case "pathLogo":
                            pathLogo = value;
                            break;
                        case "nameOwnerCompany":
                            nameOwnerCompany = value;
                            break;
                        case "nameStreetCompany":
                            nameStreetCompany = value;
                            break;
                        case "nameTownCompany":
                            nameTownCompany = value;
                            break;
                        case "phoneNumberCompany":
                            phoneNumberCompany = value;
                            break;
                        case "eMailCompany":
                            eMailCompany = value;
                            break;
                        case "GLN":
                            GLN = value;
                            break;
                        case "IBAN":
                            IBAN = value;
                            break;
                        case "BIC":
                            BIC = value;
                            break;
                        case "UST":
                            UST = Double.valueOf(value);
                            break;
                        default:
                            break;
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeOtherFields() {
        fontArial = FontFactory.getFont("Arial", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED, 10, Font.NORMAL);
        fontArialBold = FontFactory.getFont("Arial", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED, 10, Font.BOLD);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formattedDate = currentDate.format(formatter);
        DateTimeFormatter formatterRegular = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        formattedDateRegular = currentDate.format(formatterRegular);
        LocalDate datePlus14Days = currentDate.plusDays(14);
        getFormattedDateRegularPlus14Days = datePlus14Days.format(formatterRegular);
    }

    public void createPDF(Invoice invoice) throws IOException, DocumentException {
        String invoiceNumber = invoice.getInvoiceNumber();
        String companyName = invoice.getCustomer().getCompanyName();
        String customerName = invoice.getCustomer().getName();
        String customerSecondName = invoice.getCustomer().getSecondName();
        String street = invoice.getCustomer().getStreet();
        String streetNumber = invoice.getCustomer().getStreetNr();
        String plz = invoice.getCustomer().getTown().getPlz();
        String town = invoice.getCustomer().getTown().getTown();

        List<String> productNames = getProductNames(invoice);
        List<Integer> amountOfSoldProducts = invoice.getAmountOfSoldProduct();
        List<Double> productPrices = getProductPrices(invoice);
        List<Double> realSellingPrices = invoice.getRealSellingPrices();

        String pdfFileName = pathTarget + invoiceNumber + "_" + formattedDate + " " + invoice.getCustomer().getName() + " " + invoice.getCustomer().getSecondName() + ".pdf";
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFileName));
        Footer footer = new Footer(nameOwnerCompany, GLN);
        writer.setPageEvent(footer);

        document.open();

        List<PdfSection> sections = new ArrayList<>();
        sections.add(new HeaderSection(pathLogo, nameOwnerCompany, nameStreetCompany, nameTownCompany, phoneNumberCompany, eMailCompany, fontArialBold));
        sections.add(new InvoiceDetailsSection(companyName, customerName, customerSecondName, street, streetNumber, plz, town, invoiceNumber, formattedDateRegular, getFormattedDateRegularPlus14Days, GLN, fontArialBold, fontArial));
        sections.add(new ProductTableSection(invoiceNumber, productNames, amountOfSoldProducts, productPrices, realSellingPrices, fontArial, fontArialBold, UST));
        sections.add(new PaymentTermsSection(nameOwnerCompany, IBAN, BIC, fontArialBold, fontArial));

        for (PdfSection section : sections) {
            section.addSection(document);
        }

        footer.setTotalPages(writer.getPageNumber());

        document.close();
    }

    private List<String> getProductNames(Invoice invoice) {
        List<String> productNames = new ArrayList<>();
        for (Product product : invoice.getProducts()) {
            productNames.add(product.getName());
        }
        return productNames;
    }

    private List<Double> getProductPrices(Invoice invoice) {
        List<Double> productPrices = new ArrayList<>();
        for (Product product : invoice.getProducts()) {
            productPrices.add(product.getPrice());
        }
        return productPrices;
    }
}
