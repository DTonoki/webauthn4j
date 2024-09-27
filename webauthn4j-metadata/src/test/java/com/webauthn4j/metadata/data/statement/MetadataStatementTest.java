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

package com.webauthn4j.metadata.data.statement;

import com.webauthn4j.converter.util.JsonConverter;
import com.webauthn4j.converter.util.ObjectConverter;
import com.webauthn4j.data.*;
import com.webauthn4j.data.attestation.authenticator.AAGUID;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MetadataStatementTest {

    private final JsonConverter jsonConverter = new ObjectConverter().getJsonConverter();

    @SuppressWarnings("java:S5961")
    @Test
    void test(){
        MetadataStatement metadataStatement = createMetadataStatement();
        assertThat(metadataStatement.getLegalHeader()).isEqualTo("https://fidoalliance.org/metadata/metadata-statement-legal-header/");
        assertThat(metadataStatement.getAaid()).isNull();
        assertThat(metadataStatement.getAaguid()).isEqualTo(new AAGUID("0132d110-bf4e-4208-a403-ab4f5f12efe5"));
        assertThat(metadataStatement.getAttestationCertificateKeyIdentifiers()).isNull();
        assertThat(metadataStatement.getDescription()).isEqualTo("FIDO Alliance Sample FIDO2 Authenticator");
        assertThat(metadataStatement.getAuthenticatorVersion()).isEqualTo(5);
        assertThat(metadataStatement.getProtocolFamily()).isEqualTo("fido2");
        assertThat(metadataStatement.getSchema()).isEqualTo(3);
        assertThat(metadataStatement.getUpv()).containsExactly(new Version(1, 0));
        assertThat(metadataStatement.getAuthenticationAlgorithms()).containsExactly(AuthenticationAlgorithm.SECP256R1_ECDSA_SHA256_RAW, AuthenticationAlgorithm.RSASSA_PKCSV15_SHA256_RAW);
        assertThat(metadataStatement.getPublicKeyAlgAndEncodings()).containsExactly(PublicKeyRepresentationFormat.COSE);
        assertThat(metadataStatement.getAttestationTypes()).containsExactly(AuthenticatorAttestationType.BASIC_FULL);
        assertThat(metadataStatement.getUserVerificationDetails()).contains(
                new VerificationMethodANDCombinations(new VerificationMethodDescriptor(UserVerificationMethod.NONE, null, null, null)),
                new VerificationMethodANDCombinations(new VerificationMethodDescriptor(UserVerificationMethod.PRESENCE_INTERNAL, null, null, null)),
                new VerificationMethodANDCombinations(new VerificationMethodDescriptor(UserVerificationMethod.PASSCODE_EXTERNAL, new CodeAccuracyDescriptor(10, 4, null, null), null, null)),
                new VerificationMethodANDCombinations(
                        new VerificationMethodDescriptor(UserVerificationMethod.PASSCODE_EXTERNAL, new CodeAccuracyDescriptor(10, 4, null, null), null, null),
                        new VerificationMethodDescriptor(UserVerificationMethod.PRESENCE_INTERNAL, null, null, null)
                )
        );
        assertThat(metadataStatement.getKeyProtection()).containsExactly(KeyProtectionType.HARDWARE, KeyProtectionType.SECURE_ELEMENT);
        assertThat(metadataStatement.getKeyRestricted()).isNull();
        assertThat(metadataStatement.getFreshUserVerificationRequired()).isNull();
        assertThat(metadataStatement.getMatcherProtection()).containsExactly(MatcherProtectionType.ON_CHIP);
        assertThat(metadataStatement.getCryptoStrength()).isEqualTo(128);
        assertThat(metadataStatement.getAttachmentHint()).containsExactly(AttachmentHint.EXTERNAL, AttachmentHint.WIRED, AttachmentHint.WIRELESS, AttachmentHint.NFC);
        assertThat(metadataStatement.getTcDisplay()).isEmpty();
        assertThat(metadataStatement.getTcDisplayContentType()).isNull();
        assertThat(metadataStatement.getTcDisplayPNGCharacteristics()).isNull();
        assertThat(metadataStatement.getAttestationRootCertificates()).hasSize(1);
        assertThat(metadataStatement.getEcdaaTrustAnchors()).isNull();
        assertThat(metadataStatement.getIcon()).isEqualTo("data:image/png;base64,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");
        assertThat(metadataStatement.getSupportedExtensions()).containsExactly(new ExtensionDescriptor("hmac-secret", null, null, false), new ExtensionDescriptor("credProtect", null, null, false));
        assertThat(metadataStatement.getAuthenticatorGetInfo()).isNotNull();
    }

    private MetadataStatement createMetadataStatement(){
        String metadataStatementString = "{\n" +
                "        \"legalHeader\": \"https://fidoalliance.org/metadata/metadata-statement-legal-header/\",\n" +
                "        \"description\": \"FIDO Alliance Sample FIDO2 Authenticator\",\n" +
                "        \"aaguid\": \"0132d110-bf4e-4208-a403-ab4f5f12efe5\",\n" +
                "        \"alternativeDescriptions\": {\n" +
                "          \"ru-RU\": \"Пример FIDO2 аутентификатора от FIDO Alliance\",\n" +
                "          \"fr-FR\": \"Exemple FIDO2 authenticator de FIDO Alliance\",\n" +
                "          \"zh-CN\": \"來自FIDO Alliance的示例FIDO2身份驗證器\"\n" +
                "        },\n" +
                "        \"protocolFamily\": \"fido2\",\n" +
                "        \"schema\": 3,\n" +
                "        \"authenticatorVersion\": 5,\n" +
                "        \"upv\": [\n" +
                "          {\n" +
                "            \"major\": 1,\n" +
                "            \"minor\": 0\n" +
                "          }\n" +
                "        ],\n" +
                "        \"authenticationAlgorithms\": [\n" +
                "          \"secp256r1_ecdsa_sha256_raw\",\n" +
                "          \"rsassa_pkcsv15_sha256_raw\"\n" +
                "        ],\n" +
                "        \"publicKeyAlgAndEncodings\": [\n" +
                "          \"cose\"\n" +
                "        ],\n" +
                "        \"attestationTypes\": [\n" +
                "          \"basic_full\"\n" +
                "        ],\n" +
                "        \"userVerificationDetails\": [\n" +
                "          [\n" +
                "            {\n" +
                "              \"userVerificationMethod\": \"none\"\n" +
                "            }\n" +
                "          ],\n" +
                "          [\n" +
                "            {\n" +
                "              \"userVerificationMethod\": \"presence_internal\"\n" +
                "            }\n" +
                "          ],\n" +
                "          [\n" +
                "            {\n" +
                "              \"userVerificationMethod\": \"passcode_external\",\n" +
                "              \"caDesc\": {\n" +
                "                \"base\": 10,\n" +
                "                \"minLength\": 4\n" +
                "              }\n" +
                "            }\n" +
                "          ],\n" +
                "          [\n" +
                "            {\n" +
                "              \"userVerificationMethod\": \"passcode_external\",\n" +
                "              \"caDesc\": {\n" +
                "                \"base\": 10,\n" +
                "                \"minLength\": 4\n" +
                "              }\n" +
                "            },\n" +
                "            {\n" +
                "              \"userVerificationMethod\": \"presence_internal\"\n" +
                "            }\n" +
                "          ]\n" +
                "        ],\n" +
                "        \"keyProtection\": [\n" +
                "          \"hardware\",\n" +
                "          \"secure_element\"\n" +
                "        ],\n" +
                "        \"matcherProtection\": [\n" +
                "          \"on_chip\"\n" +
                "        ],\n" +
                "        \"cryptoStrength\": 128,\n" +
                "        \"attachmentHint\": [\n" +
                "          \"external\",\n" +
                "          \"wired\",\n" +
                "          \"wireless\",\n" +
                "          \"nfc\"\n" +
                "        ],\n" +
                "        \"tcDisplay\": [],\n" +
                "        \"attestationRootCertificates\": [\n" +
                "          \"MIICPTCCAeOgAwIBAgIJAOuexvU3Oy2wMAoGCCqGSM49BAMCMHsxIDAeBgNVBAMMF1NhbXBsZSBBdHRlc3RhdGlvbiBSb290MRYwFAYDVQQKDA1GSURPIEFsbGlhbmNlMREwDwYDVQQLDAhVQUYgVFdHLDESMBAGA1UEBwwJUGFsbyBBbHRvMQswCQYDVQQIDAJDQTELMAkGA1UEBhMCVVMwHhcNMTQwNjE4MTMzMzMyWhcNNDExMTAzMTMzMzMyWjB7MSAwHgYDVQQDDBdTYW1wbGUgQXR0ZXN0YXRpb24gUm9vdDEWMBQGA1UECgwNRklETyBBbGxpYW5jZTERMA8GA1UECwwIVUFGIFRXRywxEjAQBgNVBAcMCVBhbG8gQWx0bzELMAkGA1UECAwCQ0ExCzAJBgNVBAYTAlVTMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEH8hv2D0HXa59/BmpQ7RZehL/FMGzFd1QBg9vAUpOZ3ajnuQ94PR7aMzH33nUSBr8fHYDrqOBb58pxGqHJRyX/6NQME4wHQYDVR0OBBYEFPoHA3CLhxFbC0It7zE4w8hk5EJ/MB8GA1UdIwQYMBaAFPoHA3CLhxFbC0It7zE4w8hk5EJ/MAwGA1UdEwQFMAMBAf8wCgYIKoZIzj0EAwIDSAAwRQIhAJ06QSXt9ihIbEKYKIjsPkriVdLIgtfsbDSu7ErJfzr4AiBqoYCZf0+zI55aQeAHjIzA9Xm63rruAxBZ9ps9z2XNlQ==\"\n" +
                "        ],\n" +
                "        \"icon\": \"data:image/png;base64,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\",\n" +
                "        \"supportedExtensions\": [\n" +
                "          {\n" +
                "            \"id\": \"hmac-secret\",\n" +
                "            \"fail_if_unknown\": false\n" +
                "          },\n" +
                "          {\n" +
                "            \"id\": \"credProtect\",\n" +
                "            \"fail_if_unknown\": false\n" +
                "          }\n" +
                "        ],\n" +
                "        \"authenticatorGetInfo\": {\n" +
                "          \"versions\": [\n" +
                "            \"U2F_V2\",\n" +
                "            \"FIDO_2_0\"\n" +
                "          ],\n" +
                "          \"extensions\": [\n" +
                "            \"credProtect\",\n" +
                "            \"hmac-secret\"\n" +
                "          ],\n" +
                "          \"aaguid\": \"0132d110bf4e4208a403ab4f5f12efe5\",\n" +
                "          \"options\": {\n" +
                "            \"plat\": \"false\",\n" +
                "            \"rk\": \"true\",\n" +
                "            \"clientPin\": \"true\",\n" +
                "            \"up\": \"true\",\n" +
                "            \"uv\": \"true\",\n" +
                "            \"uvToken\": \"false\",\n" +
                "            \"config\": \"false\"\n" +
                "          },\n" +
                "          \"maxMsgSize\": 1200,\n" +
                "          \"pinUvAuthProtocols\": [\n" +
                "            1\n" +
                "          ],\n" +
                "          \"maxCredentialCountInList\": 16,\n" +
                "          \"maxCredentialIdLength\": 128,\n" +
                "          \"transports\": [\n" +
                "            \"usb\",\n" +
                "            \"nfc\"\n" +
                "          ],\n" +
                "          \"algorithms\": [\n" +
                "            {\n" +
                "              \"type\": \"public-key\",\n" +
                "              \"alg\": -7\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"public-key\",\n" +
                "              \"alg\": -257\n" +
                "            }\n" +
                "          ],\n" +
                "          \"maxAuthenticatorConfigLength\": 1024,\n" +
                "          \"defaultCredProtect\": 2,\n" +
                "          \"firmwareVersion\": 5\n" +
                "        }\n" +
                "      }";
        return jsonConverter.readValue(metadataStatementString, MetadataStatement.class);
    }

}