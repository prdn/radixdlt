/*
 * (C) Copyright 2021 Radix DLT Ltd
 *
 * Radix DLT Ltd licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the
 * License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 */

package com.radixdlt.statecomputer;

import com.radixdlt.atommodel.system.state.ValidatorStake;
import com.radixdlt.constraintmachine.Particle;
import com.radixdlt.engine.StateReducer;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public final class InflationReducer implements StateReducer<Rewards> {
	@Override
	public Class<Rewards> stateClass() {
		return Rewards.class;
	}

	@Override
	public Set<Class<? extends Particle>> particleClasses() {
		return Set.of(ValidatorStake.class);
	}

	@Override
	public Supplier<Rewards> initial() {
		return Rewards::create;
	}

	@Override
	public BiFunction<Rewards, Particle, Rewards> outputReducer() {
		return (prev, p) -> {
			var s = (ValidatorStake) p;
			return prev.add(s.getValidatorKey(), s.getTotalStake());
		};
	}

	@Override
	public BiFunction<Rewards, Particle, Rewards> inputReducer() {
		return (prev, p) -> {
			var s = (ValidatorStake) p;
			return prev.remove(s.getValidatorKey(), s.getTotalStake());
		};
	}
}
