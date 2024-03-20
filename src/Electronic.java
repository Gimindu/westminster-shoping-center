import java.io.Serializable;

public class Electronic extends Product implements Serializable {
    private String brand;
    private String warrantyPeriod;

    // Constructor
    public Electronic(String productId, String productName, int availableItems, double price, String brand,String warrantyPeriod) {
        super(productId, productName, availableItems, price,"Electronic");
        this.brand = brand;
        this.warrantyPeriod=warrantyPeriod;
    }



    // Getter and Setter for Electronics-specific property
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    // Method to return a String with all the information about a product
    @Override
    public String information() {
        return "brand:-" + brand  +
                ", warrantyPeriod:-" + warrantyPeriod;
    }

    @Override
    public Product clone() {
        return (Product) new Electronic(productId, productName, availableItems, price, brand,warrantyPeriod);
    }


    @Override
    public String toString() {
        return "Electronic:-{" +

                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", availableItems=" + availableItems +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                ", warrantyPeriod=" + warrantyPeriod +
                '}';
    }
}
