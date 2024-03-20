import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Class to represent the main GUI

public class WestminsterShoppingManagerGui extends JFrame {

    // Properties
    private DefaultTableModel firstTable;
    private JTable table;
    private DetailsPanel detailsPanel;
    private Map<String, Product> productMap = new HashMap<>();
    private ArrayList<Product> productList;
    private MidSlide midSlide;

    // Constructor
    public WestminsterShoppingManagerGui() {
        setTitle("Westminster Shopping Manager");
        setSize(800, 600);
        setLayout(new BorderLayout());
        table = new JTable();
        midSlide = new MidSlide();

        

//product list
        productList = new ArrayList<>();

        for (Product product : Main.productList) {
            productList.add(product);
        }

        add(new TopSlide(), BorderLayout.NORTH);
        add(midSlide, BorderLayout.CENTER);


        detailsPanel = new DetailsPanel(table);
        add(detailsPanel, BorderLayout.SOUTH);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Set the SecondPage_Gui in DetailsPanel
        detailsPanel.setSecondPageGui(new SecondPage_Gui());
    }
//top slide
    public class TopSlide extends JPanel {
        public TopSlide() {
            setLayout(new BorderLayout());
            dropdown();
        }

        public void dropdown() {
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.CENTER));
            JLabel label = new JLabel("Select the product type");
            panel.add(label);
            String[] productTypes = {"All", "Electronics", "Cloths"};
            JComboBox<String> dropdown = new JComboBox<>(productTypes);
            panel.add(dropdown);
            JButton shoppingCartButton = new JButton("Shopping Cart");
            shoppingCartButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new SecondPage_Gui();
                        }
                    });
                }
            });
            dropdown.addActionListener(dropdownAction -> {
                String selectedProduct = (String) dropdown.getSelectedItem();
                midSlide.clearFirstTable();
                // MidSlide midSlide = new MidSlide();
                if (selectedProduct.equals("All")) {
                    firstTable.setRowCount(0);
                    for (Product product : Main.productList) {
                        Object[] rowData = {product.getProductId(), product.getProductName(), product.getProductType(),
                                product.getPrice(), product.information()};
                        midSlide.addDataToFirstTable(rowData);
                    }
                } else if (selectedProduct.equals("Electronics")) {
                    firstTable.setRowCount(0);
                    for (Product product : Main.productList) {
                        if (product.getProductType().equals("Electronic")) {
                            Object[] rowData = {product.getProductId(), product.getProductName(), product.getProductType(),
                                    product.getPrice(), product.information()};
                            midSlide.addDataToFirstTable(rowData);
                        }
                    }
                } else if (selectedProduct.equals("Cloths")) {
                    firstTable.setRowCount(0);
                    for (Product product : Main.productList) {
                        if (product.getProductType().equals("Clothing")) {
                            Object[] rowData = new Object[]{product.getProductId(), product.getProductName(), product.getProductType(),
                                    product.getPrice(), product.information()};

                            midSlide.addDataToFirstTable(rowData);
                        }
                    }
                }
            });
            panel.add(shoppingCartButton);
            add(panel, BorderLayout.NORTH);
        }
    }

    //mid slide

    public class MidSlide extends JPanel {
        private static MidSlide instance;
        public MidSlide() {
            setLayout(new BorderLayout());
            createFirstTable();
        }

        public void createFirstTable() {
            firstTable = new DefaultTableModel();
            firstTable.addColumn("Product Id");
            firstTable.addColumn("Name");
            firstTable.addColumn("Category");
            firstTable.addColumn("Price");
            firstTable.addColumn("Info");

            table.setModel(firstTable);

            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);

            // Assuming Main.productList is a valid list of Product objects
            for (Product product : Main.productList) {
                Object[] rowData = {product.getProductId(), product.getProductName(), product.getProductType(),
                        product.getPrice(), product.information()};
                addDataToFirstTable(rowData);
            }
        }

        public void addDataToFirstTable(Object[] rowData) {
            firstTable.addRow(rowData);
        }

        public void clearFirstTable() {
            while(firstTable.getRowCount() > 0){
                firstTable.removeRow(0);
            }
        }
    }
