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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AddFoodController {
	private ConnectionClass connectNow;
	private String restName;
	private RestaurantData r;
	private int category_id = 1;
	private FoodsData foodItem = null;
	private boolean isAdd = true;
	
    @FXML
    private TextArea DescriptionField;

    @FXML
    private Button ImageField;

    @FXML
    private TextField NameField;

    @FXML
    private TextField Price;

    @FXML
    private Label RName;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnRestaurants;

    @FXML
    private Button btnSignout;

    @FXML
    private MenuButton category;

    @FXML
    private Pane pnlOverview;

    @FXML
    private TextField stock;

    @FXML
    private Button stockDecrease;

    @FXML
    private Button stockIncrease;

    @FXML
    private Label task;

    @FXML
    private Label userText;

    public String getName() {
    	return this.restName;
    }
    
    void setFormType(boolean add){
    	isAdd = add;
    }
    
    void setFood(FoodsData f) {
    	foodItem = f;
    }
    
    
    	
    
    @FXML
    void initialize() {
    	if (foodItem!=null)
    			category_id = connectNow.getCategory(foodItem.getId());
    	if (isAdd) {
    		System.out.print("hooha");
    		stockIncrease.setStyle("-fx-background-color: transparent;");
    		stockDecrease.setStyle("-fx-background-color: transparent;");
    	}
    	else {
    		System.out.print("booha");
    		task.setText("Edit an item");
    		btnAdd.setText("Edit");
    		NameField.setText(foodItem.getName());
    		Price.setText(Float.toString(foodItem.getPrice()));
    		stock.setText(Integer.toString(foodItem.getStock()));
    		ImageField.setText(foodItem.getImage());
    		DescriptionField.setText(foodItem.getDesc());
    		
    	}
    }
    
    @FXML
    void getImageDialog(ActionEvent event) {
    	try {
    	
    	Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
    	FileChooser fileChooser = new FileChooser();
    	File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            ImageField.setText(file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("\\")+1));
        }
    	}
    	catch(Exception e) {
    		System.out.print(e);
    	}

    }
    
    @FXML
    void getCategory(ActionEvent event) {
    	MenuItem categoryField = (MenuItem) event.getSource();
    	String categoryFx= (categoryField).getId();
    	category.setText(categoryField.getText());
    	category_id = Integer.parseInt(categoryFx.substring(categoryFx.length() -1, categoryFx.length()));
    }
    @FXML
    void Dashboard(ActionEvent event) {
    	try {
	    	 Node node = (Node) event.getSource();
	         Stage prevStage = (Stage) node.getScene().getWindow();
	         FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/RestaurantAdmin.fxml")); // ADMIN
	         RestaurantAdminController controller = new RestaurantAdminController();
	         System.out.print("ehllo");
	         controller.setName(this.restName);
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
	     } 
	     catch(Exception e) {
	         System.out.println(e);
	     };
    }
    
    public void setName(String cusname) {
    	this.restName = cusname;
    }
    public void setRestaurant(RestaurantData r) {
    	this.r = r;
    }

    
    @FXML
    void addFood(ActionEvent event) {
    	if (isAdd)
    		connectNow.addFoodItem(NameField.getText(), Float.parseFloat(Price.getText()), DescriptionField.getText(), Integer.parseInt(stock.getText()), r.getId(), category_id ,  ImageField.getText());
    	else
    		connectNow.editFoodItem(NameField.getText(), Float.parseFloat(Price.getText()), DescriptionField.getText(), Integer.parseInt(stock.getText()), r.getId(), category_id , ImageField.getText(), foodItem.getId());
    	Dashboard(event);
    }
    
    @FXML
    void increment(ActionEvent event) {
    	stock.setText(Integer.toString(Integer.parseInt(stock.getText()) + 1));
    }
    
    @FXML
    void decrement(ActionEvent event) {
    	stock.setText(Integer.toString(Integer.parseInt(stock.getText()) - 1));
    }
    
    @FXML
    void handleClicks(ActionEvent event) {

    }
    

    public void setConnection(ConnectionClass c) {
    	this.connectNow = c;
    }
    
    
    @FXML
    void signOut(ActionEvent event) {
    	try {
    	Node node = (Node) event.getSource();
        Stage prevStage = (Stage) node.getScene().getWindow();
   	 	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/signIn.fxml")); // ADMIN
   	 	LoginController controller = new LoginController();
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
        }
    	catch(Exception e) {
            System.out.println(e);
        }
    }
}
    
