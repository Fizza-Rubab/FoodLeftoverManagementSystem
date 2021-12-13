package application;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CheckOutController {
	private Cart userCart;
	private ConnectionClass connectNow;
	private String customerName;
	
	private int indexFood;
	private TreeMap<String, Text> FNameRef = new TreeMap<String, Text>();
	private TreeMap<String, Text> FPriceRef = new TreeMap<String, Text>();
	private TreeMap<String, Text> quantityRef = new TreeMap<String, Text>();
	private TreeMap<String, AnchorPane> FCardRef = new TreeMap<String, AnchorPane>();
	
	private float totalPrice;
	
	@FXML
    private Label CName;
	
    @FXML
    private Text CTotalPrice;

    @FXML
    private AnchorPane FCard1;

    @FXML
    private AnchorPane FCard11;

    @FXML
    private AnchorPane FCard2;

    @FXML
    private AnchorPane FCard3;

    @FXML
    private AnchorPane FCard4;

    @FXML
    private Text FName1;

    @FXML
    private Text FName11;

    @FXML
    private Text FName2;

    @FXML
    private Text FName3;

    @FXML
    private Text FName4;

    @FXML
    private Text Fprice1;

    @FXML
    private Text Fprice11;

    @FXML
    private Text Fprice2;

    @FXML
    private Text Fprice3;

    @FXML
    private Text Fprice4;

    @FXML
    private Text Q1;

    @FXML
    private Text Q11;

    @FXML
    private Text Q2;

    @FXML
    private Text Q3;

    @FXML
    private Text Q4;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private Button btnSignout;

    @FXML
    private Button goBack;

    @FXML
    private Button goFront;

    @FXML
    private Pane pnlOverview;

    public void setName(String customerName) {
		this.customerName = customerName;
	}

	public void setConnection(ConnectionClass c) {
		connectNow = c;
	}

	public void setCart(Cart c) {
		this.userCart = c;
	}

	public void initialize() {
		totalPrice = 0;
		CName.setText(customerName);
		
		indexFood = 0;

		FNameRef.put("1", FName1);
		FNameRef.put("2", FName2);
		FNameRef.put("3", FName3);
		FNameRef.put("4", FName4);

		FPriceRef.put("1", Fprice1);
		FPriceRef.put("2", Fprice2);
		FPriceRef.put("3", Fprice3);
		FPriceRef.put("4", Fprice4);

		quantityRef.put("1", Q1);
		quantityRef.put("2", Q2);
		quantityRef.put("3", Q3);
		quantityRef.put("4", Q4);

		FCardRef.put("1", FCard1);
		FCardRef.put("2", FCard2);
		FCardRef.put("3", FCard3);
		FCardRef.put("4", FCard4);
		setTotalPrice();
		setData();
	}
	
	public void setTotalPrice() {
		for (FoodsData f: userCart.getItems()) {
			totalPrice += f.getPrice() * f.getAmount();
		}
		CTotalPrice.setText("Total: Rs "+totalPrice);
	}
	
	public void setData() {
		ArrayList<FoodsData> data = userCart.getItems();
		FoodsData f;
		for (int i = 0; i<4; i++) { 
			if (i+indexFood < data.size()) {
				f = data.get(i+indexFood);
				FNameRef.get(String.valueOf(i+1)).setText(f.getName());
				FPriceRef.get(String.valueOf(i+1)).setText("Rs " + Float.toString(f.getPrice()*f.getAmount()));
				quantityRef.get(String.valueOf(i+1)).setText(String.valueOf(f.getAmount()));
			}
			else {
				quantityRef.get(String.valueOf(i+1)).setText("");
				if (i == 0) {
					FNameRef.get(String.valueOf(i+1)).setText("No Items Added to Cart");
					FPriceRef.get(String.valueOf(i+1)).setText("");
				}
				else FCardRef.get(String.valueOf(i+1)).setVisible(false);

			}
		}
	}
    
    @FXML
    void goBack(ActionEvent event) {
    	try {
			Node node = (Node) event.getSource();
			Stage prevStage = (Stage) node.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Home.fxml")); // ADMIN
			HomeController controller = new HomeController();
			controller.setName(this.customerName);
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
		catch(Exception e) {
			System.out.println(e);
		};
    }

    @FXML
    void goBackClick(ActionEvent event) {
    	if (indexFood>0) {
    		indexFood -=1;
    		setData();
    	};
    }

    @FXML
    void goFrontClick(ActionEvent event) {
    	if (indexFood + 5 <= userCart.getItems().size()) {
    		indexFood +=1;
    		setData();
    	}
    }

    @FXML
    void placeOrder(ActionEvent event) {
    	if(userCart.getItems().size() > 0) {
    		connectNow.placeOrder(userCart, customerName);
    		userCart.resetItems();
    		totalPrice = 0;
    		CTotalPrice.setText("Total: Rs "+totalPrice);
    		setData();
    		
    	}
    }
}
