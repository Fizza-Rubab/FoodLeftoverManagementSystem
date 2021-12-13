package application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

//This class servers as an interface for Customers, Restaurant Admins and Application Admin to enter their respective portal
public class LoginController {
	// Singleton Connection Class Instance
	private ConnectionClass connectNow;
	
	// Declaration of FXML elements and their ids
	@FXML
	private AnchorPane ap;

	@FXML
	private Button loginButton;

	@FXML
	private PasswordField passText;

	
	@FXML
	private TextField userText;

	@FXML
	private Text error;

	// This function attempts to login a user by extracting the username and password and running it against DB scripts
	@FXML
	void login(Event event) {
		// Create a hashing class object
		HashIt hash = new HashIt();
		// Find if user is  registered in db using the values in the form
		int logged = connectNow.signInUser(userText.getText(), hash.computeHash(passText.getText())); //Use password hash instead of password for verification
		FXMLLoader loader = null;
		if (logged!=0) {
			try {
				// Get previous stage
				Node node = (Node) event.getSource();
				Stage prevStage = (Stage) node.getScene().getWindow();
				// In case of user, load Home.fxml and bind the relvant controller
				if (logged==1) {
					loader = new FXMLLoader(getClass().getResource("/application/Home.fxml")); 
					Cart userCart = Cart.getInstance();
					HomeController controller = new HomeController();
					controller.setName(userText.getText());
					controller.setConnection(connectNow);
					controller.setCart(userCart);
					loader.setController(controller);

				}
				// In case of restaurant admin, load RestaurantAdmin.fxml and bind the relevant controller
				if (logged==2) {
					loader = new FXMLLoader(getClass().getResource("/application/RestaurantAdmin.fxml"));// RESTAURANT
					RestaurantAdminController controller = new RestaurantAdminController();
					controller.setName(userText.getText());
					controller.setConnection(connectNow);
					loader.setController(controller);

				}
				// In case of app admin, load Admin.fxml and bind the relevant controller
				if (logged==3) {
					loader = new FXMLLoader(getClass().getResource("/application/Admin.fxml")); // ADMIN
					AdminController controller = new AdminController();
					controller.setName(userText.getText());
					controller.setConnection(connectNow);
					loader.setController(controller);

				}          
				
				// Set the scene and stage
				Parent root = (Parent) loader.load();
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
		else {
			// If incorrect credentials 
			error.setText("Incorrect Username or password");
		}
	}


//	This method facilitates a new user to sign up by  opening another form and asking for details
	@FXML
	void signup(MouseEvent event) {
		try {
			this.connectNow = ConnectionClass.getInstance();
			Node node = (Node) event.getSource();
			Stage prevStage = (Stage) node.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/SignUp.fxml")); // ADMIN
			SignupController controller = new SignupController();
			controller.setConnection(connectNow);
			loader.setController(controller);
			Parent root = (Parent) loader.load();
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

//	This method is to facilitate login by directly pressing enter when  user is done typing the password instead of having to click on the button
	@FXML
	void enterRelease(KeyEvent event) {
		// Check if enter has been pressed
		if (event.getCode().equals(KeyCode.ENTER)) {
			login(event);
		}
	}
	// Setter for DB Connection instance
	public void setConnection(ConnectionClass c) {
		connectNow = c;
	}
}
