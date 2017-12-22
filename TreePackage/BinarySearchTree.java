/**
 * PROJECT 5
 * Java Keyword Identifier
 * Name: Andrew Talbot
 * Class: CMSC 256 - Sec 901
 * Semester: Fall 2017
 *
 */

package TreePackage;

public class BinarySearchTree<T extends Comparable<? super T>> extends BinaryTree<T> implements SearchTreeInterface<T>{

	public BinarySearchTree() {
		super();
	}
	
	public BinarySearchTree(T anObject) {
		super(anObject);
	}
	
	@Override
	public void setTree(T rootData)  {
		throw new UnsupportedOperationException();
	} 

	@Override
	public void setTree(T rootData, BinaryTreeInterface<T> leftTree, BinaryTreeInterface<T> rightTree)  {
		throw new UnsupportedOperationException();
	}
	
	@SuppressWarnings("unused")
	private T findEntry(BinaryNode<T> rootNode, T entry) {
		try {
			T rootNodeEntry = rootNode.getData();
			if (rootNode == null)
				return null;
			else if (entry.equals(rootNodeEntry))
				return rootNode.getData();
			else if (entry.compareTo(rootNodeEntry) < 0)
				return findEntry(rootNode.getLeftChild(), entry);
			else
				return findEntry(rootNode.getRightChild(), entry);
		} catch(NullPointerException ex) {
			return null;
		}
	}
	
	public T getEntry(T entry) {
		 return findEntry(getRootNode(), entry);
	}
		
	public boolean contains(T entry) {
		return getEntry(entry) != null;
	}
	
	private T addEntry (BinaryNode<T> rootNode, T newEntry) {
		T result = null;
		T rootNodeEntry = rootNode.getData();
		if (newEntry.equals(rootNodeEntry)) {
			result = rootNodeEntry;
			rootNode.setData(newEntry);
		} else if (newEntry.compareTo(rootNodeEntry) < 0) {
			if (rootNode.hasLeftChild())
				result = addEntry(rootNode.getLeftChild(), newEntry);
			else
				rootNode.setLeftChild(new BinaryNode<T>(newEntry));
		} else {
			if (rootNode.hasRightChild())
				result = addEntry(rootNode.getRightChild(), newEntry);
			else
				rootNode.setRightChild(new BinaryNode<T>(newEntry));
		}
		return result;
	}
	
	public T add(T newEntry) {
		T result = null;
		if (isEmpty())
			setRootNode(new BinaryNode<>(newEntry));
		else
			result = addEntry(getRootNode(), newEntry);
		return result;
	}
	
	public T remove(T entry) {
		ReturnObject oldEntry = new ReturnObject(null);
		BinaryNode<T> newRoot = removeEntry(getRootNode(), entry, oldEntry);
		setRootNode(newRoot);
		return oldEntry.get();
	}
		// An object input parameter used with the removeEntry method that allows
		// for the return of the element stored in the removed node.
	private class ReturnObject {
		private T item;
		private ReturnObject(T entry){
			item = entry;
		}
		public T get(){
			return item;
		}
		public void set(T entry){
			item = entry;
		}
	}
	
	private BinaryNode<T> removeEntry(BinaryNode<T> rootNode, T entry, ReturnObject oldEntry) {
		if (rootNode != null) {
			T rootData = rootNode.getData();
			int comparison = entry.compareTo(rootData);
			if (comparison == 0) { // entry == root entry
				oldEntry.set(rootData);
				rootNode = removeFromRoot(rootNode);
			} else if (comparison < 0) { // entry < root entry
				BinaryNode<T> leftChild = rootNode.getLeftChild();
				BinaryNode<T> subtreeRoot = removeEntry(leftChild, entry, oldEntry);
				rootNode.setLeftChild(subtreeRoot);
			} else { // entry > root entry
				BinaryNode<T> rightChild = rootNode.getRightChild();
				rootNode.setRightChild(removeEntry(rightChild, entry, oldEntry));
			}
		}
		return rootNode;
	}
	
	private BinaryNode<T> removeFromRoot(BinaryNode<T> rootNode) {
		// Case 1: rootNode has two children
		if (rootNode.hasLeftChild() && rootNode.hasRightChild()) {
			// Find node with largest entry in left subtree
			BinaryNode<T> leftSubtreeRoot = rootNode.getLeftChild();
			BinaryNode<T> largestNode = findLargest(leftSubtreeRoot);
			// Replace entry in root
			rootNode.setData(largestNode.getData());
			// Remove node with largest entry in left subtree
			rootNode.setLeftChild(removeLargest(leftSubtreeRoot));
		}
		// Case 2: rootNode has at most one child
		else if (rootNode.hasRightChild())
			rootNode = rootNode.getRightChild();
		else
			rootNode = rootNode.getLeftChild();
		return rootNode;
	}
	
	// Finds the node containing the largest entry in a given tree.
	// Parameter: rootNode A reference to the root node of the tree.
	// Returns: The node containing the largest entry in the tree.
	private BinaryNode<T> findLargest(BinaryNode<T> rootNode) {
		if(rootNode.hasRightChild()) {
			return findLargest(rootNode.getRightChild());
		} else
			return rootNode;
	}
	
	// Removes the node containing the largest entry in a given tree.
	// Parameter: rootNode A reference to the root node of the tree.
	// Returns: The root node of the revised tree.
	private BinaryNode<T> removeLargest(BinaryNode<T> rootNode) {
		if (rootNode.hasRightChild()) {
			BinaryNode<T> rightChild = rootNode.getRightChild();
			rightChild = removeLargest(rightChild);
			rootNode.setRightChild(rightChild);
		}
		else
			rootNode = rootNode.getLeftChild();
		return rootNode;
	} 
}
