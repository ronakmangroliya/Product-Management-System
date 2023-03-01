import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

//add products gui class
public class AddProducts extends JFrame implements ActionListener {

    //variables declaration for GUI
    private JLabel productIdLabel, productNameLabel, productDescLabel, productQtyLabel;
    private JTextField productIdField, productNameField, quantityField, unitPriceField;
    private JTextArea productDescField;
    private JButton addButton, firstButton, previousButton, nextButton, lastButton, updateButton;
    private ArrayList<Product> products = new ArrayList<>();
    private int currentIndex;
    JFrame frame = new JFrame("Add/Update Product");

    //file path
    private static final String filepath = "src/products.dat";

    //add products method
    public AddProducts() {
        JPanel panel = new JPanel();

        // set layout of panel to 5 rows, 1 column with no horizontal gap
        panel.setLayout(new GridLayout(5, 1, 0, 0));

        // first row for product_id
        productIdLabel = new JLabel("Product ID");
        productIdField = new JTextField(10);
        JPanel firstRow = new JPanel();
        firstRow.setLayout(new FlowLayout(FlowLayout.LEFT));
        firstRow.setBorder(new EmptyBorder(15, 15, 0, 0));
        firstRow.add(productIdLabel);
        firstRow.add(productIdField);
        panel.add(firstRow);

        // second row for product_name
        productNameLabel = new JLabel("Name");
        productNameLabel.setBorder(new EmptyBorder(0,0,0,30));
        productNameField = new JTextField(10);
        JPanel secondRow = new JPanel();
        secondRow.setLayout(new FlowLayout(FlowLayout.LEFT));
        secondRow.setBorder(new EmptyBorder(0, 15, 0, 0));
        secondRow.add(productNameLabel);
        secondRow.add(productNameField);
        panel.add(secondRow);

        // third row for product_description
         productDescLabel = new JLabel("Description");
        productDescField = new JTextArea(5, 15);
        JPanel leftPart = new JPanel();
        leftPart.setLayout(new FlowLayout(FlowLayout.LEFT));
        leftPart.add(productDescLabel);
        leftPart.add(productDescField);

         productQtyLabel = new JLabel("Quantity in hand");
        quantityField = new JTextField(10);
        JLabel label5 = new JLabel("Unit price");
        label5.setBorder(new EmptyBorder(0,0,0,35));
        unitPriceField = new JTextField(10);

        JPanel rightTop = new JPanel();
        rightTop.setLayout(new FlowLayout(FlowLayout.LEFT));
        rightTop.add(productQtyLabel);
        rightTop.add(quantityField);

        JPanel rightBottom = new JPanel();
        rightBottom.setLayout(new FlowLayout(FlowLayout.LEFT));
        rightBottom.add(label5);
        rightBottom.add(unitPriceField);

        JPanel rightPart = new JPanel();
        rightPart.setLayout(new GridLayout(2, 1, 0, 0));
        rightPart.add(rightTop);
        rightPart.add(rightBottom);

        JPanel thirdRow = new JPanel();
        thirdRow.setLayout(new GridLayout(1, 2, 0, 0));
        thirdRow.setBorder(new EmptyBorder(0, 15, 0, 0));
        thirdRow.add(leftPart);
        thirdRow.add(rightPart);
        panel.add(thirdRow);

        // forth row for product add/update
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        JPanel forthRow = new JPanel();
        forthRow.setLayout(new FlowLayout(FlowLayout.LEFT));
        forthRow.setBorder(new EmptyBorder(15, 15, 0, 0));
        forthRow.add(addButton);
        forthRow.add(updateButton);
        panel.add(forthRow);

        addButton.addActionListener(this);
        updateButton.addActionListener(this);

        // fifth row for first,previous,next,last
        firstButton = new JButton("First");
        previousButton = new JButton("Previous");
        nextButton = new JButton(" Next");
        lastButton = new JButton("Last");
        JPanel fifthRow = new JPanel();
        fifthRow.setLayout(new FlowLayout(FlowLayout.LEFT));
        fifthRow.setBorder(new EmptyBorder(0, 15, 0, 0));
        fifthRow.add(firstButton);
        fifthRow.add(previousButton);
        fifthRow.add(nextButton);
        fifthRow.add(lastButton);
        panel.add(fifthRow);

        firstButton.addActionListener(this);
        lastButton.addActionListener(this);
        previousButton.addActionListener(this);
        nextButton.addActionListener(this);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(650, 375);
        frame.setResizable(true);
    }


