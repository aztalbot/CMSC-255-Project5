package TreePackage;
import java.util.NoSuchElementException;

/**
   A class that implements the ADT AVL tree by extending BinarySearchTree.
   The remove operation is not supported.
   
   @author Frank M. Carrano
   @author Timothy M. Henry
   @version 4.01
*/
public class AVLTree<T extends Comparable<? super T>> 
             extends BinarySearchTree<T> {
	public AVLTree(){
		super();
	} 
	
	public AVLTree(T rootEntry)	{
		super(rootEntry);
	} 

	public T add(T newEntry)	{
      T result = null;

      if (isEmpty())
         setRootNode(new BinaryNode<>(newEntry));
      else   {
         BinaryNode<T> rootNode = getRootNode();
         result = addEntry(rootNode, newEntry);
         setRootNode(rebalance(rootNode));
      } 
      return result;
	} 

	// Adds newEntry to the non-empty subtree rooted at rootNode. 
	private T addEntry(BinaryNode<T> rootNode, T newEntry){
      assert rootNode != null;
      T result = null;
      int comparison = newEntry.compareTo(rootNode.getData());

      if (comparison == 0)  {
         result = rootNode.getData();
         rootNode.setData(newEntry);
      }
      else if (comparison < 0)  {
         if (rootNode.hasLeftChild()) {
            BinaryNode<T> leftChild = rootNode.getLeftChild();
            result = addEntry(leftChild, newEntry);
            rootNode.setLeftChild(rebalance(leftChild));
         }
         else
            rootNode.setLeftChild(new BinaryNode<>(newEntry));
      }
      else  {
         assert comparison > 0;

         if (rootNode.hasRightChild())  {
            BinaryNode<T> rightChild = rootNode.getRightChild();
            result = addEntry(rightChild, newEntry);
            rootNode.setRightChild(rebalance(rightChild));
         }
         else
            rootNode.setRightChild(new BinaryNode<>(newEntry));
      } 
      return result;
   } 
	
   public T remove(T entry) {
      throw new UnsupportedOperationException();
   } 

	private BinaryNode<T> rebalance(BinaryNode<T> nodeN) {
      int heightDifference = getHeightDifference(nodeN);

      if (heightDifference > 1)  { 
         // Left subtree is taller by more than 1,
         // so addition was in left subtree
         if (getHeightDifference(nodeN.getLeftChild()) > 0)
            // Addition was in left subtree of left child
            nodeN = rotateRight(nodeN);
         else
            // Addition was in right subtree of left child
            nodeN = rotateLeftRight(nodeN);
      }
      else if (heightDifference < -1)  { 
         // Right subtree is taller by more than 1,
         // so addition was in right subtree
         if (getHeightDifference(nodeN.getRightChild()) < 0)
            // Addition was in right subtree of right child
            nodeN = rotateLeft(nodeN);
         else
            // Addition was in left subtree of right child
            nodeN = rotateRightLeft(nodeN);
      } 
      // Else nodeN is balanced
      return nodeN;
	} 

	// Corrects an imbalance at the node closest to a structural
	// change in the left subtree of the node's left child.
	// nodeN is a node, closest to the newly added leaf, at which 
	// an imbalance occurs and that has a left child. 
	private BinaryNode<T> rotateRight(BinaryNode<T> nodeN){
      BinaryNode<T> nodeC = nodeN.getLeftChild();
      nodeN.setLeftChild(nodeC.getRightChild());
      nodeC.setRightChild(nodeN);
      return nodeC;
	} 
  
   // Corrects an imbalance at the node closest to a structural
   // change in the right subtree of the node's right child.
   // nodeN is a node, closest to the newly added leaf, at which 
   // an imbalance occurs and that has a right child. 
   private BinaryNode<T> rotateLeft(BinaryNode<T> nodeN) {
      BinaryNode<T> nodeC = nodeN.getRightChild();
      nodeN.setRightChild(nodeC.getLeftChild());
      nodeC.setLeftChild(nodeN);
      return nodeC;
   } 
  
	// Corrects an imbalance at the node closest to a structural
	// change in the left subtree of the node's right child.
	// nodeN is a node, closest to the newly added leaf, at which 
	// an imbalance occurs and that has a right child. 
	private BinaryNode<T> rotateRightLeft(BinaryNode<T> nodeN){
      BinaryNode<T> nodeC = nodeN.getRightChild();
      nodeN.setRightChild(rotateRight(nodeC));
      return rotateLeft(nodeN);
	} 
  
   // Corrects an imbalance at the node closest to a structural
   // change in the right subtree of the node's left child.
   // nodeN is a node, closest to the newly added leaf, at which  
   // an imbalance occurs and that has a left child. 
   private BinaryNode<T> rotateLeftRight(BinaryNode<T> nodeN)  {
      BinaryNode<T> nodeC = nodeN.getLeftChild();
      nodeN.setLeftChild(rotateLeft(nodeC));
      return rotateRight(nodeN);
   } 

   // Returns the difference in heights of the given node's
   // left and right subtrees.
	private int getHeightDifference(BinaryNode<T> node){
		BinaryNode<T> left = node.getLeftChild();
		BinaryNode<T> right = node.getRightChild();

		int leftHeight, rightHeight;

		if (left == null)
			leftHeight = 0;
		else
			leftHeight = left.getHeight();
		
		if (right == null)
			rightHeight = 0;
		else
			rightHeight = right.getHeight();
			
		return leftHeight - rightHeight;
	} 
	
	}
