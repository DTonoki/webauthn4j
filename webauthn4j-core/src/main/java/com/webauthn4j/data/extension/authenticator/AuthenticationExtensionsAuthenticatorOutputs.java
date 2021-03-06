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


package com.webauthn4j.data.extension.authenticator;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.webauthn4j.converter.jackson.deserializer.CredentialProtectionPolicyByteDeserializer;
import com.webauthn4j.converter.jackson.serializer.CredentialProtectionPolicyByteSerializer;
import com.webauthn4j.data.extension.CredentialProtectionPolicy;
import com.webauthn4j.data.extension.UvmEntries;
import com.webauthn4j.util.AssertUtil;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.Serializable;
import java.util.*;

public class AuthenticationExtensionsAuthenticatorOutputs<T extends ExtensionAuthenticatorOutput> implements Serializable {

    @JsonProperty
    private UvmEntries uvm;
    @JsonSerialize(using = CredentialProtectionPolicyByteSerializer.class)
    @JsonDeserialize(using = CredentialProtectionPolicyByteDeserializer.class)
    @JsonProperty
    private CredentialProtectionPolicy credProtect;
    @JsonIgnore
    private final Map<String, Serializable> unknowns = new HashMap<>();
    @JsonIgnore
    private Map<Class<? extends T>, T> extensions;

    @JsonAnySetter
    private void setUnknowns(@NonNull String name, @Nullable Serializable value) {
        this.unknowns.put(name, value);
    }

    @JsonAnyGetter
    private @NonNull Map<String, Serializable> getUnknowns() {
        return this.unknowns;
    }

    @JsonIgnore
    public @NonNull Set<String> getKeys() {
        Set<String> keys = new HashSet<>();
        if (uvm != null) {
            keys.add("uvm");
        }
        if (credProtect != null) {
            keys.add("credProtect");
        }
        keys.addAll(getUnknownKeys());
        return keys;
    }

    @JsonIgnore
    public @NonNull Set<String> getUnknownKeys() {
        return unknowns.keySet();
    }

    @JsonIgnore
    public @Nullable Object getValue(@NonNull String key) {
        switch (key) {
            case "uvm":
                return uvm;
            case "credProtect":
                return credProtect;
            default:
                return unknowns.get(key);
        }
    }

    @JsonIgnore
    public @Nullable UvmEntries getUvm() {
        return this.uvm;
    }

    @JsonIgnore
    public @Nullable CredentialProtectionPolicy getCredProtect() {
        return credProtect;
    }

    @SuppressWarnings("unchecked")
    public @Nullable <E extends T> E getExtension(@NonNull Class<E> tClass) {
        return (E) getExtensions().get(tClass);
    }

    @SuppressWarnings("unchecked")
    @JsonIgnore
    public @NonNull Map<Class<? extends T>, T> getExtensions() {
        if (extensions == null) {
            Map<Class<? extends T>, T> map = new HashMap<>();
            if (uvm != null) {
                map.put((Class<? extends T>) UserVerificationMethodExtensionAuthenticatorOutput.class, (T) new UserVerificationMethodExtensionAuthenticatorOutput(uvm));
            }
            if (credProtect != null) {
                map.put((Class<? extends T>) CredentialProtectionExtensionAuthenticatorOutput.class, (T) new CredentialProtectionExtensionAuthenticatorOutput(credProtect));
            }
            extensions = Collections.unmodifiableMap(map);
        }
        return extensions;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationExtensionsAuthenticatorOutputs<?> that = (AuthenticationExtensionsAuthenticatorOutputs<?>) o;
        return Objects.equals(uvm, that.uvm) &&
                Objects.equals(credProtect, that.credProtect) &&
                Objects.equals(unknowns, that.unknowns) &&
                Objects.equals(extensions, that.extensions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uvm, credProtect, unknowns, extensions);
    }

    public static class BuilderForRegistration {

        private UvmEntries uvm;
        private CredentialProtectionPolicy credProtect;

        private final Map<String, Serializable> unknowns = new HashMap<>();

        public @NonNull AuthenticationExtensionsAuthenticatorOutputs<RegistrationExtensionAuthenticatorOutput> build() {
            AuthenticationExtensionsAuthenticatorOutputs<RegistrationExtensionAuthenticatorOutput> instance = new AuthenticationExtensionsAuthenticatorOutputs<>();
            instance.uvm = this.uvm;
            instance.credProtect = this.credProtect;
            instance.unknowns.putAll(this.unknowns);

            return instance;
        }

        public @NonNull BuilderForRegistration setUvm(@Nullable UvmEntries uvm) {
            this.uvm = uvm;
            return this;
        }

        public @NonNull BuilderForRegistration setCredProtect(@Nullable CredentialProtectionPolicy credProtect) {
            this.credProtect = credProtect;
            return this;
        }

        public @NonNull BuilderForRegistration set(@NonNull String key, @Nullable Serializable value) {
            AssertUtil.notNull(key, "key must not be null.");
            AssertUtil.notNull(value, "value must not be null.");
            unknowns.put(key, value);
            return this;
        }

    }

    public static class BuilderForAuthentication {

        private UvmEntries uvm;

        private final Map<String, Serializable> unknowns = new HashMap<>();


        public @NonNull AuthenticationExtensionsAuthenticatorOutputs<AuthenticationExtensionAuthenticatorOutput> build() {
            AuthenticationExtensionsAuthenticatorOutputs<AuthenticationExtensionAuthenticatorOutput> instance = new AuthenticationExtensionsAuthenticatorOutputs<>();
            instance.uvm = this.uvm;
            instance.unknowns.putAll(this.unknowns);

            return instance;
        }

        public @NonNull BuilderForAuthentication setUvm(@Nullable UvmEntries uvm) {
            this.uvm = uvm;
            return this;
        }

        public @NonNull BuilderForAuthentication set(@NonNull String key, @Nullable Serializable value) {
            AssertUtil.notNull(key, "key must not be null.");
            AssertUtil.notNull(value, "value must not be null.");
            unknowns.put(key, value);
            return this;
        }


    }

}
