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

package com.webauthn4j.anchor;

import com.webauthn4j.data.attestation.authenticator.AAGUID;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.TrustAnchor;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Deprecated
class KeyStoreFileTrustAnchorsProviderTest {

    private KeyStoreFileTrustAnchorsProvider target;

    @Test
    void provide_test() throws Exception {
        target = new KeyStoreFileTrustAnchorsProvider();
        Path path = Paths.get(ClassLoader.getSystemResource("com/webauthn4j/anchor/KeyStoreFileTrustAnchorsProviderTest/test.jks").toURI());
        target.setKeyStore(path);
        target.setPassword("password");

        Map<AAGUID, Set<TrustAnchor>> trustAnchors = target.provide();
        assertThat(trustAnchors.keySet()).containsExactly(AAGUID.NULL);
        assertThat(trustAnchors).isNotEmpty();
    }

    @Test
    void provide_test_with_invalid_path() {
        target = new KeyStoreFileTrustAnchorsProvider();
        Path path = Paths.get("invalid.path.to.jks");
        target.setKeyStore(path);
        target.setPassword("password");

        assertThrows(KeyStoreException.class,
                () -> target.provide()
        );
    }
}
