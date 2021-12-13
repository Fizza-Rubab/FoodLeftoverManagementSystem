package application;

//A class that implements the food object and contains all necessary attributes from the schema
public class FoodsData {
	private String image;
	private String name;
	private int item_id;
	private String desc;
	private int inStock;
	private Float price;
	private int amount;

	// Constructor for populating all the fields with passed parameters
	FoodsData(int id, String n, Float pr, String desc, int inS, String img) {
		this.item_id = id;
		this.name = n;
		this.price = pr;
		this.desc = desc;
		this.inStock = inS;
		this.image = img;
		this.amount = 0;
	}
	// Copy constructor
	public FoodsData(FoodsData f) {
		this.image = f.image;
		this.name = f.name;
		this.item_id = f.item_id;
		this.desc = f.desc;
		this.inStock = f.inStock;
		this.price = f.price;
		this.amount = f.amount;
	}

	// Getters and Setters for private attributes
	
	public String getName(){
		return name;
	}
	public int getId() {
		return this.item_id;
	}
	public void setName(String fname){
		name = fname;
	}
	public String getDesc(){
		return desc;
	}
	public void setDesc(String desc){
		this.desc = desc;
	}
	public String getImage(){
		return image;
	}
	public void setImage(String img){
		this.image = img;
	}
	public Float getPrice(){
		return price;
	}
	public void setPrice(Float pr){
		price = pr;
	}
	public int getStock(){
		return inStock;
	}
	public void setStock(int Stock){
		inStock = Stock;
	}
	public void setAmount(int a) {
		this.amount = a;
	}
	public int getAmount() {
		return this.amount;
	}
	public int getType() {
		return 0;
	}
}