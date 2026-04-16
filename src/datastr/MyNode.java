package datastr;

public class MyNode<Ttype> {
	private Ttype value;
	private MyNode<Ttype> parentNode = null;
	private MyNode<Ttype> leftNode = null;
	private MyNode<Ttype> rightNode = null;
	
	public MyNode(Ttype value){
		setValue(value);
	}
	
	public Ttype getValue() {
		return value;
	}
	
	public void setValue(Ttype value) {
		if(value != null) {
			this.value = value;
		} else {
			this.value = (Ttype)new Object();
		}
	}
	
	public MyNode<Ttype> getParentNode() {
		return parentNode;
	}
	
	public void setParentNode(MyNode<Ttype> parentNode) {
		this.parentNode = parentNode;
	}
	
	public MyNode<Ttype> getLeftNode() {
		return leftNode;
	}
	
	public void setLeftNode(MyNode<Ttype> leftNode) {
		this.leftNode = leftNode;
	}
	
	public MyNode<Ttype> getRightNode() {
		return rightNode;
	}
	
	public void setRightNode(MyNode<Ttype> rightNode) {
		this.rightNode = rightNode;
	}
	
	public String toString() {
		return "" + value;
	}
	
}