//details panel
    private class DetailsPanel extends JPanel {
        private final JPanel panel;
        JLabel selectedProductDetailsJLabel = new JLabel("Selected Product-Details");
        JLabel productIdJLabel = new JLabel("Product ID: ");
        JLabel productNameJLabel = new JLabel("Product Name: ");
        JLabel categoryJLabel = new JLabel("Category: ");
        JLabel priceJLabel = new JLabel("Price: ");
        JLabel infoJLabel = new JLabel("Info: ");
        JLabel quantityJLabel = new JLabel("Quantity: ");
        JButton AddButton = new JButton("Add to cart");

        private JTable productTable;
        private SecondPage_Gui secondPageGui;

        public DetailsPanel(JTable productTable) {
            super();
            this.productTable = productTable;
            this.panel = new JPanel();
            panel.setLayout(new GridLayout(8, 1, 0, 10));
            panel.add(selectedProductDetailsJLabel);
            panel.add(productIdJLabel);
            panel.add(productNameJLabel);
            panel.add(categoryJLabel);
            panel.add(priceJLabel);
            panel.add(infoJLabel);
            panel.add(quantityJLabel);
            panel.add(AddButton);
            updatePanel();
            add(panel);

            Timer timer = new Timer(1000, e -> SwingUtilities.invokeLater(this::updatePanel));
            timer.start();

            // Add action listener to the AddButton
            AddButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addToCart();
                }
            });
        }

        public void setSecondPageGui(SecondPage_Gui secondPage_Gui) {
            secondPageGui = secondPage_Gui;
        }

        private void updatePanel() {
            int activeRow = productTable.getSelectedRow();
            if (activeRow == -1) {
                panel.setVisible(false);
                return;
            }
            String productId = String.valueOf(productTable.getValueAt(activeRow, 0));
            String productName = String.valueOf(productTable.getValueAt(activeRow, 1));
            String category = String.valueOf(productTable.getValueAt(activeRow, 2));
            String price = String.valueOf(productTable.getValueAt(activeRow, 3));
            String info = String.valueOf(productTable.getValueAt(activeRow, 4));
            String quantity = null;

            for (Product product : productList) {
                if (product.getProductId().equals(productId)) {
                    quantity = product.getAvailableItems()+ "";
                }
            }

            productIdJLabel.setText("Product ID: " + productId);
            productNameJLabel.setText("Product Name: " + productName);
            categoryJLabel.setText("Category: " + category);
            priceJLabel.setText("Price: " + price);
            infoJLabel.setText("Info: " + info);
            quantityJLabel.setText("Quantity: " +quantity);
            panel.revalidate();
            panel.repaint();
            panel.setVisible(true);
            revalidate();
            repaint();
}
//add to cart
        private void addToCart() {
            int activeRow = productTable.getSelectedRow();
            if (activeRow == -1) {
                return;
            }
            String productId = String.valueOf(productTable.getValueAt(activeRow, 0));
            String productName = String.valueOf(productTable.getValueAt(activeRow, 1));
            String price = String.valueOf(productTable.getValueAt(activeRow, 3));
            String productType = String.valueOf(productTable.getValueAt(activeRow, 2));

            // Check if secondPageGui is initialized before adding the row
            if (secondPageGui != null) {
                secondPageGui.clearSecondTable();
                if (productMap.get(productId) != null) {
                    Product product = productMap.get(productId);
                    for(Product p : productList){
                        if(p.getProductId().equals(productId)){
                            p.setAvailableItems(p.getAvailableItems()-1);
                            if(p.getAvailableItems()<0) {
                                product.setAvailableItems(product.getAvailableItems());
                                productList.remove(p);
                            }else{
                                    product.setAvailableItems(product.getAvailableItems()+1);
                                }
                                break;
                            }
                        }


//                    product.setAvailableItems(product.getAvailableItems() + 1);
                } else {
                    Product productToBeAdded = null;
                    for (Product p : productList) {
                        if (p.getProductId().equals(productId)) {
                            productToBeAdded = p.clone();
                            p.setAvailableItems(p.getAvailableItems() - 1);
                            productToBeAdded.setAvailableItems(1);
                            productMap.put(productId, productToBeAdded);
                        }
                    }
                }
                productMap.forEach((key, product) -> {
                    System.out.println(product);
                    Object[] data = {
                            product.getProductName(),
                            product.getAvailableItems(),
                            product.getPrice() * product.getAvailableItems(),
                    };
                    secondPageGui.addToSecondTable(data);
                });

                // Calculate and update total, discounts, and final total
                secondPageGui.calculateAndSetTotals(productMap.values());
            } else {
                System.out.println("SecondPage_Gui is not initialized!");
            }
        }

//login not use it
        public void login() {
            setLocationRelativeTo(null);
            JPanel loginPanel = new JPanel();
            loginPanel.setLayout(new GridLayout(2, 2));
            JTextField usernameField = new JTextField();
            JPasswordField passwordField = new JPasswordField();
            loginPanel.add(new JLabel("Username:"));
            loginPanel.add(usernameField);
            loginPanel.add(new JLabel("Password:"));
            loginPanel.add(passwordField);

            int result = JOptionPane.showConfirmDialog(this, loginPanel, "Enter Login Information",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (!"1".equals(username) || !"1".equals(password)) {
                    JOptionPane.showMessageDialog(this, "Invalid username or password. Exiting.");
                    System.exit(0);
                }
            } else {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WestminsterShoppingManagerGui());
    }
}