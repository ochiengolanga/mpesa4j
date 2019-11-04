/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2019 Daniel Ochieng' Olanga.
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
package com.github.ochiengolanga.mpesa4j.spring.boot.autoconfigure;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;

class AdditionalConfigurationMetadataTest {

  @Test
  void testProperties() throws IOException {

    DocumentContext documentContext =
        JsonPath.parse(
            new FileSystemResource(
                    "src/main/resources/META-INF/additional-spring-configuration-metadata.json")
                .getInputStream());

    List<Map<String, Object>> properties = documentContext.read("$.properties");

    assertThat(properties.size()).isEqualTo(22);

    // assert for mpesa4j.debug
    {
      Map<String, Object> element = properties.get(0);
      assertThat(element.get("name")).isEqualTo("mpesa4j.debug");
      assertThat(element.get("type")).isEqualTo("java.lang.Boolean");
      assertThat(element.get("description")).isEqualTo("Enables or disables debug mode.");
    }

    // assert for mpesa4j.sandbox-enabled
    {
      Map<String, Object> element = properties.get(1);
      assertThat(element.get("name")).isEqualTo("mpesa4j.sandbox-enabled");
      assertThat(element.get("type")).isEqualTo("java.lang.Boolean");
      assertThat(element.get("description"))
          .isEqualTo(
              "Enables or disables MPesa's sandbox or live mode. Enabled uses sandbox-mode api endpoints, disabled toggles use of live-mode api endpoints.");
    }
  }
}
