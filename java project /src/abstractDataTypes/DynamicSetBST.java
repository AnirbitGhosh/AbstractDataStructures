/*
 * Name: Anirbit Ghosh
 * Student ID: 2439281G
 */

package abstractDataTypes;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Dynamic Set class implemented with a Binary Search Tree 
 * @author Anirbit
 *
 * @param <Item>
 */
public class DynamicSetBST<Item extends Comparable<Item>> {
	// Field representing the first element of the tree
	Node<Item> root;

	/**
	 * Class representing each Node in the Dynamic Set.
	 * Each Node has a value and two pointers, one pointing to the right and the other to the left branch of the tree.
	 * @param <Item>
	 */
	class Node<Item extends Comparable<Item>> {
		Item key;
		Node<Item> left;
		Node<Item> right;

		// Constructor for initialising a Node of a given value
		public Node(Item value) {
			key = value;
			left = null;
			right = null;
		}
	}

	// Constructor for initialising a Dynamic Set. Root set to null by default
	public DynamicSetBST() {
		root = null;
	}

	/**
	 * Method wrapper to add a Node of a given value to the Set
	 * @param key (value of the Node)
	 */
	public void add(Item key) {
		// If the value is not already in the Set, add the Node to the Set
		if (!this.isElement(key)) {
			root = insertRecursive(root, key);
		}

	}

	/**
	 * Recursive helper method to add a node to the correct branch of the Set based on the value of the Node with respect to the Root of the BST
	 * @param root
	 * @param key
	 * @return Root node of the Set
	 */
	public Node<Item> insertRecursive(Node<Item> root, Item key) {
		// If Set is empty, add the node as the root of the Set
		if (root == null) {
			root = new Node<Item>(key);
			return root;
		}

		// If the value of the Node being added is less than the root, add to the left branch of the BST
		if (key.compareTo(root.key) < 0) {
			// Recursively call the method to now check the same conditions on the left branch
			root.left = insertRecursive(root.left, key);

		// IF the value of the node being added is greater than the root, add to the right branch of the BST
		} else if (key.compareTo(root.key) > 0) {
			root.right = insertRecursive(root.right, key);
		}

		return root;
	}

	/**
	 * Method wrapper to remove a Node of a given value from the Set
	 * @param key
	 */
	public void remove(Item key) {
		root = deleteRecursive(root, key);
	}

	/** 
	 * Recursive helper method to delete a Node of a given value from the Set 
	 * @param root
	 * @param key
	 * @return Root node of the changed Set
	 */
	public Node<Item> deleteRecursive(Node<Item> root, Item key) {
		// If Set is empty, nothing to delete
		if (root == null) {
			return root;
		}

		// If the node value being deleted is less than the root value, check the left branch of the BST 
		if (key.compareTo(root.key) < 0) {
			// Recursively call the method on the left branch to look for the right Node to delete
			root.left = deleteRecursive(root.left, key);

		// If the node value being deleted is greater than the root value, check the right branch of the BST
		} else if (key.compareTo(root.key) > 0) {
			root.right = deleteRecursive(root.right, key);

		// If the node value being deleted is equal to the root value
		} else {
			// Check if the root as only one child or no child
			if (root.left == null) {
				return root.right;

			
			} else if (root.right == null) {
				return root.left;

			}
			
			// If root has 2 children, both left and right branch, get the in-order successor (minimum value of the right subTree)
			root.key = minValue(root.right);

			// Delete the in-order successor
			root.right = deleteRecursive(root.right, root.key);
		}

		return root;
	}

	/**
	 * Helper method to get the least value from the Set 
	 * @param root
	 * @return
	 */
	public Item minValue(Node<Item> root) {
		// Initialise variable to the value of the Root Node
		Item minV = root.key;

		// Iterate through the left branch of the BST 
		while (root.left != null) {
			// Set the new value of the minV to the value of the current Node being traversed
			minV = root.left.key;
			// Move to the next node on the left subTree
			root = root.left;
		}
		return minV;
	}

