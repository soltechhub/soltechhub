package dev.simplesolution.pdf;

import dev.simplesolution.pdf.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootGeneratePdfApplication implements CommandLineRunner {

    @Autowired
    private EmailService emailService;
    public static void main(String[] args) {
        SpringApplication.run(SpringBootGeneratePdfApplication.class, args);
    }


    @Override
    public void run(String... args)
    {
       // emailService.sendMail("manishsinghcd01@gmail.com", "Happy Coding", "Email sent with demo application");

        //emailService.sendPreConfiguredMail("Happy Coding");
    }
}
