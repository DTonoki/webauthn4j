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

package com.webauthn4j.converter;

import com.webauthn4j.converter.exception.DataConversionException;
import com.webauthn4j.converter.util.ObjectConverter;
import com.webauthn4j.data.attestation.authenticator.AuthenticatorData;
import com.webauthn4j.data.extension.UvmEntries;
import com.webauthn4j.data.extension.authenticator.AuthenticationExtensionAuthenticatorOutput;
import com.webauthn4j.data.extension.authenticator.AuthenticationExtensionsAuthenticatorOutputs;
import com.webauthn4j.data.extension.authenticator.RegistrationExtensionAuthenticatorOutput;
import com.webauthn4j.data.extension.authenticator.UserVerificationMethodExtensionAuthenticatorOutput;
import com.webauthn4j.util.Base64UrlUtil;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static com.webauthn4j.data.attestation.authenticator.AuthenticatorData.BIT_ED;
import static com.webauthn4j.data.attestation.authenticator.AuthenticatorData.BIT_UP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test for AuthenticatorDataConverter
 */
@SuppressWarnings("ConstantConditions")
class AuthenticatorDataConverterTest {

    private final ObjectConverter objectConverter = new ObjectConverter();

    @Test
    void convert_test() {
        //Given
        //noinspection SpellCheckingInspection
        String input = "SZYN5YgOjGh0NBcPZHZgW4_krrmihjLHmVzzuoMdl2MBAAABRQ";

        //When
        AuthenticatorData<RegistrationExtensionAuthenticatorOutput> result = new AuthenticatorDataConverter(objectConverter).convert(Base64UrlUtil.decode(input));

        //Then
        assertThat(result.getRpIdHash()).isNotNull();
        assertThat(result.getRpIdHash()).hasSize(32);
        assertThat(result.getFlags()).isEqualTo(BIT_UP);
        assertThat(result.getSignCount()).isEqualTo(325);
        assertThat(result.getAttestedCredentialData()).isNull();
        assertThat(result.getExtensions().getKeys()).isEmpty();
    }

    @Test
    void convert_apple_app_attest_authenticatorData() {
        byte[] authenticatorDataBytes = Base64UrlUtil.decode("MGACygVeBsWIpS7GqlSC9TaOAq8wh7Hp0wnQQMr12VpAAAAAAQ");
        AuthenticatorDataConverter authenticatorDataConverter = new AuthenticatorDataConverter(objectConverter);
        AuthenticatorData<AuthenticationExtensionAuthenticatorOutput> authenticatorData = authenticatorDataConverter.convert(authenticatorDataBytes);
        assertThat(authenticatorData.getAttestedCredentialData()).isNull();
    }

    @Test
    void convert_too_short_data_test() {
        //Given
        //noinspection SpellCheckingInspection
        byte[] input = Base64UrlUtil.decode("SZYN5YgOjGh0NBcP");
        AuthenticatorDataConverter authenticatorDataConverter = new AuthenticatorDataConverter(objectConverter);

        //When
        assertThrows(DataConversionException.class,
                () -> authenticatorDataConverter.convert(input)
        );
    }

    @Test
    void serialize_deserialize_test() {
        //Given
        byte[] rpIdHash = new byte[32];
        //noinspection UnnecessaryLocalVariable
        byte flags = BIT_ED;
        AuthenticationExtensionsAuthenticatorOutputs.BuilderForRegistration builder = new AuthenticationExtensionsAuthenticatorOutputs.BuilderForRegistration();
        builder.setUvm(new UvmEntries());
        AuthenticatorData<RegistrationExtensionAuthenticatorOutput> authenticatorData =
                new AuthenticatorData<>(rpIdHash, flags, 0, builder.build());

        //When
        byte[] serialized = new AuthenticatorDataConverter(objectConverter).convert(authenticatorData);
        AuthenticatorData<RegistrationExtensionAuthenticatorOutput> result = new AuthenticatorDataConverter(objectConverter).convert(serialized);

        //Then
        assertThat(result.getRpIdHash()).isNotNull();
        assertThat(result.getRpIdHash()).hasSize(32);
        assertThat(result.getFlags()).isEqualTo(BIT_ED);
        assertThat(result.getSignCount()).isZero();
        assertThat(result.getAttestedCredentialData()).isNull();
        assertThat(result.getExtensions().getKeys()).contains(UserVerificationMethodExtensionAuthenticatorOutput.ID);
    }

