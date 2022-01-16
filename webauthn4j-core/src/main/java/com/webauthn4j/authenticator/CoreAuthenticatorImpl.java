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

package com.webauthn4j.authenticator;

import com.webauthn4j.data.CoreRegistrationData;
import com.webauthn4j.data.attestation.authenticator.AttestedCredentialData;
import com.webauthn4j.data.attestation.statement.AttestationStatement;
import com.webauthn4j.data.extension.authenticator.AuthenticationExtensionsAuthenticatorOutputs;
import com.webauthn4j.data.extension.authenticator.RegistrationExtensionAuthenticatorOutput;
import com.webauthn4j.util.AssertUtil;
import com.webauthn4j.util.ConstUtil;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Objects;

/**
 * An {@link CoreAuthenticator} implementation
 */
public class CoreAuthenticatorImpl implements CoreAuthenticator {

    private static final String ATTESTED_CREDENTIAL_DATA_MUST_NOT_BE_NULL = "attestedCredentialData must not be null";

    //~ Instance fields ================================================================================================
    private AttestedCredentialData attestedCredentialData;
    private AttestationStatement attestationStatement;
    private long counter;
    private AuthenticationExtensionsAuthenticatorOutputs<RegistrationExtensionAuthenticatorOutput> authenticatorExtensions;

    public CoreAuthenticatorImpl(@NonNull AttestedCredentialData attestedCredentialData,
                                 @Nullable AttestationStatement attestationStatement,
                                 long counter,
                                 @Nullable AuthenticationExtensionsAuthenticatorOutputs<RegistrationExtensionAuthenticatorOutput> authenticatorExtensions) {

        AssertUtil.notNull(attestedCredentialData, ATTESTED_CREDENTIAL_DATA_MUST_NOT_BE_NULL);

        this.attestedCredentialData = attestedCredentialData;
        this.attestationStatement = attestationStatement;
        setCounter(counter);
        this.authenticatorExtensions = authenticatorExtensions;
    }

    public static @NonNull CoreAuthenticatorImpl createFromCoreRegistrationData(@NonNull CoreRegistrationData coreRegistrationData) {

        AssertUtil.notNull(coreRegistrationData.getAttestationObject(), "attestationObject must not be null");
        AssertUtil.notNull(coreRegistrationData.getAttestationObject().getAuthenticatorData(), "authenticatorData must not be null");
        AssertUtil.notNull(coreRegistrationData.getAttestationObject().getAuthenticatorData().getAttestedCredentialData(), ATTESTED_CREDENTIAL_DATA_MUST_NOT_BE_NULL);

        return new CoreAuthenticatorImpl(
                coreRegistrationData.getAttestationObject().getAuthenticatorData().getAttestedCredentialData(),
                coreRegistrationData.getAttestationObject().getAttestationStatement(),
                coreRegistrationData.getAttestationObject().getAuthenticatorData().getSignCount(),
                coreRegistrationData.getAttestationObject().getAuthenticatorData().getExtensions());
    }

    @Override
    public @NonNull AttestedCredentialData getAttestedCredentialData() {
        return attestedCredentialData;
    }

    public void setAttestedCredentialData(@NonNull AttestedCredentialData attestedCredentialData) {
        AssertUtil.notNull(attestedCredentialData, ATTESTED_CREDENTIAL_DATA_MUST_NOT_BE_NULL);
        this.attestedCredentialData = attestedCredentialData;
    }

    @Override
    public @Nullable AttestationStatement getAttestationStatement() {
        return attestationStatement;
    }

    public void setAttestationStatement(@Nullable AttestationStatement attestationStatement) {
        this.attestationStatement = attestationStatement;
    }

    @Override
    public long getCounter() {
        return this.counter;
    }

    @Override
    public void setCounter(long value) {
        if (value > ConstUtil.UINT_MAX_VALUE) {
            throw new IllegalArgumentException("[Assertion failed] - this argument is unsigned int. it must not exceed 4294967295.");
        }
        if (value < 0) {
            throw new IllegalArgumentException("[Assertion failed] - this argument is unsigned int. it must not be negative value.");
        }
        this.counter = value;
    }

    @Override
    public @Nullable AuthenticationExtensionsAuthenticatorOutputs<RegistrationExtensionAuthenticatorOutput> getAuthenticatorExtensions() {
        return authenticatorExtensions;
    }

    public void setAuthenticatorExtensions(@Nullable AuthenticationExtensionsAuthenticatorOutputs<RegistrationExtensionAuthenticatorOutput> authenticatorExtensions) {
        this.authenticatorExtensions = authenticatorExtensions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoreAuthenticatorImpl that = (CoreAuthenticatorImpl) o;
        return counter == that.counter &&
                Objects.equals(attestedCredentialData, that.attestedCredentialData) &&
                Objects.equals(attestationStatement, that.attestationStatement) &&
                Objects.equals(authenticatorExtensions, that.authenticatorExtensions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attestedCredentialData, attestationStatement, counter, authenticatorExtensions);
    }
}
