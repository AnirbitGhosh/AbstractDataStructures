/*
 * Name: Anirbit Ghosh
 * Student ID: 2439281G
 */

package abstractDataTypes;

import java.util.Random;

/**
 * Dynamic Set class implemented with a Doubly Linked List
 * @author Anirbit
 *
 * @param <Item>
 */
public class DynamicSetDLL<Item extends Comparable<Item>>{
	// Field representing the first element of the list
	Node<Item> head;

	/*
	 * Class representing each Node in the Dynamic Set.
	 * Each Node has a value, and 2 pointers pointing to the preceding element and the next element.
	 */
	class Node<Item extends Comparable<Item>> {
		Item data;
		Node<Item> prev;
		Node<Item> next;

		// Constructor for a Node, with value d, other fields are set to null by default.
		public Node(Item d) {
			data = d;
		}
	}

	// Constructor to initialise a DynamicSetDLL object, sets the first element to null by default
	public DynamicSetDLL() {
		head = null;
	}

	/**
	 * Method to add a new element of generic type to the Set.
	 * @param newData
	 */
	public void add(Item newData) {
		// initialise the element to be inserted as a Node
		Node<Item> newNode = new Node<Item>(newData);

		// Add the element to the beginning of the Set only if it is not in the Set already
		if (!isElement(newData)) {
			// set the previous head element as the second element by making Node.next point to it
			newNode.next = head;
			// set the preceding element to null by making Node.prev point to null
			newNode.prev = null;

			// make the Node.prev of the previous head element point to the newly added Node
			if (head != null) {
				head.prev = newNode;
			}

			// Make the newly added node the new head node
			head = newNode;
		}

	}

	/**
	 * Method to remove a Node of the given value from the Set
	 * @param n
	 * @return head of the modified Set
	 */
	public void remove(Item n) {
		// Use search helper method to find the Node with the given value
		Node<Item> del = search(n);

		// if Set is empty of the Node being deleted doesn't exist, return null
		if (head == null || del == null) {
			return;
		}

		// Check if the Node being deleted is the Head node
		if (head == del) {
			head = del.next;
		}

		// Check if the Node after the one being deleted exists
		if (del.next != null) {
			del.next.prev = del.prev;
		}

		// Check is the Node preceding the one being deleted exists
		if (del.prev != null) {
			del.prev.next = del.next;
		}

		// Set the node that was deleted to null
		del = null;

	}

	/**
	 * Check if a Node of the given value is present in the Set
	 * @param n
	 * @return true or false
	 */
	public boolean isElement(Item n) {
		// Create a Node element of the current head element
		Node<Item> current = head;
		
		// Iterate through the set until reaching the last Node
		while (current != null) {
			// check if any Node in the set has the same value as the parameter passed
			if (n.compareTo(current.data) == 0) {
				return true;
			}
			// if not, check the next Node in the Set
			current = current.next;
		}
		return false;
	}

	/**
	 * Method to check if the given set is Empty
	 * @return
	 */
	public boolean isEmpty() {
		return head == null;
	}

	
	/**
	 * Method wrapper for the getSize method that returns the size of the Set
	 * @return
	 */
	public int size() {
		int size = getSize(this.head);
		return size;
	}
	/**
	 * Method to retrieve the number of elements in the Set associated with the Head node passed as parameter
	 * @param headNode
	 * @return
	 */
	public int getSize(Node<Item> headNode) {
		// initialise counter variable
		int counter = 0;
		
		// initialise a node variable assigned to Head of the Set
		Node<Item> node = headNode;
		// Iterate through the set until the last Node
		while (node != null) {
			// Increment the counter by 1 after each Node
			counter++;
			// Go to the next node
			node = node.next;
		}
		return counter;
	}

	//Set operations
	
	/**
	 * Method to find the Union of 2 Sets, the current Set and the set whose Head node is passed as the parameter.
	 * @param head2 (head of the Set with which the union is to be found)
	 * @return a new set containing all union elements of the two sets
	 */
	public DynamicSetDLL<Item> union(DynamicSetDLL<Item> set) {
		Node<Item> head2 = set.head;
		// Create a blank Set object
		DynamicSetDLL<Item> unionElements = new DynamicSetDLL<Item>();

		// Initialise the two head nodes
		Node<Item> head_self = head;
		Node<Item> head_other = head2;

		// Iterate through the current set and add all elements to the empty Set
		while (head_self != null) {
			unionElements.add(head_self.data);
			head_self = head_self.next;
		}

		// Iterate through the second Set
		while (head_other != null) {
			// Add all elements which are not already in the newly created Set, to the Set
			if (!unionElements.isElement(head_other.data)) {
				unionElements.add(head_other.data);
			}
			head_other = head_other.next;
		}

		return unionElements;
	}

