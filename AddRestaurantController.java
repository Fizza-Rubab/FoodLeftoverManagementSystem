package application;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AddRestaurantController {
	private String adminName;
	private ConnectionClass connectNow;
	
	@FXML
    private TextArea AddressField;

    @FXML
    private TextField CategoryField;

    @FXML
    private TextArea DescriptionField;

    @FXML
    private Button ImageField;

    @FXML
    private TextField LoginnameField;

    @FXML
    private TextField LoginpwdField;

    @FXML
    private TextField NameField;

    @FXML
    private Label RName;

    @FXML
    private Label RName1;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnRestaurants;

    @FXML
    private Button btnSignout;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Label userText;

    // Getters and Setters for name and connection
    void setName(String n) {
    	this.adminName = n;
    }
    void setConnection(ConnectionClass c) {
    	this.connectNow = c;
    }
    
    // Set the side panel name as soon as fxml loads
    void initialize() {
    	userText.setText(adminName);
    }
    
    
    
    // This method is used to add a restaurant according to admin's specified data in the text fields and add it in DB
    @FXML
    void addRest(ActionEvent event) {
    	System.out.print("b5 db");
    	try {
    	connectNow.addRestaurant(adminName, NameField.getText(), CategoryField.getText(), ImageField.getText(), AddressField.getText(), DescriptionField.getText(), LoginnameField.getText(), (new HashIt()).computeHash(LoginpwdField.getText()));
    	}
    	catch(Exception e) {
    		System.out.print(e);
    	}
    	// Head back to dashboard after insertion
    	Dashboard(event);

    }
//    This method is to create a fileChoose dialog box to get path of a file we want to select for the restaurant image
    @FXML
    void getImageDialog(ActionEvent event) {
    	try {
		// Obtain the previous stage
    	Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        // Open the browse file dialog
    	FileChooser fileChooser = new FileChooser();
    	File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
        	// Obtain the path by trimming and set it to the text
            ImageField.setText(file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("\\")+1));
        }
    	}
    	// Catch any exception e.g file missing, corrupt etc.
    	catch(Exception e) {
    		System.out.print(e);
    	}

    }
    
    // Method to head back to main admin page where all restaurants are listed
    @FXML
    void Dashboard(ActionEvent event) {
    	try {
    		// Obtain the previous stage
	    	 Node node = (Node) event.getSource();
	         Stage prevStage = (Stage) node.getScene().getWindow();
	      // Load the FXML, Create a controller and bind them
	         FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Admin.fxml")); // ADMIN
	         AdminController controller = new AdminController();
	         System.out.print("ehllo");
	         controller.setName(this.adminName);
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
	     };
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
