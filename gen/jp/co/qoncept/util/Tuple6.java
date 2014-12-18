package jp.co.qoncept.util;

public class Tuple6<T1, T2, T3, T4, T5, T6> {
	private final T1 value1;
	private final T2 value2;
	private final T3 value3;
	private final T4 value4;
	private final T5 value5;
	private final T6 value6;

	public Tuple6(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6) {
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
		this.value4 = value4;
		this.value5 = value5;
		this.value6 = value6;
	}

	public T1 get1() {
		return value1;
	}

	public T2 get2() {
		return value2;
	}

	public T3 get3() {
		return value3;
	}

	public T4 get4() {
		return value4;
	}

	public T5 get5() {
		return value5;
	}

	public T6 get6() {
		return value6;
	}
}
