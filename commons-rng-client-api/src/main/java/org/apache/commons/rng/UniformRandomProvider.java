/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.rng;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * Applies to generators of random number sequences that follow a uniform
 * distribution.
 *
 * @since 1.0
 */
public interface UniformRandomProvider {
    /**
     * Generates {@code byte} values and places them into a user-supplied array.
     *
     * <p>The number of random bytes produced is equal to the length of the
     * the byte array.
     *
     * @param bytes Byte array in which to put the random bytes.
     * Cannot be {@code null}.
     */
    default void nextBytes(byte[] bytes) {
        UniformRandomProviderSupport.nextBytes(this, bytes, 0, bytes.length);
    }

    /**
     * Generates {@code byte} values and places them into a user-supplied array.
     *
     * <p>The array is filled with bytes extracted from random integers.
     * This implies that the number of random bytes generated may be larger than
     * the length of the byte array.
     *
     * @param bytes Array in which to put the generated bytes.
     * Cannot be {@code null}.
     * @param start Index at which to start inserting the generated bytes.
     * @param len Number of bytes to insert.
     * @throws IndexOutOfBoundsException if {@code start < 0} or
     * {@code start >= bytes.length}.
     * @throws IndexOutOfBoundsException if {@code len < 0} or
     * {@code len > bytes.length - start}.
     */
    default void nextBytes(byte[] bytes, int start, int len) {
        UniformRandomProviderSupport.validateFromIndexSize(start, len, bytes.length);
        UniformRandomProviderSupport.nextBytes(this, bytes, start, len);
    }

    /**
     * Generates an {@code int} value.
     *
     * @return the next random value.
     */
    default int nextInt() {
        return (int) (nextLong() >>> 32);
    }

    /**
     * Generates an {@code int} value between 0 (inclusive) and the
     * specified value (exclusive).
     *
     * @param n Bound on the random number to be returned.  Must be positive.
     * @return a random {@code int} value between 0 (inclusive) and {@code n}
     * (exclusive).
     * @throws IllegalArgumentException if {@code n} is not above zero.
     */
    default int nextInt(int n) {
        UniformRandomProviderSupport.validateUpperBound(n);
        return UniformRandomProviderSupport.nextInt(this, n);
    }

    /**
     * Generates an {@code int} value between the specified {@code origin} (inclusive) and
     * the specified {@code bound} (exclusive).
     *
     * @param origin Lower bound on the random number to be returned.
     * @param bound Upper bound (exclusive) on the random number to be returned.
     * @return a random {@code int} value between {@code origin} (inclusive) and
     * {@code bound} (exclusive).
     * @throws IllegalArgumentException if {@code origin} is greater than or equal to
     * {@code bound}.
     * @since 1.5
     */
    default int nextInt(int origin, int bound) {
        UniformRandomProviderSupport.validateRange(origin, bound);
        return UniformRandomProviderSupport.nextInt(this, origin, bound);
    }

    /**
     * Generates a {@code long} value.
     *
     * @return the next random value.
     */
    long nextLong();

    /**
     * Generates a {@code long} value between 0 (inclusive) and the specified
     * value (exclusive).
     *
     * @param n Bound on the random number to be returned.  Must be positive.
     * @return a random {@code long} value between 0 (inclusive) and {@code n}
     * (exclusive).
     * @throws IllegalArgumentException if {@code n} is not greater than 0.
     */
    default long nextLong(long n) {
        UniformRandomProviderSupport.validateUpperBound(n);
        return UniformRandomProviderSupport.nextLong(this, n);
    }

    /**
     * Generates a {@code long} value between the specified {@code origin} (inclusive) and
     * the specified {@code bound} (exclusive).
     *
     * @param origin Lower bound on the random number to be returned.
     * @param bound Upper bound (exclusive) on the random number to be returned.
     * @return a random {@code long} value between {@code origin} (inclusive) and
     * {@code bound} (exclusive).
     * @throws IllegalArgumentException if {@code origin} is greater than or equal to
     * {@code bound}.
     * @since 1.5
     */
    default long nextLong(long origin, long bound) {
        UniformRandomProviderSupport.validateRange(origin, bound);
        return UniformRandomProviderSupport.nextLong(this, origin, bound);
    }

    /**
     * Generates a {@code boolean} value.
     *
     * @return the next random value.
     */
    default boolean nextBoolean() {
        return nextInt() < 0;
    }

    /**
     * Generates a {@code float} value between 0 (inclusive) and 1 (exclusive).
     *
     * @return the next random value between 0 (inclusive) and 1 (exclusive).
     */
    default float nextFloat() {
        return (nextInt() >>> 8) * 0x1.0p-24f;
    }

    /**
     * Generates a {@code float} value between 0 (inclusive) and the
     * specified {@code bound} (exclusive).
     *
     * @param bound Upper bound (exclusive) on the random number to be returned.
     * @return a random {@code float} value between 0 (inclusive) and {@code bound}
     * (exclusive).
     * @throws IllegalArgumentException if {@code bound} is not both finite and greater than 0.
     * @since 1.5
     */
    default float nextFloat(float bound) {
        UniformRandomProviderSupport.validateUpperBound(bound);
        return UniformRandomProviderSupport.nextFloat(this, bound);
    }

