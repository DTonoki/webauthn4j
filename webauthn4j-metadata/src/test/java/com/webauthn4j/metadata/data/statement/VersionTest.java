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

package com.webauthn4j.metadata.data.statement;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VersionTest {

    @Test
    void constructor_test(){
        Version version = new Version(1, 2);
        assertThat(version.getMajor()).isEqualTo(1);
        assertThat(version.getMinor()).isEqualTo(2);
    }

    @Test
    void equals_hashCode_test(){
        Version instanceA = new Version(1, 2);
        Version instanceB = new Version(1, 2);
        assertThat(instanceA)
                .isEqualTo(instanceB)
                .hasSameHashCodeAs(instanceB);
    }

}