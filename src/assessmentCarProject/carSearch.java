package assessmentCarProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class carSearch{
	
	public static void main(String[] args) {
		
		ArrayList<Car> cars = new ArrayList<Car>();
		readCSV("Car_Data.csv", cars);
		
		ArrayList<Car> working = new ArrayList<>(cars.subList(0, 2000));

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
	
	public void sort() {
		//placeholder
	}
}