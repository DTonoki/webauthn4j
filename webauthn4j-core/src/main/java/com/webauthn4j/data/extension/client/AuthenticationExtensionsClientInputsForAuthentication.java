package com.webauthn4j.data.extension.client;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.webauthn4j.util.CollectionUtil;
import com.webauthn4j.util.exception.NotImplementedException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AuthenticationExtensionsClientInputsForAuthentication
        extends AuthenticationExtensionsClientInputsBase<AuthenticationExtensionClientInput<?>> {

    private FIDOAppIDExtensionClientInput appId;

    @JsonGetter("appid") //"appid" is not typo
    public FIDOAppIDExtensionClientInput getAppId(){
        return appId;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends AuthenticationExtensionClientInput<?>> T getExtension(Class<T> tClass) {
        if(tClass == FIDOAppIDExtensionClientInput.class){
            if(getAppId() == null){
                return null;
            }
            return (T)getAppId();
        }
        throw new NotImplementedException("dummy"); //TODO
    }

    @Override
    public <V> V getValue(String key) {
        return null; //TODO
    }

    // As members of AuthenticationExtensionsClientInputs will increase in the future,
    // builder pattern is applied here.
    public static class Builder {

        private FIDOAppIDExtensionClientInput appId;
        private Map<String, Object> unknowns = new HashMap<>();

        public Builder(){}

        public AuthenticationExtensionsClientInputsForAuthentication build(){
            AuthenticationExtensionsClientInputsForAuthentication instance = new AuthenticationExtensionsClientInputsForAuthentication();
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

        public Builder setAppId(FIDOAppIDExtensionClientInput appId){
            this.appId = appId;
            return this;
        }

        public Builder setUnknowns(Map<String, Object> unknowns) {
            this.unknowns = unknowns;
            return this;
        }
    }


}
