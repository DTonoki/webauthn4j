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

package com.webauthn4j.converter.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedClassResolver;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.webauthn4j.data.extension.client.AuthenticationExtensionClientInput;
import com.webauthn4j.util.exception.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

/**
 * Jackson Deserializer for {@link AuthenticationExtensionClientInputDeserializer}
 */
public class AuthenticationExtensionClientInputDeserializer extends StdDeserializer<AuthenticationExtensionClientInput<?>> {

    private final transient Logger logger = LoggerFactory.getLogger(AuthenticationExtensionClientInputDeserializer.class);

    public AuthenticationExtensionClientInputDeserializer() {
        super(AuthenticationExtensionClientInput.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationExtensionClientInput<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        String name = p.getParsingContext().getCurrentName();
        if (name == null) {
            name = p.getParsingContext().getParent().getCurrentName();
        }

        DeserializationConfig config = ctxt.getConfig();
        AnnotatedClass annotatedClass = AnnotatedClassResolver.resolveWithoutSuperTypes(config, AuthenticationExtensionClientInput.class);
        Collection<NamedType> namedTypes = config.getSubtypeResolver().collectAndResolveSubtypesByClass(config, annotatedClass);

        for (NamedType namedType : namedTypes) {
            if (Objects.equals(namedType.getName(), name)) {
                return (AuthenticationExtensionClientInput<?>) ctxt.readValue(p, namedType.getType());
            }
        }

        throw new NotImplementedException();
//        logger.warn("Unknown extension '{}' is contained.", name);
//        return ctxt.readValue(p, UnknownExtensionClientInput.class);
    }
}
