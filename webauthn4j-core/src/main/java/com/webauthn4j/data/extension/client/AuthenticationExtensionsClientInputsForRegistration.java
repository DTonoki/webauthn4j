package com.webauthn4j.data.extension.client;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.webauthn4j.util.CollectionUtil;
import com.webauthn4j.util.exception.NotImplementedException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AuthenticationExtensionsClientInputsForRegistration
        extends AuthenticationExtensionsClientInputsBase<RegistrationExtensionClientInput<?>> {

    private CredentialPropertiesExtensionClientInput credProps;

    @JsonGetter("credProps")
    public CredentialPropertiesExtensionClientInput getCredProps(){
        return credProps;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RegistrationExtensionClientInput<?>> T getExtension(Class<T> tClass){
        if(tClass == CredentialPropertiesExtensionClientInput.class){
            if(getCredProps() == null){
                return null;
            }
            return (T) getCredProps();
        }
        throw new NotImplementedException("dummy"); //TODO
    }

    @Override
    public <V> V getValue(String key) {
        return null; //TODO
    }


    public static class Builder {

        private CredentialPropertiesExtensionClientInput credProps;
        private Map<String, Object> unknowns = new HashMap<>();

        public Builder(){}

        public AuthenticationExtensionsClientInputsForRegistration build(){
            AuthenticationExtensionsClientInputsForRegistration instance = new AuthenticationExtensionsClientInputsForRegistration();
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

        public Builder setCredProps(CredentialPropertiesExtensionClientInput credProps){
            this.credProps = credProps;
            return this;
        }

        public Builder setUnknowns(Map<String, Object> unknowns) {
            this.unknowns = unknowns;
            return this;
        }

    }

}
