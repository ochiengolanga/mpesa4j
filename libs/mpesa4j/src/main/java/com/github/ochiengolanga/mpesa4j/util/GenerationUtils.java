/*
 * Copyright 2019-2020 Daniel Ochieng' Olanga.
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

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public abstract class GenerationUtils {
  public static String generateTimestamp() {
    return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
  }

  public static String generatePassword(String shortCode, String passkey, String timestamp) {
    return Base64.getEncoder()
        .encodeToString((shortCode + passkey + timestamp).getBytes(StandardCharsets.UTF_8));
  }
}
