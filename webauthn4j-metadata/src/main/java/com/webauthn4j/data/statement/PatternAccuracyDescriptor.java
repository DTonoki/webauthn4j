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

package com.webauthn4j.data.statement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

/**
 * The PatternAccuracyDescriptor describes relevant accuracy/complexity aspects in the case that a pattern is used as the user verification method.
 */
public class PatternAccuracyDescriptor implements Serializable {

    private final BigInteger minComplexity;
    private final Integer maxRetries;
    private final Integer blockSlowdown;

    @JsonCreator
    public PatternAccuracyDescriptor(
            @JsonProperty("minComplexity") BigInteger minComplexity,
            @JsonProperty("maxRetries") Integer maxRetries,
            @JsonProperty("blockSlowdown") Integer blockSlowdown) {
        this.minComplexity = minComplexity;
        this.maxRetries = maxRetries;
        this.blockSlowdown = blockSlowdown;
    }

    public BigInteger getMinComplexity() {
        return minComplexity;
    }

    public Integer getMaxRetries() {
        return maxRetries;
    }

    public Integer getBlockSlowdown() {
        return blockSlowdown;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatternAccuracyDescriptor that = (PatternAccuracyDescriptor) o;
        return Objects.equals(minComplexity, that.minComplexity) &&
                Objects.equals(maxRetries, that.maxRetries) &&
                Objects.equals(blockSlowdown, that.blockSlowdown);
    }

    @Override
    public int hashCode() {

        return Objects.hash(minComplexity, maxRetries, blockSlowdown);
    }
}
