/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webauthn4j.test.authenticator.webauthn;


import com.webauthn4j.data.attestation.authenticator.EC2COSEKey;
import com.webauthn4j.data.attestation.statement.COSEAlgorithmIdentifier;
import com.webauthn4j.test.client.RegistrationEmulationOption;
import com.webauthn4j.util.ECUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class TPMAuthenticatorTest {

    private final TPMAuthenticator target = new TPMAuthenticator();

    @Test
    void generateAttestationStatement_test() {
        byte[] signedData = new byte[32];
        RegistrationEmulationOption option = new RegistrationEmulationOption();
        AttestationStatementRequest attestationStatementRequest = new AttestationStatementRequest(signedData, EC2COSEKey.create(ECUtil.createKeyPair(), COSEAlgorithmIdentifier.ES256), new byte[0]);
        assertThatCode(() -> target.createAttestationStatement(attestationStatementRequest, option)).doesNotThrowAnyException();
    }
}