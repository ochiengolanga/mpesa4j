package sample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Sample spring application using Mpesa4j spring boot starter to do a Business to Customer (B2C) salary payment transactions between
 * a company and its employees
 *
 * @author Daniel Ochieng' Olanga
 */
@RequiredArgsConstructor
@Slf4j
@SpringBootApplication
public class Mpesa4jSpringBootSampleApplication implements CommandLineRunner {

    //private final PaymentService paymentService;

    public static void main(String[] args) {
        SpringApplication.run(Mpesa4jSpringBootSampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        SalaryPaymentRequestResponse response = mpesaService.paySalary("254708374149", new BigDecimal(100.00), "Salary payment (JUL-AUG)", "");
//
//            System.out.println("Response Description: " + response.getResponseDescription());
//            System.out.println("Response Code: " + response.getResponseCode());
//            System.out.println("ConversationID: " + response.getConversationId());
//            System.out.println("OriginatorConversationID: " + response.getOriginatorConversationId());
    }
}

//@RequiredArgsConstructor
//@Service
//class MpesaService {
//    private final Mpesa mpesa;
//
//    public SalaryPaymentRequestResponse paySalary(String phoneNumber, BigDecimal salary, String description, String remark) {
//        return mpesa.paySalary(phoneNumber, salary, description, remark);
//    }
//}
