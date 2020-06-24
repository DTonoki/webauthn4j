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

package com.webauthn4j.data.extension.client;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webauthn4j.util.CollectionUtil;

import java.util.*;

/**
 * Map containing the client extension output values for zero or more WebAuthn
 * extensions, as defined in §9 WebAuthn Extensions.
 *
 * @see <a href="https://www.w3.org/TR/webauthn-1/#dictdef-authenticationextensionsclientoutputs">§5.8. Authentication Extensions Client Outputs</a>
 */
public class AuthenticationExtensionsClientOutputs<V extends ExtensionClientOutput<?>> {

    private Set<String> keys;
    private FIDOAppIDExtensionClientOutput appId;
    private CredentialPropertiesExtensionClientOutput credProps;
    private Map<String, Object> unknowns;

    public AuthenticationExtensionsClientOutputs(){
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
        if(key.equals(FIDOAppIDExtensionClientOutput.ID)){
            return (T)getAppId().getValue();
        }
        return (T) unknowns.get(key);
    }

    @JsonGetter("appid") // "appid" is not typo
    public FIDOAppIDExtensionClientOutput getAppId(){
        return appId;
    }

    @JsonGetter("credProps")
    public CredentialPropertiesExtensionClientOutput getCredProps(){
        return credProps;
    }

    @JsonAnyGetter
    private Map<String, Object> getUnknowns(){
        return unknowns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationExtensionsClientOutputs<?> that = (AuthenticationExtensionsClientOutputs<?>) o;
        return Objects.equals(keys, that.keys) &&
                Objects.equals(appId, that.appId) &&
                Objects.equals(credProps, that.credProps) &&
                Objects.equals(unknowns, that.unknowns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keys, appId, credProps, unknowns);
    }

    public static class BuilderForRegistration {

        private Map<String, Object> unknowns = new HashMap<>();
        private CredentialPropertiesExtensionClientOutput credProps;

        public BuilderForRegistration(){}

        public AuthenticationExtensionsClientOutputs<RegistrationExtensionClientOutput<?>> build(){
            AuthenticationExtensionsClientOutputs<RegistrationExtensionClientOutput<?>> instance = new AuthenticationExtensionsClientOutputs<>();
            HashSet<String> keys = new HashSet<>();
            if(this.credProps != null){
                keys.add(CredentialPropertiesExtensionClientOutput.ID);
            }
            if(unknowns != null){
                keys.addAll(unknowns.keySet());
            }
            instance.keys = CollectionUtil.unmodifiableSet(keys);
            instance.credProps = this.credProps;
            instance.unknowns = CollectionUtil.unmodifiableMap(unknowns);
            return instance;
        }

        public AuthenticationExtensionsClientOutputs.BuilderForRegistration setCredProps(CredentialPropertiesExtensionClientOutput credProps){
            this.credProps = credProps;
            return this;
        }

        public AuthenticationExtensionsClientOutputs.BuilderForRegistration setUnknowns(Map<String, Object> unknowns) {
            this.unknowns = unknowns;
            return this;
        }

    }


    // As members of AuthenticationExtensionsClientOutputs will increase in the future,
    // builder pattern is applied here.
    public static class BuilderForAuthentication{

        private FIDOAppIDExtensionClientOutput appId;
        private Map<String, Object> unknowns = new HashMap<>();

        public BuilderForAuthentication(){}

        public AuthenticationExtensionsClientOutputs<AuthenticationExtensionClientOutput<?>> build(){
            AuthenticationExtensionsClientOutputs<AuthenticationExtensionClientOutput<?>> instance = new AuthenticationExtensionsClientOutputs<>();
            HashSet<String> keys = new HashSet<>();
            if(this.appId != null){
                keys.add(FIDOAppIDExtensionClientOutput.ID);
            }
            if(unknowns != null){
                keys.addAll(unknowns.keySet());
            }
            instance.keys = CollectionUtil.unmodifiableSet(keys);
            instance.appId = this.appId;
            instance.unknowns = CollectionUtil.unmodifiableMap(unknowns);
            return instance;
        }

        public AuthenticationExtensionsClientOutputs.BuilderForAuthentication setAppId(FIDOAppIDExtensionClientOutput appId){
            this.appId = appId;
            return this;
        }

        public AuthenticationExtensionsClientOutputs.BuilderForAuthentication setUnknowns(Map<String, Object> unknowns) {
            this.unknowns = unknowns;
            return this;
        }
    }

}
