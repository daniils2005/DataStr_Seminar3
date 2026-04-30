package service;

import datastr.MyLinkedHeap;
import model.Patient;

public class MainService {

	public static void main(String[] args) {
		MyLinkedHeap<Integer> integersInHeap = new MyLinkedHeap<Integer>();
		try {
			integersInHeap.enqueue(40);//P: 40
			integersInHeap.enqueue(50);//P: 50(LC: 40)
			integersInHeap.enqueue(35);//P: 50(LC: 40, RC:35)
			integersInHeap.enqueue(99);//P:99 (LC:50, RC:35), P:50 (LC:40)
			integersInHeap.enqueue(55);//P:99 (LC:55, RC:35), P:55 (LC:40, RC:50)
			integersInHeap.enqueue(2);//P:99 (LC:55, RC:35), P:55 (LC:40, RC:50); ):35 (LC:2)
			integersInHeap.print();
			
			System.out.println("Max vērtība " + integersInHeap.dequeue());
			integersInHeap.print();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("---------------DARBIBAS AR PATIENT------------------");
		
		MyLinkedHeap<Patient> patients = new MyLinkedHeap<Patient>();
		try {
			patients.enqueue(new Patient("Janis", "Berzins", 1)); //P: Janis
			patients.enqueue(new Patient("Liga", "Jauka", 3)); //P: Liga (LC: Janis)
			patients.enqueue(new Patient("Baiba", "Kalnina", 2)); //P: Liga(LC: Janis, RC: Baiba)
			patients.enqueue(new Patient("Juris", "Nejaukais", 5)); //P: Juris (LC: Līga, RC: Baiba), P:Līga (LC:Jānis)
			patients.print();
			
			System.out.println("Max pacients: " + patients.dequeue()); //Juris
			patients.print(); //P: Liga (LC: Janis, RC: Baiba)
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
