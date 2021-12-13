package application;

// A class that creates restaurant objects and comprises of all the necessary information in the schema
public class RestaurantData {

   // Declare the attributes
   private  int id;
   private String image;
   private String name;
   private String address;
   private String Description;
   private String restaurant_category;
   private int registered_by;
   
  // Empty constructor
   public RestaurantData() {
	   this.name="";
	   this.address="";
	   this.image="";
   }
   
   // Constructor 1 without ID and description
   public RestaurantData(String name, String address,String image) {
      this.image = image;
      this.name = name;
      this.address = address;
   }
   
   // Constructor 2 without description
   public RestaurantData(int id, String name, String address,String image,String restaurant_category) {
	      this.id = id;
	   	  this.image = image;
	      this.name = name;
	      this.address = address;
	      this.restaurant_category = restaurant_category;
	   }
   
   // Constructor 3 with all the information except for id
   public RestaurantData(String name, String address, String image, String Description, String restaurant_category, int registered_by) {
	      this.image = image;
	      this.name = name;
	      this.address = address;
	      this.Description = Description;
	      this.restaurant_category = Description;
	      this.registered_by = registered_by;
   }
   
   // Constructor 3 with all the information
   public RestaurantData(int id, String name, String address, String image, String Description, String restaurant_category, int registered_by) {
	      this.id = id;
	   	  this.image = image;
	      this.name = name;
	      this.address = address;
	      this.Description = Description;
	      this.restaurant_category = Description;
	      this.registered_by = registered_by;
   }
   
   // Getters and setters for private attributes
   
   public String getName(){
      return this.name;
   }
   public void setName(String fname){
      name = fname;
   }
   public String getImage(){
      return this.image;
   }
   public String getType(){
	      return this.restaurant_category;
	   }
   public void setImage(String img){
      this.image = img;
   }
   public String getAddress(){
      return address;
   }
   public int getId(){
	      return id;
   }

   public void setAddress(String faddress){
      address = faddress;
   }
}