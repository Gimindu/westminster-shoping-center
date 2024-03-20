import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.Collection;

public class SecondPage_Gui extends JFrame {

    // Properties
    private DefaultTableModel secondTable;
    private JTable table;

    private JLabel totalLabel;
    private JLabel firstPurchaseDiscountLabel;
    private JLabel threeItemsDiscountLabel;
    private JLabel finalTotalLabel;

    private ShoppingCart shoppingCart; // New instance of ShoppingCart

    // Constructor
    public SecondPage_Gui() {
        setTitle("Shopping Cart");
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Initialize table and add it to the frame
        table = new JTable();
        createSecondTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // New panel for displaying total and discounts
        JPanel bottomPanel = new JPanel(new GridLayout(4, 1));
        totalLabel = new JLabel("Total: $0.0");
        firstPurchaseDiscountLabel = new JLabel("First Purchase Discount (10%): $0.0");
        threeItemsDiscountLabel = new JLabel("Three Items in Same Category Discount (20%): $0.0");
        finalTotalLabel = new JLabel("Final Total: $0.0");

        bottomPanel.add(totalLabel);
        bottomPanel.add(firstPurchaseDiscountLabel);
        bottomPanel.add(threeItemsDiscountLabel);
        bottomPanel.add(finalTotalLabel);

        add(bottomPanel, BorderLayout.SOUTH);

        shoppingCart = new ShoppingCart(""); // Initialize an empty ShoppingCart

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only the current window, not the entire application
        setVisible(true);
    }

    public void createSecondTable() {
        secondTable = new DefaultTableModel();
        secondTable.addColumn("Product Name");
        secondTable.addColumn("Quantity");
        secondTable.addColumn("Price");

        table.setModel(secondTable);
    }

    public void addToSecondTable(Object[] rowData) {
        secondTable.addRow(rowData);
    }

    public void clearSecondTable() {
        while (secondTable.getRowCount() > 0) {
            secondTable.removeRow(0);
        }
    }

    public void calculateAndSetTotals(Collection<Product> products) {
        double total = 0.0;
        int totalItems = 0;
        double firstPurchaseDiscount = 0.0;
        double threeItemsDiscount = 0.0;
        double finalTotal = 0.0;

        for (Product product : products) {
            total += product.getPrice() * product.getAvailableItems();
            totalItems += product.getAvailableItems();
        }

        // Apply first purchase discount (10%)
        if (totalItems > 0) {
            firstPurchaseDiscount = 0.1 * total;
        }

        // Apply three items discount (20%) if there are at least three items
        if (totalItems >= 3) {
            threeItemsDiscount = 0.2 * total;
        }

        // Calculate final total
        finalTotal = total - firstPurchaseDiscount - threeItemsDiscount;

        // Update labels in SecondPage_Gui
        setTotalLabel("Total: $" + total);
        setFirstPurchaseDiscountLabel("First Purchase Discount (10%): $" + firstPurchaseDiscount);
        setThreeItemsDiscountLabel("Three Items Discount (20%): $" + threeItemsDiscount);
        setFinalTotalLabel("Final Total: $" + finalTotal);
    }

    public void setTotalLabel(String text) {
        totalLabel.setText(text);
    }

    public void setFirstPurchaseDiscountLabel(String text) {
        firstPurchaseDiscountLabel.setText(text);
    }

    public void setThreeItemsDiscountLabel(String text) {
        threeItemsDiscountLabel.setText(text);
    }

    public void setFinalTotalLabel(String text) {
        finalTotalLabel.setText(text);
    }

    // New method to update the shopping cart in SecondPage_Gui
    public void updateShoppingCart(String listOfProduct) {
        shoppingCart.setListOfProduct(listOfProduct);
        // Additional logic can be added here if needed
    }

    // New method to get the shopping cart from SecondPage_Gui
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SecondPage_Gui secondPage = new SecondPage_Gui();

            // Example usage:
            secondPage.updateShoppingCart("Product1,Product2,Product3");
            ShoppingCart cart = secondPage.getShoppingCart();
            System.out.println("Current Shopping Cart: " + cart);
        });
    }
}