    @Test
    void deserialize_data_with_surplus_bytes_test() {
        //noinspection SpellCheckingInspection
        String input = "SZYN5YgOjGh0NBcPZHZgW4_krrmihjLHmVzzuoMdl2MBAAABRQ";
        byte[] data = Base64UrlUtil.decode(input);
        byte[] bytes = Arrays.copyOf(data, data.length + 1);
        AuthenticatorDataConverter authenticatorDataConverter = new AuthenticatorDataConverter(objectConverter);
        //When
        assertThrows(DataConversionException.class,
                () -> authenticatorDataConverter.convert(bytes)
        );
    }

    @Test
    void convertToExtensions_with_0_length_ByteBuffer_test() {
        AuthenticatorDataConverter authenticatorDataConverter = new AuthenticatorDataConverter(objectConverter);
        AuthenticationExtensionsAuthenticatorOutputs<?> extensions = authenticatorDataConverter.convertToExtensions(ByteBuffer.allocate(0));
        assertThat(extensions.getKeys()).isEmpty();
    }

    @Test
    void convertToExtensions_with_cbor_null_ByteBuffer_test() {
        byte[] data = new byte[]{(byte) 0xF6};
        AuthenticatorDataConverter authenticatorDataConverter = new AuthenticatorDataConverter(objectConverter);
        AuthenticationExtensionsAuthenticatorOutputs<?> extensions = authenticatorDataConverter.convertToExtensions(ByteBuffer.wrap(data));
        assertThat(extensions).isNull();
    }

    @Test
    void extractAttestedCredentialData_test() {
        //Given
        //noinspection SpellCheckingInspection
        String input = "SZYN5YgOjGh0NBcPZHZgW4_krrmihjLHmVzzuoMdl2NBAAAARlUOS1SqR0CfmpUat2wTATEAIHEiziyGohCFUc_hJJZGdtSu9ThnEb74K6NZC3U-KbwgpQECAyYgASFYICw4xPmHIvquDRz2KUzyyQlZFhZMbi-mc_YylL1o55jPIlggGQI5ESYAOfR8QM6quTQSoyhjZET806A3yOoCUe2AWJE";
        //When
        byte[] result = new AuthenticatorDataConverter(objectConverter).extractAttestedCredentialData(Base64UrlUtil.decode(input));

        assertThat(result).isEqualTo(Base64UrlUtil.decode("VQ5LVKpHQJ-alRq3bBMBMQAgcSLOLIaiEIVRz-EklkZ21K71OGcRvvgro1kLdT4pvCClAQIDJiABIVggLDjE-Yci-q4NHPYpTPLJCVkWFkxuL6Zz9jKUvWjnmM8iWCAZAjkRJgA59HxAzqq5NBKjKGNkRPzToDfI6gJR7YBYkQ"));

    }

    @Test
    void extractSignCount_test() {
        //Given
        //noinspection SpellCheckingInspection
        String input = "SZYN5YgOjGh0NBcPZHZgW4_krrmihjLHmVzzuoMdl2NBAAAARlUOS1SqR0CfmpUat2wTATEAIHEiziyGohCFUc_hJJZGdtSu9ThnEb74K6NZC3U-KbwgpQECAyYgASFYICw4xPmHIvquDRz2KUzyyQlZFhZMbi-mc_YylL1o55jPIlggGQI5ESYAOfR8QM6quTQSoyhjZET806A3yOoCUe2AWJE";
        //When
        long signCount = new AuthenticatorDataConverter(objectConverter).extractSignCount(Base64UrlUtil.decode(input));

        assertThat(signCount).isEqualTo(70);

    }

}
