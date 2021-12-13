package application;
import java.util.*;
//This class provides the checkout functionality in our application where customers can proceed with payment after adding onto the cart
public class Cart {
	// Singleton Cart instance
	private static Cart cartInstance = null;
	// List of food items in the card
	private ArrayList<FoodsData> items = null;
	// Total price
	private float price;

	// Empty constructor
	private Cart(){
		this.items = new ArrayList<FoodsData>();
		this.price = 0;
	}
	
	// create an instance to make a Singleton pattern
	public static Cart getInstance() {
		if (cartInstance == null) {
			cartInstance = new Cart();
		}
		return cartInstance;
	}
	
	// Getter Setters for private attributes
	public Float getPrice(){
		return price; 
	}

	public void addItem(FoodsData fd){
		items.add(fd);
		price += fd.getPrice();
	}
	
	public ArrayList<FoodsData> getItems() {
		return this.items;
	}
	
	// A method to make the array list in the cart empty and remove them all
	public void resetItems() {
		this.items = new ArrayList<FoodsData>();
	}
}
