package datastr;

public class MyLinkedHeap<Ttype> {
	MyNode<Ttype> rootNode = null;
	MyNode<Ttype> lastNode = null;
	private int howManyElements = 0;
	private int level = 0;
	
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
		if (isFull()) {
			throw new Exception("Kaudze ir pilna un nav iespējams pievienot elementu");
		}

		if (element == null) {
			throw new Exception("Elements nevar būt null");
		}

		if (isEmpty()) {// ja tiek pievienots pirmais elements
			MyNode<Ttype> newNode = new MyNode<Ttype>(element);
			rootNode = newNode;
			lastNode = newNode;
			howManyElements++;
		} else// ja tiek pievienots kārtējais ( ne pirmais) elements
		{
			MyNode<Ttype> newNode = new MyNode<Ttype>(element);
			// ja būs saknes elementam kreisais bērns
			if (howManyElements == 1) {
				rootNode.setLeftNode(newNode);
				newNode.setParentNode(rootNode);
				lastNode = newNode;
				howManyElements++;
				level++;
				reheapUp(newNode);
				return;
			}

			// kad pedjeam blokam nav blakus labais bloks
			if (lastNode.getParentNode() != null && lastNode.getParentNode().getRightNode() == null) {

				MyNode<Ttype> parentNodeTemp = lastNode.getParentNode();
				parentNodeTemp.setRightNode(newNode);
				newNode.setParentNode(parentNodeTemp);

				lastNode = newNode;
				howManyElements++;
				reheapUp(newNode);
				return;

			}
			// 2^0 = 1 elements 0.līmenī
			// 2^1 = 2 elementi 1.līmenī
			// 2^2 = 4 elementi 2.līmenī
			int sum = 0;
			// es noskaidroju, cik ir jābūt blokiem līdz šim līmenim ieskaitot
			for (int i = 0; i <= level; i++) {
				sum = (int) (sum + Math.pow(2, i));
			}

			// lastNode ir kā pēdejais bloks sava līmenī
			if (sum == howManyElements) {
				MyNode<Ttype> currentNode = rootNode;

				// ja blokam ir kreisais berns, tad jelec uz to
				while (currentNode.getLeftNode() != null) {
					currentNode = currentNode.getLeftNode();
				}

				lastNode = currentNode;

				lastNode.setLeftNode(newNode);
				newNode.setParentNode(lastNode);

				lastNode = newNode;
				howManyElements++;
				level++;
				reheapUp(newNode);
				return;

			} else {
				// pēdējam blokam ir abi bērni
				if (lastNode.getParentNode().getLeftNode() != null
						&& lastNode.getParentNode().getRightNode() != null) {

					int numberForNewNode = howManyElements;
					//otrais -1, jo kartas skaitlis sāks no 1 nevis no 0 ka masīva
					int numberForNewNodeParent = (numberForNewNode-1 -1)/2;
					System.out.println("Parent number: " + numberForNewNodeParent);
					//leftIndex = parentIndex*2 + 1;
					//((leftIndex -1)/2) = parentIndex
					//rightIndex = parentIndex*2 +2
					MyNode currentParent = getLastNodeByNumber(numberForNewNodeParent);
					currentParent.setLeftNode(newNode);
					newNode.setParentNode(currentParent);
					lastNode = newNode;
					reheapUp(newNode);
					howManyElements++;
					return;
				}

				// pēdējam blokam nav neviens no bērniem
				if (lastNode.getLeftNode() == null && lastNode.getRightNode() == null) {
					lastNode.setLeftNode(newNode);
					newNode.setParentNode(lastNode);
					lastNode = newNode;
					howManyElements++;
					reheapUp(newNode);
					return;
				}

			}

			
		}

	}
	
	//MAX kaudzes gadījums
	private void reheapUp(MyNode<Ttype> nodeTemp) {
		//vai blokam ir vecāks
		if(nodeTemp.getParentNode() != null) {
			MyNode<Ttype> parentTempNode = nodeTemp.getParentNode();
			if(((Comparable)nodeTemp.getValue()).compareTo(parentTempNode.getValue()) > 0) {
				//mainam vietā vērtības
				swap(nodeTemp, parentTempNode);
				reheapUp(parentTempNode); //izsaucam šo pašu funkciju rekursīvi, bet 
			}
		}
	}
	
	private void swap(MyNode<Ttype> node1, MyNode<Ttype> node2) {
		Ttype temp = node1.getValue();
		node1.setValue(node2.getValue());
		node2.setValue(temp);
	}
	
	public void print() throws Exception {
		if(isEmpty()) {
			throw new Exception("Kaudze ir tukša un to nevar izprintēt");
		}
		printHelper(rootNode);
		
	
	}
	
	private void printHelper(MyNode<Ttype> nodeTemp) {
		if(nodeTemp != null) {
			System.out.println("P: " + nodeTemp.getValue());
			//noskaidrojam, vai eksistē kreisais bērns
			if(nodeTemp.getLeftNode() != null) {
				System.out.println("P: " + nodeTemp.getValue() + " Left Child: " + nodeTemp.getLeftNode().getValue());
				//izpildi so pasu funkciju uz kreiso bērnu
				printHelper(nodeTemp.getLeftNode());
			}
			//noskaidrojam, vai eksistē labais bērns
			if(nodeTemp.getRightNode() != null) {
				System.out.println("P: " + nodeTemp.getValue() + " Right child: " + nodeTemp.getRightNode().getValue());
			}
			printHelper(nodeTemp.getRightNode());
		}
	}
	
	private MyNode<Ttype> getLastNodeByNumber(int number) throws Exception {
		if(number < 1) {
			throw new Exception("Kartas skaitlis nevar but mazaks par 1");
		}
		
		//ja number ir 6, tad binary bus 110
		String binary = Integer.toBinaryString(number);
		MyNode<Ttype> currentNode = rootNode;
		
		for(int i = 1; i < binary.length(); i++) {
			if(binary.charAt(i) == '0') {
				currentNode = currentNode.getLeftNode();
			} else {
				currentNode = currentNode.getRightNode();
			}
		}
		
		return currentNode;
	}
	
	
	//uztaisīt dequeue funkciju
	//veicam visas pārbaudes
	//saglabāsim root elementu mainīgajā
	//pēdējo bloka vērtību ieliekam root blokā
	//samazinam howManyElements
	//lastNode samainīt (level samazināt, kur tas nepieciešams)
	//reheapDown izsaukt
	//atgriežam elementu, kurš bija sākumā saknes blokā
	
	
	
	//leftChindex = parentindex*2+1
	//rightChindex = parentindex*2+2
	//parentindex = (leftChindex-1)/2
	//parentindex = (rightChindex-2)/2
}
