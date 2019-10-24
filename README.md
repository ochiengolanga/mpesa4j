# Mpesa4j

[![CircleCI](https://circleci.com/gh/ochiengolanga/mpesa4j/tree/master.svg?style=svg)](https://circleci.com/gh/ochiengolanga/mpesa4j/tree/master)
[![Coverage Status](https://coveralls.io/repos/github/ochiengolanga/mpesa4j/badge.svg?branch=master)](https://coveralls.io/github/ochiengolanga/mpesa4j?branch=master)
[ ![Download](https://api.bintray.com/packages/ochiengolanga/mpesa4j/mpesa4j/images/download.svg) ](https://bintray.com/ochiengolanga/mpesa4j/mpesa4j/_latestVersion)

Mpesa4j is a thread safe M-Pesa API aka "Daraja" client library based on Java 11. For more details, see details below with example on how to use the library.

> "The goal of library design is to give application developers ready-to-use tools that cover their most frequent use-cases and solve the most popular problems. If something is needed all the time by any kind of application, it should be simple and straightforward to code." --Roman Elizarov (JetBrains)

## Features at a glance

Mpesa4j provides easy to use and straightforward APIs drawn from experience implementing clients over and over. Implemented Daraja features include:

| M-Pesa "Daraja" API        | Implemented |
| ------------- |:-------------:| -----:|
| Account Balance Query      | yes|
| Business To Business (B2B) | yes (Suspended by provider) |
| Business To Customer (B2C) | yes |
| C2B Callback URL Registration | yes |
| Lipa Na M-Pesa Online Payment (STK Push) | yes |
| Lipa Na M-Pesa Online Query | yes |
| Transaction Status | yes |
| Transaction Reversal | yes |

## Prerequisites

* Java 11 and above.

## Installation

### Maven

```xml
<dependency>
  <groupId>com.github.ochiengolanga.mpesa4j</groupId>
  <artifactId>mpesa4j</artifactId>
  <version>0.0.1</version>
</dependency>
```

### Gradle

```groovy
compile "com.github.ochiengolanga.mpesa4j:mpesa4j:0.0.1"
```

## Usage

```java
package sample;

import com.github.ochiengolanga.mpesa4j.Mpesa;
import com.github.ochiengolanga.mpesa4j.MpesaFactory;
import com.github.ochiengolanga.mpesa4j.models.exceptions.MpesaApiException;
import com.github.ochiengolanga.mpesa4j.models.responses.SalaryPaymentRequestResponse;

import java.math.BigDecimal;

/**
 * Sample plain java application using Mpesa4j library to do a Business to Customer (B2C) salary payment transactions between
 * a company and its employees
 *
 * @author Daniel Ochieng' Olanga
 */
public final class Mpesa4jSampleApplication {

    public static void main(String[] args) {
        Mpesa mpesa = new MpesaFactory().getInstance();
        try {
            SalaryPaymentRequestResponse response = mpesa.paySalary(
              "254708374149",
              new BigDecimal(100.00),
              "Salary payment (JUL-AUG)",
              ""
            );

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
```

See the project's [functional tests](https://github.com/ochiengolanga/mpesa4j/tree/master/libs/mpesa4j/src/test/java/com/github/ochiengolanga/mpesa4j/) for more examples.

### Configuration

Mpesa4j can read configuration properties from ConfigurationBuilder, environment variables, system properties and properties file named "mpesa4j.properties".

An example of configurable properties via file name "mpesa4j.properties":

```yaml
MPESA4J_DEBUG=true
MPESA4J_SANDBOX_ENABLED=true
MPESA4J_HTTP_CONNECTION_TIMEOUT=20000
MPESA4J_HTTP_READ_TIMEOUT=120000
MPESA4J_ACCOUNT_BALANCE_QUEUE_TIMEOUT_URL=https://peternjeru.co.ke/safdaraja/api/callback.php
MPESA4J_ACCOUNT_BALANCE_RESULT_URL=https://peternjeru.co.ke/safdaraja/api/callback.php
MPESA4J_B2B_QUEUE_TIMEOUT_URL=https://peternjeru.co.ke/safdaraja/api/callback.php
MPESA4J_B2B_BALANCE_RESULT_URL=https://peternjeru.co.ke/safdaraja/api/callback.php
MPESA4J_B2C_QUEUE_TIMEOUT_URL=https://peternjeru.co.ke/safdaraja/api/callback.php
MPESA4J_B2C_BALANCE_RESULT_URL=https://peternjeru.co.ke/safdaraja/api/callback.php
MPESA4J_CONSUMER_KEY=test # from M-Pesa Developer portal
MPESA4J_CONSUMER_SECRET=test # from M-Pesa Developer portal
MPESA4J_INITIATOR_NAME=test # from M-Pesa Developer portal
MPESA4J_INITIATOR_SHORTCODE=12345 # from M-Pesa Developer portal
MPESA4J_INITIATOR_SECURITY_CREDENTIAL=test # from M-Pesa Developer portal
MPESA4J_LIPA_NA_MPESA_SHORTCODE=12345 # from M-Pesa Developer portal
MPESA4J_LIPA_NA_MPESA_PASSKEY=test # from M-Pesa Developer portal
MPESA4J_LIPA_NA_MPESA_CALLBACK_URL=https://peternjeru.co.ke/safdaraja/api/callback.php
MPESA4J_TRANSACTION_QUERY_QUEUE_TIMEOUT_URL=https://peternjeru.co.ke/safdaraja/api/callback.php
MPESA4J_TRANSACTION_QUERY_RESULT_URL=https://peternjeru.co.ke/safdaraja/api/callback.php
MPESA4J_TRANSACTION_REVERSAL_QUEUE_TIMEOUT_URL=https://peternjeru.co.ke/safdaraja/api/callback.php
MPESA4J_TRANSACTION_REVERSAL_RESULT_URL=https://peternjeru.co.ke/safdaraja/api/callback.php
```

via ConfigurationBuiler:

```java
ConfigurationBuilder builder = new ConfigurationBuilder();
builder.setDebugEnabled(true)
      .setSandboxEnabled(true)
      .setHttpConnectionTimeout(20000)
      .setHttpReadTimeout(120000)
      .setAccountBalanceQueueTimeoutUrl("https://example.com/accountbalance/queuetimeout")
      .setAccountBalanceResultUrl("https://example.com/accountbalance/result")
      .setConsumerKey("test")
      .setConsumerSecret("test")
      .setLipaNaMpesaShortCode("12345");
Mpesa mpesa = new MpesaFactory(builder.build()).getInstance();
```

via System Properties:

```bash
java -DMPESA4J_DEBUG=true
    -DMPESA4J_SANDBOX_ENABLED=true
    -DMPESA4J_HTTP_CONNECTION_TIMEOUT=20000
    -DMPESA4J_HTTP_READ_TIMEOUT=120000
    -DMPESA4J_ACCOUNT_BALANCE_QUEUE_TIMEOUT_URL=https://example.com/accountbalance/queuetimeout
    -cp mpesa4j-0.0.1.jar:yourApp.jar yourpackage.Main
```

via Environment variables:

```bash
export MPESA4J_DEBUG=true
export MPESA4J_SANDBOX_ENABLED=true
export MPESA4J_HTTP_CONNECTION_TIMEOUT=20000
export MPESA4J_HTTP_READ_TIMEOUT=120000
export MPESA4J_ACCOUNT_BALANCE_QUEUE_TIMEOUT_URL=https://peternjeru.co.ke/safdaraja/api/callback.php
```

## How to Build and Contribute

This project welcomes contributions and suggestions via issues and pull requests.

Please follow instructions [here](https://github.com/ochiengolanga/mpesa4j/blob/master/CONTRIBUTE.md) to build from source or contribute.

## Filing Issues

If you encounter any bug, please file an issue [here](https://github.com/ochiengolanga/mpesa4j/issues).

To suggest a new feature or changes that could be made, file an issue the same way you would for a bug.

## Pull Requests

Pull requests are welcome. To open your own pull request, click [here](https://github.com/ochiengolanga/mpesa4j/pulls). When creating a pull request, make sure you are pointing to the fork and branch that your changes were made in.

## Features in the pipeline

* Pluggable logging
* Micrometer metrics
