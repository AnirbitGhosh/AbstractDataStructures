/*
 * Name: Anirbit Ghosh
 * Student ID: 2439281G
 */

package abstractDataTypes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Class to test execution times of Set methods
 * @author Anirbit
 *
 */
public class TimeTest {
	
	// Initalize the two ADTs, one with the Doubly Linked List and the other with a Binary Search Tree
	private static DynamicSetDLL<Integer> setDLL = new DynamicSetDLL<Integer>();
	private static  DynamicSetBST<Integer> setBST = new DynamicSetBST<Integer>();
	
	/**
	 * Method to scan a file given file and read all the values from the file into the two datastructures initialized before
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public static void setGenerator(String filename) throws FileNotFoundException{
		Scanner scanner1 = new Scanner(new File(filename));
		
		ArrayList<Integer> numList = new ArrayList<>();
		
		// Add all integers from the file into a list
		int i = 0;
		while(scanner1.hasNextLine()) {
			numList.add(i++, Integer.parseInt(scanner1.nextLine()));
		}
		
		// Add each integer from the list into both Dynamic Sets
		for(int n : numList) {
			setDLL.add(n);
			setBST.add(n);
		}
		
	}
	
	/**
	 * Method to measure the average time of execution of 100 calls to the Binary Search Tree isPresent method on 100 random integers passed as a List parameter
	 * @param randomList
	 * @return average time 
	 */
	public static double timeIsPresentBST(ArrayList<Integer> randomList) {
		double averageTime = 0;
		ArrayList<Double> times = new ArrayList<>();
		
		// For each each integer in the List, record the time before calling isPresent and then after the method execution to get the execution time
		for(int n  : randomList) {
			long timeStart = System.nanoTime();
			setBST.isElement(n);
			long timeEnd = System.nanoTime();
			
			double timeTaken = (timeEnd - timeStart);
			
			// Add the execution time to an empty list
			times.add(timeTaken);
			// Take a sum of all the execution times
			averageTime += timeTaken;	
		}
		
		// Return average over 100 calls
		return averageTime/100;
	}
	
	/**
	 * Method to measure the average time of execution of 100 calls to the Doubly Linked List isPresent method on 100 random integers passed as a List parameter
	 * @param randomList
	 * @return
	 */
	public static double timeIsPresentDLL(ArrayList<Integer> randomList) {
		double averageTime = 0;
		ArrayList<Double> times = new ArrayList<>();
		
		for(int n  : randomList) {
			long timeStart = System.nanoTime();
			setDLL.isElement(n);
			long timeEnd = System.nanoTime();
			
			double timeTaken = (timeEnd - timeStart);
			
			times.add(timeTaken);
			averageTime += timeTaken;	
		}
		
		return averageTime/100;
	}
		
	public static void main(String[] args) throws FileNotFoundException {
		Random rd = new Random();
		ArrayList<Integer> randomNums = new ArrayList<>();
		
		for(int i = 0; i < 100; i++) {
			randomNums.add(rd.nextInt(49999));
		}
		
		
		TimeTest.setGenerator("int20k.txt");
		
		System.out.println("Size of the DLL Dynamic Set: " + TimeTest.setDLL.size());
		System.out.println("Size of the BST Dynamic Set: " + TimeTest.setBST.size());
		System.out.println("Height of the BST Dynamic Set: " + TimeTest.setBST.height(setBST.root));
		System.out.println("Average time over 100 isPresent calls in a BST Dyanmic Set = " + TimeTest.timeIsPresentBST(randomNums) + " nanoseconds");
		System.out.println("Average time over 100 isPresent calls in a DLL Dyanmic Set = " + TimeTest.timeIsPresentDLL(randomNums) + " nanoseconds");
	}
	
	
}
