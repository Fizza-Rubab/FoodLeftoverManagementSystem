package application;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.TreeMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminController {


	private ConnectionClass connectNow; // Instance of singleton connection
	private String adminName; // Admin Name to be displayed on vertical side panel
	private ArrayList<RestaurantData> data; // A list of restaurants to be displayed
	private int index; // Index for the list
	
	// Tree maps/ Dictionaries for storing references to Javafx objects(Restaurant names, descriptions, images) against indices
	private TreeMap<String, Text> RNameRef = new TreeMap<String, Text>();
	private TreeMap<String, Text> RTypeRef = new TreeMap<String, Text>();
	private TreeMap<String, Text> RAddressRef = new TreeMap<String, Text>();
	private TreeMap<String, ImageView> imgRef = new TreeMap<String, ImageView>();
	private TreeMap<String, AnchorPane> RCardRef = new TreeMap<String, AnchorPane>();

	// Java FX scene variables
	@FXML
	private Text Address1;

	@FXML
	private Text Address2;

	@FXML
	private Text Address3;

	@FXML
	private Text Address4;

	@FXML
	private Text Address5;

	@FXML
	private Label CName;

	@FXML
	private AnchorPane RCard1;

	@FXML
	private AnchorPane RCard2;

	@FXML
	private AnchorPane RCard3;

	@FXML
	private AnchorPane RCard4;

	@FXML
	private AnchorPane RCard5;

	@FXML
	private Label RName;

	@FXML
	private Text RName1;

	@FXML
	private Text RName2;

	@FXML
	private Text RName3;

	@FXML
	private Text RName4;

	@FXML
	private Text RName5;

	@FXML
	private Button btnAdd;

	@FXML
	private Button btnRestaurants;

	@FXML
	private Button btnSignout;

	@FXML
	private Button goBack;

	@FXML
	private Button goFront;

	@FXML
	private ImageView img1;

	@FXML
	private ImageView img2;

	@FXML
	private ImageView img3;

	@FXML
	private ImageView img4;

	@FXML
	private ImageView img5;

	@FXML
	private Pane pnlOverview;

	@FXML
	private Label totalNo;

	@FXML
	private Text type1;

	@FXML
	private Text type2;

	@FXML
	private Text type3;

	@FXML
	private Text type4;

	@FXML
	private Text type5;


	@FXML
	private Text delete1;

	@FXML
	private Text delete2;

	@FXML
	private Text delete3;

	@FXML
	private Text delete4;

	@FXML
	private Text delete5;

	// Method that allows external classes to set the admin name. 
	void setName(String n) {
		System.out.println("heya");
		this.adminName = n;
	}
	
	// Java FX FXML method that initializes everything on the stage
	@FXML
	public void initialize() {
		// Initialize the index to the beginning of the list
		index = 0;
		
		// Set the name to label
		CName.setText(adminName);
		
		// Populate all the references
		RNameRef.put("1", RName1);
		RNameRef.put("2", RName2);
		RNameRef.put("3", RName3);
		RNameRef.put("4", RName4);
		RNameRef.put("5", RName5);
		
		RAddressRef.put("1", Address1);
		RAddressRef.put("2", Address2);
		RAddressRef.put("3", Address3);
		RAddressRef.put("4", Address4);
		RAddressRef.put("5", Address5);
		
		imgRef.put("1", img1);
		imgRef.put("2", img2);
		imgRef.put("3", img3);
		imgRef.put("4", img4);
		imgRef.put("5", img5);
		
		RTypeRef.put("1", type1);
		RTypeRef.put("2", type2);
		RTypeRef.put("3", type3);
		RTypeRef.put("4", type4);
		RTypeRef.put("5", type5);
		
		RCardRef.put("1", RCard1);
		RCardRef.put("2", RCard2);
		RCardRef.put("3", RCard3);
		RCardRef.put("4", RCard4);
		RCardRef.put("5", RCard5);
		
		// get all the restaurants in form of list using the connection class
		data = connectNow.getRestaurantsAdmin();
		// Set the total no
		totalNo.setText("Total Restaurants: "  + Integer.toString(data.size()));
		// Set the data in list on to the stage elements
		setData();
	}



	public void setData() {
		InputStream stream = null;
		RestaurantData r;
		// Iterate over the array data on the scene and place it on fxml elements
		for (int i = 0; i<5; i++) { 
			if (i+index < data.size()) {
				// Obtain the restaurant data object
				r = data.get(i+index);
				try {
					// Load and access the image
					stream = new FileInputStream(System.getProperty("user.dir") + "\\src\\application\\img\\" + r.getImage());

				}
				catch(Exception e) {
					System.out.println(e);
					try {
						// Replace with a default image if not found
						stream = new FileInputStream(System.getProperty("user.dir") + "\\src\\application\\img\\placebo.png");
					}
					catch(Exception e2) {
						System.out.println(e2);
					}
				}
				// Set the values of labels/texts by retreiing from list
				RNameRef.get(String.valueOf(i+1)).setText(r.getName());
				RAddressRef.get(String.valueOf(i+1)).setText(r.getAddress());
				RTypeRef.get(String.valueOf(i+1)).setText(r.getType());
				imgRef.get(String.valueOf(i+1)).setImage(new Image(stream));
				RCardRef.get(String.valueOf(i+1)).setVisible(true);
			}
			else {
				if (i == 0) {
					try {
						stream = new FileInputStream(System.getProperty("user.dir") + "\\src\\application\\img\\placebo.png");
					}
					catch(Exception e) {
						System.out.println(e);
					}
					// switch to default values
					RNameRef.get(String.valueOf(i+1)).setText("Coming More Soon!");
					RAddressRef.get(String.valueOf(i+1)).setText("Right here!");
					imgRef.get(String.valueOf(i+1)).setImage(new Image(stream));
					RTypeRef.get(String.valueOf(i+1)).setText("");
				}
				// Hide visiblity if there are less items/cards
				else RCardRef.get(String.valueOf(i+1)).setVisible(false);
			}
		}
	}


	// Method that opens up a create a restaruant font
	@FXML
	void addRest(ActionEvent event) {
		FXMLLoader loader = null;
		try {
			//Access the prevoius stage through the clicked node
			Node node = (Node) event.getSource();
			Stage prevStage = (Stage) node.getScene().getWindow();
			
			// Load the add restaurant fxml
			loader = new FXMLLoader(getClass().getResource("/application/AddRestaurant.fxml")); // USER
			AddRestaurantController controller = new AddRestaurantController();
			
			// Set the controller properties
			controller.setName(adminName);
			controller.setConnection(connectNow);
			loader.setController(controller);
			Parent root = (Parent) loader.load();
			
			//Set scene and stage attributes
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.setTitle("Food Leftover System Portal");
			stage.setScene(scene);
			prevStage.close();
			stage.show();
		} catch(Exception e) {
			System.out.println(e);
		};

	}
	
	// method to set the DB Connection Class
	void setConnection(ConnectionClass c) {
		this.connectNow = c;
	}
	
	
	// Method to scroll up
	@FXML
	public void goBackClick(ActionEvent e) {
		// Alter the index and reset the data;
		if (index>0) {
			index-=1;
			setData();
		}
	}

	// Method to scroll down
	@FXML
	public void goFrontClick(ActionEvent e) {
		// if the index doesn't  exceed 6, increment
		if (index + 6 <= data.size()) index+=1;
		// reset data
		setData();
	}

	// Method that invokes when user clicks on the delete icon for one of the restaurants
	@FXML
	void handleDelete(MouseEvent event) {
		// obtain id of the card clicked
		String deleteId = ((Text)event.getSource()).getId();
		// Extract the last digit and add it to index to obtain the retaurant id
		int rest_id = Integer.parseInt(deleteId.substring(deleteId.length() -1, deleteId.length())) -1 +index;
		
		// Delete the restaurant and remove it from list
		connectNow.deleteRestaurant(rest_id);
		data.remove(rest_id);
		
		// On deletion update index and reset data values on fxml elements
		if (index > 0) index--;
		setData();

	}

	// Method to sign out from the current scene to the sign in fxml form
	@FXML
	void signOut(ActionEvent event) {
		try {
			// Obtain the previous stage
			Node node = (Node) event.getSource();
			Stage prevStage = (Stage) node.getScene().getWindow();
			
			// Load the FXML, Create a controller and bind them
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/signIn.fxml")); // ADMIN
			LoginController controller = new LoginController();
			controller.setConnection(connectNow);
			loader.setController(controller);
			Parent root = (Parent) loader.load();
			
			// Set scene and stage attributes and display
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.setTitle("Food Leftover System Portal");
			stage.setScene(scene);
			prevStage.close();
			stage.show();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

}
