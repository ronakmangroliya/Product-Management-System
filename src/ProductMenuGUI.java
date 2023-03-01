import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//class for product menu gui
public class ProductMenuGUI extends JFrame implements ActionListener {

    //variables declaration
    private JPanel panel;
    private JLabel mainLabel;
    private JMenuBar menuBar;
    private JMenu fileMenu, productMenu;
    private JMenuItem exitMenuItem, addMenuItem, findMenuItem;


    //method for product menu gui
    public ProductMenuGUI() {
        setTitle("Product Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        mainLabel = new JLabel("Project Management System");
        mainLabel.setFont(new Font("Monospace", Font.BOLD, 22));
        mainLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalGlue());
        panel.add(mainLabel);
        panel.add(Box.createVerticalGlue());

        add(panel, BorderLayout.CENTER);

        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        productMenu = new JMenu("Product");
        menuBar.add(fileMenu);
        menuBar.add(productMenu);

        exitMenuItem = new JMenuItem("Exit");
        addMenuItem = new JMenuItem("Add/Update");
        findMenuItem = new JMenuItem("Find/Display");
        fileMenu.add(exitMenuItem);
        productMenu.add(addMenuItem);
        productMenu.add(findMenuItem);

        exitMenuItem.addActionListener(this);
        addMenuItem.addActionListener(this);
        findMenuItem.addActionListener(this);

        setJMenuBar(menuBar);
        setVisible(true);
    }

    //action event method for menu item
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitMenuItem) {
            System.exit(0);
        } else if (e.getSource() == addMenuItem) {
            new AddProducts();
        } else if (e.getSource() == findMenuItem) {
            new DisplayProduct();
        }
    }

    //main method
    public static void main(String[] args) {
        new ProductMenuGUI();
    }
}
