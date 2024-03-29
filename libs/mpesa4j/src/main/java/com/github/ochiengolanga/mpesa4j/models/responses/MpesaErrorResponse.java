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
package com.github.ochiengolanga.mpesa4j.models.responses;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

/** A data interface representing an error response from M-Pesa API */
@SuppressWarnings("SameNameButDifferent")
@Getter
@ToString
public class MpesaErrorResponse extends MpesaResponse implements java.io.Serializable {
  private static final long serialVersionUID = 7092906238192790951L;

  @SerializedName("requestId")
  private String requestId;

  @SerializedName("errorCode")
  private String errorCode;

  @SerializedName("errorMessage")
  private String errorMessage;
}
