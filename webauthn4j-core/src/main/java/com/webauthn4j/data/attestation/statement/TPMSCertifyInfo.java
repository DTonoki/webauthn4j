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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

public class TPMSCertifyInfo implements TPMUAttest {

    private final TPMTHA name;
    private final TPMTHA qualifiedName;

    public TPMSCertifyInfo(@NotNull TPMTHA name, @NotNull TPMTHA qualifiedName) {
        this.name = name;
        this.qualifiedName = qualifiedName;
    }

    public @NotNull TPMTHA getName() {
        return name;
    }

    public @NotNull TPMTHA getQualifiedName() {
        return qualifiedName;
    }

    @Override
    public @NotNull byte[] getBytes() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            TPMUtil.writeSizedArray(outputStream, name.getBytes());
            TPMUtil.writeSizedArray(outputStream, qualifiedName.getBytes());
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TPMSCertifyInfo that = (TPMSCertifyInfo) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(qualifiedName, that.qualifiedName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, qualifiedName);
    }
}