	/**
	 * Method to check if a Node of a given value exists in the Set 
	 * @param key
	 * @return True or False
	 */
	public boolean isElement(Item key) {
		// Initialise a Node object of the root value
		Node<Item> node = root;

		// Traverse through the BST from the current node
		while (node != null) {
			// Check if the given value is greater, lesser or equal to the current node value
			int val = key.compareTo(node.key);

			// If equal, element exists
			if (val == 0) {
				return true;

			// If lesser, traverse through the left subtree and check again
			} else if (val < 0) {
				node = node.left;

			// If greater, traverse through the right subtree and check again
			} else {
				node = node.right;
			}

		}
		return false;
	}

	/**
	 * Method to check if a given Set is empty
	 * @return True or False
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Method wrapper to get the total number of Nodes in a given Set
	 * @return
	 */
	public int size() {
		return sizeRecursive(root);
	}

	/**
	 * Recursive method to get the size of the Set
	 * @param node
	 * @return Size of the Set
	 */
	public int sizeRecursive(Node<Item> node) {
		// If Set is empty, size is 0
		if (node == null) {
			return 0;

		// If not empty, recur through the left and right subtrees and get the sizes of each and add 1 for the root node
		} else {
			return (sizeRecursive(node.left) + 1 + sizeRecursive(node.right));
		}
	}

	// set operations
	
	/**
	 * Recursive method to get the Union elements of two Sets
	 * @param root
	 */
	public void inOrderUnion(Node<Item> root) {
		Node<Item> root_other = root;
		
		// Check If the root is a leaf, then end recursion
		if (root == null) {
			return;
		}

		// In-Order traversal of the given Set
		// Recursively call the method to traverse the left Subtree 
		inOrderUnion(root_other.left);

		// if the element is not present in the current Set, add it to the current Set
		if (!isElement(root_other.key)) {
			this.add(root_other.key);
		}
		
		// Recursively traverse through the right subtree 
		inOrderUnion(root_other.right);
	}

	/**
	 * Method wrapper to get the union of a Set with the current set, given the root of the second set
	 * @param root2
	 * @return DynamicBST with union elements
	 */
	public DynamicSetBST<Item> union(DynamicSetBST<Item> set) {
		// get root of the set given as parameter
		Node<Item> root2 = set.root; 
		// set up a blank Set object
		DynamicSetBST<Item> unionSet = new DynamicSetBST<Item>();
		// make the set point to the current set's root element
		unionSet.root = this.root;

		// Call the recursive method to get the union elements
		unionSet.inOrderUnion(root2);

		return unionSet;
	}

	
	/**
	 * Non naive union method, with more efficient runtime complexity
	 * @param set
	 * @return DynamicSetBST 
	 */
	public DynamicSetBST<Item> unionNonNaive(DynamicSetBST<Item> set){
		Node<Item> root_self = root;
		Node<Item> root_other = set.root;
		
		// get sizes of both BSTs
		int size1 = this.size();
		int size2 = set.size();
		
		// create empty arrays of each size
		Item[] setArr1 = (Item[])Array.newInstance(this.root.key.getClass(), size1);
		Item[] setArr2 = (Item[]) Array.newInstance(set.root.key.getClass(), size2);
		
		// call createArray on each BST and set them to their respective arrays
		createArray(root_self, 0, setArr1);
		createArray(root_other, 0, setArr2);
		
		Arrays.sort(setArr1);
		Arrays.sort(setArr2);
		
		System.out.println(Arrays.asList(setArr1));
		System.out.println(Arrays.asList(setArr2));
		
		// call mergeArray to merge the two arrays
		Item[] mergedArr = mergeArray(setArr1, setArr2);
		
		// Create an empty DynamicSetBST object
		DynamicSetBST<Item> unionSet = new DynamicSetBST<>();
		
		// add elements to set
		for(Item item : mergedArr) {
			unionSet.add(item);
		}
		
		return unionSet;
		
	}
	
