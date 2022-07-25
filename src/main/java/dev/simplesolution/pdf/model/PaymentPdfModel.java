package dev.simplesolution.pdf.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class PaymentPdfModel implements Serializable {

    private String supportName;
    private String mobileNumber;
    private String address;
}