	/**
	 * Method to find the Intersection of two given Sets, the current Set and the Set whose head Node is passed as parameter
	 * @param head2 (Head of the Set with which Intersection is to be found)
	 * @return a new Set with the intersection elements
	 */
	public DynamicSetDLL<Item> intersection(DynamicSetDLL<Item> set) {
		Node<Item> head2 = set.head;
		// Create a blank Set object
		DynamicSetDLL<Item> intersectionList = new DynamicSetDLL<Item>();

		// Initialise the head nodes of the two sets that are being considered
		Node<Item> head_self = head;
		Node<Item> head_other = head2;

		// If any of the Sets is empty, there is no intersection
		if (head_self == null || head_other == null) {
			return null;
		}

		// get the sizes of the two sets 
		int length_self = getSize(head_self);
		int length_other = getSize(head_other);

		// check if they are the same size
		if (length_self == length_other) {
			// Iterate through one of the Sets and check if they also belong to the Other set
			while (head_other != null) {
				if (this.isElement(head_other.data)) {
					// If the elements are present in both set add them to the blank Set created earlier
					intersectionList.add(head_other.data);
				}
				head_other = head_other.next;
			}

		// If the sets are different sizes, iterate through the Nodes of the smaller Set and check if they are present in the larger set
		} else if (length_self > length_other) {
			while (head_other != null) {
				if (this.isElement(head_other.data)) {
					intersectionList.add(head_other.data);
				}
				head_other = head_other.next;
			}

		} else {
			DynamicSetDLL<Item> temp = getDynamicSet(head_other);

			while (head_self != null) {
				if (temp.isElement(head_self.data)) {
					intersectionList.add(head_self.data);
				}
				head_self = head_self.next;
			}
		}

		return intersectionList;
	}

	/**
	 * Method to find the Difference elements between two Sets
	 * @param head2 (head Node of the Set with the respect to which the difference is to be determined)
	 * @return a new Set with the difference elements
	 */
	public DynamicSetDLL<Item> difference(DynamicSetDLL<Item> set) {
		Node<Item> head2 = set.head;
		// Create a blank dynamic set 
		DynamicSetDLL<Item> differenceList = new DynamicSetDLL<Item>();

		// Initialise the head nodes of the two Sets being considered
		Node<Item> head_self = head;
		Node<Item> head_other = head2;

		// Use getDynamicSet helper method to get the second Set being considered, associated with head_other
		DynamicSetDLL<Item> other_list = getDynamicSet(head_other);

		// If either set is empty, the difference is null
		if (head_self == null || head_other == null) {
			return null;
		}

		// Iterate through the two Sets individually 
		while (head_other != null) {
			// Check if the element of one Set is not present in the other Set and add to the Difference Set
			if (!this.isElement(head_other.data)) {
				differenceList.add(head_other.data);
			}
			head_other = head_other.next;
		}

		while (head_self != null) {
			if (!other_list.isElement(head_self.data)) {
				differenceList.add(head_self.data);
			}
			head_self = head_self.next;
		}

		return differenceList;

	}

	/**
	 * Method to get check if a Set is a subset of another Set
	 * @param head2 (Head of the Set being considered as the parent Set and checking if the current set is a subset of it)
	 * @return True or False
	 */
	public boolean subset(DynamicSetDLL<Item> set) {
		Node<Item> head2 = set.head;
		boolean isSubset = false;

		// Initialise the head node of the current Set
		Node<Item> head_self = this.head;
		
		// If the current Set is empty, it is a subset of the other Set being considered
		if(head_self == null) {
			return true;
		}
		
		// If the other Set is empty, the current set is not a subset of it 
		if(head2 == null) {
			return false;
		}

		// use helper function to get the Set associated with Head2 
		DynamicSetDLL<Item> otherSet = getDynamicSet(head2);

		// Iterate through the entire current Set 
		while (head_self != null) {
			// Check all elements of the current Set are present in the other Set
			if (otherSet.isElement(head_self.data)) {
				isSubset = true;

			// If not all elements are present in the other Set, it is not a subset
			} else {
				isSubset = false;
				break;
			}

			head_self = head_self.next;
		}

		return isSubset;
	}

	
	// Helper Functions 
	/**
	 * Print helper function to visualize the Dynamic Set implementation as a Doubly Linked List
	 * @param node
	 */
	public void printSet(Node<Item> node) {
		Node<Item> last = null;
		while (node != null) {
			System.out.println(node.data + " -> ");
			last = node;
			node = node.next;
		}
	}

	/** 
	 * Search helper function to Search for a Node with the given value
	 * @param n
	 * @return Node with the given value
	 */
	public Node<Item> search(Item n) {
		Node<Item> temp = head;
		while (temp != null) {
			if (temp.data == n) {
				return temp;
			}
			temp = temp.next;
		}
		return null;
	}

	/**
	 * Helper method to get the Dynamic Set associated with the given Head node
	 * @param head
	 * @return Dynamic Set with the given head Node
	 */
	public DynamicSetDLL<Item> getDynamicSet(Node<Item> head) {
		DynamicSetDLL<Item> dynamicSet = new DynamicSetDLL<Item>();
		Node<Item> temp_node = head;
		while (temp_node != null) {
			dynamicSet.add(temp_node.data);
			temp_node = temp_node.next;
		}

		return dynamicSet;
	}

	public static void main(String[] args) {
		Random rd = new Random();

		DynamicSetDLL<Integer> dll = new DynamicSetDLL<Integer>();

		while (dll.getSize(dll.head) != 20) {
			int data = rd.nextInt(50);
			dll.add(data);
		}

		int[] l2 = {4, 5, 6, 7, 20, 4, 4, 4 };
		int[] l3 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

		DynamicSetDLL<Integer> dll2 = new DynamicSetDLL<Integer>();
		for (int n : l2) {
			dll2.add(n);
		}

		DynamicSetDLL<Integer> dll3 = new DynamicSetDLL<Integer>();
		for (int n : l3) {
			dll3.add(n);
		}
		
		dll2.printSet(dll2.head);
		System.out.println("++++++");
		dll3.printSet(dll3.head);
		System.out.println("++++++");

		DynamicSetDLL<Integer> intersectionList = new DynamicSetDLL<Integer>();
		intersectionList = dll2.difference(dll3);
		
		intersectionList.printSet(intersectionList.head);

		System.out.println(dll2.subset(dll3));
	}
}
