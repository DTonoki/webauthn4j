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

package com.webauthn4j.data.attestation.statement;

import com.webauthn4j.test.TestDataUtil;
import com.webauthn4j.verifier.RegistrationObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@SuppressWarnings("ConstantConditions")
class AppleAnonymousAttestationStatementTest {

    @Test
    void constructor_x5c_null() {
        assertThatThrownBy(() -> new AppleAnonymousAttestationStatement(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void validate() {
        RegistrationObject registrationObject = TestDataUtil.createRegistrationObjectWithAppleAttestation();
        AppleAnonymousAttestationStatement instance = (AppleAnonymousAttestationStatement) registrationObject.getAttestationObject().getAttestationStatement();
        assertThatCode(instance::validate).doesNotThrowAnyException();
    }


    @Test
    void getter_test() {
        RegistrationObject registrationObject = TestDataUtil.createRegistrationObjectWithAppleAttestation();
        AppleAnonymousAttestationStatement instance = (AppleAnonymousAttestationStatement) registrationObject.getAttestationObject().getAttestationStatement();

        assertThat(instance.getFormat()).isEqualTo(AppleAnonymousAttestationStatement.FORMAT);
        assertThat(instance.getX5c()).hasSize(2);
    }


    @Test
    void equals_hashCode_test() {
        RegistrationObject registrationObjectA = TestDataUtil.createRegistrationObjectWithAppleAttestation();
        AppleAnonymousAttestationStatement instanceA = (AppleAnonymousAttestationStatement) registrationObjectA.getAttestationObject().getAttestationStatement();
        RegistrationObject registrationObjectB = TestDataUtil.createRegistrationObjectWithAppleAttestation();
        AppleAnonymousAttestationStatement instanceB = (AppleAnonymousAttestationStatement) registrationObjectB.getAttestationObject().getAttestationStatement();

        assertThat(instanceA)
                .isEqualTo(instanceB)
                .hasSameHashCodeAs(instanceB);
    }


}