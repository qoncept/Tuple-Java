package jp.co.qoncept.util;

public class Tuple3<T0, T1, T2> {
	private final T0 value0;
	private final T1 value1;
	private final T2 value2;

	public Tuple3(T0 value0, T1 value1, T2 value2) {
		this.value0 = value0;
		this.value1 = value1;
		this.value2 = value2;
	}

	public T0 get0() {
		return value0;
	}

	public T1 get1() {
		return value1;
	}

	public T2 get2() {
		return value2;
	}
}
