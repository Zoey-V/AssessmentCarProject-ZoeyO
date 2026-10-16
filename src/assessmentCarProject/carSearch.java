package assessmentCarProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
//collections only added for SWAP, not SORT
import java.util.Collections;

public class carSearch{
	public static ArrayList<Car> cars = new ArrayList<Car>();
	public static ArrayList<Car> selection = new ArrayList<Car>();
	public static void main(String[] args) {
		selection.clear();
		//Read the csv into the cars arraylist
		readCSV("Car_Data.csv", cars);
		//give us a working arraylist to work with + initial sort
		ArrayList<Car> working = new ArrayList<>(cars.subList(0, 2000));
		sortCarsByYearAscending(working);
		
		
		searchCarsByYear(working, 2015);
		//for(Car item :selection) {
		
		System.out.println();
		searchCarsByYear(working, 2020);
		
		
	}
	
	public static void readCSV(String filename, ArrayList<Car> array) {
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			br.readLine(); //skip header
			String line;
		    while ((line = br.readLine()) != null) {
		    	String[] parts = line.split(",");
		    	//check if information is malformed
		    	
		    	//check for right length
		    	if(parts.length !=7) {
		    		continue;
		    	}
		    	
		    	//make sure all datatypes correct
		    	try { //not the best way to do it, but the simplest/best that I *understand* well enough to explain :c
			        Integer.parseInt(parts[3]);
			        Double.parseDouble(parts[6]);
			        // no exception means it worked, no action needed
			    } catch (NumberFormatException e) {
			        continue;  // couldn't be parsed, so it's not a valid int/double, malformed
			    }
		    	//if we got to this point, the list is the right size and typing, so we can fill the object
		    	array.add(new Car(parts));
		    }
		} catch (IOException e) {
		    System.out.println("Error :c");
		    e.printStackTrace();
		    //crap
		}
		
	}
	
	public static void sortCarsByYearAscending(ArrayList<Car> array) {
		//insertion sort ba-by
		for (int i = 1; i < array.size(); i++) {
            int j = i;

            while (j > 0 && array.get(j).getYear() < array.get(j-1).getYear()) {
                // swap numbers[j] and numbers[j - 1]
            	// using collections swap IN the sort, not using collections sort
            	Collections.swap(array, j, j-1);
                j--;
            } // end inner while loop
        } // end outer for loop
	}
	public static void sortCarsByYearDescending(ArrayList<Car> array) {
		//insertion sort ba-by
		for (int i = 1; i < array.size(); i++) {
            int j = i;

            while (j > 0 && array.get(j).getYear() > array.get(j-1).getYear()) {
                // swap numbers[j] and numbers[j - 1]
            	// using collections swap IN the sort, not using collections sort
            	Collections.swap(array, j, j-1);
                j--;
            } // end inner while loop
        } // end outer for loop
	}
	
	public static void searchCarsByYear(ArrayList<Car> array, int target) {
		//binary search for specified value	
		//meant to find ALL entries with specified target value, return a sublist of them to print
		
		//ensure array is sorted and selection array is clear for fail case
		sortCarsByYearAscending(array);
		selection.clear();
		
		int low = 0;
        int high = array.size() - 1;
        int startingIndex=-1;
        
        while (high >= low) {
            int mid = (low + high) / 2;
            if (target < array.get(mid).getYear())
                high = mid - 1;
            else if (target == array.get(mid).getYear()) {
                startingIndex = mid;
                break;
            }    
            else
                low = mid + 1;
        }
        
        if (startingIndex==-1) {
        	System.out.println("No items found for inputted year!");
        }else {
        	//after finding ONE item, because the list is sorted
        	//all items for that year should be contiguous
        	//So I can just index out to find the boundaries
        	//add everything inside those boundaries to the selection
        	int margin=1;
        	int upper=-1, lower=-1;
        	while(upper==-1 || lower==-1){
        		if(upper==-1 && array.get(startingIndex+margin).getYear() != target) {
        			upper=startingIndex+margin-1;
        		}
        		if(lower==-1 && array.get(startingIndex-margin).getYear() != target) {
        			lower=startingIndex-margin+1;
        		}
        		margin++;
        	}
        	selection = new ArrayList<>(array.subList(lower, upper));
        }
	}
}