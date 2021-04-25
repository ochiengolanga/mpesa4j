# Mpesa4j Sample application

## Prerequisites

You need an access token to publish, install, and delete packages. You can use a personal access token to authenticate with your username directly to GitHub Packages or the GitHub API. When you create a personal access token, you can assign the token different scopes depending on your needs.

To authenticate using a GitHub Actions workflow:

For package registries (PACKAGE-REGISTRY.pkg.github.com), you can use a GITHUB_TOKEN.

## Adding mpesa4j dependency

You can install a package by adding the package as a dependency to your project.

Authenticate to GitHub Packages. For more information, see "Authenticating to GitHub Packages."

Add the package dependencies to your build.gradle file (Gradle Groovy) or build.gradle.kts file (Kotlin DSL) file.

Example using Gradle Groovy:

dependencies {
implementation 'com.example:package'
}
Example using Kotlin DSL:

dependencies {
implementation("com.example:package")
}
Add the maven plugin to your build.gradle file (Gradle Groovy) or build.gradle.kts file (Kotlin DSL) file.

Example using Gradle Groovy:

plugins {
id 'maven'
}
Example using Kotlin DSL:

plugins {
`maven`
}
Install the package.

$ gradle install

## Usage

````java
public final class Mpesa4jSampleApplication {

    public static void main(String[] args) {
        Mpesa mpesa = new MpesaFactory().getInstance();
        SalaryPaymentRequestResponse response =
                mpesa.paySalary(
                        PhoneNumber.of("254708374149"),
                        TransactionAmount.of(new BigDecimal("100.00")),
                        Description.of("Salary payment (JUL-AUG)"),
                        Occasion.of("Test"));

        System.out.println("Response Description: " + response.getResponseDescription());
        System.out.println("Response Code: " + response.getResponseCode());
        System.out.println("ConversationID: " + response.getConversationId());
        System.out.println("OriginatorConversationID: " + response.getOriginatorConversationId());

        System.exit(0);
    }

}
````