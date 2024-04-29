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

import java.util.Base64;

/**
 * A Utility class for base64url manipulation
 */
public class Base64UrlUtil {

    private static final java.util.Base64.Decoder decoder = Base64.getUrlDecoder();
    private static final java.util.Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

    private Base64UrlUtil() {
    }

    public static @NotNull byte[] decode(@NotNull String source) {
        AssertUtil.notNull(source, "source must not be null");
        return decoder.decode(source);
    }

    public static @NotNull byte[] decode(@NotNull byte[] source) {
        AssertUtil.notNull(source, "source must not be null");
        return decoder.decode(source);
    }

    public static @NotNull byte[] encode(@NotNull byte[] source) {
        AssertUtil.notNull(source, "source must not be null");
        return encoder.encode(source);
    }

    public static @NotNull String encodeToString(@NotNull byte[] source) {
        AssertUtil.notNull(source, "source must not be null");
        return encoder.encodeToString(source);
    }
}
