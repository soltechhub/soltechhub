package dev.simplesolution.pdf.service;

import com.lowagie.text.DocumentException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

public interface PdfGenerateService {
    ByteArrayInputStream generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName);
    String createPdf(String templatename, Map map)throws IOException, DocumentException;
}