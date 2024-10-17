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

package com.webauthn4j.appattest.server;

import com.webauthn4j.data.client.challenge.Challenge;
import com.webauthn4j.test.TestDataUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DCServerPropertyTest {

    @Test
    void constructor_test() {
        Challenge challenge = TestDataUtil.createChallenge();
        DCServerProperty instanceA = new DCServerProperty("8YE23NZS57.com.kayak.travel", challenge);
        DCServerProperty instanceB = new DCServerProperty("8YE23NZS57", "com.kayak.travel", challenge);

        assertThat(instanceA)
                .isEqualTo(instanceB)
                .hasSameHashCodeAs(instanceB);
    }

}