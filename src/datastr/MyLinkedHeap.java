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
	
	public void enqueue(Ttype element) throws Exception {
		if(isFull()) {
			throw new Exception("Kaudze ir pilna un nav iespējams pievienot elementu");
		}
		if(element == null) {
			throw new Exception("Elements nevar būt null");
		}
		if(isEmpty()) {
			MyNode<Ttype> newNode = new MyNode<Ttype>(element);
			rootNode = newNode;
			lastNode = newNode;
			howManyElements++;
		}
	}
}
