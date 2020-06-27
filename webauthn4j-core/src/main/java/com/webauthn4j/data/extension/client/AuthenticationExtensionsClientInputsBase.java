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

package com.webauthn4j.data.extension.client;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * {@link AuthenticationExtensionsClientInputsBase} is a map containing the client extension input values for
 * zero or more WebAuthn extensions, as defined in §9 WebAuthn Extensions.
 *
 * @see <a href="https://www.w3.org/TR/webauthn-1/#dictdef-authenticationextensionsclientinputs">
 * §5.7. Authentication Extensions Client Inputs (typedef AuthenticationExtensionsClientInputs)</a>
 */
@SuppressWarnings("unused")
public abstract class AuthenticationExtensionsClientInputsBase<E extends ExtensionClientInput<?>> implements AuthenticationExtensionsClientInputs<E>  {

    protected Set<String> keys;
    protected Map<String, Object> unknowns;

    public AuthenticationExtensionsClientInputsBase(){
        keys = Collections.emptySet();
        unknowns = Collections.emptyMap();
    }

    @JsonIgnore
    @Override
    public Set<String> getKeys() {
        return keys;
    }

    @JsonIgnore
    @Override
    public Set<String> getUnknownKeys(){
        return unknowns.keySet();
    }

    @JsonIgnore
    @Override
    public abstract <T extends E> T getExtension(Class<T> tClass);

    @JsonAnyGetter
    private Map<String, Object> getUnknowns(){
        return unknowns;
    }

}
