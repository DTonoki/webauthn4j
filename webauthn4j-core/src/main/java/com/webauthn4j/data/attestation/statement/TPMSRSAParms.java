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

package com.webauthn4j.data.attestation.statement;

import com.webauthn4j.util.ArrayUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class TPMSRSAParms implements TPMUPublicParms {

    private final byte[] symmetric;
    private final byte[] scheme;
    private final byte[] keyBits;
    private final byte[] exponent;

    public TPMSRSAParms(@NotNull byte[] symmetric, @NotNull byte[] scheme, @NotNull byte[] keyBits, @NotNull byte[] exponent) {
        this.symmetric = symmetric;
        this.scheme = scheme;
        this.keyBits = keyBits;
        this.exponent = exponent;
    }

    public @NotNull byte[] getSymmetric() {
        return ArrayUtil.clone(symmetric);
    }

    public @NotNull byte[] getScheme() {
        return ArrayUtil.clone(scheme);
    }

    public @NotNull byte[] getKeyBits() {
        return ArrayUtil.clone(keyBits);
    }

    public @NotNull byte[] getExponent() {
        return ArrayUtil.clone(exponent);
    }

    public @NotNull byte[] getBytes() {
        return ByteBuffer.allocate(10)
                .put(symmetric)
                .put(scheme)
                .put(keyBits)
                .put(exponent)
                .array();
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TPMSRSAParms that = (TPMSRSAParms) o;
        return Arrays.equals(symmetric, that.symmetric) &&
                Arrays.equals(scheme, that.scheme) &&
                Arrays.equals(keyBits, that.keyBits) &&
                Arrays.equals(exponent, that.exponent);
    }

    @Override
    public int hashCode() {

        int result = Arrays.hashCode(symmetric);
        result = 31 * result + Arrays.hashCode(scheme);
        result = 31 * result + Arrays.hashCode(keyBits);
        result = 31 * result + Arrays.hashCode(exponent);
        return result;
    }
}
