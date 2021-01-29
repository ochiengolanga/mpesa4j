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
package com.github.ochiengolanga.mpesa4j.models.types;

import com.github.ochiengolanga.mpesa4j.util.Preconditions;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.NonNull;

public final class ConfirmationUrl extends CallbackUrl {
  private ConfirmationUrl(String value) throws MalformedURLException {
    super();
    this.setValue(new URL(value));
  }

  public static ConfirmationUrl of(@NonNull String value) throws MalformedURLException {
    Preconditions.checkEmptyString(value, "Invalid confirmation url. Empty or null.");

    return new ConfirmationUrl(value);
  }
}