    /**
     * Generates a {@code float} value between the specified {@code origin} (inclusive)
     * and the specified {@code bound} (exclusive).
     *
     * @param origin Lower bound on the random number to be returned.
     * @param bound Upper bound (exclusive) on the random number to be returned.
     * @return a random {@code float} value between {@code origin} (inclusive) and
     * {@code bound} (exclusive).
     * @throws IllegalArgumentException if {@code origin} is not finite, or {@code bound}
     * is not finite, or {@code origin} is greater than or equal to {@code bound}.
     * @since 1.5
     */
    default float nextFloat(float origin, float bound) {
        UniformRandomProviderSupport.validateRange(origin, bound);
        return UniformRandomProviderSupport.nextFloat(this, origin, bound);
    }

    /**
     * Generates a {@code double} value between 0 (inclusive) and 1 (exclusive).
     *
     * @return the next random value between 0 (inclusive) and 1 (exclusive).
     */
    default double nextDouble() {
        return (nextLong() >>> 11) * 0x1.0p-53;
    }

    /**
     * Generates a {@code double} value between 0 (inclusive) and the
     * specified {@code bound} (exclusive).
     *
     * @param bound Upper bound (exclusive) on the random number to be returned.
     * @return a random {@code double} value between 0 (inclusive) and {@code bound}
     * (exclusive).
     * @throws IllegalArgumentException if {@code bound} is not both finite and greater than 0.
     * @since 1.5
     */
    default double nextDouble(double bound) {
        UniformRandomProviderSupport.validateUpperBound(bound);
        return UniformRandomProviderSupport.nextDouble(this, bound);
    }

    /**
     * Generates a {@code double} value between the specified {@code origin} (inclusive)
     * and the specified {@code bound} (exclusive).
     *
     * @param origin Lower bound on the random number to be returned.
     * @param bound Upper bound (exclusive) on the random number to be returned.
     * @return a random {@code double} value between {@code origin} (inclusive) and
     * {@code bound} (exclusive).
     * @throws IllegalArgumentException if {@code origin} is not finite, or {@code bound}
     * is not finite, or {@code origin} is greater than or equal to {@code bound}.
     * @since 1.5
     */
    default double nextDouble(double origin, double bound) {
        UniformRandomProviderSupport.validateRange(origin, bound);
        return UniformRandomProviderSupport.nextDouble(this, origin, bound);
    }

    /**
     * Returns an effectively unlimited stream of {@code int} values.
     *
     * @return a stream of random {@code int} values.
     * @since 1.5
     */
    default IntStream ints() {
        return IntStream.generate(this::nextInt).sequential();
    }

    /**
     * Returns an effectively unlimited stream of {@code int} values between the specified
     * {@code origin} (inclusive) and the specified {@code bound} (exclusive).
     *
     * @param origin Lower bound on the random number to be returned.
     * @param bound Upper bound (exclusive) on the random number to be returned.
     * @return a stream of random values between the specified {@code origin} (inclusive)
     * and the specified {@code bound} (exclusive).
     * @throws IllegalArgumentException if {@code origin} is greater than or equal to
     * {@code bound}.
     * @since 1.5
     */
    default IntStream ints(int origin, int bound) {
        UniformRandomProviderSupport.validateRange(origin, bound);
        return IntStream.generate(() -> nextInt(origin, bound)).sequential();
    }

    /**
     * Returns a stream producing the given {@code streamSize} number of {@code int}
     * values.
     *
     * @param streamSize Number of values to generate.
     * @return a stream of random {@code int} values; the stream is limited to the given
     * {@code streamSize}.
     * @throws IllegalArgumentException if {@code streamSize} is negative.
     * @since 1.5
     */
    default IntStream ints(long streamSize) {
        UniformRandomProviderSupport.validateStreamSize(streamSize);
        return ints().limit(streamSize);
    }

    /**
     * Returns a stream producing the given {@code streamSize} number of {@code int}
     * values between the specified {@code origin} (inclusive) and the specified
     * {@code bound} (exclusive).
     *
     * @param streamSize Number of values to generate.
     * @param origin Lower bound on the random number to be returned.
     * @param bound Upper bound (exclusive) on the random number to be returned.
     * @return a stream of random values between the specified {@code origin} (inclusive)
     * and the specified {@code bound} (exclusive); the stream is limited to the given
     * {@code streamSize}.
     * @throws IllegalArgumentException if {@code streamSize} is negative, or if
     * {@code origin} is greater than or equal to {@code bound}.
     * @since 1.5
     */
    default IntStream ints(long streamSize, int origin, int bound) {
        UniformRandomProviderSupport.validateStreamSize(streamSize);
        UniformRandomProviderSupport.validateRange(origin, bound);
        return ints(origin, bound).limit(streamSize);
    }

