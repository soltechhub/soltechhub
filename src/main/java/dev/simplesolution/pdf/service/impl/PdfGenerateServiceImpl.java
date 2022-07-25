package dev.simplesolution.pdf.service.impl;

import com.lowagie.text.DocumentException;
import dev.simplesolution.pdf.service.PdfGenerateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;

@Service
public class PdfGenerateServiceImpl implements PdfGenerateService {
    private Logger logger = LoggerFactory.getLogger(PdfGenerateServiceImpl.class);

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${pdf.directory}")
    private String pdfDirectory;



    public String createPdf(String templatename, Map map) throws IOException, DocumentException  {

        String fileNameUrl = "";

        Context ctx = new Context();

        if (map != null) {

            Iterator itMap = map.entrySet().iterator();

            while (itMap.hasNext()) {

                Map.Entry pair = (Map.Entry) itMap.next();

                ctx.setVariable(pair.getKey().toString(), pair.getValue());

            }

        }

        String processedHtml = templateEngine.process(templatename, ctx);

        FileOutputStream os = null;

        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate localDate = LocalDate.now();
            String pdfFileName="payment_slip_"+localDate;
            final File outputFile = File.createTempFile(pdfFileName, ".pdf");

            os = new FileOutputStream(outputFile);

            ITextRenderer itr = new ITextRenderer();

            itr.setDocumentFromString(processedHtml);

            itr.layout();

            itr.createPDF(os, false);

            itr.finishPDF();

            fileNameUrl = outputFile.getName();

        }

        finally {

            if (os != null) {

                try {

                    os.close();

                } catch (IOException e) { }

            }

        }

        return fileNameUrl;

    }






    @Override
    public ByteArrayInputStream generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName) {
        ByteArrayInputStream byteArrayInputStream = null;

        Context context = new Context();
        context.setVariables(data);

        String htmlContent = templateEngine.process(templateName, context);
        try {
           // FileOutputStream fileOutputStream = new FileOutputStream(pdfDirectory + pdfFileName);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
           // renderer.createPDF(fileOutputStream, false);
            renderer.createPDF(byteArrayOutputStream,false);

            renderer.finishPDF();
            byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        }
//        catch (FileNotFoundException e) {
//            logger.error(e.getMessage(), e);
//        }
        catch (DocumentException e) {
            logger.error(e.getMessage(), e);
        }
        return byteArrayInputStream;
    }
}