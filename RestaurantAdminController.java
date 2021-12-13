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



public class RestaurantAdminController {
	private ConnectionClass connectNow;
	
	private RestaurantData restaurant;
	
	private TreeMap<String, Text> FNameRef = new TreeMap<String, Text>();
	private TreeMap<String, Text> FStockRef = new TreeMap<String, Text>();
	private TreeMap<String, ImageView> imgRef = new TreeMap<String, ImageView>();
	private TreeMap<String, Text> DescRef = new TreeMap<String, Text>();
	private TreeMap<String, Text> FPriceRef = new TreeMap<String, Text>();
	private TreeMap<String, AnchorPane> FCardRef = new TreeMap<String, AnchorPane>();

	private int indexFood;
	private String restName;
	private ArrayList<FoodsData> data;
    @FXML
    private Label CName;

    @FXML
    private AnchorPane FCard1;

    @FXML
    private AnchorPane FCard2;

    @FXML
    private AnchorPane FCard3;

    @FXML
    private AnchorPane FCard4;

    @FXML
    private AnchorPane FCard5;

    @FXML
    private Text FName1;

    @FXML
    private Text q4;

    @FXML
    private Text FName2;

    @FXML
    private Text FName3;

    @FXML
    private Text FName4;

    @FXML
    private Text FName5;

    @FXML
    private Text Fdesc1;

    @FXML
    private Text Fdesc2;

    @FXML
    private Text Fdesc3;

    @FXML
    private Text Fdesc4;

    @FXML
    private Text Fdesc5;

    @FXML
    private Text Fprice1;

    @FXML
    private Text Fprice2;

    @FXML
    private Text Fprice3;

    @FXML
    private Text Fprice4;

    @FXML
    private Text Fprice5;

    @FXML
    private Label RAddress;

    @FXML
    private ImageView RImg;

    @FXML
    private Label RName;

    @FXML
    private Button btnAddfood;

    @FXML
    private Button btnRestaurants;

    @FXML
    private Button btnSignout;

    @FXML
    private ImageView delete1;

    @FXML
    private ImageView delete2;

    @FXML
    private ImageView delete3;

    @FXML
    private ImageView delete4;

    @FXML
    private ImageView delete5;

    @FXML
    private ImageView edit1;

    @FXML
    private ImageView edit2;

    @FXML
    private ImageView edit3;

    @FXML
    private ImageView edit4;

    @FXML
    private ImageView edit5;

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
    private Text q1;

    @FXML
    private Text q2;

    @FXML
    private Text q3;

    @FXML
    private Text q5;
    
    public void setConnection(ConnectionClass c) {
    	connectNow = c;
    }
    
    public String getName() {
    	return this.restName;
    }
    
    public void setName(String rname) {
    	this.restName = rname;
    }
    

    
    @FXML
    public void  initialize() {
    	indexFood = 0;
    	restaurant = connectNow.getRestaurantAdmin(restName);
    	
    	this.RName.setText(restaurant.getName());
    	this.RAddress.setText(restaurant.getAddress());
    	try {
			this.RImg.setImage(new Image(new FileInputStream(System.getProperty("user.dir") + "\\src\\application\\img\\" + restaurant.getImage())));
		} 
    	catch(Exception e) {
			System.out.println(e);
			try {
				this.RImg.setImage(new Image(new FileInputStream(System.getProperty("user.dir") + "\\src\\application\\img\\placebo.png")));
//				stream = new FileInputStream();
    		}
    		catch(Exception e2) {
    			System.out.println(e2);
    		}
		}
    	System.out.println(restaurant.getName());
    	
    	CName.setText(restName);
    	FNameRef.put("1", FName1);
    	FNameRef.put("2", FName2);
    	FNameRef.put("3", FName3);
    	FNameRef.put("4", FName4);
    	FNameRef.put("5", FName5);
    	
    	FPriceRef.put("1", Fprice1);
    	FPriceRef.put("2", Fprice2);
    	FPriceRef.put("3", Fprice3);
    	FPriceRef.put("4", Fprice4);
    	FPriceRef.put("5", Fprice5);
    	
    	DescRef.put("1", Fdesc1);
    	DescRef.put("2", Fdesc2);
    	DescRef.put("3", Fdesc3);
    	DescRef.put("4", Fdesc4);
    	DescRef.put("5", Fdesc5);
    	
    	imgRef.put("1", img1);
    	imgRef.put("2", img2);
    	imgRef.put("3", img3);
    	imgRef.put("4", img4);
    	imgRef.put("5", img5);
    	
    	FStockRef.put("1", q1);
    	FStockRef.put("2", q2);
    	FStockRef.put("3", q3);
    	FStockRef.put("4", q4);
    	FStockRef.put("5", q5);
    	

    	FCardRef.put("1", FCard1);
    	FCardRef.put("2", FCard2);
    	FCardRef.put("3", FCard3);
    	FCardRef.put("4", FCard4);
    	FCardRef.put("5", FCard5);
    	
    	data = connectNow.getFoodsRestaurant(restaurant.getId());
    	data.get(0).getName();
    	setData();    	
    }
    

