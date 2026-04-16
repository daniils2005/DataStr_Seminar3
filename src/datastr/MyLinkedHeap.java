package datastr;

public class MyLinkedHeap<Ttype> {
	MyNode<Ttype> rootNode = null;
	MyNode<Ttype> lastNode = null;
	private int howManyElements = 0;
	
	public int length() {
		return howManyElements;
	}
	
	public boolean isFull() {
		try {
			new MyNode<Character>('X');
			return false;
		} catch(OutOfMemoryError e) {
			return true;
		}
	}
	
	public boolean isEmpty() {
		return howManyElements == 0;
	}
}
