package org.automata;

import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Logger;

import org.hamcrest.core.Is;
import org.junit.Test;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

public class TuringMachineTest {

	private static class TuringMachine<STATE, SYMBOL> {

		private static interface Transition<STATE, SYMBOL> {
			STATE getState();
			SYMBOL getWrite();
			Integer next(int i);
		}

		@ToString
		@RequiredArgsConstructor(staticName = "of")
		protected static class Left<STATE, SYMBOL> implements Transition<STATE, SYMBOL> {
			@NonNull
			@Getter
			protected SYMBOL write;

			@NonNull
			@Getter
			protected STATE state;

			public Integer next(int i) {
				return Math.max(0, --i);
			}
		}

		@ToString
		@RequiredArgsConstructor(staticName = "of")
		protected static class Right<STATE, SYMBOL> implements Transition<STATE, SYMBOL> {
			@NonNull
			@Getter
			protected SYMBOL write;

			@NonNull
			@Getter
			protected STATE state;

			public Integer next(int i) {
				return ++i;
			}
		}

		public TuringMachine(STATE q0, SYMBOL b, Collection<STATE> accepted) {
			this.q0 = q0;
			this.F = new HashSet<>(accepted);
			this.b = b;

		}

		private Set<STATE> Q; // Not empty set of states
		private Set<SYMBOL> Γ; // Not empty set of tape alphabet
		private SYMBOL b; // Blank Symbol
		private Set<SYMBOL> Σ; // Set of input symbols
		private STATE q0; // initial State
		private Set<STATE> F; // Final Status
		private Map<STATE, Map<SYMBOL, Transition<STATE, SYMBOL>>> 𝛿 = new HashMap<>();

		private STATE state;

		public void reset() {
			this.state = q0;
		}

		public void feed(SYMBOL[] tape) {
			int index = 0;
			Function<Integer, Integer> bounded = i -> Math.min(tape.length - 1, i);
			reset();
			while (true) {
				System.out.printf("%n %s %d %s ",state , index, Arrays.toString(tape));
				Transition<STATE, SYMBOL> transition = 𝛿.get(state).get(tape[bounded.apply(index)]);
				state = transition.getState();
				index = transition.next(index);
				if(F.contains(state)) break;
			}
		}

		public void addTransition(STATE state, SYMBOL input, Transition<STATE, SYMBOL> transition) {
			𝛿.putIfAbsent(state, new HashMap<>());
			Optional.ofNullable(𝛿.get(state).put(input, transition))
					.ifPresent(t -> Logger.getGlobal().warning("Replaced existing Transition : " + t));
		}
		
		public STATE getFinalState() {
			return state;
		}
	}

	private enum Status1 {
		START, ACCEPTED, REJECTED, FINE;
	}

	@Test
	public void recognizeStringEndingWithZero() {
		TuringMachine<Status1, String> tm = new TuringMachine<>(Status1.START, " ", Arrays.asList(Status1.ACCEPTED, Status1.REJECTED));
		tm.addTransition(Status1.START, " ", TuringMachine.Right.of(" ", Status1.REJECTED));
		tm.addTransition(Status1.START, "0", TuringMachine.Right.of("0", Status1.FINE));
		tm.addTransition(Status1.START, "1", TuringMachine.Right.of("1", Status1.START));
		tm.addTransition(Status1.FINE, " ", TuringMachine.Right.of(" ", Status1.ACCEPTED));
		tm.addTransition(Status1.FINE, "0", TuringMachine.Right.of("0", Status1.FINE));
		tm.addTransition(Status1.FINE, "1", TuringMachine.Right.of("1", Status1.START));

		tm.addTransition(Status1.ACCEPTED, " ", TuringMachine.Right.of(" ", Status1.ACCEPTED));
		tm.addTransition(Status1.ACCEPTED, "0", TuringMachine.Right.of("0", Status1.ACCEPTED));
		tm.addTransition(Status1.ACCEPTED, "1", TuringMachine.Right.of("1", Status1.ACCEPTED));

		tm.addTransition(Status1.REJECTED, " ", TuringMachine.Right.of(" ", Status1.REJECTED));
		tm.addTransition(Status1.REJECTED, "0", TuringMachine.Right.of("0", Status1.REJECTED));
		tm.addTransition(Status1.REJECTED, "1", TuringMachine.Right.of("1", Status1.REJECTED));

		tm.feed("011111111 ".split("(?!^)"));
		assertThat(tm.getFinalState(), Is.is(Status1.REJECTED));

		tm.feed("011111110 ".split("(?!^)"));
		assertThat(tm.getFinalState(), Is.is(Status1.ACCEPTED));

		tm.feed("011100000 ".split("(?!^)"));
		assertThat(tm.getFinalState(), Is.is(Status1.ACCEPTED));
		
		tm.feed("011100000 ".split("(?!^)"));
		assertThat(tm.getFinalState(), Is.is(Status1.ACCEPTED));

		
		tm.feed("011100001 0000".split("(?!^)"));
		assertThat(tm.getFinalState(), Is.is(Status1.REJECTED));
	}
}
