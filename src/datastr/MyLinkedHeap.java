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
		MyNode<Ttype> newNode = new MyNode<Ttype>(element);
		if(isEmpty()) {
			rootNode = newNode;
			lastNode = newNode;
			howManyElements++;
		} else {
			//ja būs saknes elementam kreisais bērns
			if(howManyElements == 1) {
				rootNode.setLeftNode(newNode);
				newNode.setParentNode(rootNode);
				lastNode = newNode;
				howManyElements++;
				level++;
				//TODO izsaukt reheapUp funkciju
				return;
			}
			//pēdējam blokam nav neviens no bērniem
			if(lastNode.getLeftNode() == null && lastNode.getRightNode() == null) {
				lastNode.setLeftNode(newNode);
				newNode.setParentNode(lastNode);
				lastNode = newNode;
				howManyElements++;
				//TODO izsaukt reheapUp funkciju
				return;
			//kad pēdējam blokam nav blakus labais bloks
			}
			if(lastNode.getParentNode() != null && lastNode.getParentNode().getRightNode() == null) {
				MyNode<Ttype> parentNodeTemp = lastNode.getParentNode();
				parentNodeTemp.setRightNode(newNode);
				newNode.setParentNode(parentNodeTemp);
				lastNode = newNode;
				howManyElements++;
				//TODO izsaukt reheapUP funkciju
				return;
			}
			//2^0 = 1 elements 0.līmenī
			//2^1 = 2 elementi 1.līmenī
			//2^2 = 4 elementi 2.līmenī
			int sum = 0;
			//es noskaidroju, cik ir jābūt blokiem līdz šim līmenim ieskaitot
			for(int i = 0; i <= level; i++) {
				sum = (int)(sum + Math.pow(2, i));
			}
			//lastNode ir kā pēdējais bloks savā līmenī
			if(sum == howManyElements) {
				MyNode<Ttype> currentNode = rootNode;
				while(currentNode.getLeftNode() != null) {
					currentNode = currentNode.getLeftNode();
				}
				lastNode = currentNode;
				lastNode.setLeftNode(newNode);
				newNode.setParentNode(lastNode);
				lastNode = newNode;
				howManyElements++;
				level++;
				//TODO izsaucam reheapUP funkciju
			}
			//TODO izviedot pēdējo scenāriju, kur no labā bērna spēj pārlekt uz blakus apakškoka kreiso bērnu
		}
	}
}
