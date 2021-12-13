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

public class RestaurantPageController {
	private Cart userCart;
	private boolean changesMade;
	private ConnectionClass connectNow;

	private RestaurantData restaurant;

	private TreeMap<String, Text> FNameRef = new TreeMap<String, Text>();
	private TreeMap<String, Text> FPriceRef = new TreeMap<String, Text>();
	private TreeMap<String, ImageView> imgRef = new TreeMap<String, ImageView>();
	private TreeMap<String, Text> quantityRef = new TreeMap<String, Text>();
	private TreeMap<String, Text> DescRef = new TreeMap<String, Text>();
	private TreeMap<String, AnchorPane> FCardRef = new TreeMap<String, AnchorPane>();

	private int indexFood;
	private String customerName;
	private ArrayList<FoodsData> data;
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
	private Text FName2;

	@FXML
	private Text FName3;

	@FXML
	private Text FName4;

	@FXML
	private Text FName5;

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
	private Label CName;

	@FXML
	private Text Q1;

	@FXML
	private Text Q2;

	@FXML
	private Text Q3;

	@FXML
	private Text Q4;

	@FXML
	private Text Q5;

	@FXML
	private Label RAddress;

	@FXML
	private ImageView RImg;

	@FXML
	private Label RName;

	@FXML
	private Button btnAddToCart;

	@FXML
	private Button btnCart;

	@FXML
	private Button btnRestaurants;

	@FXML
	private Button btnSignout;

	@FXML
	private Text decrease1;

	@FXML
	private Text decrease2;

	@FXML
	private Text decrease3;

	@FXML
	private Text decrease4;

	@FXML
	private Text decrease5;

	@FXML
	private Button goBack;

	@FXML
	private Button goFront;

	@FXML
	private Text increase1;

	@FXML
	private Text increase2;

	@FXML
	private Text increase3;

	@FXML
	private Text increase4;

	@FXML
	private Text increase5;

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
    private Text desc1;

    @FXML
    private Text desc2;

    @FXML
    private Text desc3;

    @FXML
    private Text desc4;

    @FXML
    private Text desc5;
    
	public void setName(String customerName) {
		this.customerName = customerName;
	}

	public void setRestaurant(RestaurantData r ) {
		this.restaurant = r;   	
	}

	public void setConnection(ConnectionClass c) {
		connectNow = c;
	}

	public void setCart(Cart c) {
		this.userCart = c;
	}
	
	void storeQuantity() {
    	FoodsData f = null;
    	for (int i = 0; i<5; i++) { 
    		if (i+indexFood < data.size()) {
    			f = data.get(i+indexFood);
    			f.setAmount(Integer.valueOf(quantityRef.get(String.valueOf(i+1)).getText()));
    		}
    	}
    }