    //action performed method for all buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            //here i used try catch block to get all products fields
            try {
                int productId = Integer.parseInt(productIdField.getText());
                String productName = productNameField.getText();
                String productDescription = productDescField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                double unitPrice = Double.parseDouble(unitPriceField.getText());


                //checking for all fields i used if condition and display the message
                if (productId <= 0 || productName.isEmpty() || productDescription.isEmpty() || quantity <= 0 || unitPrice <= 0) {
                    JOptionPane.showMessageDialog(this, "Please enter valid information for all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // here i used for loop to check unique id for product
                for (int i = 0; i < products.size(); i++) {
                    Product product = products.get(i);
                    if (product.getProductId() == productId) {
                        JOptionPane.showMessageDialog(this, "Product ID already exists. Please enter a unique ID.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                //create object of product
                Product product = new Product(productId, productName, productDescription, quantity, unitPrice);
                products.add(product);
                writeToFile();
                readFile();
                JOptionPane.showMessageDialog(this, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } catch (NumberFormatException | IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid information for all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
       //getting the first product details
        else if (e.getSource() == firstButton) {
            if (!products.isEmpty()) {
                currentIndex = 0;
                displayProductDetails(currentIndex);
            } else {
                JOptionPane.showMessageDialog(this, "No products found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        //getting the previous product details
        else if (e.getSource() == previousButton) {
            if (!products.isEmpty() && currentIndex > 0) {
                currentIndex--;
                displayProductDetails(currentIndex);
            } else {
                JOptionPane.showMessageDialog(this, "No previous product found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        //getting the next product details
        else if (e.getSource() == nextButton) {
            if (!products.isEmpty() && currentIndex < products.size() - 1) {
                currentIndex++;
                displayProductDetails(currentIndex);
            } else {
                JOptionPane.showMessageDialog(this, "No next product found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        //getting the last product details
        else if (e.getSource() == lastButton) {
            if (!products.isEmpty()) {
                currentIndex = products.size() - 1;
                displayProductDetails(currentIndex);
            } else {
                JOptionPane.showMessageDialog(this, "No products found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        //update button
        else if (e.getSource() == updateButton) {
            if (!products.isEmpty()) {

                //try catch block for update
                try {
                    int productId = Integer.parseInt(productIdField.getText());
                    String productName = productNameField.getText();
                    String productDescription = productDescField.getText();
                    int quantity = Integer.parseInt(quantityField.getText());
                    double unitPrice = Double.parseDouble(unitPriceField.getText());

                    if (productId <= 0 || productName.isEmpty() || productDescription.isEmpty() || quantity <= 0 || unitPrice <= 0) {
                        JOptionPane.showMessageDialog(this, "Please enter valid information for all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Product product = products.get(currentIndex);
                    product.setProductId(productId);
                    product.setProductName(productName);
                    product.setDescription(productDescription);
                    product.setQuantity(quantity);
                    product.setPrice(unitPrice);
                    writeToFile();
                    readFile();
                    JOptionPane.showMessageDialog(this, "Product updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException | IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter valid information for all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }

    //method implementation for displaying product details
    private void displayProductDetails(int currentIndex) {
        Product product = products.get(currentIndex);
        productIdField.setText(String.valueOf(product.getProductId()));
        productNameField.setText(product.getProductName());
        productDescField.setText(product.getDescription());
        quantityField.setText(String.valueOf(product.getQuantity()));
        unitPriceField.setText(String.valueOf(product.getPrice()));

    }

    //writing the file
    private void writeToFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(products);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //reading the file
    private void readFile() throws IOException, ClassNotFoundException {

        // Load the data from the binary file
        File file = new File(filepath);
        ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
        ArrayList<Product> products = (ArrayList<Product>) input.readObject();
        input.close();

        // Print the contents of the ArrayList to the console
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            System.out.println("Product ID: " + product.getProductId());
            System.out.println("Product Name: " + product.getProductName());
            System.out.println("Product Description: " + product.getDescription());
            System.out.println("Quantity: " + product.getQuantity());
            System.out.println("Unit Price: " + product.getPrice());
            System.out.println();
        }

    }

    //clearing all fields after adding products
    private void clearFields () {
        productIdField.setText("");
        productNameField.setText("");
        productDescField.setText("");
        quantityField.setText("");
        unitPriceField.setText("");
    }

    //product model class
    class Product implements java.io.Serializable {

        //variables declaration
        private int productId;
        private String productName;
        private String description;
        private int quantity;
        private double price;

        //parameterized Constructor
        public Product(int productId, String productName, String description, int quantity, double price) {
            this.productId = productId;
            this.productName = productName;
            this.description = description;
            this.quantity = quantity;
            this.price = price;
        }

        // here all getter and setter methods
        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }


        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

    }
}
