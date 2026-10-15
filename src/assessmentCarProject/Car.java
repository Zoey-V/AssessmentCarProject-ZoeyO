//this is the start of your Car class 

package assessmentCarProject;

public class Car {
	//Car_ID,Brand,Model,Year,Fuel_Type,Color,Mileage_kmpl
	private String ID, brand, model, fuel, color;
	private int year;
	private double mileage;

	//getters
	public String getID() {
	    return ID;
	}
	
	public String getBrand() {
	    return brand;
	}
	
	public String getModel() {
	    return model;
	}
	
	public String getFuelType() {
	    return fuel;
	}

	public String getColor() {
	    return color;
	}

	public int getYear() {
	    return year;
	}
	
	public double getMileage() {
	    return mileage;
	}
	
	//toString
	
	public String toString() {
	    return ("ID-" + ID + ": " + year + " " + brand + " " + model + " " + ", fuel: " + fuel + ", Color: " + color + " " + ", Mileage: " + mileage);
	}
	
	//initializers
	public Car() {
		//default case, shouldn't be possible to come up in this program but still good practice
		this.ID = "XXXXX";
	}
	
	public Car(String id, String Brand, String Model, int Year, String Fuel, String Color, double Mileage) {
		//manual assignment
		this.ID = id;
		this.brand = Brand;
		this.model = Model;
		this.year = Year;
		this.fuel = Fuel;
		this.color = Color;
		this.mileage = Mileage;
	}
	
	public Car(String[] vals) {
		//assignment from a list of values, for easier CSV conversion (more painless for me) (kinda)
		//assumes format handling is handled by the reader
		this.ID = vals[0];
		this.brand = vals[1];
		this.model = vals[2];
		this.year = Integer.parseInt(vals[3]);
		this.fuel = vals[4];
		this.color = vals[5];
		this.mileage = Double.parseDouble(vals[6]);
	}
}
