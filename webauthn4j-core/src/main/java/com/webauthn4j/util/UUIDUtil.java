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

package com.webauthn4j.util;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDUtil {

    private UUIDUtil() {
    }

    public static @NotNull UUID fromString(@NotNull String value) {
        return UUID.fromString(value);
    }

    public static @NotNull byte[] convertUUIDToBytes(@NotNull UUID uuid) {
        long hi = uuid.getMostSignificantBits();
        long lo = uuid.getLeastSignificantBits();
        return ByteBuffer.allocate(16).putLong(hi).putLong(lo).array();
    }

    public static @NotNull UUID fromBytes(@NotNull byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long hi = bb.getLong();
        long lo = bb.getLong();
        return new UUID(hi, lo);
    }
}
