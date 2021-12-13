package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

// A class that provides the sign up as a customer interface and redirects to sign in once user has been created
public class SignupController {

	// Connection class singleton instance
	private ConnectionClass connectNow;

	// FXML labels, texts and elements
	@FXML
	private PasswordField passText;

	@FXML
	private Button signupButton;

	@FXML
	private TextField userContact;

	@FXML
	private TextField userText;

	// Setter for DB connection
	public void setConnection(ConnectionClass c) {
		this.connectNow = c;
	}

	// Main method for signing up. This creates a customer entry with the provided details
	// within the Customer table and allows user to sign in once he has been created
	@FXML
	void signup(MouseEvent event) {
		try {
			// Get the Hashing Object
			HashIt hash = new HashIt();
			// Call the signUpUser method of the connection class and provide name, password hash and other details for customer creation
			connectNow.signUpUser(userText.getText(),  hash.computeHash(passText.getText()), userContact.getText());

			// Get the previous stage through event source
			Node node = (Node) event.getSource();
			Stage prevStage = (Stage) node.getScene().getWindow();

			// Load the sign in fxml form and its controller and bind both
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/signIn.fxml")); // ADMIN
			LoginController controller = new LoginController();
			controller.setConnection(connectNow);
			loader.setController(controller);
			Parent root = (Parent) loader.load();
			// Set up the stage and the scene
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.setTitle("Food Leftover System Portal");
			stage.setScene(scene);
			prevStage.close();
			stage.show();
		} // Check for any exception 
		catch(Exception e) {
			System.out.println(e);
		};

	}

}
