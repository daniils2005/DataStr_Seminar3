package datastr;

import java.util.ArrayList;
import java.util.LinkedList;

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

					MyNode currentParent = findInsertionNode();
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

			// TODO izveidot pedējo scenāriju, kurs no labā bērna spej pārlekt
			// uz blakus apkaškoka kreiso bērnu - paņemt piemēru no apraksta

		}

	}

	private MyNode findInsertionNode() {
		LinkedList<MyNode> queue = new LinkedList<>();
		queue.add(rootNode);
		while (!queue.isEmpty()) {
			MyNode currentNode = queue.poll();
			if (currentNode.getRightNode() == null) {
				return currentNode;
			} else {
				queue.add(currentNode.getRightNode());
			}
			if (currentNode.getLeftNode() == null) {
				return currentNode;
			} else {
				queue.add(currentNode.getLeftNode());
			}

		}
		return null;
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
	
	//uztaisīt dequeue funkciju
	//veicam visas pārbaudes
	//saglabāsim root elementu mainīgajā
	//pēdējo bloka vērtību ieliekam root blokā
	//samazinam howManyElements
	//lastNode samainīt (level samazināt, kur tas nepieciešams)
	//reheapDown izsaukt
	//atgriežam elementu, kurš bija sākumā saknes blokā
	
	public Ttype dequeue() throws Exception {
		if(isEmpty()) {
			throw new Exception("Kaudze ir tukša un nevar atgriez max elementu");
		}
		
		Ttype maxElement = rootNode.getValue();
		
		rootNode.setValue(lastNode.getValue());
		
		//tagadejais pedejais mezgls ir sava vecaka kreisais berns
		if(lastNode.getParentNode().getLeftNode() == lastNode) {
			lastNode.getParentNode().setLeftNode(null);
		}
		
		if(lastNode.getParentNode().getRightNode() == lastNode) {
			lastNode.getParentNode().setRightNode(null);
		}
		
		howManyElements--;
		reheapDown(rootNode);
		
		return maxElement;
	}
	
	private void reheapDown(MyNode<Ttype> nodeTemp) {
		if(nodeTemp != null) {
			//ja ir tikai viens berns un tas ir kreisais
			if(nodeTemp.getLeftNode() != null && nodeTemp.getRightNode() == null) {
				if(((Comparable)nodeTemp.getValue()).compareTo(nodeTemp.getLeftNode().getValue()) < 0) {
					swap(nodeTemp, nodeTemp.getLeftNode());
				}
			}
			
			//ja ir abi berni
			else if(nodeTemp.getLeftNode() != null && nodeTemp.getRightNode() != null) {
				//parbaudam, vai kreisais berns ir lielaks par labo
				if(((Comparable)nodeTemp.getLeftNode().getValue()).compareTo(nodeTemp.getRightNode().getValue()) > 0) {
					//vai kreisais berns ir lielaks par pasu bloka vertibu
					if(((Comparable)nodeTemp.getLeftNode().getValue()).compareTo(nodeTemp.getValue()) > 0) {
						swap(nodeTemp, nodeTemp.getLeftNode());
						reheapDown(nodeTemp.getLeftNode());
					}
				} else { //ja kreisais berns ir mazaks vai vienads ar labo bernu
					if(((Comparable)nodeTemp.getLeftNode().getValue()).compareTo(nodeTemp.getValue()) > 0) {
						swap(nodeTemp, nodeTemp.getLeftNode());
						reheapDown(nodeTemp.getRightNode());
					}
				}
			}
		}
	}
	
	
	//leftChindex = parentindex*2+1
	//rightChindex = parentindex*2+2
	//parentindex = (leftChindex-1)/2
	//parentindex = (rightChindex-2)/2
}
