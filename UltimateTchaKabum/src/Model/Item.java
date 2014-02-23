package Model;

public class Item {

	private int Id;
	private String Name;
	private String Description;
	private int Price;

	// private boolean IsVIP;

	public Item(int id, String name, String description, int price) {
		Id = id;
		Name = name;
		Description = description;
		Price = price;
	}

	// -----------------------------------------------
	// Getters and Setters
	// -----------------------------------------------

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getPrice() {
		return Price;
	}

	public void setPrice(int price) {
		Price = price;
	}

}
