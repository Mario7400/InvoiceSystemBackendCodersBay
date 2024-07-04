package com.example.InvoiceSystemCB.invoiceCreator;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import java.io.IOException;

public interface PdfSection {
    void addSection(Document document) throws DocumentException, IOException;
}
