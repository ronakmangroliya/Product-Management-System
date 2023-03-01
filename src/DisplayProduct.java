import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

// class for display product gui
public class DisplayProduct implements ActionListener {

    //variables declaration
    JRadioButton keywordButton, priceButton, allButton;
    JFrame frame = new JFrame("Find/Display Product");
    // Define instance variables for UI components, file path, and product list
    private JTextField toField, fromField, keywordField;
    private JTable table;
    private JButton findButton;
    private String filePath = "src/products.dat"; // Default file path
    private ArrayList<AddProducts.Product> productList;

    // displaying the product method
    public DisplayProduct() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(600, 400);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 0, 0, 20));

        ButtonGroup group = new ButtonGroup();

        // First panel for price range
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0));
        priceButton = new JRadioButton("Price Range");
        group.add(priceButton);
        pricePanel.add(priceButton);

        toField = new JTextField("to", 10);
        pricePanel.add(toField);

        fromField = new JTextField("from", 10);
        pricePanel.add(fromField);

        findButton = new JButton("Find/Display");
        pricePanel.add(findButton);

        findButton.addActionListener(this);
        mainPanel.add(pricePanel);

        // Second panel for keyword
        JPanel keywordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0));
        keywordButton = new JRadioButton("Keyword");
        keywordButton.setBorder(new EmptyBorder(0,4,0,25));
        group.add(keywordButton);
        keywordPanel.add(keywordButton);

        keywordField = new JTextField("keyword", 10);
        keywordPanel.add(keywordField);

        mainPanel.add(keywordPanel);

        // Third panel all products
        JPanel allPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0));
        allButton = new JRadioButton("All");
        group.add(allButton);
        allPanel.add(allButton);

        JPanel displayPanel = new JPanel();
        displayPanel.setBorder(new EmptyBorder(10,20,0,0));
        displayPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 0));
        table = new JTable();
        table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Product Id","Product Name",  "Description", "Quantity","UnitPrice" }));
        JScrollPane scrollPane = new JScrollPane();

        scrollPane.setViewportView(table);
        displayPanel.add(scrollPane);
        mainPanel.add(allPanel);
        mainPanel.add(displayPanel);
        productList = readAllProductsFromFile();

        frame.add(mainPanel);
        frame.setVisible(true);
        frame.setResizable(false);

    }

    // table model to add a fetch product data into rows using arraylist
    private void displayProductList(ArrayList<AddProducts.Product> filteredProducts) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = 0; i < filteredProducts.size(); i++) {
            AddProducts.Product product = filteredProducts.get(i);

            // Display data in Jtable row
            Object[] row = {product.getProductId(), product.getProductName(), product.getDescription(), product.getQuantity(), product.getPrice()};
            model.addRow(row);
        }

        table.setModel(model);
    }

    //method for filtering the product by price range
    public ArrayList<AddProducts.Product> filterProductsByPriceRange(int fromPrice, int toPrice) {
        ArrayList<AddProducts.Product> filteredProducts = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        System.out.println(productList +"productList");
        for (int i = 0; i < productList.size(); i++) {
            AddProducts.Product product = productList.get(i);
            if (product.getPrice() >= toPrice && product.getPrice() <= fromPrice) {
                filteredProducts.add(product);

            }
        }

        return filteredProducts;
    }

    //filtering the products by keyword
    public ArrayList<AddProducts.Product> filterProductsByKeyword(String keyword) {
        ArrayList<AddProducts.Product> filteredProducts = new ArrayList<>();
         DefaultTableModel model = (DefaultTableModel) table.getModel();
         model.setRowCount(0);
        for (int i = 0; i < productList.size(); i++) {
            AddProducts.Product product = productList.get(i);

            if (product.getProductName().toLowerCase().contains(keyword.toLowerCase()) || product.getDescription().contains(keyword)) {
                filteredProducts.add(product);

            }
            System.out.println(filteredProducts);
        }
        return filteredProducts;
    }

    //reading the all products data from file
    public ArrayList<AddProducts.Product> readAllProductsFromFile() {
        ArrayList<AddProducts.Product> products = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            products = (ArrayList<AddProducts.Product>) ois.readObject();
            ois.close();
            fis.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return products;
    }

//action_performed action event
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == findButton) {
            System.out.println("Clicked");
            if (priceButton.isSelected()) {

                // Get the price range from the text fields
                int fromPrice = Integer.parseInt(fromField.getText());
                int toPrice = Integer.parseInt(toField.getText());

                // Filter the products by price range
                ArrayList<AddProducts.Product> products = filterProductsByPriceRange(fromPrice, toPrice);
                // Display the products in the table
                displayProductList(products);

            }  if (keywordButton.isSelected()) {
                // Get the keyword from the text field
                String keyword = keywordField.getText();

                // Filter the products by keyword
                ArrayList<AddProducts.Product> products = filterProductsByKeyword(keyword);

                System.out.println(products);
                // Display the products in the text area
                displayProductList(products);
            } if (allButton.isSelected()) {
                // Display all products in the file
                ArrayList<AddProducts.Product> products = readAllProductsFromFile();

                displayProductList(products);
            }
        }
    }
}
