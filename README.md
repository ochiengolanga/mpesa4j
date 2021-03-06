# Mpesa4j - The unofficial M-Pesa Java library

[![Licence](https://img.shields.io/github/license/ochiengolanga/mpesa4j)](https://img.shields.io/github/license/ochiengolanga/mpesa4j)
[![Coverage Status](https://coveralls.io/repos/github/ochiengolanga/mpesa4j/badge.svg?branch=master)](https://coveralls.io/github/ochiengolanga/mpesa4j?branch=master)
[![Version](https://img.shields.io/github/v/release/ochiengolanga/mpesa4j)](https://img.shields.io/github/v/release/ochiengolanga/mpesa4j)
[![Build Status](https://img.shields.io/github/workflow/status/ochiengolanga/mpesa4j/CI/master)](https://img.shields.io/github/workflow/status/ochiengolanga/mpesa4j/CI/master)
[![Contributors](https://img.shields.io/github/contributors/ochiengolanga/mpesa4j)](https://img.shields.io/github/contributors/ochiengolanga/mpesa4j)

Mpesa4j is a thread safe M-Pesa API aka "Daraja" client library based on Java 11 for Vanilla Java and Spring Boot applications.

> "The goal of library design is to give application developers ready-to-use tools that cover their most frequent use-cases and solve the most popular problems. If something is needed all the time by any kind of application, it should be simple and straightforward to code." --Roman Elizarov (JetBrains)

## Features at a glance

Mpesa4j provides easy to use and straightforward APIs drawn from experience implementing clients over and over again. The implemented Daraja features include:

| M-Pesa "Daraja" API        | Implemented |
| ------------- |:-------------:|
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
* [GitHub personal access token](https://docs.github.com/en/packages/guides/configuring-gradle-for-use-with-github-packages#authenticating-to-github-packages) used to install mpesa4j dependencies from GitHub. 
* Valid M-Pesa Daraja API credentials (https://developer.safaricom.co.ke/login-register).

## Usage

<details>
<summary>Maven</summary>

To authenticate to GitHub Packages with Apache Maven by editing your ~/.m2/settings.xml file to include your personal access token. Create a new ~/.m2/settings.xml file if one doesn't exist.

Add dependency entry to pom.xml file.

```xml
<repositories>
    <repository>
      <id>github</id>
      <name>GitHubPackages</name>
      <releases><enabled>true</enabled></releases>
      <url>https://maven.pkg.github.com/ochiengolanga/mpesa4j</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.ochiengolanga.mpesa4j</groupId>
        <artifactId>mpesa4j</artifactId>
        <version>0.1.3</version>
    </dependency>
</dependencies>
```

Install the package

```bash
$ mvn install
```

</details>

<details>
<summary>Gradle</summary>


```gradle
plugins {
    id 'maven'
}

repositories {
    ...
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/ochiengolanga/mpesa4j")
        credentials {
            username = project.hasProperty('gpr.user') ? project.property('gpr.user') : System.getenv('GITHUB_USERNAME')
            password = project.hasProperty('gpr.key') ? project.property('gpr.key') : System.getenv('GITHUB_TOKEN')
        }
    }
}

dependencies {
    implementation "com.github.ochiengolanga.mpesa4j:mpesa4j:0.1.3"
}
```

Install the package

```bash
$ gradle install
```

</details>

## Examples

<details>
<summary>Vanilla Java application</summary>

```java
package sample;

import com.github.ochiengolanga.mpesa4j.Mpesa;
import com.github.ochiengolanga.mpesa4j.MpesaFactory;
import com.github.ochiengolanga.mpesa4j.exceptions.MpesaApiException;
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
        SalaryPaymentRequestResponse response = mpesa.paySalary(
            PhoneNumber.of("254708374149"),
            TransactionAmount.of(new BigDecimal("100.00")),
            Description.of("Salary payment"),
            Occasion.none());

        System.out.println("Response Description: " + response.getResponseDescription());
        System.out.println("Response Code: " + response.getResponseCode());
        System.out.println("ConversationID: " + response.getConversationId());
        System.out.println("OriginatorConversationID: " + response.getOriginatorConversationId());

        System.exit(0);
    }

}
```

See the project's [functional tests](https://github.com/ochiengolanga/mpesa4j/tree/master/libs/mpesa4j/src/test/java/com/github/ochiengolanga/mpesa4j/) for more examples.

## Configuration

Mpesa4j accepts configuration properties from ConfigurationBuilder, environment variables, system properties and properties file named "mpesa4j.properties".

Examples of configurable properties:

* Via properties file named "mpesa4j.properties":

```yaml
MPESA4J_DEBUG=true
MPESA4J_SANDBOX_ENABLED=true
MPESA4J_HTTP_CONNECTION_TIMEOUT=20000
MPESA4J_HTTP_READ_TIMEOUT=120000
MPESA4J_ACCOUNT_BALANCE_QUEUE_TIMEOUT_URL=https://example.com/callback
MPESA4J_ACCOUNT_BALANCE_RESULT_URL=https://example.com/callback
MPESA4J_B2B_QUEUE_TIMEOUT_URL=https://example.com/callback
MPESA4J_B2B_BALANCE_RESULT_URL=https://example.com/callback
MPESA4J_B2C_QUEUE_TIMEOUT_URL=https://example.com/callback
MPESA4J_B2C_BALANCE_RESULT_URL=https://example.com/callback
MPESA4J_CONSUMER_KEY=test # from M-Pesa Developer portal
MPESA4J_CONSUMER_SECRET=test # from M-Pesa Developer portal
MPESA4J_INITIATOR_NAME=test # from M-Pesa Developer portal
MPESA4J_INITIATOR_SHORTCODE=12345 # from M-Pesa Developer portal
MPESA4J_INITIATOR_SECURITY_CREDENTIAL=test # from M-Pesa Developer portal
MPESA4J_LIPA_NA_MPESA_SHORTCODE=12345 # from M-Pesa Developer portal
MPESA4J_LIPA_NA_MPESA_PASSKEY=test # from M-Pesa Developer portal
MPESA4J_LIPA_NA_MPESA_CALLBACK_URL=https://example.com/callback
MPESA4J_TRANSACTION_QUERY_QUEUE_TIMEOUT_URL=https://example.com/callback
MPESA4J_TRANSACTION_QUERY_RESULT_URL=https://example.com/callback
MPESA4J_TRANSACTION_REVERSAL_QUEUE_TIMEOUT_URL=https://example.com/callback
MPESA4J_TRANSACTION_REVERSAL_RESULT_URL=https://example.com/callback
```

* Via ConfigurationBuiler:

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
      .setLipaNaMpesaShortCode("12345")
      ... // other configurations
      ;
Mpesa mpesa = new MpesaFactory(builder.build()).getInstance();
```

* Via System Properties:

```bash
java -DMPESA4J_DEBUG=true
    -DMPESA4J_SANDBOX_ENABLED=true
    -DMPESA4J_HTTP_CONNECTION_TIMEOUT=20000
    -DMPESA4J_HTTP_READ_TIMEOUT=120000
    -DMPESA4J_ACCOUNT_BALANCE_QUEUE_TIMEOUT_URL=https://example.com/accountbalance/queuetimeout
    ...
    -cp mpesa4j-0.1.2.jar:yourApp.jar yourpackage.Main
```

* Via Environment variables:

```bash
export MPESA4J_DEBUG=true
export MPESA4J_SANDBOX_ENABLED=true
export MPESA4J_HTTP_CONNECTION_TIMEOUT=20000
export MPESA4J_HTTP_READ_TIMEOUT=120000
export MPESA4J_ACCOUNT_BALANCE_QUEUE_TIMEOUT_URL=https://example.com/callback
...
```

</details>

<details>
<summary>Spring Boot application</summary>

#### Dependencies

Maven

```xml
<dependencies>
    ...
    <dependency>
        <groupId>com.github.ochiengolanga.mpesa4j</groupId>
        <artifactId>mpesa4j</artifactId>
        <version>0.1.3</version>
    </dependency>
    <dependency>
        <groupId>com.github.ochiengolanga.mpesa4j</groupId>
        <artifactId>mpesa4j-spring-boot-starter</artifactId>
        <version>0.1.3</version>
    </dependency>    
    ...
</dependencies>
```

Gradle

```groovy
plugins {
    id 'maven'
}

...

dependencies {
    implementation "com.github.ochiengolanga.mpesa4j:mpesa4j:0.1.3"
    implementation "com.github.ochiengolanga.mpesa4j:mpesa4j-spring-boot-starter:0.1.3"
}
```

#### Usage

```java
package sample;

import com.github.ochiengolanga.mpesa4j.Mpesa;
import com.github.ochiengolanga.mpesa4j.models.responses.SalaryPaymentRequestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Sample spring application using Mpesa4j spring boot starter to do a Business to Customer (B2C) salary payment transactions between
 * a company and its employees
 *
 * @author Daniel Ochieng' Olanga
 */
@RequiredArgsConstructor
@Slf4j
@SpringBootApplication
public class Mpesa4jSpringBootSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(Mpesa4jSpringBootSampleApplication.class, args);
    }

    /**
    * Execute salary payment after application has started
    *
    * @param paymentService
    */
    @Bean
    CommandLineRunner run(PaymentService paymentService) {
        return args -> paymentService.paySalary(
            PhoneNumber.of("254708374149"),
            TransactionAmount.of(new BigDecimal("100.00")),
            Description.of("Salary payment"),
            Occasion.none());
    }
}

interface PaymentService {
    void paySalary(PhoneNumber employeePhoneNumber, TransactionAmount payableAmount, Description description, Occassion occassion);
}

@Slf4j
@RequiredArgsConstructor
@Service
class PaymentServiceImpl implements PaymentService {
    private final Mpesa mpesa;

    @override
    public void paySalary(PhoneNumber employeePhoneNumber, TransactionAmount payableAmount, Description description, Occassion occassion) {
        SalaryPaymentRequestResponse response = mpesa.paySalary(
                employeePhoneNumber,
                payableAmount,
                description,
                occassion);

        System.out.println("Response Description: " + response.getResponseDescription());
        System.out.println("Response Code: " + response.getResponseCode());
        System.out.println("ConversationID: " + response.getConversationId());
        System.out.println("OriginatorConversationID: " + response.getOriginatorConversationId());
    }
}
```

#### Configuration

Add the following properties to your Spring application.properties file

```yaml
mpesa4j.debug=true
mpesa4j.sandbox-enabled=true
mpesa4j.connection-timeout=20000
mpesa4j.read-timeout=120000
mpesa4j.account-balance.queue-timeout-url=https://example.com/accountBalance/queueTimeout
mpesa4j.account-balance.result-url=https://example.com/accountBalance/result
mpesa4j.b2b.queue-timeout-url=https://example.com/b2b/queueTimeout
mpesa4j.b2b.result-url=https://example.com/b2b/result
mpesa4j.b2c.queue-timeout-url=https://example.com/b2c/queueTimeout
mpesa4j.b2c.result-url=https://example.com/b2c/result
mpesa4j.consumer-key=demo
mpesa4j.consumer-secret=demo
mpesa4j.initiator.name=abcdemo
mpesa4j.initiator.short-code=12345
mpesa4j.initiator.security-credential=Aafs234we
mpesa4j.lipa-na-mpesa.short-code=98868
mpesa4j.lipa-na-mpesa.passkey=wert3434
mpesa4j.lipa-na-mpesa.callback-url=https://example.com/lnm/callback
mpesa4j.transaction-query.queue-timeout-url=https://example.com/transactionQuery/queueTimeout
mpesa4j.transaction-query.result-url=https://example.com/transactionQuery/result
mpesa4j.transaction-reversal.queue-timeout-url=https://example.com/transactionReversal/queueTimeout
mpesa4j.transaction-reversal.result-url=https://example.com/transactionReversal/result
```

</details>

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
