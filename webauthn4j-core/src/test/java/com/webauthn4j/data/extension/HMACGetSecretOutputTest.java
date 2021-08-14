/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webauthn4j.data.extension;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HMACGetSecretOutputTest {

    @Test
    void constructor_test() {
        HMACGetSecretOutput instance1 = new HMACGetSecretOutput(new byte[16], null);
        HMACGetSecretOutput instance2 = new HMACGetSecretOutput(new byte[16]);

        assertThat(instance1)
                .isEqualTo(instance2)
                .hasSameHashCodeAs(instance2);
    }

    @Test
    void getter_test(){
        HMACGetSecretOutput instance = new HMACGetSecretOutput(new byte[16], null);
        assertThat(instance.getOutput1()).isEqualTo(new byte[16]);
        assertThat(instance.getOutput2()).isNull();
    }

    @Test
    void equals_hashCode_test() {
        HMACGetSecretOutput instance1 = new HMACGetSecretOutput(new byte[16], new byte[16]);
        HMACGetSecretOutput instance2 = new HMACGetSecretOutput(new byte[16], new byte[16]);

        assertThat(instance1)
                .isEqualTo(instance2)
                .hasSameHashCodeAs(instance2);
    }

}