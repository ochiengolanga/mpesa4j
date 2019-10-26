package sample;

import com.github.ochiengolanga.mpesa4j.Mpesa;
import com.github.ochiengolanga.mpesa4j.MpesaFactory;
import com.github.ochiengolanga.mpesa4j.models.exceptions.MpesaApiException;
import com.github.ochiengolanga.mpesa4j.models.responses.SalaryPaymentRequestResponse;
import java.math.BigDecimal;

/**
 * Sample plain java application using Mpesa4j library to do a Business to Customer (B2C) salary
 * payment transactions between a company and its employees
 *
 * @author Daniel Ochieng' Olanga
 */
public final class Mpesa4jSampleApplication {

  public static void main(String[] args) {
    Mpesa mpesa = new MpesaFactory().getInstance();
    try {
      SalaryPaymentRequestResponse response =
          mpesa.paySalary("254708374149", new BigDecimal(100.00), "Salary payment (JUL-AUG)", "");

      System.out.println("Response Description: " + response.getResponseDescription());
      System.out.println("Response Code: " + response.getResponseCode());
      System.out.println("ConversationID: " + response.getConversationId());
      System.out.println("OriginatorConversationID: " + response.getOriginatorConversationId());

      System.exit(0);
    } catch (MpesaApiException e) {
      e.printStackTrace();
      System.out.println("Failed to get request pay business b2c settings: " + e.getMessage());
      System.exit(-1);
    }
  }
}
