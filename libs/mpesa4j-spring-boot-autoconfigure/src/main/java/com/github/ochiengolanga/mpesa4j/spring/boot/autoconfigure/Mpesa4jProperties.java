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
//package com.github.ochiengolanga.mpesa4j.spring.boot.autoconfigure;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.NestedConfigurationProperty;
//
//@ConfigurationProperties(prefix= Mpesa4jProperties.MPESA4J_PREFIX)
//public class Mpesa4jProperties {
//
//    public static final String MPESA4J_PREFIX = "mpesa4j";
//
//    @NestedConfigurationProperty
//    private Mpesa mpesa = new Mpesa();
//
//    public Mpesa getMpesa() {
//        return mpesa;
//    }
//
//    public void setMpesa(Mpesa mpesa) {
//        this.mpesa = mpesa;
//    }
//
//    public static class Mpesa {
//        private Boolean debug = false;
//        private int connectionTimeout = -1;
//        private int readTimeout = -1;
//        private String consumerKey;
//        private String consumerSecret;
//        private AccountBalance accountBalance;
//        private B2B b2B;
//        private B2C b2C;
//        private Initiator initiator;
//        private LipaNaMpesa lipaNaMpesa;
//        private TransactionReversal transactionReversal;
//        private TransactionQuery transactionQuery;
//
//        public Boolean getDebug() {
//            return debug;
//        }
//
//        public void setDebug(Boolean debug) {
//            this.debug = debug;
//        }
//
//        public int getConnectionTimeout() {
//            return connectionTimeout;
//        }
//
//        public void setConnectionTimeout(int connectionTimeout) {
//            this.connectionTimeout = connectionTimeout;
//        }
//
//        public int getReadTimeout() {
//            return readTimeout;
//        }
//
//        public void setReadTimeout(int readTimeout) {
//            this.readTimeout = readTimeout;
//        }
//
//        public String getConsumerKey() {
//            return consumerKey;
//        }
//
//        public void setConsumerKey(String consumerKey) {
//            this.consumerKey = consumerKey;
//        }
//
//        public String getConsumerSecret() {
//            return consumerSecret;
//        }
//
//        public void setConsumerSecret(String consumerSecret) {
//            this.consumerSecret = consumerSecret;
//        }
//
//        public AccountBalance getAccountBalance() {
//            return accountBalance;
//        }
//
//        public void setAccountBalance(AccountBalance accountBalance) {
//            this.accountBalance = accountBalance;
//        }
//
//        public B2B getB2B() {
//            return b2B;
//        }
//
//        public void setB2B(B2B b2B) {
//            this.b2B = b2B;
//        }
//
//        public B2C getB2C() {
//            return b2C;
//        }
//
//        public void setB2C(B2C b2C) {
//            this.b2C = b2C;
//        }
//
//        public Initiator getInitiator() {
//            return initiator;
//        }
//
//        public void setInitiator(Initiator initiator) {
//            this.initiator = initiator;
//        }
//
//        public LipaNaMpesa getLipaNaMpesa() {
//            return lipaNaMpesa;
//        }
//
//        public void setLipaNaMpesa(LipaNaMpesa lipaNaMpesa) {
//            this.lipaNaMpesa = lipaNaMpesa;
//        }
//
//        public TransactionReversal getTransactionReversal() {
//            return transactionReversal;
//        }
//
//        public void setTransactionReversal(TransactionReversal transactionReversal) {
//            this.transactionReversal = transactionReversal;
//        }
//
//        public TransactionQuery getTransactionQuery() {
//            return transactionQuery;
//        }
//
//        public void setTransactionQuery(TransactionQuery transactionQuery) {
//            this.transactionQuery = transactionQuery;
//        }
//
//        public static class AccountBalance {
//            private String queueTimeoutUrl;
//            private String resultUrl;
//
//            public String getQueueTimeoutUrl() {
//                return queueTimeoutUrl;
//            }
//
//            public void setQueueTimeoutUrl(String queueTimeoutUrl) {
//                this.queueTimeoutUrl = queueTimeoutUrl;
//            }
//
//            public String getResultUrl() {
//                return resultUrl;
//            }
//
//            public void setResultUrl(String resultUrl) {
//                this.resultUrl = resultUrl;
//            }
//        }
//
//        public static class B2B {
//            private String queueTimeoutUrl;
//            private String resultUrl;
//
//            public String getQueueTimeoutUrl() {
//                return queueTimeoutUrl;
//            }
//
//            public void setQueueTimeoutUrl(String queueTimeoutUrl) {
//                this.queueTimeoutUrl = queueTimeoutUrl;
//            }
//
//            public String getResultUrl() {
//                return resultUrl;
//            }
//
//            public void setResultUrl(String resultUrl) {
//                this.resultUrl = resultUrl;
//            }
//        }
//
//        public static class B2C {
//            private String queueTimeoutUrl;
//            private String resultUrl;
//
//            public String getQueueTimeoutUrl() {
//                return queueTimeoutUrl;
//            }
//
//            public void setQueueTimeoutUrl(String queueTimeoutUrl) {
//                this.queueTimeoutUrl = queueTimeoutUrl;
//            }
//
//            public String getResultUrl() {
//                return resultUrl;
//            }
//
//            public void setResultUrl(String resultUrl) {
//                this.resultUrl = resultUrl;
//            }
//        }
//
//        public static class Initiator {
//            private String name;
//            private String shortCode;
//            private String securityCredential;
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
//
//            public String getShortCode() {
//                return shortCode;
//            }
//
//            public void setShortCode(String shortCode) {
//                this.shortCode = shortCode;
//            }
//
//            public String getSecurityCredential() {
//                return securityCredential;
//            }
//
//            public void setSecurityCredential(String securityCredential) {
//                this.securityCredential = securityCredential;
//            }
//        }
//
//        public static class LipaNaMpesa {
//            private String shortCode;
//            private String passkey;
//            private String callbackUrl;
//
//            public String getShortCode() {
//                return shortCode;
//            }
//
//            public void setShortCode(String shortCode) {
//                this.shortCode = shortCode;
//            }
//
//            public String getPasskey() {
//                return passkey;
//            }
//
//            public void setPasskey(String passkey) {
//                this.passkey = passkey;
//            }
//
//            public String getCallbackUrl() {
//                return callbackUrl;
//            }
//
//            public void setCallbackUrl(String callbackUrl) {
//                this.callbackUrl = callbackUrl;
//            }
//        }
//
//        public static class TransactionQuery {
//            private String queueTimeoutUrl;
//            private String resultUrl;
//
//            public String getQueueTimeoutUrl() {
//                return queueTimeoutUrl;
//            }
//
//            public void setQueueTimeoutUrl(String queueTimeoutUrl) {
//                this.queueTimeoutUrl = queueTimeoutUrl;
//            }
//
//            public String getResultUrl() {
//                return resultUrl;
//            }
//
//            public void setResultUrl(String resultUrl) {
//                this.resultUrl = resultUrl;
//            }
//        }
//
//        public static class TransactionReversal {
//            private String queueTimeoutUrl;
//            private String resultUrl;
//
//            public String getQueueTimeoutUrl() {
//                return queueTimeoutUrl;
//            }
//
//            public void setQueueTimeoutUrl(String queueTimeoutUrl) {
//                this.queueTimeoutUrl = queueTimeoutUrl;
//            }
//
//            public String getResultUrl() {
//                return resultUrl;
//            }
//
//            public void setResultUrl(String resultUrl) {
//                this.resultUrl = resultUrl;
//            }
//        }
//    }
//}
