package com.webauthn4j.data.extension.client;


import com.fasterxml.jackson.annotation.*;
import com.webauthn4j.util.AssertUtil;

import java.io.Serializable;
import java.util.*;

/**
 * {@link com.webauthn4j.data.extension.client.AuthenticationExtensionsClientInputs} is a map containing the client extension input values for
 * zero or more WebAuthn extensions, as defined in §9 WebAuthn Extensions.
 *
 * @see <a href="https://www.w3.org/TR/webauthn-1/#dictdef-authenticationextensionsclientinputs">
 * §5.7. Authentication Extensions Client Inputs (typedef AuthenticationExtensionsClientInputs)</a>
 */
public class AuthenticationExtensionsClientInputs<T extends ExtensionClientInput> implements Serializable {

    @JsonProperty
    private String appid;
    @JsonProperty
    private String appidExclude;
    @JsonProperty
    private Boolean uvm;
    @JsonProperty
    private Boolean credProps;
    @JsonIgnore
    private Map<String, Serializable> unknowns = new HashMap<>();
    @JsonIgnore
    private Map<Class<? extends T>, T> extensions;

    @JsonAnyGetter
    private Map<String, Serializable> getUnknowns(){
        return Collections.unmodifiableMap(this.unknowns);
    }

    @JsonAnySetter
    private void setUnknowns(String name, Serializable value){
        this.unknowns.put(name, value);
    }

    @JsonIgnore
    public Set<String> getKeys() {
        Set<String> keys = new HashSet<>();
        if(appid != null){
            keys.add("appid");
        }
        if(appidExclude != null){
            keys.add("appidExclude");
        }
        if(uvm != null){
            keys.add("uvm");
        }
        if(credProps != null){
            keys.add("credProps");
        }
        keys.addAll(getUnknownKeys());
        return keys;
    }

    @JsonIgnore
    public Set<String> getUnknownKeys() {
        return unknowns.keySet();
    }

    @JsonIgnore
    public Serializable getValue(String key) {
        switch (key){
            case "appid":
                return appid;
            case "appidExclude":
                return appidExclude;
            case "uvm":
                return uvm;
            case "credProps":
                return credProps;
            default:
                return unknowns.get(key);
        }
    }

    @JsonIgnore
    public String getAppid(){
        return this.appid;
    }

    @JsonIgnore
    public String getAppidExclude(){
        return this.appidExclude;
    }

    @JsonIgnore
    public Boolean getUvm(){
        return this.uvm;
    }

    @JsonIgnore
    public Boolean getCredProps(){
        return this.credProps;
    }


    @SuppressWarnings("unchecked")
    public <E extends T> E getExtension(Class<E> tClass) {
        return (E)getExtensions().get(tClass);
    }

    @SuppressWarnings("unchecked")
    @JsonIgnore
    public Map<Class<? extends T>, T> getExtensions(){
        if(extensions == null){
            Map<Class<? extends T>, T> map = new HashMap<>();
            if(appid != null){
                map.put((Class<? extends T>)FIDOAppIDExtensionClientInput.class, (T)new FIDOAppIDExtensionClientInput(appid));
            }
            if(appidExclude != null){
                map.put((Class<? extends T>)FIDOAppIDExclusionExtensionClientInput.class, (T)new FIDOAppIDExclusionExtensionClientInput(appidExclude));
            }
            if(uvm != null){
                map.put((Class<? extends T>)UserVerificationMethodExtensionClientInput.class, (T)new UserVerificationMethodExtensionClientInput(uvm));
            }
            if(credProps != null){
                map.put((Class<? extends T>)CredentialPropertiesExtensionClientInput.class, (T)new CredentialPropertiesExtensionClientInput(credProps));
            }
            extensions = Collections.unmodifiableMap(map);
        }
        return extensions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationExtensionsClientInputs<?> that = (AuthenticationExtensionsClientInputs<?>) o;
        return Objects.equals(appid, that.appid) &&
                Objects.equals(appidExclude, that.appidExclude) &&
                Objects.equals(uvm, that.uvm) &&
                Objects.equals(credProps, that.credProps) &&
                Objects.equals(unknowns, that.unknowns) &&
                Objects.equals(extensions, that.extensions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appid, appidExclude, uvm, credProps, unknowns, extensions);
    }

    public static class BuilderForRegistration {

        private Boolean uvm;
        private Boolean credProps;

        private Map<String, Serializable> unknowns = Collections.emptyMap();

        public AuthenticationExtensionsClientInputs<RegistrationExtensionClientInput> build(){
            AuthenticationExtensionsClientInputs<RegistrationExtensionClientInput> instance = new AuthenticationExtensionsClientInputs<>();
            instance.uvm = this.uvm;
            instance.credProps = this.credProps;
            instance.unknowns = this.unknowns;

            return instance;
        }

        public BuilderForRegistration setUvm(Boolean uvm){
            this.uvm = uvm;
            return this;
        }

        public BuilderForRegistration setCredProps(Boolean credProps){
            this.credProps = credProps;
            return this;
        }

        public BuilderForRegistration setUnknowns(Map<String, Serializable> unknowns){
            AssertUtil.notNull(unknowns, "unknowns must not be null.");
            this.unknowns = unknowns;
            return this;
        }

    }

    public static class BuilderForAuthentication {

        private String appid;
        private String appidExclude;
        private Boolean uvm;

        private Map<String, Serializable> unknowns = Collections.emptyMap();

        public AuthenticationExtensionsClientInputs<AuthenticationExtensionClientInput> build(){
            AuthenticationExtensionsClientInputs<AuthenticationExtensionClientInput> instance = new AuthenticationExtensionsClientInputs<>();
            instance.appid = this.appid;
            instance.appidExclude = this.appidExclude;
            instance.uvm = this.uvm;
            instance.unknowns = this.unknowns;

            return instance;
        }

        public AuthenticationExtensionsClientInputs.BuilderForAuthentication setAppid(String appid){
            this.appid = appid;
            return this;
        }

        public AuthenticationExtensionsClientInputs.BuilderForAuthentication setAppidExclude(String appidExclude){
            this.appidExclude = appidExclude;
            return this;
        }

        public AuthenticationExtensionsClientInputs.BuilderForAuthentication setUvm(Boolean uvm){
            this.uvm = uvm;
            return this;
        }

        public AuthenticationExtensionsClientInputs.BuilderForAuthentication setUnknowns(Map<String, Serializable> unknowns){
            AssertUtil.notNull(unknowns, "unknowns must not be null.");
            this.unknowns = unknowns;
            return this;
        }

    }



}
