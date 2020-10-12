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

import java.math.BigDecimal;

/** Utils for checking preconditions and invariants */
public abstract class Preconditions {

  private static final String DEFAULT_MESSAGE = "Received an invalid parameter";

  /**
   * Checks that an object is not null.
   *
   * @param object any object
   * @param errorMsg error message
   * @throws IllegalArgumentException if the object is null
   */
  public static void checkNotNull(Object object, String errorMsg) {
    check(object != null, errorMsg);
  }

  /**
   * Checks that a string is not null or empty
   *
   * @param string any string
   * @param errorMsg error message
   * @throws IllegalArgumentException if the string is null or empty
   */
  public static void checkEmptyString(String string, String errorMsg) {
    check(hasText(string), errorMsg);
  }

  public static void checkPositiveNumber(BigDecimal bigDecimal, String errorMsg) {
    check(bigDecimal.compareTo(new BigDecimal(0)) > 0, errorMsg);
  }

  public static boolean hasText(String str) {
    if (str == null || str.isEmpty()) {
      return false;
    }
    final int strLen = str.length();
    for (int i = 0; i < strLen; i++) {
      if (!Character.isWhitespace(str.charAt(i))) {
        return true;
      }
    }
    return false;
  }

  private static void check(boolean requirements, String error) {
    if (!requirements) {
      throw new IllegalArgumentException(hasText(error) ? error : DEFAULT_MESSAGE);
    }
  }
}
