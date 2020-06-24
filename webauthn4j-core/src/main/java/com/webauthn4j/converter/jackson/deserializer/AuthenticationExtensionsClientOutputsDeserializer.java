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

public class AuthenticationExtensionsClientOutputsDeserializer extends StdDeserializer<AuthenticationExtensionsClientOutputs<? extends ExtensionClientOutput<?>>> implements ContextualDeserializer {

    private JavaType valueType;

    public AuthenticationExtensionsClientOutputsDeserializer() {
        super(AuthenticationExtensionsClientOutputs.class);
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        AuthenticationExtensionsClientOutputsDeserializer deserializer = new AuthenticationExtensionsClientOutputsDeserializer();
        deserializer.valueType = ctxt.getContextualType().containedType(0);
        return deserializer;
    }

    @Override
    public AuthenticationExtensionsClientOutputs<? extends ExtensionClientOutput<?>> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        if(valueType.isTypeOrSubTypeOf(RegistrationExtensionClientOutput.class)){
            return deserializeForRegistration(p);
        }
        if(valueType.isTypeOrSubTypeOf(AuthenticationExtensionClientOutput.class)){
            return deserializeForAuthentication(p);
        }
        throw new IllegalArgumentException("Only RegistrationExtensionClientOutput and AuthenticationExtensionClientOutput are supported.");
    }

    private AuthenticationExtensionsClientOutputs<RegistrationExtensionClientOutput<?>> deserializeForRegistration(JsonParser p) throws IOException {
        TreeNode treeNode = p.readValueAsTree();
        Iterator<String> fieldNames = treeNode.fieldNames();
        AuthenticationExtensionsClientOutputs.BuilderForRegistration builder = new AuthenticationExtensionsClientOutputs.BuilderForRegistration();
        Map<String, Object> unknowns = new HashMap<>();
        while(fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            switch (fieldName){
                case CredentialPropertiesExtensionClientOutput.ID:
                    CredentialPropertiesExtensionClientOutput credProps = p.getCodec().treeToValue(treeNode.get(fieldName), CredentialPropertiesExtensionClientOutput.class);
                    builder.setCredProps(credProps);
                    break;
                default:
                    unknowns.put(fieldName, p.getCodec().treeToValue(treeNode.get(fieldName), Object.class));
            }
        }
        builder.setUnknowns(unknowns);
        return builder.build();
    }

    private AuthenticationExtensionsClientOutputs<AuthenticationExtensionClientOutput<?>> deserializeForAuthentication(JsonParser p) throws IOException {
        TreeNode treeNode = p.readValueAsTree();
        Iterator<String> fieldNames = treeNode.fieldNames();
        AuthenticationExtensionsClientOutputs.BuilderForAuthentication builder = new AuthenticationExtensionsClientOutputs.BuilderForAuthentication();
        Map<String, Object> unknowns = new HashMap<>();
        while(fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            switch (fieldName){
                case FIDOAppIDExtensionClientOutput.ID:
                    FIDOAppIDExtensionClientOutput appId = p.getCodec().treeToValue(treeNode.get(fieldName), FIDOAppIDExtensionClientOutput.class);
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
