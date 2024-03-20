import java.io.Serializable;

public class Clothing extends Product implements Serializable {
    private String size;
    private String color;

    public Clothing(String productId, String productName, int availableItems, double price, String size, String color) {
        super(productId, productName, availableItems, price,    "Clothing");
        this.size = size;
        this.color = color;
    }
    // Getters and Setters
    public String getColor() {
        return color;
    }

    public void setColor(String color){
        this.color=color;
    }

    public String getSize(){
        return size;
    }

    public void setSize(String size){
        this.size=size;
    }

    // Method to return a String with all the information about a product

    @Override
    public String information() {
        return "size:-" + size +
                ", color:-" + color ;
    }

    @Override
    public Product clone() {
        return (Product) new  Clothing(productId, productName, availableItems, price, size, color);
    }


    @Override
    public String toString() {
        return "Clothing:-{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", availableItems=" + availableItems +
                ", price=" + price +
                ", size=" + size +
                ", color='" + color + '\'' +
                '}';
    }
}
