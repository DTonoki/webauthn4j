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

package com.webauthn4j.converter.jackson.deserializer.cbor;

import com.webauthn4j.data.extension.authenticator.AuthenticationExtensionsAuthenticatorOutputs;
import com.webauthn4j.data.extension.authenticator.ExtensionAuthenticatorOutput;
import org.jetbrains.annotations.NotNull;

/**
 * Internal envelope class for {@link AuthenticationExtensionsAuthenticatorOutputs} deserialization
 */
public class AuthenticationExtensionsAuthenticatorOutputsEnvelope<T extends ExtensionAuthenticatorOutput> {

    private final AuthenticationExtensionsAuthenticatorOutputs<T> authenticationExtensionsAuthenticatorOutputs;
    private final int length;

    AuthenticationExtensionsAuthenticatorOutputsEnvelope(@NotNull AuthenticationExtensionsAuthenticatorOutputs<T> authenticationExtensionsAuthenticatorOutputs, int length) {
        this.authenticationExtensionsAuthenticatorOutputs = authenticationExtensionsAuthenticatorOutputs;
        this.length = length;
    }

    public @NotNull AuthenticationExtensionsAuthenticatorOutputs<T> getAuthenticationExtensionsAuthenticatorOutputs() {
        return authenticationExtensionsAuthenticatorOutputs;
    }

    public int getLength() {
        return length;
    }
}
