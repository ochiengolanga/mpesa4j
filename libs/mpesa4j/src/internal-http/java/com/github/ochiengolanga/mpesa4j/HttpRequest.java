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
package com.github.ochiengolanga.mpesa4j;

import com.github.ochiengolanga.mpesa4j.models.ApiResource;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@SuppressWarnings({"SameNameButDifferent"})
@Getter
@ToString
@EqualsAndHashCode
public final class HttpRequest implements java.io.Serializable {
  private static final long serialVersionUID = 3365496352032493020L;

  private final ApiResource.RequestMethod method;
  private final String url;
  private final Map<String, String> requestHeaders;
  private final HttpParameter[] parameters;

  private static final HttpParameter[] NULL_PARAMETERS = new HttpParameter[0];

  public HttpRequest(
      ApiResource.RequestMethod method,
      String url,
      Map<String, String> requestHeaders,
      HttpParameter[] parameters) {
    this.method = method;
    if (method != ApiResource.RequestMethod.POST && parameters != null && parameters.length != 0) {
      this.url = url + "?" + HttpParameter.encodeParameters(parameters);
      this.parameters = NULL_PARAMETERS;
    } else {
      this.url = url;
      this.parameters = parameters;
    }
    this.requestHeaders = requestHeaders;
  }
}
