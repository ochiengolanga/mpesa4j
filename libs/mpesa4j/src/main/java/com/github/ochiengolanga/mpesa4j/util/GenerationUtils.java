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
package com.github.ochiengolanga.mpesa4j.util;

import com.github.ochiengolanga.mpesa4j.models.types.LipaNaMpesaPasskey;
import com.github.ochiengolanga.mpesa4j.models.types.LipaNaMpesaShortCode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public abstract class GenerationUtils {
  public static String generateTimestamp() {
    return LocalDateTime.now(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
  }

  public static String generatePassword(
      LipaNaMpesaShortCode shortCode, LipaNaMpesaPasskey passkey, String timestamp) {
    return Base64.getEncoder()
        .encodeToString(
            (shortCode.getValue() + passkey.getValue() + timestamp)
                .getBytes(StandardCharsets.UTF_8));
  }
}
