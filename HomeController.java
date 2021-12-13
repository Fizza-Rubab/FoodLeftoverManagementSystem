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

public class HomeController{
	private Cart userCart;
	private ConnectionClass connectNow;

	@FXML
    private Button btnOrders;
	
	
	private int index;
    private String customerName;
	private TreeMap<String, Text> RNameRef = new TreeMap<String, Text>();
	private TreeMap<String, Text> RAddressRef = new TreeMap<String, Text>();
	private TreeMap<String, ImageView> imgRef = new TreeMap<String, ImageView>();

	private TreeMap<String, AnchorPane> RCardRef = new TreeMap<String, AnchorPane>();


    @FXML
    private Button btnOverview;
    
    private ArrayList<RestaurantData> data;

    @FXML
    private Button btnSignout;

    @FXML
    private Pane pnlOverview;
    
    @FXML
    private Label name;
    
    @FXML
    private Text Address1;

    @FXML
    private Text Address2;

    @FXML
    private Text Address3;

    @FXML
    private Text Address4;

    @FXML
    private AnchorPane RCard1;

    @FXML
    private AnchorPane RCard2;

    @FXML
    private AnchorPane RCard3;

    @FXML
    private AnchorPane RCard4;

    @FXML
    private Text RName1;

    @FXML
    private Text RName2;

    @FXML
    private Text RName3;

    @FXML
    private Text RName4;
    
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
    public void goBackClick(ActionEvent e) {
    	if (index>0) {
    		index-=1;
    		setData();
    	}
    }
    
    @FXML
    public void goFrontClick(ActionEvent e) {
    	if (index + 5 <= data.size()) index+=1;
		setData();
    }
    
    public void setConnection(ConnectionClass c) {
    	connectNow = c;
    }
    
    public void setCart(Cart c) {
    	this.userCart = c;
    }
    
    public String getName() {
    	return this.customerName;
    }
    
    public void setName(String cusname) {
    	this.customerName = cusname;
    }

    public void setData() {
    	InputStream stream = null;
    	RestaurantData r;
    	for (int i = 0; i<4; i++) { 
    		if (i+index < data.size()) {
    			r = data.get(i+index);
    			try {
    				stream = new FileInputStream(System.getProperty("user.dir") + "\\src\\application\\img\\" + r.getImage());
    				}
				catch(Exception e) {
					System.out.println(e);
					try {
	            		stream = new FileInputStream(System.getProperty("user.dir") + "\\src\\application\\img\\placebo.png");
	            		}
            		catch(Exception e2) {
            			System.out.println(e2);
            		}
				}
    			RNameRef.get(String.valueOf(i+1)).setText(r.getName());
    			RAddressRef.get(String.valueOf(i+1)).setText(r.getAddress());
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
					RNameRef.get(String.valueOf(i+1)).setText("Coming More Soon!");
					RAddressRef.get(String.valueOf(i+1)).setText("Right here!");
					imgRef.get(String.valueOf(i+1)).setImage(new Image(stream));
				}
				else RCardRef.get(String.valueOf(i+1)).setVisible(false);
    			
    		}
    	}
    }
    
    @FXML
    public void  initialize() {
    	index = 0;
    	name.setText(customerName);
    	RNameRef.put("1", RName1);
    	RNameRef.put("2", RName2);
    	RNameRef.put("3", RName3);
    	RNameRef.put("4", RName4);
    	
    	RAddressRef.put("1", Address1);
    	RAddressRef.put("2", Address2);
    	RAddressRef.put("3", Address3);
    	RAddressRef.put("4", Address4);
    	
    	
    	RCardRef.put("1", RCard1);
    	RCardRef.put("2", RCard2);
    	RCardRef.put("3", RCard3);
    	RCardRef.put("4", RCard4);

    	
    	imgRef.put("1", img1);
    	imgRef.put("2", img2);
    	imgRef.put("3", img3);
    	imgRef.put("4", img4);
    	data = connectNow.getRestaurants();
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
    
    @FXML
    public void gotoRestaurant(MouseEvent e) {
        FXMLLoader loader = null;
        
         try {
        	 String fxId = ((Node) e.getSource()).getId();
        	 Node node = (Node) e.getSource();
             Stage prevStage = (Stage) node.getScene().getWindow();
        	 loader = new FXMLLoader(getClass().getResource("/application/RestaurantPage.fxml")); 
             RestaurantPageController controller = new RestaurantPageController();
             controller.setName(customerName);             
             int no = Integer.parseInt(fxId.substring(fxId.length()-1));
             controller.setRestaurant(data.get(index + no - 1));
             controller.setConnection(connectNow);
             controller.setCart(userCart);
             loader.setController(controller);
             Parent root = (Parent) loader.load();
             Scene scene = new Scene(root);
             Stage stage = new Stage();
             stage.setResizable(false);
             stage.setTitle("Food Leftover System Portal");
             stage.setScene(scene);
             prevStage.close();
             stage.show();
         } catch(Exception error) {
             System.out.println(error);
         }
    	
    }

    @FXML 
	void goToCart(ActionEvent e) {
		FXMLLoader loader = null;

		try {
			Node node = (Node) e.getSource();
			Stage prevStage = (Stage) node.getScene().getWindow();
			loader = new FXMLLoader(getClass().getResource("/application/CheckOut.fxml")); 
			CheckOutController controller = new CheckOutController();
			controller.setName(customerName);     
			controller.setConnection(connectNow);
			controller.setCart(userCart);
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
		catch(Exception error) {
			System.out.println(error);
		}
	}

}
