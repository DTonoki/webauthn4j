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

package com.webauthn4j.util;

import org.junit.jupiter.api.Test;

import java.security.cert.CertPathValidator;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class CertificateUtilTest {

    @Test
    void generateCertPathValidator_test() {
        CertPathValidator certPathValidator = CertificateUtil.createCertPathValidator();
        assertThat(certPathValidator).isNotNull();
    }

    @Test
    void createPKIXParameters_test() {
        HashSet<TrustAnchor> trustAnchors = new HashSet<>();
        trustAnchors.add(new TrustAnchor(mock(X509Certificate.class), null));
        PKIXParameters pkixParameters = CertificateUtil.createPKIXParameters(trustAnchors);
        assertThat(pkixParameters).isNotNull();
    }

    @Test
    void createPKIXParameters_test_with_empty_trustAnchors() {
        HashSet<TrustAnchor> trustAnchors = new HashSet<>();
        Throwable t = assertThrows(IllegalArgumentException.class,
                () -> CertificateUtil.createPKIXParameters(trustAnchors)
        );
        assertThat(t).hasMessage("trustAnchors is required; it must not be empty");
    }

    @Test
    void createKeystore_test() {
        CertificateUtil.createKeyStore();
    }
}
