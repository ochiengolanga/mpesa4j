/*
 * Copyright 2019-2021 Daniel Ochieng' Olanga.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.ochiengolanga.mpesa4j.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.ochiengolanga.mpesa4j.Mpesa;
import com.github.ochiengolanga.mpesa4j.MpesaFactory;
import com.github.ochiengolanga.mpesa4j.models.enums.DefaultAction;
import com.github.ochiengolanga.mpesa4j.models.responses.CallbackUrlsRegistrationResponse;
import com.github.ochiengolanga.mpesa4j.models.types.ConfirmationUrl;
import com.github.ochiengolanga.mpesa4j.models.types.ValidationUrl;
import java.net.MalformedURLException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class RegisterCallbackUrlsTest {

  @BeforeEach
  void init() {}

  @Disabled("Daraja API timeouts randomly or is unavailable")
  @Test
  void registerCallbackUrlsTest() throws MalformedURLException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    CallbackUrlsRegistrationResponse response =
        mpesa.registerCallbackUrls(
            DefaultAction.COMPLETE,
            ValidationUrl.of("https://example.com/callback"),
            ConfirmationUrl.of("https://example.com/callback"));

    assertNotNull(response.getConversationId());
    assertNotNull(response.getOriginatorConversationId());
    assertNotNull(response.getResponseDescription());
    assertTrue(response.getResponseDescription().equalsIgnoreCase("success"));
  }

  @Test
  void nullDefaultAction_registerCallbackUrlsTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.registerCallbackUrls(
                null,
                ValidationUrl.of("https://example.com/callback"),
                ConfirmationUrl.of("https://example.com/callback")));
  }

  @Test
  void nullValidationUrl_registerCallbackUrlsTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.registerCallbackUrls(
                DefaultAction.COMPLETE, null, ConfirmationUrl.of("https://example.com/callback")));
  }

  @Test
  void emptyValidationUrl_accountBalanceTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.registerCallbackUrls(
                DefaultAction.COMPLETE,
                ValidationUrl.of(""),
                ConfirmationUrl.of("https://example.com/callback")));
  }

  @Test
  void nullConfirmationUrl_registerCallbackUrlsTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.registerCallbackUrls(
                DefaultAction.COMPLETE, ValidationUrl.of("https://example.com/callback"), null));
  }

  @Test
  void emptyConfirmationUrl_accountBalanceTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.registerCallbackUrls(
                DefaultAction.COMPLETE,
                ValidationUrl.of("https://example.com/callback"),
                ConfirmationUrl.of("")));
  }
}
