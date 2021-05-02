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

package com.webauthn4j.data;

import com.webauthn4j.converter.exception.DataConversionException;
import com.webauthn4j.converter.util.JsonConverter;
import com.webauthn4j.converter.util.ObjectConverter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MessageDigestAlgorithmTest {

    private final JsonConverter jsonConverter = new ObjectConverter().getJsonConverter();

    @Test
    void test() {
        assertThat(MessageDigestAlgorithm.create("SHA-256")).isEqualTo(MessageDigestAlgorithm.SHA256);
    }

    @Test
    void deserialize_test_with_invalid_value() {
        assertThatThrownBy(
                () -> jsonConverter.readValue("{\"alg\": -1}", MessageDigestAlgorithmTest.TestDTO.class)
        ).isInstanceOf(DataConversionException.class);
    }

    static class TestDTO {
        @SuppressWarnings("WeakerAccess")
        public MessageDigestAlgorithm alg;
    }

}