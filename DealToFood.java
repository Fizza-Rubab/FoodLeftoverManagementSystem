package application;

public class DealToFood extends FoodsData {
	// Deal object
	private DealData deal = null;
	// Copy Constructor
	public DealToFood (DealData d) {
		super(d.getId(), d.getName(), d.getPrice(), "", 1, "");

		String dealDesc = " includes ";
		for (int i=0; i<d.getItems().size()-1; i++) {
			dealDesc += d.getItems().get(i) + ", ";
		}
		dealDesc += d.getItems().get(d.getItems().size()-1);
		super.setDesc(dealDesc);
		deal = d;
	}
	// Constructor to construct the deal with amount and description as items
	public DealToFood (DealData d, int amount) {
		super(d.getId(), d.getName(), d.getPrice(), "", 1, "");

		String dealDesc = " includes ";
		for (int i=0; i<d.getItems().size()-1; i++) {
			dealDesc += d.getItems().get(i) + ", ";
		}
		dealDesc += d.getItems().get(d.getItems().size()-1);
		super.setDesc(dealDesc);
		super.setAmount(amount);
		deal = d;
	}
	
	// Getters and Setters for private attributes
	public String getName(){
		return super.getName();
	}
	public int getId() {
		return super.getId();
	}
	public void setName(String fname){
		super.setName(fname);;
	}
	public String getImage(){
		return super.getImage();
	}
	public void setImage(String img){
		super.setImage(img);;
	}
	public Float getPrice(){
		return super.getPrice();
	}
	public void setPrice(Float pr){
		super.setPrice(pr);
	}
	public int getStock(){
		return super.getStock();
	}
	public void setStock(int Stock){
		super.setStock(Stock);
	}
	public void setAmount(int a) {
		super.setAmount(a);
	}
	public int getAmount() {
		return super.getAmount();
	}
	public String getDesc(){
		return super.getDesc();
	}
	public void setDesc(String desc){
		super.setDesc(desc);
	}
	public int getType() {
		return 1;
	}
	public DealData getDeal() {
		return this.deal;
	}
}