    public void setData() {
    	InputStream stream = null;
    	FoodsData f;
    	for (int i = 0; i<5; i++) { 
    		if (i+indexFood < data.size()) {
    			f = data.get(i+indexFood);
    			try {
    				stream = new FileInputStream(System.getProperty("user.dir") + "\\src\\application\\img\\" + f.getImage());
    				}
				catch(Exception e) {
					try {
	            		stream = new FileInputStream(System.getProperty("user.dir") + "\\src\\application\\img\\placebo.png");
	            		}
            		catch(Exception e2) {
            			System.out.println(e2);
            		}
				}
    			FNameRef.get(String.valueOf(i+1)).setText(f.getName());
    			DescRef.get(String.valueOf(i+1)).setText(f.getDesc());
    			FPriceRef.get(String.valueOf(i+1)).setText("Price: Rs " + Float.toString(f.getPrice()));
    			FStockRef.get(String.valueOf(i+1)).setText(Integer.toString(f.getStock()));
    			imgRef.get(String.valueOf(i+1)).setImage(new Image(stream));
    			FCardRef.get(String.valueOf(i+1)).setVisible(true);
    		}
    		else {
    			FStockRef.get(String.valueOf(i+1)).setText("");
    			DescRef.get(String.valueOf(i+1)).setText("");
    			if (i == 0) {
    				try {
                		stream = new FileInputStream(System.getProperty("user.dir") + "\\src\\application\\img\\placebo.png");
                		}
            		catch(Exception e) {
            			System.out.println(e);
            		}
        			FNameRef.get(String.valueOf(i+1)).setText("Coming More Soon!");
        			FPriceRef.get(String.valueOf(i+1)).setText("Right here!");
        			imgRef.get(String.valueOf(i+1)).setImage(new Image(stream));
    			}
    			else FCardRef.get(String.valueOf(i+1)).setVisible(false);
    			
    		}
    	}
		
    }
    
    
    @FXML
    void addFood(MouseEvent event) {
    	try {
    	Node node = (Node) event.getSource();
        Stage prevStage = (Stage) node.getScene().getWindow();
   	 	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/AddFood.fxml")); // ADMIN
   	 	AddFoodController controller = new AddFoodController();
        controller.setConnection(connectNow);
        controller.setName(restName);
        controller.setRestaurant(restaurant);
        if (node!=btnAddfood) {
        	String editId = ((ImageView)event.getSource()).getId();
        	controller.setFood(data.get( Integer.parseInt(editId.substring(editId.length() -1, editId.length())) -1 + indexFood));
        	controller.setFormType(false);
        	
        }
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
    
    @FXML
    void handleDelete(MouseEvent event) {
    	String deleteId = ((ImageView)event.getSource()).getId();
    	System.out.print(deleteId);
    	int temp_id = Integer.parseInt(deleteId.substring(deleteId.length() -1, deleteId.length())) -1 + indexFood;
    	int item_id = data.get(temp_id).getId();
    	System.out.print(item_id);
    	connectNow.deleteItem(item_id);
    	data.remove(temp_id);
    	if (indexFood > 0)
    		indexFood--;
    	setData();
    	
    }
    
    @FXML
    void handleEdit(MouseEvent event) {
    	
    }
    @FXML
    void goBackClick(ActionEvent event) {
    	if (indexFood>0) {
    		indexFood -=1;
    		setData();
    	}
    }

    @FXML
    void goFrontClick(ActionEvent event) {
    	if (indexFood + 6 <= data.size())
    	indexFood +=1;
		setData();
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
