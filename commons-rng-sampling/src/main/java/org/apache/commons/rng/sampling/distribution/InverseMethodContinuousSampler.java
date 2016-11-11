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
package org.apache.commons.rng.sampling.distribution;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.sampling.AbstractContinuousSampler;

/**
 * Distribution sampler that uses the
 * <a href="https://en.wikipedia.org/wiki/Inverse_transform_sampling">
 * inversion method</a>.
 *
 * <p>
 * It can be used to sample any distribution that provides access to its
 * <em>inverse cumulative probabilty function</em>.
 * </p>
 *
 * <p>Example:</p>
 * <pre><source>
 * import org.apache.commons.math3.distribution.RealDistribution;
 * import org.apache.commons.math3.distribution.ChiSquaredDistribution;
 *
 * import org.apache.commons.rng.simple.RandomSource;
 * import org.apache.commons.rng.sampling.ContinuousSampler;
 * import org.apache.commons.rng.sampling.distribution.InverseMethodContinuousSampler;
 * import org.apache.commons.rng.sampling.distribution.ContinuousInverseCumulativeProbabilityFunction;
 *
 * // Distribution to sample.
 * final RealDistribution dist = new ChiSquaredDistribution(9);
 * // Create the sampler.
 * final ContinuousSampler chiSquareSampler =
 *     new InverseMethodContinuousSampler(RandomSource.create(RandomSource.MT),
 *                                        new ContinuousInverseCumulativeProbabilityFunction() {
 *                                            @Override
 *                                            public double inverseCumulativeProbability(double p) {
 *                                                return dist.inverseCumulativeProbability(p);
 *                                            }
 *                                        });
 *
 * // Generate random deviate.
 * double random = chiSquareSampler.sample();
 * </source></pre>
 */
public class InverseMethodContinuousSampler extends AbstractContinuousSampler {
    /** Inverse cumulative probability function. */
    private final ContinuousInverseCumulativeProbabilityFunction function;

    /**
     * @param rng Generator of uniformly distributed random numbers.
     * @param function Inverse cumulative probability function.
     */
    public InverseMethodContinuousSampler(UniformRandomProvider rng,
                                          ContinuousInverseCumulativeProbabilityFunction function) {
        super(rng);
        this.function = function;
    }

    /** {@inheritDoc} */
    @Override
    public double sample() {
        return function.inverseCumulativeProbability(nextUniform());
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "[inverse method " + super.toString() + "]";
    }
}
