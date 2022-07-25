package dev.simplesolution.pdf.controller;

import dev.simplesolution.pdf.entity.Customer;
import dev.simplesolution.pdf.entity.QuoteItem;
import dev.simplesolution.pdf.model.PaymentPdfModel;
import dev.simplesolution.pdf.service.PdfGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PaymentPdfController {
    @Autowired
    private PdfGenerateService pdfGenerateService;



    @GetMapping("payment/pdf/{supportName}")

    public ResponseEntity generatePdf(@PathVariable String supportName){

        Map<String,Object> paymentPdfMap = new HashMap<>();

        paymentPdfMap.put("supportName","Amit");

        paymentPdfMap.put("address","Delhi");

        paymentPdfMap.put("mobileNumber","1234567899");

        paymentPdfMap.put("amountPaid","20,000");


        Resource resource = null;

        try {

            String property = "java.io.tmpdir";

            String tempDir = System.getProperty(property);

            String fileNameUrl = pdfGenerateService.createPdf("soltechhub-salary-slip", paymentPdfMap);

            Path path = Paths.get(tempDir+"/" + fileNameUrl);

            resource = new UrlResource(path.toUri());



        } catch (Exception e) {

            e.printStackTrace();

        }

        return ResponseEntity.ok()

                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE))

                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")

                .body(resource);

    }


    @GetMapping("/generate/payment/pdf")
    public void generatePdf( HttpServletResponse response) throws IOException {
        Map<String, Object> data = new HashMap<>();
        Customer customer = new Customer();
        customer.setCompanyName("Simple Solution");
        customer.setContactName("John Doe");
        customer.setAddress("123, Simple Street");
        customer.setEmail("john@simplesolution.dev");
        customer.setPhone("123 456 789");
        data.put("customer", customer);

        List<QuoteItem> quoteItems = new ArrayList<>();
        QuoteItem quoteItem1 = new QuoteItem();
        quoteItem1.setDescription("Test Quote Item 1");
        quoteItem1.setQuantity(1);
        quoteItem1.setUnitPrice(100.0);
        quoteItem1.setTotal(100.0);
        quoteItems.add(quoteItem1);

        QuoteItem quoteItem2 = new QuoteItem();
        quoteItem2.setDescription("Test Quote Item 2");
        quoteItem2.setQuantity(4);
        quoteItem2.setUnitPrice(500.0);
        quoteItem2.setTotal(2000.0);
        quoteItems.add(quoteItem2);

        QuoteItem quoteItem3 = new QuoteItem();
        quoteItem3.setDescription("Test Quote Item 3");
        quoteItem3.setQuantity(2);
        quoteItem3.setUnitPrice(200.0);
        quoteItem3.setTotal(400.0);
        quoteItems.add(quoteItem3);

        data.put("quoteItems", quoteItems);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        String pdfFileName="payment_slip_"+localDate;
        ByteArrayInputStream exportedData=pdfGenerateService.generatePdfFile("quotation", data, pdfFileName+".pdf");


       // ByteArrayInputStream exportedData = exportPdfService.exportReceiptPdf("receipt", data);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename="+pdfFileName+".pdf");
        IOUtils.copy(exportedData, response.getOutputStream());
        //return ResponseEntity.ok("Pdf generated succesfully");
    }
}
