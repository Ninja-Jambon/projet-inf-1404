// Antoine CRETUAL, Lukian LEIZOUR, 21/02/2024

package universe;

import java.util.Vector;

public class Stack <T> {
	// Atributes

	private Vector <T> array; 

	// Construcors

	public Stack() {
		array = new Vector<T>();
	}

	public Stack(Vector<T> array) {
		array = array;
	}

	// Methods

	public void push(T x) {
		this.array.add(this.array.size(), x);
	}

	public T pop() {
		T x = this.array.elementAt(this.array.size() - 1);
		this.array.remove(this.array.size() - 1);
		return x;
	}

	public int size() {
		return this.array.size();
	}

	public Stack copy() {
		return new Stack(new Vector<T>(this.array));
	}
}
