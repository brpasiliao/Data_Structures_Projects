import java.util.*;

/*
Student: Bian Pasiliao
*/

public class A2ExpressionTreePB{
   public static void main(String argc[]){
      ExprTree et = new ExprTree();
      et.inToTree("(3+(4*5))");
      et.print(); //print above
      et.inToTree("((1-(2+3))+(4*5))");
      et.print(); //print above
      et.inToTree("(3(4+5))");
      et.print(); //no output
      et.inToTree("((4+5)*2+1)");
      et.print(); //no output
      et.inToTree("((4+5)*3))");
      et.print(); //no output
      et.postToTree("34+5");
      et.print(); //no output
/*
      System.out.println("Enter an algebraic expression: ");
      Scanner s = new Scanner(System.in);
      String alg =  s.nextLine();
      s.close();
      et = new ExprTree(alg);
      et.print();
*/
      Tree t1 = new Tree();
      Tree t2 = new Tree();
      t1.makeTree();
      t1.levelOrder();	//0 1 2 3 4 5 6 13 14 15 7 8 9 10 11 12
      t2.makeTree2();
      t2.levelOrder();	//0 1 2 3
/*    System.out.println("sub tree t1 and t1 " + t1.isSubTree(t1));
      System.out.println("sub tree t1 and t2 " + t1.isSubTree(t2)); //t2 is not a subTree of t1
      Tree t3 = new Tree();
      t3.makeTree3();
      t3.levelOrder();
      System.out.println("sub tree t1 and t3 " + t1.isSubTree(t3)); //t3 is a subTree of t1
      Tree t4 = new Tree();
      t4.makeTree4();
      t4.levelOrder();
      System.out.println("sub tree t1 and t4 " + t1.isSubTree(t4)); //t4 is a subTree of t1
*/
   }
}

class ExprTree{
  private static class BTNode{
    char value;
    BTNode parent, left, right;
    public BTNode(char e){
      this(e, null, null, null);
    }
    public BTNode(char e, BTNode p, BTNode l, BTNode r){
      value = e;
      parent = p;
      left = l;
      right = r;
    }
  }
  BTNode root;
  public ExprTree(){
    root = null;
  }
  public ExprTree(String expression){

  }
  public boolean isEmpty(){
    return root == null;
  }
  public void inToTree(String expression){
    LinkedStack<Character> in = new LinkedStack<Character>();
    String post = "";
    char c, throwAway;
    int precedence = 0, parentheses = 0, opNum = 0;
    for (int i = 0; i < expression.length(); i++) {
      c = expression.charAt(i);
      if (c == '(') {
        precedence++;
        parentheses++;
      }
      if (c == ')') {
        precedence--;
        parentheses++;
      }
      if (Character.isDigit(c)) {
        post = post + c;
        opNum++;
      }
      if (isOperator(c)) {
        while (!in.isEmpty() && precedence <=
        Character.getNumericValue(in.peek())) {
          throwAway = in.pop();
          post = post + in.pop();
        }
        in.push(c);
        in.push(Integer.toString(precedence).charAt(0));
        opNum++;
      }
    }
    while (!in.isEmpty()) {
      throwAway = in.pop();
      post = post + in.pop();
    }
    if (parentheses == opNum - 1) postToTree(post);
    else root = null;
  }
  public void postToTree(String expression) {
    LinkedStack<BTNode> tree = new LinkedStack<BTNode>();
    BTNode l, r;
    int num = 0; int op = 0;
    char c;
    for (int i = 0; i < expression.length(); i++) {
      c = expression.charAt(i);
      if (Character.isDigit(c)) {
        tree.push(new BTNode(c));
        num++;
      }
      else {
        r = tree.pop();
        l = tree.pop();
        tree.push(new BTNode(c, null, l, r));
        op++;
      }
    }
    if (op == num - 1) root = tree.peek();
    else root = null;
  }
  public void print(){
    if (root != null) {
      printHelper(root);
      System.out.println();
    }
  }
  public void printHelper(BTNode btn) {
    if (isLeaf(btn)) {
      System.out.print(btn.value);
      return;
    }
    System.out.print('(');
    printHelper(btn.left);
    System.out.print(btn.value);
    printHelper(btn.right);
    System.out.print(')');
  }
  public boolean isOperator(char c){
    return (c == '+') || (c == '-') || (c == '*') || (c == '/');
  }
  public boolean isLeaf(BTNode btn){
    return (btn.left == null) && (btn.right == null);
  }
}

class Tree{
 private static class TNode{
   private int value;
   private TNode parent;
   private List<TNode> children;
   public TNode(){
     this(0, null);
   }
   public TNode(int e){
     this(e, null);
   }
   public TNode(int e, TNode p){
     value = e;
     parent = p;
     children = new ArrayList<TNode>();
   }
 }
 private TNode root;
 private int size;
 Tree(){
   root = null;
   size = 0;
 }
 public TNode createNode(int e, TNode p){
   return new TNode(e, p);
 }
 public TNode addChild(TNode n, int e){
   TNode temp = createNode(e, n);
   n.children.add(temp);
   size++;
   return temp;
 }
 public void makeTree(){
   root = createNode(0, null);
   size++;
   buildTree(root, 3);
 }
 public void makeTree2(){
   root = createNode(0, null);
   size++;
   buildTree(root, 1);
 }
 public void makeTree3(){
   root = createNode(3, null);
   size++;
 }
 public void makeTree4(){
   root = createNode(2, null);
   size += 13;
   buildTree(root, 1);
   size -= 12;
 }
 private void buildTree(TNode n, int i){
   if (i <= 0) return;
   TNode fc = addChild(n, size);
   TNode sc = addChild(n, size);
   TNode tc = addChild(n, size);
   buildTree(fc, i - 1);
   buildTree(sc, i - 2);
   if (i % 2 == 0)
     buildTree(tc, i - 1);
 }
 public void levelOrder() {
   ArrayList<TNode> level = new ArrayList<TNode>();
   level.add(root);
   TNode temp;
   int index = 0;
   while (index < level.size()) {
     temp = level.get(index);
     System.out.print(temp.value + " ");
     for (int i = 0; i < temp.children.size(); i++)
       level.add(temp.children.get(i));
     index++;
   }
   System.out.println();
 }
}

interface Stack<T> {
   void push(T ele);
   T pop();
}

class LinkedStack<E> implements Stack<E>{
  private static class Node<E>{
    E element;
    Node<E> next;
    public Node(E element, Node<E> next){
      this.element = element;
      this.next = next;
    }
  }
  private Node<E> top;
  private int size;
  public LinkedStack(){
    top = null;
    size = 0;
  }
  public int size(){ return size;}
  public boolean isEmpty(){ return size == 0;}
  public void push(E element){
    Node<E> ntop = new Node<E>(element, top);
    top = ntop;
    size++;
  }
  public E peek(){
    if (isEmpty()) return null;
    return top.element;
  }
  public E pop() {
    E ans = top.element;
    top = top.next;
    size--;
    return ans;
  }
}
