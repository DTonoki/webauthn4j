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
import com.webauthn4j.util.AssertUtil;
import com.webauthn4j.util.UnsignedNumberUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class ECCUnique implements TPMUPublicId {

    private final byte[] x;
    private final byte[] y;

    public ECCUnique(@NotNull byte[] x, @NotNull byte[] y) {
        AssertUtil.notNull(x, "x must not be null");
        AssertUtil.notNull(y, "y must not be null");
        this.x = x;
        this.y = y;
    }

    public @NotNull byte[] getX() {
        return ArrayUtil.clone(x);
    }

    public @NotNull byte[] getY() {
        return ArrayUtil.clone(y);
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ECCUnique eccUnique = (ECCUnique) o;
        return Arrays.equals(x, eccUnique.x) &&
                Arrays.equals(y, eccUnique.y);
    }

    @Override
    public int hashCode() {

        int result = Arrays.hashCode(x);
        result = 31 * result + Arrays.hashCode(y);
        return result;
    }

    @Override
    public @NotNull byte[] getBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(2 + x.length + 2 + y.length);
        buffer.put(UnsignedNumberUtil.toBytes(x.length));
        buffer.put(x);
        buffer.put(UnsignedNumberUtil.toBytes(y.length));
        buffer.put(y);
        return buffer.array();
    }
}