    /**
     * Returns an effectively unlimited stream of {@code long} values.
     *
     * @return a stream of random {@code long} values.
     * @since 1.5
     */
    default LongStream longs() {
        return LongStream.generate(this::nextLong).sequential();
    }

    /**
     * Returns an effectively unlimited stream of {@code long} values between the
     * specified {@code origin} (inclusive) and the specified {@code bound} (exclusive).
     *
     * @param origin Lower bound on the random number to be returned.
     * @param bound Upper bound (exclusive) on the random number to be returned.
     * @return a stream of random values between the specified {@code origin} (inclusive)
     * and the specified {@code bound} (exclusive).
     * @throws IllegalArgumentException if {@code origin} is greater than or equal to
     * {@code bound}.
     * @since 1.5
     */
    default LongStream longs(long origin, long bound) {
        UniformRandomProviderSupport.validateRange(origin, bound);
        return LongStream.generate(() -> nextLong(origin, bound)).sequential();
    }

    /**
     * Returns a stream producing the given {@code streamSize} number of {@code long}
     * values.
     *
     * @param streamSize Number of values to generate.
     * @return a stream of random {@code long} values; the stream is limited to the given
     * {@code streamSize}.
     * @throws IllegalArgumentException if {@code streamSize} is negative.
     * @since 1.5
     */
    default LongStream longs(long streamSize) {
        UniformRandomProviderSupport.validateStreamSize(streamSize);
        return longs().limit(streamSize);
    }

    /**
     * Returns a stream producing the given {@code streamSize} number of {@code long}
     * values between the specified {@code origin} (inclusive) and the specified
     * {@code bound} (exclusive).
     *
     * @param streamSize Number of values to generate.
     * @param origin Lower bound on the random number to be returned.
     * @param bound Upper bound (exclusive) on the random number to be returned.
     * @return a stream of random values between the specified {@code origin} (inclusive)
     * and the specified {@code bound} (exclusive); the stream is limited to the given
     * {@code streamSize}.
     * @throws IllegalArgumentException if {@code streamSize} is negative, or if
     * {@code origin} is greater than or equal to {@code bound}.
     * @since 1.5
     */
    default LongStream longs(long streamSize, long origin, long bound) {
        UniformRandomProviderSupport.validateStreamSize(streamSize);
        UniformRandomProviderSupport.validateRange(origin, bound);
        return longs(origin, bound).limit(streamSize);
    }

    /**
     * Returns an effectively unlimited stream of {@code double} values between 0
     * (inclusive) and 1 (exclusive).
     *
     * @return a stream of random values between 0 (inclusive) and 1 (exclusive).
     * @since 1.5
     */
    default DoubleStream doubles() {
        return DoubleStream.generate(this::nextDouble).sequential();
    }

    /**
     * Returns an effectively unlimited stream of {@code double} values between the
     * specified {@code origin} (inclusive) and the specified {@code bound} (exclusive).
     *
     * @param origin Lower bound on the random number to be returned.
     * @param bound Upper bound (exclusive) on the random number to be returned.
     * @return a stream of random values between the specified {@code origin} (inclusive)
     * and the specified {@code bound} (exclusive).
     * @throws IllegalArgumentException if {@code origin} is not finite, or {@code bound}
     * is not finite, or {@code origin} is greater than or equal to {@code bound}.
     * @since 1.5
     */
    default DoubleStream doubles(double origin, double bound) {
        UniformRandomProviderSupport.validateRange(origin, bound);
        return DoubleStream.generate(() -> nextDouble(origin, bound)).sequential();
    }

    /**
     * Returns a stream producing the given {@code streamSize} number of {@code double}
     * values between 0 (inclusive) and 1 (exclusive).
     *
     * @param streamSize Number of values to generate.
     * @return a stream of random values between 0 (inclusive) and 1 (exclusive);
     * the stream is limited to the given {@code streamSize}.
     * @throws IllegalArgumentException if {@code streamSize} is negative.
     * @since 1.5
     */
    default DoubleStream doubles(long streamSize) {
        UniformRandomProviderSupport.validateStreamSize(streamSize);
        return doubles().limit(streamSize);
    }

    /**
     * Returns a stream producing the given {@code streamSize} number of {@code double}
     * values between the specified {@code origin} (inclusive) and the specified
     * {@code bound} (exclusive).
     *
     * @param streamSize Number of values to generate.
     * @param origin Lower bound on the random number to be returned.
     * @param bound Upper bound (exclusive) on the random number to be returned.
     * @return a stream of random values between the specified {@code origin} (inclusive)
     * and the specified {@code bound} (exclusive); the stream is limited to the given
     * {@code streamSize}.
     * @throws IllegalArgumentException if {@code streamSize} is negative, or if
     * {@code origin} is not finite, or {@code bound} is not finite, or {@code origin} is
     * greater than or equal to {@code bound}.
     * @since 1.5
     */
    default DoubleStream doubles(long streamSize, double origin, double bound) {
        UniformRandomProviderSupport.validateStreamSize(streamSize);
        UniformRandomProviderSupport.validateRange(origin, bound);
        return doubles(origin, bound).limit(streamSize);
    }
}
