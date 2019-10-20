## Usage

````java
public final class Mpesa4jSampleApplication {

    public static void main(String[] args) {
        Mpesa mpesa = new MpesaFactory().getInstance();
        try {
            SalaryPaymentRequestResponse response = mpesa.paySalary("254708374149", new BigDecimal(100.00), "Business payment", "");
            .... // process response
        } catch (MpesaApiException e) {
            .... // handle raised exception
        }
    }

}
````