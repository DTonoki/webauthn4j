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

package com.webauthn4j.converter.jackson.deserializer.cbor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.webauthn4j.data.attestation.statement.*;
import com.webauthn4j.util.UnsignedNumberUtil;
import com.webauthn4j.util.exception.NotImplementedException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Jackson Deserializer for {@link TPMTPublic}
 */
public class TPMTPublicDeserializer extends StdDeserializer<TPMTPublic> {

    public TPMTPublicDeserializer() {
        super(TPMTPublic.class);
    }

    @Override
    public @NotNull TPMTPublic deserialize(@NotNull JsonParser p, @NotNull DeserializationContext ctxt) throws IOException {
        byte[] value = p.getBinaryValue();
        try {
            return deserialize(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidFormatException(p, "input byte array contains surplus data", value, TPMTPublic.class);
        }
    }

    @NotNull TPMTPublic deserialize(@NotNull byte[] value) {
        ByteBuffer buffer = ByteBuffer.wrap(value);

        int typeValue = UnsignedNumberUtil.getUnsignedShort(buffer);
        TPMIAlgPublic type = TPMIAlgPublic.create(typeValue);
        TPMIAlgHash nameAlgValue = TPMIAlgHash.create(UnsignedNumberUtil.getUnsignedShort(buffer));
        TPMAObject objectAttributes = extractTPMAObject(buffer);
        int authPolicySize = UnsignedNumberUtil.getUnsignedShort(buffer);
        byte[] authPolicy = new byte[authPolicySize];
        buffer.get(authPolicy);
        TPMUPublicParms parameters = extractTPMUPublicParms(type, buffer);
        TPMUPublicId unique = extractTPMUPublicId(type, buffer);
        if (buffer.remaining() > 0) {
            throw new IllegalArgumentException("input byte array contains surplus data");
        }

        return new TPMTPublic(type, nameAlgValue, objectAttributes, authPolicy, parameters, unique);
    }


    private @NotNull TPMAObject extractTPMAObject(@NotNull ByteBuffer buffer) {
        int value = buffer.getInt();
        return new TPMAObject(value);
    }

    private @NotNull TPMUPublicParms extractTPMUPublicParms(@NotNull TPMIAlgPublic type, @NotNull ByteBuffer buffer) {
        switch (type) {
            case TPM_ALG_RSA:
                return extractTPMSRSAParms(buffer);
            case TPM_ALG_ECC:
                return extractTPMSECCParms(buffer);
            default:
                throw new NotImplementedException();
        }
    }

    private @NotNull TPMSRSAParms extractTPMSRSAParms(@NotNull ByteBuffer buffer) {
        byte[] symmetric = new byte[2];
        buffer.get(symmetric);
        byte[] scheme = new byte[2];
        buffer.get(scheme);
        byte[] keyBits = new byte[2];
        buffer.get(keyBits);
        byte[] exponent = new byte[4];
        buffer.get(exponent);
        return new TPMSRSAParms(symmetric, scheme, keyBits, exponent);
    }

    private @NotNull TPMSECCParms extractTPMSECCParms(@NotNull ByteBuffer buffer) {
        byte[] symmetric = new byte[2];
        buffer.get(symmetric);
        byte[] scheme = new byte[2];
        buffer.get(scheme);
        byte[] curveId = new byte[2];
        buffer.get(curveId);
        byte[] kdf = new byte[2];
        buffer.get(kdf);
        return new TPMSECCParms(symmetric, scheme, TPMEccCurve.create(UnsignedNumberUtil.getUnsignedShort(curveId)), kdf);
    }

    private @NotNull TPMUPublicId extractTPMUPublicId(@NotNull TPMIAlgPublic type, @NotNull ByteBuffer buffer) {
        if (type == TPMIAlgPublic.TPM_ALG_RSA) {
            int nSize = UnsignedNumberUtil.getUnsignedShort(buffer);
            byte[] n = new byte[nSize];
            buffer.get(n);
            return new RSAUnique(n);
        }
        else if (type == TPMIAlgPublic.TPM_ALG_ECC) {
            int xSize = UnsignedNumberUtil.getUnsignedShort(buffer);
            byte[] x = new byte[xSize];
            buffer.get(x);
            int ySize = UnsignedNumberUtil.getUnsignedShort(buffer);
            byte[] y = new byte[ySize];
            buffer.get(y);
            return new ECCUnique(x, y);
        }
        else {
            throw new NotImplementedException();
        }
    }


}
