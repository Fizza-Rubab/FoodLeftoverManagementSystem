package application;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Main extends Application {
	// ConnectionClass DB variable
	private ConnectionClass connectNow;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
    	// Get the instance of singleton class Connection Class that executes database related instructions
    	this.connectNow = ConnectionClass.getInstance();
        try {
        	// Load signIn fxml form and corresponding controller
       	 	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/signIn.fxml")); // ADMIN
            LoginController controller = new LoginController();
            // Pass the connection to controller
            controller.setConnection(connectNow);
            loader.setController(controller);
            Parent root = (Parent) loader.load();
            // Set scene and stage
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Food Leftover System Portal");
            stage.setScene(scene);
            stage.show();
            }
        // For any exceptions that might occur in loading
        catch (Exception e) {
            e.printStackTrace();
        }
        ;
    }
}
