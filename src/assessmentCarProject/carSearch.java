package assessmentCarProject;

// Zoey Oldham, 15/10/2026
// Car CSV loading, sorting, and value searching/sorting assessment.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
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
		
		//establish scanner
		Scanner scanner = new Scanner(System.in);
		System.out.println("Hello! Welcome to the Dealership!");
		while(true) {
			//scanner bullshit here i fucking guess
			// kill me nowsausssss
			
			//print menu, t
			System.out.println("\n"+"Please input a command or exit transaction: "+"\n");
			System.out.println("sort [old/new]: sorts our options by age, showing you the 10 oldest or newest models availible");
			System.out.println("display statistics [selection/all]: displays the average mileage and fuel types for either your most recent search results or all availible stock");
			System.out.println("search [year]: searches for all availible stock produced in the selected year");
			System.out.println("Type exit to end transaction."+"\n");
			
			System.out.println("Command: ");
			String input = scanner.nextLine();
			
			//menu logic
			if(input.equals("sort old")) {
				sortCarsByYearAscending(working);
				displaySelectionLimited(working, 10, 0);
			}else if(input.equals("sort new")) {
				sortCarsByYearDescending(working);
				displaySelectionLimited(working, 10, 0);
			}else if(input.equals("display statistics selection")) {
				if(selection.size()==0) {
					System.out.println("Please make a selection using 'search' before using this command!");
				}else {
					doStats(selection);
				}
			}else if(input.equals("display statistics all")) {
				doStats(working);
			}else if(input.equals("exit")) {
				break;
			}else {
				String[] inputSplit = input.split(" ");
				if(inputSplit[0].equals("search") && inputSplit[1].equals("year")) {
					try { 
				        int target = Integer.parseInt(inputSplit[2]);
				        searchCarsByYear(working, target);
				        displaySelectionFull(selection);
				    } catch (NumberFormatException e) {
				    	// couldn't be parsed, year wasn't an integer, ergo invalid
				    	System.out.println("Year inputted invalid. Please try again.");
					}
					
				}else {
					System.out.println("Error, please try again");
				}
			}
		}
		System.out.println("Thank you, please come again!");
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
		    	try { 
		    		//not the best way to do it, but the simplest/best that I *understand* well enough to explain :c
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
        		//if it reaches the highest or lowest index, catches it early to prevent oob index errors
        		if(startingIndex+margin==(array.size()-1) && array.get(startingIndex+margin).getYear() == target) {
        			lower = 0;
        		}
        		if(startingIndex-margin==0 && array.get(startingIndex-margin).getYear() == target) {
        			lower = 0;
        		}
        		margin++;
        	}
        	selection = new ArrayList<>(array.subList(lower, upper));
        }

	}
	
	public static void displaySelectionLimited(ArrayList<Car> array, int quantity, int index) {
		System.out.println();
    	for(int i=index;i<quantity;i++) {
    		System.out.println(array.get(i));
    	}
    }
	
	public static void displaySelectionFull(ArrayList<Car> array) {
		System.out.println();
    	for(Car item : array) {
    		System.out.println(item);
    	}
    }
	
	public static void doStats(ArrayList<Car> array) {
		double sum = 0;
		int[] fuel = new int[5];
		//fuels in order of array index: diesel petrol electric hybrid cng
		for(Car item : array) {
			sum+=item.getMileage();
    		if(item.getFuelType().equals("Diesel")) {
    			fuel[0]++;
    		}else if(item.getFuelType().equals("Petrol")) {
    			fuel[1]++;
    		}else if(item.getFuelType().equals("Electric")) {
    			fuel[2]++;
    		}else if(item.getFuelType().equals("Hybrid")) {
    			fuel[3]++;
    		}else if(item.getFuelType().equals("CNG")) {
    			fuel[4]++;
    		}
    	}
		double avg = sum/array.size();
		System.out.println("Average mileage: "+avg);
		System.out.println("Fuel types:"+"\n"+"Diesel: "+fuel[0]+"\n"+"Petrol: "+fuel[1]+"\n"+"Electric: "+fuel[2]+"\n"+"Hybrid: "+fuel[3]+"\n"+"CNG: "+fuel[4]);
	}
}

