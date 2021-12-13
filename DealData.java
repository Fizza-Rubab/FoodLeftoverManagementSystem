package application;
import javafx.beans.property.SimpleStringProperty;
import java.util.*;


public class DealData {
	// General attributes according to schema
	private int deal_id;
	private SimpleStringProperty dealName;
	private Float price;
	private ArrayList<String> temp = new ArrayList<String>(); //List of item names in the deal

	// Constructor that initializes with given values from the form
	DealData(int id, String n, Float pr, ArrayList<String> its) {
		this.deal_id = id;
		this.dealName = new SimpleStringProperty(n);
		this.price = pr;
		this.temp = its;
	}
	
	// Getter and setters for attributes
	public int getId() {
		return this.deal_id;
	}
	public String getName(){
		return dealName.get();
	}
	public void setName(String fname){
		dealName.set(fname);
	}

	public Float getPrice(){
		return price;
	}
	public void setPrice(Float pr){
		price = pr;
	}

	public ArrayList<String> getItems(){
		return temp;
	}
	
	// Add an item to temp list
	public void addItems(String itm){
		temp.add(itm);
	}
	// Overloaded function to add a list of items to temp lis
	public void addItems(ArrayList<String> itm){
		for(int i =0; i< itm.size(); i++) temp.add(itm.get(i));
	}

	public void removeItems(String itm){
		temp.remove(itm);
	}
}