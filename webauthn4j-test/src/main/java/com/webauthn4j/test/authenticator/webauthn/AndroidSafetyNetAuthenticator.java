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

package com.webauthn4j.test.authenticator.webauthn;

import com.webauthn4j.converter.util.CborConverter;
import com.webauthn4j.data.attestation.authenticator.AAGUID;
import com.webauthn4j.data.attestation.statement.AndroidSafetyNetAttestationStatement;
import com.webauthn4j.data.attestation.statement.AttestationCertificatePath;
import com.webauthn4j.data.attestation.statement.AttestationStatement;
import com.webauthn4j.data.attestation.statement.Response;
import com.webauthn4j.data.jws.JWAIdentifier;
import com.webauthn4j.data.jws.JWS;
import com.webauthn4j.data.jws.JWSFactory;
import com.webauthn4j.data.jws.JWSHeader;
import com.webauthn4j.test.AttestationCertificateBuilder;
import com.webauthn4j.test.CACertificatePath;
import com.webauthn4j.test.TestAttestationUtil;
import com.webauthn4j.test.client.RegistrationEmulationOption;
import com.webauthn4j.util.Base64Util;
import com.webauthn4j.util.MessageDigestUtil;
import com.webauthn4j.util.WIP;

import javax.security.auth.x500.X500Principal;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@WIP
public class AndroidSafetyNetAuthenticator extends WebAuthnModelAuthenticator {

    private KeyPair attestationKeyPair;
    private CACertificatePath caCertificatePath;

    private JWSFactory jwsFactory;

    public AndroidSafetyNetAuthenticator(AAGUID aaguid,
                                         int counter,
                                         KeyPair attestationKeyPair,
                                         CACertificatePath caCertificatePath,
                                         boolean capableOfUserVerification,
                                         CborConverter cborConverter) {
        super(aaguid, counter, capableOfUserVerification, cborConverter);
        this.attestationKeyPair = attestationKeyPair;
        this.caCertificatePath = caCertificatePath;
        this.jwsFactory = new JWSFactory(cborConverter.getJsonConverter());
    }

    public AndroidSafetyNetAuthenticator(){
        this(AAGUID.ZERO,
                0,
                new KeyPair(TestAttestationUtil.load3tierTestAuthenticatorAttestationPublicKey(),
                        TestAttestationUtil.load3tierTestAuthenticatorAttestationPrivateKey()),
                TestAttestationUtil.load3tierTestCACertificatePath(),
                true,
                new CborConverter());
    }

    @Override
    protected AttestationStatement createAttestationStatement(AttestationStatementRequest attestationStatementRequest, RegistrationEmulationOption registrationEmulationOption) {
        X509Certificate attestationCertificate = createAttestationCertificate(TestAttestationUtil.load3tierTestIntermediateCAPrivateKey(), attestationKeyPair.getPublic());
        List<X509Certificate> attestationCertificates = new ArrayList<>();
        attestationCertificates.add(attestationCertificate);
        attestationCertificates.addAll(caCertificatePath);
        AttestationCertificatePath attestationCertificatePath = new AttestationCertificatePath(attestationCertificates);
        JWSHeader jwsHeader = new JWSHeader(JWAIdentifier.ES256, attestationCertificatePath);
        String nonce = Base64Util.encodeToString(MessageDigestUtil.createSHA256().digest(attestationStatementRequest.getSignedData()));
        long timestampMs = Instant.now().toEpochMilli();
        String apkPackageName = "com.android.keystore.androidkeystoredemo";
        String[] apkCertificateDigestSha256 = new String[]{"bsb4/WQdaaOWYCd/j9OJiQpg7b0iwFgAc/zzA1tCfwE="};
        String apkDigestSha256 = "dM/LUHSI9SkQhZHHpQWRnzJ3MvvB2ANSauqYAAbS2Jg=";
        boolean ctsProfileMatch = true;
        boolean basicIntegrity = true;
        String advice = null;
        Response response = new Response(nonce, timestampMs, apkPackageName, apkCertificateDigestSha256,apkDigestSha256, ctsProfileMatch, basicIntegrity, advice);

        String ver = "12685023";
        JWS<Response> jws = jwsFactory.create(jwsHeader, response, attestationKeyPair.getPrivate());
        if (registrationEmulationOption.isSignatureOverrideEnabled()) {
            jws = jwsFactory.create(jws.getHeader(), jws.getPayload(), registrationEmulationOption.getSignature());
        }
        return new AndroidSafetyNetAttestationStatement(ver, jws);
    }

    public X509Certificate createAttestationCertificate(PrivateKey issuerPrivateKey, PublicKey publicKey) {

        X509Certificate issuerCertificate = caCertificatePath.get(0);
        AttestationCertificateBuilder builder = new AttestationCertificateBuilder(issuerCertificate, new X500Principal("CN=attest.android.com"), publicKey);

        builder.addBasicConstraintsExtension();
        builder.addKeyUsageExtension();
        return builder.build(issuerPrivateKey);
    }
}
