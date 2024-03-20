class ShoppingCart {

    // New class to represent the shopping cart
    private String listOfProduct;

    public ShoppingCart(String listOfProduct) {
        this.listOfProduct = listOfProduct;
    }

    public String getListOfProduct() {
        return listOfProduct;
    }

    public void setListOfProduct(String listOfProduct) {
        this.listOfProduct = listOfProduct;
    }

    // Additional methods for ShoppingCart class
    public void addProduct(String product) {
        if (listOfProduct.isEmpty()) {
            listOfProduct = product;
        } else {
            listOfProduct += "," + product;
        }
    }

    public void removeProduct(String product) {
        String[] products = listOfProduct.split(",");
        StringBuilder updatedList = new StringBuilder();

        for (String p : products) {
            if (!p.equals(product)) {
                if (updatedList.length() > 0) {
                    updatedList.append(",");
                }
                updatedList.append(p);
            }
        }

        listOfProduct = updatedList.toString();
    }
// toString method
    @Override
    public String toString() {
        return "ShoppingCart{" +
                "listOfProduct='" + listOfProduct + '\'' +
                '}';
    }
}