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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.Getter;
import lombok.ToString;

/** A data class representing HTTP Post parameter */
@Getter
@ToString
public final class HttpParameter implements Comparable<HttpParameter>, java.io.Serializable {
  private static final long serialVersionUID = 4046908449190454692L;
  private String name = null;
  private String value = null;

  public HttpParameter(String name, String value) {
    this.name = name;
    this.value = value;
  }

  @Override
  public int compareTo(HttpParameter o) {
    int compared = 0;
    if (name != null) {
      compared = name.compareTo(o.name);
    }
    if (0 == compared) {
      if (value != null) {
        compared = value.compareTo(o.value);
      }
    }
    return compared;
  }

  public static String encodeParameters(HttpParameter[] httpParams) {
    if (null == httpParams) {
      return "";
    }
    StringBuilder buf = new StringBuilder();
    for (int j = 0; j < httpParams.length; j++) {
      if (j != 0) {
        buf.append("&");
      }
      buf.append(encode(httpParams[j].name)).append("=").append(encode(httpParams[j].value));
    }
    return buf.toString();
  }

  /**
   * <a href="http://wiki.oauth.net/TestCases">OAuth / TestCases</a>
   *
   * @see <a href="http://wiki.oauth.net/TestCases">OAuth / TestCases</a>
   * @see <a
   *     href="http://groups.google.com/group/oauth/browse_thread/thread/a8398d0521f4ae3d/9d79b698ab217df2?hl=en&lnk=gst&q=space+encoding#9d79b698ab217df2">Space
   *     encoding - OAuth | Google Groups</a>
   * @see <a href="http://tools.ietf.org/html/rfc3986#section-2.1">RFC 3986 - Uniform Resource
   *     Identifier (URI): Generic Syntax - 2.1. Percent-Encoding</a>
   * @param value string to be encoded
   * @return encoded string
   */
  public static String encode(String value) {
    String encoded = URLEncoder.encode(value, StandardCharsets.UTF_8);
    StringBuilder buf = new StringBuilder(encoded.length());
    char focus;
    for (int i = 0; i < encoded.length(); i++) {
      focus = encoded.charAt(i);
      if (focus == '*') {
        buf.append("%2A");
      } else if (focus == '+') {
        buf.append("%20");
      } else if (focus == '%'
          && (i + 1) < encoded.length()
          && encoded.charAt(i + 1) == '7'
          && encoded.charAt(i + 2) == 'E') {
        buf.append('~');
        i += 2;
      } else {
        buf.append(focus);
      }
    }
    return buf.toString();
  }
}
