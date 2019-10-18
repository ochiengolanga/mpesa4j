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

//import com.github.ochiengolanga.mpesa4j.Mpesa;
//import com.github.ochiengolanga.mpesa4j.MpesaFactory;
//import com.github.ochiengolanga.mpesa4j.config.ConfigurationBuilder;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@ConditionalOnClass({ MpesaFactory.class, Mpesa.class })
//@EnableConfigurationProperties(Mpesa4jProperties.class)
//public class Mpesa4jAutoConfiguration {
//
//    private static Log log = LogFactory.getLog(Mpesa4jAutoConfiguration.class);
//
//    @Autowired
//    private Mpesa4jProperties properties;
//
//    @Bean
//    @ConditionalOnMissingBean
//    public MpesaFactory mpesaFactory(){
//        if (this.properties.getConsumerKey() == null || this.properties.getConsumerSecret() == null)
//        {
//            String msg = "Mpesa4j properties not configured properly." +
//                    " Please check mpesa4j.* properties settings in configuration file.";
//            log.error(msg);
//            throw new RuntimeException(msg);
//        }
//
//        ConfigurationBuilder cb = new ConfigurationBuilder();
//        cb.setDebugEnabled(properties.getDebug())
//                .setConsumerKey(properties.getConsumerKey())
//                .setConsumerSecret(properties.getConsumerSecret())
//                .setLipaNaMpesaKey(properties.getLipaNaMpesa().getPasskey());
//
//        return new MpesaFactory(cb.build());
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public Mpesa mpesa(MpesaFactory mpesaFactory){
//        return mpesaFactory.getInstance();
//    }
//
//}