	public void setData() {
		InputStream stream = null;
		FoodsData f = null;
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
				FPriceRef.get(String.valueOf(i+1)).setText("Price: Rs " + Float.toString(f.getPrice()));
				imgRef.get(String.valueOf(i+1)).setImage(new Image(stream));
				quantityRef.get(String.valueOf(i+1)).setText(String.valueOf(f.getAmount()));
				DescRef.get(String.valueOf(i+1)).setText(f.getDesc());
				FCardRef.get(String.valueOf(i+1)).setVisible(true);
			}
			else {
				quantityRef.get(String.valueOf(i+1)).setText("");
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
					DescRef.get(String.valueOf(i+1)).setText("");
				}
				else FCardRef.get(String.valueOf(i+1)).setVisible(false);

			}
		}

	}

	public void initialize() {
		changesMade = false;
		btnAddToCart.setText("No Items Added");
		btnAddToCart.setStyle("-fx-background-color: #282828;");

		this.CName.setText(customerName);
		indexFood = 0;

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

		imgRef.put("1", img1);
		imgRef.put("2", img2);
		imgRef.put("3", img3);
		imgRef.put("4", img4);
		imgRef.put("5", img5);

		quantityRef.put("1", Q1);
		quantityRef.put("2", Q2);
		quantityRef.put("3", Q3);
		quantityRef.put("4", Q4);
		quantityRef.put("5", Q5);
		
    	DescRef.put("1", desc1);
    	DescRef.put("2", desc2);
    	DescRef.put("3", desc3);
    	DescRef.put("4", desc4);
    	DescRef.put("5", desc5);
    	
		FCardRef.put("1", FCard1);
		FCardRef.put("2", FCard2);
		FCardRef.put("3", FCard3);
		FCardRef.put("4", FCard4);
		FCardRef.put("5", FCard5);

		this.RName.setText(restaurant.getName());
		this.RAddress.setText(restaurant.getAddress());
		try {
			this.RImg.setImage(new Image(new FileInputStream(System.getProperty("user.dir") + "\\src\\application\\img\\" + restaurant.getImage())));
		} 
		catch(Exception e) {
			System.out.println(e);
			try {
				this.RImg.setImage(new Image(new FileInputStream(System.getProperty("user.dir") + "\\src\\application\\img\\placebo.png")));
			}
			catch(Exception e2) {
				System.out.println(e2);
			}
		}
		data = connectNow.getFoods(restaurant.getName());
		setData();
	}


	@FXML
	void handleClicks(ActionEvent event) {

	}

	@FXML
	void addItem(MouseEvent event) {
		String fxId = ((Node) event.getSource()).getId();
		quantityRef.get(fxId.substring(fxId.length()-1)).setText(String.valueOf(Integer.valueOf(quantityRef.get(fxId.substring(fxId.length()-1)).getText())+1)); 
		changesMade = true;
		btnAddToCart.setText("+ Add To Cart");
		btnAddToCart.setStyle("-fx-background-color:  #e7694b;");
	}

	@FXML
	void removeItem(MouseEvent event) {
		String fxId = ((Node) event.getSource()).getId();
		int currQ = Integer.valueOf(quantityRef.get(fxId.substring(fxId.length()-1)).getText());
		if (currQ > 0) {
			quantityRef.get(fxId.substring(fxId.length()-1)).setText(String.valueOf(currQ - 1));
			changesMade = true;
			btnAddToCart.setText("+ Add To Cart");
			btnAddToCart.setStyle("-fx-background-color:  #e7694b;");
		}
	}
	
	@FXML
    void goBackClick(ActionEvent event) {
    	if (indexFood>0) {
    		storeQuantity();
    		indexFood -=1;
    		setData();
    	}
    }

    @FXML
    void goFrontClick(ActionEvent event) {
    	if (indexFood + 6 <= data.size()) {
    		storeQuantity();
    		indexFood +=1;
    		setData();
    	}
    }

	@FXML
	void signOut(ActionEvent event) {
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
	void addToCart(ActionEvent event) {
		if (changesMade) {
			storeQuantity();
			userCart.resetItems();
			for (FoodsData f: data) {
				
				if (f.getAmount() > 0) {
					if (f.getType()==0) userCart.addItem(new FoodsData(f));
					else if (f.getType()==1) {
						DealToFood d = (DealToFood) f;
						userCart.addItem(new DealToFood(d.getDeal(), f.getAmount()));
					}
				}
			}
			btnAddToCart.setText("Items Added");
			btnAddToCart.setStyle("-fx-background-color:  #8ccc4c;");
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


//    @FXML
//    void gotoRestaurant(MouseEvent event) {
//    	try {
//            Node node = (Node) event.getSource();
//            Stage prevStage = (Stage) node.getScene().getWindow();
//            Parent root = FXMLLoader.load(getClass().getResource("/application/Home.fxml"));
//            Scene scene = new Scene(root);
//            Stage stage = new Stage();
//            stage.setResizable(false);
//            stage.setTitle("LeftOver Food Management System - LogIn");
//            stage.setScene(scene);
//            prevStage.close();
//            stage.show();
//        }
//        catch(Exception e) {
//            System.out.println(e);
//        }
//
//    }
//
//    @FXML
//    void signOut(ActionEvent event) {
//    	
//
//}
