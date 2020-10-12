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
package com.github.ochiengolanga.mpesa4j.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class ApiResource {

  public enum RequestMethod {
    GET,
    POST
  }

  public static final Gson GSON = createGson();

  private static Gson createGson() {
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting().serializeNulls();
    return builder.create();
  }

  public String toJson() {
    return GSON.toJson(this);
  }

  @Override
  public String toString() {
    return String.format(
        "<%s@%s> JSON: %s",
        this.getClass().getName(), System.identityHashCode(this), GSON.toJson(this));
  }
}