	/**
	 * Helper method to create an Array from a BST
	 * @param node 
	 * @param int i (index)
	 * @param array 
	 * @return index i
	 */
	public int createArray(Node<Item> node, int i, Item[] arr) {
		// recur down left tree 
		if(node.left != null) {
			i = createArray(node.left, i, arr);
		}
		
		// recur down right tree 
		if(node.right != null) {
			i = createArray(node.right, i, arr);
		}
		
		// add element to the array given at current index value
		arr[i] = node.key;
		
		// increment index 
		return i+1;
	}
	
	/**
	 * Helper method to merge two arrays without taking duplicates
	 * @param arr1
	 * @param arr2
	 * @return merged array 
	 */
	public Item[] mergeArray(Item[] arr1, Item[] arr2) {
		// create an empty array of size equal to sum of sizes of arr1 and arr2
		Item[] mergedArr = (Item[])Array.newInstance(this.root.key.getClass(), arr1.length+arr2.length);
		
		// add elements of arr1 to merged array
		for(int i = 0; i < arr1.length; i++) {
			mergedArr[i] = arr1[i];
		}
		
		// set up an index counter for arr2
		int x = 0;
		// Iterate through the remaining length of merged array
		for(int j = arr1.length; j < mergedArr.length; j++) {
			// check for duplicates and then add element 
			if(!Arrays.asList(mergedArr).contains(arr2[x])) {
				mergedArr[j] = arr2[x];
			}
			// increment index counter
			x++;
		}
		
		// get the actual size of the merged array without null elements which are created as a result of discarding duplicates
		int actualSize = 0;
		for(Item item : mergedArr) {
			if(item != null) {
				actualSize += 1;
			}
		}
		
		// create a final array of the actual size without nulls and duplicates
		Item[] finalArr = (Item[])Array.newInstance(this.root.key.getClass(), actualSize);
		
		// Iterate through each index of final array
		for(int k = 0; k < finalArr.length; k++) {
			// Iterate through each element of merged array 
			for(Item item : mergedArr) {
				// If element of merged array is not Null or not present in final array add to final array
				if (item != null && !Arrays.asList(finalArr).contains(item)) {
					finalArr[k] = item;
					break;
				}
			}
		}
		
		return finalArr;
	}
	
	
	/**
	 * Recursive helper method to get the intersection of the current set and another set
	 * @param root2
	 * @param dSet
	 */
	public void preOrderIntersection(Node<Item> root2, DynamicSetBST<Item> dSet) {
		// Initialise the root node of the second Set in a variable
		Node<Item> root_other = root2;

		// set up base case of recursion
		if (root2 == null) {
			return;
		}

		// PreOrder traversal 
		// if the current node exists in both Sets, add it to the DynamicSet passed as a parameter
		if (isElement(root_other.key)) {
			dSet.add(root_other.key);
		}

		// Recursively traverse through the right and left subtree and check each Node
		preOrderIntersection(root_other.left, dSet);
		preOrderIntersection(root_other.right, dSet);
	}

	/**
	 * Method wrapper to get the Intersection of the current Set and the set whose root node is passed as parameter
	 * @param root2
	 * @return
	 */
	public DynamicSetBST<Item> intersection(DynamicSetBST<Item> set) {
		// Get root of the set given as parameter
		Node<Item> root2 = set.root;
		// Set up a blank dynamic Set to add the intersection elements to 
		DynamicSetBST<Item> result = new DynamicSetBST<Item>();
		
		// Call recursive method to get the intersection and add the nodes to the previously created Set
		preOrderIntersection(root2, result);

		return result;
	}

	/**
	 * Recursive helper method to get the difference elements between the current Set and a set whose root node is passed as parameter
	 * @param root2
	 * @param dSet
	 */
	public void postOrderDifference(Node<Item> root2, DynamicSetBST<Item> dSet) {
		// Set up a variable starting at the root of the second Set
		Node<Item> root_other = root2;

		// Set up base case of recursion
		if (root2 == null) {
			return;
		}

		// PostOrder traversal
		// Recur through the left and right trees 
		postOrderDifference(root_other.left, dSet);
		postOrderDifference(root_other.right, dSet);

		// If the current node is not present in both the current set and the other set, add it to the Set passed as parameter
		if (!isElement(root_other.key)) {
			dSet.add(root_other.key);
		}
	}

