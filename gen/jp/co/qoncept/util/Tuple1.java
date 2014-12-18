package jp.co.qoncept.util;

public class Tuple1<T1> {
	private final T1 value1;

	public Tuple1(T1 value1) {
		this.value1 = value1;
	}

	public T1 get1() {
		return value1;
	}
}
