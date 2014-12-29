package jp.co.qoncept.util;

public class Tuple2<T0, T1> {
	private final T0 value0;
	private final T1 value1;

	public Tuple2(T0 value0, T1 value1) {
		this.value0 = value0;
		this.value1 = value1;
	}

	public T0 get0() {
		return value0;
	}

	public T1 get1() {
		return value1;
	}
}
