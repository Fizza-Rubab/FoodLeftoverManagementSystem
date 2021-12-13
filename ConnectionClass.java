package application;
import java.sql.*;

import java.util.ArrayList;

public class ConnectionClass {
	Connection conn = null;
	PreparedStatement p = null;
	ResultSet rs = null;
	private static ConnectionClass instance = null;

	private ConnectionClass() {
		//connect to the database with every call of this class 
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

			conn = DriverManager.getConnection("jdbc:mysql://localhost/food_leftover_db", "root", "root");
			System.out.println("Connection is opened sucessfully");

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		catch (Exception e) {
			System.out.println("exception occurred: " + e);
		}
	}

	public static ConnectionClass getInstance() { //singleton class to be used simultaneously for all calls  
		if (instance == null) instance = new ConnectionClass();
		return instance;
	}
	
	public int getCustomerId(String Username) {  //fetching id from database from the customer username which would be unique
		int id = -1;
		try {
			System.out.println(String.format("select customer_id from customer where name = '%s';", Username));
			p = conn.prepareStatement(String.format("select customer_id from customer where name = '%s';", Username));
			rs = p.executeQuery();

			if (rs.next()) {	
				id = rs.getInt("customer_id");
			}
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return id;
	}

	public int signInUser(String Username, String Password){ //to check if the signed in user is a customer, admin (server side) or the restaurant admin 
		int find = 0; 
		String name = "";
		String password = "";
		try {
			p = conn.prepareStatement("select name, password from Customer where name ='"+ Username + "' and password ='" + Password + "';");
			rs = p.executeQuery();
			if (rs.next()) {
				name = rs.getString("name");
				password = rs.getString("password");
				find = 1; //if sign in is by a customer
			}            
			if (find==0) {
				p = conn.prepareStatement("select name, password from Admin where name ='"+ Username + "' and password ='" + Password + "';");
				rs = p.executeQuery();
				if (rs.next()) {
					name = rs.getString("name");
					password = rs.getString("password");
					find = 3; //if sign in is by a server admin
				}
				if (find==0) {
					p = conn.prepareStatement("select loginname, password from Restaurant where loginname ='"+ Username  + "' and password ='" + Password + "';");
					rs = p.executeQuery();
					if (rs.next()) {
						name = rs.getString("loginname");
						password = rs.getString("password");
						find = 2; //if sign in is by a restaurant admin
					}
				}
			}
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		catch (Exception e) {
			System.out.println("exception occorued: " + e);
		}
		return find; // find would be 0 if the username does not exist
	}

	//to see the onboarded restaurants in the system
	public ArrayList<RestaurantData> getRestaurants(){ 
		String name = "";
		String address = "";
		String image = "";
		ArrayList<RestaurantData> output = new ArrayList<RestaurantData>();

		try {
			p = conn.prepareStatement("select name, address, icon from Restaurant;");
			rs = p.executeQuery();

			while (rs.next()) {	
				name = rs.getString("name");
				address = rs.getString("address");
				image = rs.getString("icon");
				RestaurantData r = new RestaurantData(name, address, image);
				output.add(r);
			}
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return output;

	}

	//removing a restaurant and all of its regulation details from the database  
	public void deleteRestaurant(int restaurant_id) { 
		try {
			Statement s1 = conn.createStatement();
			s1.executeUpdate("delete from item_has_order where item_id in (select item_id from item where restaurant_id ="+ restaurant_id+");");	
			System.out.println("Delete 1");
			Statement s2 = conn.createStatement();
			s2.executeUpdate("delete from deal_has_item where item_id in (select item_id from item where restaurant_id ="+ restaurant_id+");");	
			System.out.println("Delete 2");
			Statement s3 = conn.createStatement();
			s3.executeUpdate("delete from deal where Restaurant_restaurant_id = "+ restaurant_id+";");	
			System.out.println("Delete 3");
			Statement s4 = conn.createStatement();
			s4.executeUpdate("delete from item where restaurant_id = "+ restaurant_id+";");	
			System.out.println("Delete 4");
			Statement s5 = conn.createStatement();
			s5.executeUpdate("delete from restaurant where restaurant_id = "+ restaurant_id+";");	
			System.out.println("Delete 5 Final");
			return;
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		

	}
	

	//delete an item from the database; a restaurant might need to remove an item it has been previously offering 
    public void deleteItem(int item_id) {
    	try {
            Statement s1 = conn.createStatement();
            s1.executeUpdate("delete from deal_has_item where Item_id = " + item_id + ";");	
            System.out.println("Delete 1");
            Statement s2 = conn.createStatement();
            s2.executeUpdate("delete from item_has_order where Item_id = "+ item_id+";");	
            System.out.println("Delete 2");
            Statement s3 = conn.createStatement();
            s3.executeUpdate("delete from item where item_id ="+ item_id+";");	
            System.out.println("Delete 3");
            return;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    	
    }
    

	//get the details for every onboarded restaurant in the database - its admin details 
	public ArrayList<RestaurantData> getRestaurantsAdmin(){
		int id;
		String name = "";
		String address = "";
		String image = "";
		String type = "";
		ArrayList<RestaurantData> output = new ArrayList<RestaurantData>();

		try {
			p = conn.prepareStatement("select restaurant_id, name, address, icon, restaurant_category from restaurant;");
			rs = p.executeQuery();
			while (rs.next()) {
				id = rs.getInt("restaurant_id");
				name = rs.getString("name");
				address = rs.getString("address");
				image = rs.getString("icon");
				type = rs.getString("restaurant_category");
				RestaurantData r = new RestaurantData(id, name, address, image, type);
				System.out.println(r.getName());

				output.add(r);
			}
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return output;

	}

	//overloaded function to get restaurant details with its unique username 
	RestaurantData getRestaurantAdmin(String loginname){
		int Restaurant_id = 0;
		RestaurantData output = null;
		String name, address, description, icon, restaurant_category;
		int registered_by = 0;    	
		try {
			p = conn.prepareStatement("select restaurant_id, name, address, Description, registered_by, icon, restaurant_category from Restaurant where loginname='"+loginname+"';");
			rs = p.executeQuery();
			if (rs.next()) {
				Restaurant_id = rs.getInt("Restaurant_id");
				name = rs.getString("name");
				address = rs.getString("address");
				description  = rs.getString("Description");
				registered_by = rs.getInt("registered_by");
				icon = rs.getString("icon");
				restaurant_category = rs.getString("restaurant_category");
				output = new RestaurantData(Restaurant_id, name, address, icon, description, restaurant_category, registered_by);

			}
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return output;


	}

	//get all items that are part of a deal
	private ArrayList<String> getDealItems(int dealID){
		String name; 
		ArrayList<String> output =  new ArrayList<String>();
		try {
			p = conn.prepareStatement("select name from item where Item_id in (select Item_id from deal_has_item where deal_id ="+dealID +" );");
			rs = p.executeQuery();
			while (rs.next()) {
				name = rs.getString("name");
				output.add(name);
			}
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return output;
	}

	// For restaurant admin - all items and deals in stock
	ArrayList<FoodsData> getFoodsRestaurant(int Restaurant_id){
		Float price = 0.0f;
		int inStock = 0;
		String name = "";
		String desc= "";
		String image = "";
		int item_id = 0;
		ArrayList<FoodsData> output = new ArrayList<FoodsData>();
		try {
			p = conn.prepareStatement("select item_id, name, price, Description, in_stock, image from item where Restaurant_id ="+Integer.toString(Restaurant_id)+";");
			rs = p.executeQuery();

			while (rs.next()) {
				item_id = rs.getInt("item_id");
				name = rs.getString("name");
				price = rs.getFloat("price");
				desc = rs.getString("Description");
				inStock = rs.getInt("in_stock");
				image = rs.getString("image");
				FoodsData f = new FoodsData(item_id, name, price, desc, inStock, image);
				System.out.println(name);
				output.add(f);
			}
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		int deal_id;
		String dealName = "";
		float Dprice;    

		try {
			p = conn.prepareStatement("select deal_id, deal_name, price from deal where Restaurant_restaurant_id ="+Restaurant_id+";");
			rs = p.executeQuery();
			if (rs.next()) {
				deal_id = rs.getInt("deal_id");
				dealName = rs.getString("deal_name");
				Dprice = rs.getFloat("price");
				ArrayList<String> temp = getDealItems(deal_id);
				output.add(new DealToFood(new DealData(deal_id, dealName, Dprice, temp)));
			}
		} 
		catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return output;
	}

	//getting the unique identifier id for an onboarded restaurant
	public int getRestId(String RestaurantName){
		int Restaurant_id = 0;
		try {
			p = conn.prepareStatement("select Restaurant_id from Restaurant where name='"+RestaurantName+"';");
			rs = p.executeQuery();
			if (rs.next()) {
				Restaurant_id = rs.getInt("Restaurant_id");
			}
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return Restaurant_id;
	}

	//helper functions to get in stock items' details from either the name or the id of an onboarded restaurnat  
	public ArrayList<FoodsData> getFoods(String RestaurantName){
		int Restaurant_id = getRestId(RestaurantName);
		return getFoodsRestaurant(Restaurant_id);
	}
	
	public ArrayList<FoodsData> getFoods(int Restaurant_id){
		return getFoodsRestaurant(Restaurant_id);
	}

	//a new user sign up in the server
	public void signUpUser(String username, String password, String contact) {
		try {
			Statement s = conn.createStatement();
			s.executeUpdate("insert into Customer (name, contact, password) values('" + username + "', '" + contact + "', '" + password + "');");	
			System.out.println("Inserted records into the table...");
			return;
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	//onboarding a new restaurant
	public void addRestaurant(String Username, String Name, String RestaurantCategory, String Image, String Address, String Description, String Loginname, String Loginpassword) {
		int admin_id = 0;
		try {
			p = conn.prepareStatement("select admin_id from Admin where name ='"+ Username + "';");
			rs = p.executeQuery();
			if (rs.next()) {
				admin_id = rs.getInt("admin_id");
			}
			Statement s = conn.createStatement();
			System.out.println("insert into restaurant (name, address, Description, registered_by, icon, restaurant_category, loginname,  password) values( '" + Name + "', '" + Address + "', '" + Description + "', " + Integer.toString(admin_id)+ ", '" + 	Image + "', '" + RestaurantCategory + "', '" + Loginname +  "', '" + Loginpassword + "');");
			s.executeUpdate("insert into restaurant (name, address, Description, registered_by, icon, restaurant_category, loginname,  password) values( '" + Name + "', '" + Address + "', '" + Description + "', " + Integer.toString(admin_id)+ ", '" + 	Image + "', '" + RestaurantCategory + "', '" + Loginname +  "', '" + Loginpassword + "');");	
			System.out.println("Inserted records into the table...");
			return;
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	//restaurant admin adding a new item in stock
	public void addFoodItem(String name, float price, String Description, int inStock, int restaurant_id, int Food_category_id, String Image) {
		try {
			Statement s = conn.createStatement();
			String sql = "Insert into item (name, price, description, in_stock, restaurant_id, Food_category_id, image) values ('"+name + "', '" + price + "', '" + Description + "', " + inStock + ", " + restaurant_id + ", " + Food_category_id + ", '" + Image + "');";
			s.executeUpdate(sql);	
			System.out.println("Inserted records into the table...");
			return;
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	//editing details of a food item within a restaurant
	public void editFoodItem(String name, float price, String Description, int inStock, int restaurant_id, int Food_category_id, String Image, int item_id) {
		try {
			Statement s = conn.createStatement();
			String sql = "Update item set name='"+name+"', price=" +  Float.toString(price) + ", description='"+ Description +"', in_stock = " + Integer.toString(inStock) + ", food_category_id = " + Food_category_id + ", image = '" +  Image + "' where item_id = " + item_id + ";";
			s.executeUpdate(sql);	
			System.out.println("Updated");
			return;
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}


	//get the category for a particular food item to display details for the customer	
	public int getCategory(int food_id) {
		int category_id = 0;
		try {
			p = conn.prepareStatement("select Food_category_id from item where item_id=" + food_id + ";");
			rs = p.executeQuery();
			if (rs.next()) {
				category_id = rs.getInt("Food_category_id");
			}
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return category_id;
		
	}
	
	//customer places an order and it goes into the database for further processing and confirmation - not every order is saved specifically
	public void placeOrder(Cart currCart, int custID){
		float price;
		int orderID = -1;
		price = currCart.getPrice();
		try {
			p = conn.prepareStatement("insert into `order`(total, Customer_id) values (" + price + ", " + custID + ")");
			p.executeUpdate();
			rs = conn.prepareStatement("SELECT LAST_INSERT_ID();").executeQuery();

			if (rs.next()) orderID = rs.getInt("LAST_INSERT_ID()");
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			if (orderID == -1) {
				return;
			}
		}

//		int dealID;
//		for(DealData entry : currCart.deals){
//			dealID = entry.deal_id;
//			try {
//				p = conn.prepareStatement("insert into order_has_deal(order_order_id, deal_deal_id) values ("+orderID+", "+dealID+")");
//				p.executeUpdate();
//
//			} catch (SQLException ex) {
//				System.out.println("SQLException: " + ex.getMessage());
//				System.out.println("SQLState: " + ex.getSQLState());
//				System.out.println("VendorError: " + ex.getErrorCode());
//			}
//		}
		
		int itemID;
		int quantity;
		for(FoodsData entry : currCart.getItems()){
			itemID = entry.getId();
			quantity = entry.getAmount();
			try {
				if (entry.getType() == 0) {
				p = conn.prepareStatement("insert into item_has_order(Item_id, Order_id, Quantity) values ("+itemID+", "+orderID+", "+quantity+")");
				p.executeUpdate();
				}
				else if (entry.getType() == 1) {
					p = conn.prepareStatement("insert into deal_has_order(order_order_id, deal_deal_id, quantity) values ("+orderID+", "+itemID+", "+quantity+")");
					p.executeUpdate();
				}

			} catch (SQLException ex) {
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
		}
	}
	
	//helper function - overloaded with different attribute calls
	public void placeOrder(Cart currCart, String customerName){
		int customerId = getCustomerId(customerName);
		placeOrder(currCart, customerId);
	}
	
}