	// Method wrapper to get the difference between two Sets
	public DynamicSetBST<Item> difference(DynamicSetBST<Item> set) {
		// Get root of the set passed as parameter
		Node<Item> root2 = set.root;
		
		// Set up the root nodes of the two sets in question
		Node<Item> root_self = this.root;
		Node<Item> root_other = root2;
		
		// Get the Set associated with the root node passed as the parameter
		DynamicSetBST<Item> other_set = new DynamicSetBST<Item>();
		other_set.root = root_other;

		// Initialise a blank set to add the difference elements to
		DynamicSetBST<Item> result = new DynamicSetBST<Item>();
		
		// Call again on the current set, to traverse through all its elements and get the difference elements
		other_set.postOrderDifference(root_self, result);

		return result;
	}

	/**
	 * Helper method to traverse through a Set and check if the all elements from another set belong to it
	 * @param Node
	 * @return boolean
	 */
	public boolean checkSubset(Node<Item> node) {	
		if (node == null) {
			return true;
		}
		
		boolean left_subset = this.checkSubset(node.left);
		boolean right_subset = this.checkSubset(node.right);
		
		return this.isElement(node.key) && left_subset && right_subset; 

	}

	/**
	 * Method wrapper to check if the current Set is a subset of the given Set 
	 * @param root2
	 * @return boolean
	 */
	public boolean Subset(DynamicSetBST<Item> set) {
		return set.checkSubset(root);
	}

	// helper functions
	
	/**
	 * Helper method to search for a node of a given value in the Set
	 * @param key
	 * @return Node of given value
	 */
	public Node<Item> search(Item key) {
		Node<Item> root = this.root;

		if (root == null || root.key == key) {
			return root;
		}

		if (root.key.compareTo(key) < 0) {
			return search(root.right.key);
		}

		return search(root.left.key);
	}

	
	public int height(Node<Item> root) {
		if(root == null) {
			return -1;
		}
		
		int left_height = height(root.left);
		int right_height = height(root.right);
		
		if(left_height > right_height) {
			return left_height + 1;
			
		}else { 
			return right_height + 1;
		}
		
	}
	
	/**
	 * Helper method to print the elements of a given Set in a tree structure 
	 * @param root
	 * @param space
	 */
	public void printSetUtil(Node<Item> root, int space) {
		// Base case
		if (root == null)
			return;

		// Increase distance between levels
		space += 10;

		// Process right child first
		printSetUtil(root.right, space);

		// Print current node after space
		// count
		System.out.print("\n");
		for (int i = 10; i < space; i++)
			System.out.print(" ");
		System.out.print(root.key + "\n");

		// Process left child
		printSetUtil(root.left, space);
	}

	/**
	 * Method wrapper to print a given Set 
	 * @param root
	 */
	public void printSet(Node<Item> root) {
		printSetUtil(root, 0);
	}

	public static void main(String[] args) {
		int[] l1 = {5, 8, 1, 10, 11, 20};
		DynamicSetBST<Integer> bst1 = new DynamicSetBST<Integer>();
		for (int n : l1) {
			bst1.add(n);
		}

		int[] l2 = {9, 12, 5, 12, 16, 20};
		DynamicSetBST<Integer> bst2 = new DynamicSetBST<Integer>();
		for (int n : l2) {
			bst2.add(n);
		}

		bst1.printSet(bst1.root);
		System.out.println("++++++");
		bst2.printSet(bst2.root);
		System.out.println("+++++++++");
		
		DynamicSetBST<Integer> unionSet = bst1.unionNonNaive(bst2);
		unionSet.printSet(unionSet.root);
		System.out.println(bst1.Subset(bst2));
	}

}
