/**
 *  * Student: Bian Pasiliao
 *   */
public class A1LinkedListPB{
   	public static void main(String argc[]){
		Linkedlist<Integer> sl = new Linkedlist<>();
		DLinkedList<String> dl = new DLinkedList<>();
		PolynomialLinkedlist sum, prod;
		for (int i = 1000; i > 0; i-=3) sl.add(i);

		try {
			sl.insert(111, sl.getNode(50), sl.getNode(51));
			if (sl.detectLoop()) System.out.println("Loop!");
			else System.out.println("No loop.");


			sl.insert(123, sl.getNode(51), sl.getNode(50));
			if (sl.detectLoop()) System.out.println("Loop!");
			else System.out.println("No loop.");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		dl.add("Three",0);
		dl.add("Five",1);
		dl.add("One",0);
		dl.add("Two",1);
		dl.add("Four",3);
		dl.print();

		PolynomialLinkedlist p1 = new PolynomialLinkedlist(2,3);
		PolynomialLinkedlist p2 = new PolynomialLinkedlist(3,2);
		PolynomialLinkedlist p3 = p1.add(p2);
		p1 = new PolynomialLinkedlist(3,2);
		p2 = new PolynomialLinkedlist(1,0);
		PolynomialLinkedlist p4 = p1.add(p2);
		sum = p3.add(p4);
		prod = p3.multiply(p4);
		sum.print();
		prod.print();
		p1.print();
		p2.print();
	}
}
class Linkedlist<E>{
	private static class Node<E>{
		private E element;
		private Node<E> next;
		public Node(E e, Node<E> n){
			element = e;
			next = n;
		}
		public E getE(){
			return element;
		}
		public Node<E> getNext(){
			return next;
		}
		public void setE(E e){
			element = e;
		}
		public void setNext(Node<E> n){
			next = n;
		}
	}
	private Node<E> head;
	public Linkedlist(){
		head = null;
	}
	public void add(E e){
		Node<E> temp = new Node<>(e, head);
		head = temp;
	}
	public void insert(E e, Node<E> p, Node<E> n){
		p.setNext(new Node<>(e, n));
	}
	public Node<E> getNode(int i) throws Exception{
		Node<E> temp = head;
		while (i > 0){
			if (temp == null) throw new Exception("Out of bound");
			temp = temp.getNext();
			i--;
		}
		return temp;
	}
    public boolean detectLoop() {
        Node<E> slow = head;
        Node<E> fast = head;
        while (fast.getNext() != null && fast.getNext().getNext() != null) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();
            if (slow == fast) return true;
        }
        return false;
    }
}
class DLinkedList<E>{
	private static class DNode<E>{
		private E element;
		private DNode<E> prev;
		private DNode<E> next;
		public DNode(E e){
			this(e, null, null);
		}
		public DNode(E e, DNode<E> p, DNode<E> n){
			element = e;
			prev = p;
			next = n;
		}
		public E getE(){
			return element;
		}
		public DNode<E> getPrev(){
			return prev;
		}
		public DNode<E> getNext(){
			return next;
		}
		public void setE(E e){
			element = e;
		}
		public void setPrev(DNode<E> p){
			prev = p;
		}
		public void setNext(DNode<E> n){
			next = n;
		}
	}
	private DNode<E> header;
	private DNode<E> trailer;
	private int size;
	public DLinkedList(){
		header = new DNode<E>(null);
		trailer = new DNode<E>(null, header, null);
		header.setNext(trailer);
		size = 0;
	}
	public void print(){
	        DNode<E> temp = header.getNext();
		while (temp != trailer){
			System.out.print(temp.getE().toString() + ", ");
			temp = temp.getNext();
		}
		System.out.println();
	}
    public void add(E newE, int pos) {
        if (pos > size) throw new IllegalArgumentException("Out of bound");
        DNode<E> temp, newN;
            if (pos < size/2) {
                temp = header.next;
                for (int i = 0; i < pos; i++) {
                    temp = temp.getNext();
                }
                newN = new DNode<E>(newE, temp.getPrev(), temp);
            }
            else {
                temp = trailer.prev;
                for (int i = size; i > pos; i--) {
                    temp = temp.getPrev();
            }
            newN = new DNode<E>(newE, temp, temp.getNext());
        }
        newN.prev.setNext(newN);
        newN.next.setPrev(newN);
        size++;
    }
}
class PolynomialLinkedlist {
	private static class PNode{
		private int coe;
		private int exp;
		private PNode next;
		public PNode(int c, int e){
			this(c, e, null);
		}
		public PNode(int c, int e, PNode n){
			coe = c;
			exp = e;
			next = n;
		}
		public void setCoe(int c){ coe = c;}
		public void setExp(int e){ exp = e;}
		public void setNext(PNode n){ next = n;}
		public int getCoe(){ return coe;}
		public int getExp(){ return exp;}
		public PNode getNext(){ return next;}
	}
	private PNode first;
	private PNode last;
    public PolynomialLinkedlist() {
		first = last = null;
	}
	public PolynomialLinkedlist(int c, int e){
		PNode tempn = new PNode(c, e);
		first = last = tempn;
	}
	public void print(){
		if (first == null){
			System.out.println();
			return;
		}
		PNode temp = first;
		String ans = "";
		while (temp != null){
			if (temp.getCoe() > 0) {
				if (temp != first) ans = ans + " + ";
				ans = ans + temp.getCoe();
			}
			else if (temp.getCoe() < 0) ans = ans + " - " + temp.getCoe() * -1;
			if (temp.getExp() != 0){
				ans = ans + "X^" + temp.getExp();
			}
			temp = temp.getNext();
		}
		System.out.println(ans);
	}
    public PolynomialLinkedlist add(PolynomialLinkedlist addPLL) {
        PolynomialLinkedlist newPLL = new PolynomialLinkedlist(first.getCoe(), first.getExp());
        PNode oldCopy = first.next;
        PNode newCopy = newPLL.first;
        while (oldCopy != null) {
            PNode temp = new PNode(oldCopy.getCoe(), oldCopy.getExp());
            newCopy.setNext(temp);
            newPLL.last = temp;
            oldCopy = oldCopy.next;
            newCopy = newCopy.next;
        }
        PNode addNode = addPLL.first;
        PNode newNode = newPLL.first;
        while (addNode != null) {
            if (newNode == null) {
                PNode temp = new PNode(addNode.getCoe(), addNode.getExp());
                newPLL.last.setNext(temp);
                newPLL.last = temp;
                addNode = addNode.getNext();
            }
            else if (addNode.getExp() < newNode.getExp()) {
                newNode = newNode.getNext();
            }
            else if (addNode.getExp() > newNode.getExp()) {
                PNode temp = new PNode(newNode.getCoe(), newNode.getExp(), newNode.getNext());
                newNode.setCoe(addNode.coe);
                newNode.setExp(addNode.exp);
                newNode.setNext(temp);
                newNode = newNode.getNext();
                addNode = addNode.getNext();
                newNode = newNode.getNext();
            }
            else if (addNode.getExp() == newNode.getExp()) {
                newNode.setCoe(newNode.getCoe() + addNode.getCoe());
                addNode = addNode.getNext();
                newNode = newNode.getNext();
            }
        }
        return newPLL;
    }
    public PolynomialLinkedlist multiply(PolynomialLinkedlist mulPLL) {
        PNode mulNode = mulPLL.first;
        PolynomialLinkedlist newPLL = new PolynomialLinkedlist(
            first.getCoe()*mulNode.getCoe(), first.getExp()+mulNode.getExp()
        );
        PNode oldNode = first.next;
        PNode newNode = newPLL.first;
        while (oldNode != null) {
            PNode prod = new PNode(
                oldNode.getCoe()*mulNode.getCoe(), oldNode.getExp()+mulNode.getExp()
            );
            newNode.setNext(prod);
            oldNode = oldNode.next;
        }
        mulNode = mulNode.getNext();
        while (mulNode != null) {
            oldNode = first;
            PolynomialLinkedlist tempPLL = new PolynomialLinkedlist(
                oldNode.getCoe()*mulNode.getCoe(), oldNode.getExp()+mulNode.getExp()
            );
            PNode temp = tempPLL.first;
            while (oldNode != null) {
                PNode prod = new PNode(
                    oldNode.getCoe()*mulNode.getCoe(), oldNode.getExp()+mulNode.getExp()
                );
                temp.setNext(prod);
                oldNode = oldNode.next;
            }
            newPLL = newPLL.add(tempPLL);
            mulNode = mulNode.next;
        }
        return newPLL;
    }
}
