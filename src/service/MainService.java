package service;

import datastr.MyLinkedHeap;

public class MainService {

	public static void main(String[] args) {
		MyLinkedHeap<Integer> integersInHeap = new MyLinkedHeap<Integer>();
		try {
			integersInHeap.enqueue(40); //P: 40
			integersInHeap.enqueue(50); //P: 50(LC: 40)
			integersInHeap.enqueue(35); //P: 50(LC40, RC: 35)
			integersInHeap.enqueue(99); //P: 99(LC50, RC: 35), P:50(LC: 40)
			integersInHeap.enqueue(55); //P: 99(LC:55, RC: 35), P:55 (LC:40, RC:50)
			integersInHeap.print();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
