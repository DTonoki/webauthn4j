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

package com.webauthn4j.converter.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.webauthn4j.data.extension.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AuthenticationExtensionsClientInputsDeserializer extends StdDeserializer<AuthenticationExtensionsClientInputs<? extends ExtensionClientInput<?>>> implements ContextualDeserializer {

    private JavaType valueType;

    public AuthenticationExtensionsClientInputsDeserializer() {
        super(AuthenticationExtensionsClientInputs.class);
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        AuthenticationExtensionsClientInputsDeserializer deserializer = new AuthenticationExtensionsClientInputsDeserializer();
        deserializer.valueType = ctxt.getContextualType().containedType(0);
        return deserializer;
    }

    @Override
    public AuthenticationExtensionsClientInputs<? extends ExtensionClientInput<?>> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        if(valueType.isTypeOrSubTypeOf(RegistrationExtensionClientInput.class)){
            return deserializeForRegistration(p);
        }
        if(valueType.isTypeOrSubTypeOf(AuthenticationExtensionClientInput.class)){
            return deserializeForAuthentication(p);
        }
        throw new IllegalArgumentException("Only RegistrationExtensionClientInput and AuthenticationExtensionClientInput are supported.");
    }

    private AuthenticationExtensionsClientInputs<RegistrationExtensionClientInput<?>> deserializeForRegistration(JsonParser p) throws IOException {
        TreeNode treeNode = p.readValueAsTree();
        Iterator<String> fieldNames = treeNode.fieldNames();
        AuthenticationExtensionsClientInputs.BuilderForRegistration builder = new AuthenticationExtensionsClientInputs.BuilderForRegistration();
        Map<String, Object> unknowns = new HashMap<>();
        while(fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            switch (fieldName){
                case CredentialPropertiesExtensionClientInput.ID:
                    CredentialPropertiesExtensionClientInput credProps = p.getCodec().treeToValue(treeNode.get(fieldName), CredentialPropertiesExtensionClientInput.class);
                    builder.setCredProps(credProps);
                    break;
                default:
                    unknowns.put(fieldName, p.getCodec().treeToValue(treeNode.get(fieldName), Object.class));
            }
        }
        builder.setUnknowns(unknowns);
        return builder.build();
    }

    private AuthenticationExtensionsClientInputs<AuthenticationExtensionClientInput<?>> deserializeForAuthentication(JsonParser p) throws IOException {
        TreeNode treeNode = p.readValueAsTree();
        Iterator<String> fieldNames = treeNode.fieldNames();
        AuthenticationExtensionsClientInputs.BuilderForAuthentication builder = new AuthenticationExtensionsClientInputs.BuilderForAuthentication();
        Map<String, Object> unknowns = new HashMap<>();
        while(fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            switch (fieldName){
                case FIDOAppIDExtensionClientInput.ID:
                    FIDOAppIDExtensionClientInput appId = p.getCodec().treeToValue(treeNode.get(fieldName), FIDOAppIDExtensionClientInput.class);
                    builder.setAppId(appId);
                    break;
                default:
                    unknowns.put(fieldName, p.getCodec().treeToValue(treeNode.get(fieldName), Object.class));
            }
        }
        builder.setUnknowns(unknowns);
        return builder.build();
    }

}
