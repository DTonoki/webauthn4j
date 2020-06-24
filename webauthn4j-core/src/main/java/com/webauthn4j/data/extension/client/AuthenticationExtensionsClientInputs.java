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
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webauthn4j.util.CollectionUtil;

import java.util.*;

/**
 * {@link com.webauthn4j.data.extension.client.AuthenticationExtensionsClientInputs} is a map containing the client extension input values for
 * zero or more WebAuthn extensions, as defined in §9 WebAuthn Extensions.
 *
 * @see <a href="https://www.w3.org/TR/webauthn-1/#dictdef-authenticationextensionsclientinputs">
 * §5.7. Authentication Extensions Client Inputs (typedef AuthenticationExtensionsClientInputs)</a>
 */
@SuppressWarnings("unused")
public class AuthenticationExtensionsClientInputs<V extends ExtensionClientInput<?>>  {

    private Set<String> keys;
    private FIDOAppIDExtensionClientInput appId;
    private CredentialPropertiesExtensionClientInput credProps;
    private Map<String, Object> unknowns;

    public AuthenticationExtensionsClientInputs(){
        keys = Collections.emptySet();
        unknowns = Collections.emptyMap();
    }

    @JsonIgnore
    public Set<String> getKeys() {
        return keys;
    }

    @JsonIgnore
    public Set<String> getUnknownKeys(){
        return unknowns.keySet();
    }

    @SuppressWarnings("unchecked")
    @JsonIgnore
    public <T> T getValue(String key){
        if(key.equals(FIDOAppIDExtensionClientInput.ID)){
            if(getAppId() == null){
                return null;
            }
            return (T) getAppId().getValue();
        }
        return (T) unknowns.get(key);
    }

    @JsonGetter("appid") //"appid" is not typo
    public FIDOAppIDExtensionClientInput getAppId(){
        return appId;
    }

    @JsonGetter("credProps")
    public CredentialPropertiesExtensionClientInput getCredProps(){
        return credProps;
    }

    @JsonAnyGetter
    private Map<String, Object> getUnknowns(){
        return unknowns;
    }

    public static class BuilderForRegistration {

        private CredentialPropertiesExtensionClientInput credProps;
        private Map<String, Object> unknowns = new HashMap<>();

        public BuilderForRegistration(){}

        public AuthenticationExtensionsClientInputs<RegistrationExtensionClientInput<?>> build(){
            AuthenticationExtensionsClientInputs<RegistrationExtensionClientInput<?>> instance = new AuthenticationExtensionsClientInputs<>();
            HashSet<String> keys = new HashSet<>();
            if(this.credProps != null){
                keys.add(CredentialPropertiesExtensionClientInput.ID);
            }
            if(unknowns != null){
                keys.addAll(unknowns.keySet());
            }
            instance.keys = CollectionUtil.unmodifiableSet(keys);
            instance.credProps = this.credProps;
            instance.unknowns = CollectionUtil.unmodifiableMap(unknowns);
            return instance;
        }

        public BuilderForRegistration setCredProps(CredentialPropertiesExtensionClientInput credProps){
            this.credProps = credProps;
            return this;
        }

        public BuilderForRegistration setUnknowns(Map<String, Object> unknowns) {
            this.unknowns = unknowns;
            return this;
        }

    }

    // As members of AuthenticationExtensionsClientInputs will increase in the future,
    // builder pattern is applied here.
    public static class BuilderForAuthentication{

        private FIDOAppIDExtensionClientInput appId;
        private Map<String, Object> unknowns = new HashMap<>();

        public BuilderForAuthentication(){}

        public AuthenticationExtensionsClientInputs<AuthenticationExtensionClientInput<?>> build(){
            AuthenticationExtensionsClientInputs<AuthenticationExtensionClientInput<?>> instance = new AuthenticationExtensionsClientInputs<>();
            HashSet<String> keys = new HashSet<>();
            if(this.appId != null){
                keys.add(FIDOAppIDExtensionClientInput.ID);
            }
            if(unknowns != null){
                keys.addAll(unknowns.keySet());
            }
            instance.keys = CollectionUtil.unmodifiableSet(keys);
            instance.appId = this.appId;
            instance.unknowns = CollectionUtil.unmodifiableMap(unknowns);
            return instance;
        }

        public BuilderForAuthentication setAppId(FIDOAppIDExtensionClientInput appId){
            this.appId = appId;
            return this;
        }

        public BuilderForAuthentication setUnknowns(Map<String, Object> unknowns) {
            this.unknowns = unknowns;
            return this;
        }
    }

}
