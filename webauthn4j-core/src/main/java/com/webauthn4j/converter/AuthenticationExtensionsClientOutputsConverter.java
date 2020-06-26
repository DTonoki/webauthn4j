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

package com.webauthn4j.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.webauthn4j.converter.util.JsonConverter;
import com.webauthn4j.converter.util.ObjectConverter;
import com.webauthn4j.data.extension.client.AuthenticationExtensionClientOutput;
import com.webauthn4j.data.extension.client.AuthenticationExtensionsClientOutputs;
import com.webauthn4j.data.extension.client.RegistrationExtensionClientOutput;
import com.webauthn4j.util.AssertUtil;

/**
 * Converter for {@link AuthenticationExtensionsClientOutputs}
 */
public class AuthenticationExtensionsClientOutputsConverter {

    // ~ Instance fields
    // ================================================================================================
    private final JsonConverter jsonConverter;

    // ~ Constructors
    // ================================================================================================

    public AuthenticationExtensionsClientOutputsConverter(ObjectConverter objectConverter) {
        AssertUtil.notNull(objectConverter, "objectConverter must not be null");
        this.jsonConverter = objectConverter.getJsonConverter();
    }

    // ~ Methods
    // ================================================================================================

    public AuthenticationExtensionsClientOutputs<RegistrationExtensionClientOutput<?>> convertStringToRegistrationExtensions(String value) {
        if (value == null) {
            return null;
        }
        return jsonConverter.readValue(value, new TypeReference<AuthenticationExtensionsClientOutputs<RegistrationExtensionClientOutput<?>>>(){});
    }

    public AuthenticationExtensionsClientOutputs<AuthenticationExtensionClientOutput<?>> convertStringToAuthenticationExtensions(String value) {
        if (value == null) {
            return null;
        }
        return jsonConverter.readValue(value, new TypeReference<AuthenticationExtensionsClientOutputs<AuthenticationExtensionClientOutput<?>>>(){});
    }

    public String convertRegistrationExtensionsToString(AuthenticationExtensionsClientOutputs<RegistrationExtensionClientOutput<?>> value) {
        if (value == null) {
            return null;
        }
        return jsonConverter.writeValueAsString(value);
    }

    public String convertAuthenticationExtensionsToString(AuthenticationExtensionsClientOutputs<AuthenticationExtensionClientOutput<?>> value) {
        if (value == null) {
            return null;
        }
        return jsonConverter.writeValueAsString(value);
    }
}
