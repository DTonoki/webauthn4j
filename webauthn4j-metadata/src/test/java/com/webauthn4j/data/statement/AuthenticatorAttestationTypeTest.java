package com.webauthn4j.data.statement;

import com.webauthn4j.converter.exception.DataConversionException;
import com.webauthn4j.converter.util.JsonConverter;
import com.webauthn4j.converter.util.ObjectConverter;
import com.webauthn4j.data.AuthenticatorAttestationType;
import com.webauthn4j.util.UnsignedNumberUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthenticatorAttestationTypeTest {

    private final ObjectConverter objectConverter = new ObjectConverter();
    private final JsonConverter jsonConverter = objectConverter.getJsonConverter();

    @Test
    void create_test() {
        assertAll(
                () -> assertThat(AuthenticatorAttestationType.create(0x3E07)).isEqualTo(AuthenticatorAttestationType.BASIC_FULL),
                () -> assertThat(AuthenticatorAttestationType.create(0x3E08)).isEqualTo(AuthenticatorAttestationType.BASIC_SURROGATE),
                () -> assertThat(AuthenticatorAttestationType.create(0x3E09)).isEqualTo(AuthenticatorAttestationType.ECDAA),
                () -> assertThat(AuthenticatorAttestationType.create(0x3E0A)).isEqualTo(AuthenticatorAttestationType.ATTCA)
        );
    }

    @Test
    void create_test_with_value_over_upper_bound() {
        assertThrows(IllegalArgumentException.class,
                () -> AuthenticatorAttestationType.create(UnsignedNumberUtil.UNSIGNED_SHORT_MAX + 1)
        );
    }

    @Test
    void create_test_with_value_under_lower_bound() {
        assertThrows(IllegalArgumentException.class,
                () -> AuthenticatorAttestationType.create(-1)
        );
    }

    @Test
    void create_test_with_out_of_range_value() {
        assertThrows(IllegalArgumentException.class,
                () -> AuthenticatorAttestationType.create(0x2A1D)
        );
    }

    @Test
    void getValue_test() {
        assertAll(
                () -> assertThat(AuthenticatorAttestationType.BASIC_FULL.getValue()).isEqualTo(0x3E07),
                () -> assertThat(AuthenticatorAttestationType.BASIC_SURROGATE.getValue()).isEqualTo(0x3E08),
                () -> assertThat(AuthenticatorAttestationType.ECDAA.getValue()).isEqualTo(0x3E09),
                () -> assertThat(AuthenticatorAttestationType.ATTCA.getValue()).isEqualTo(0x3E0A)
        );
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void fromInt_test() {
        TestDTO dto1 = jsonConverter.readValue("{\"attestation_type\":15879}", TestDTO.class);
        TestDTO dto2 = jsonConverter.readValue("{\"attestation_type\":15880}", TestDTO.class);
        TestDTO dto3 = jsonConverter.readValue("{\"attestation_type\":15881}", TestDTO.class);
        TestDTO dto4 = jsonConverter.readValue("{\"attestation_type\":15882}", TestDTO.class);

        assertAll(
                () -> assertThat(dto1.attestation_type).isEqualTo(AuthenticatorAttestationType.BASIC_FULL),
                () -> assertThat(dto2.attestation_type).isEqualTo(AuthenticatorAttestationType.BASIC_SURROGATE),
                () -> assertThat(dto3.attestation_type).isEqualTo(AuthenticatorAttestationType.ECDAA),
                () -> assertThat(dto4.attestation_type).isEqualTo(AuthenticatorAttestationType.ATTCA)
        );
    }

    @Test
    void fromInt_test_with_invalid_value() {
        assertThrows(DataConversionException.class,
                () -> jsonConverter.readValue("{\"attestation_type\":123}", TestDTO.class)
        );
    }

    static class TestDTO {
        @SuppressWarnings("WeakerAccess")
        public AuthenticatorAttestationType attestation_type;
    }
}
