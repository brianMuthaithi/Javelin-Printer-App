import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.util.ArrayList;

public class AppUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField nameField;
    private JTextField countField;
    private DefaultListModel<String> listModel;
    private ArrayList<String> names; 
    private App app; 
    
    public native String GetPAN();
    public native String PrintLine(String embline, String accno);
    public native String ResetDevice(String serial);
    public native String EjectCard();
    public native String GetPrinterStat();
    public native String GetFeederStat();
    public native int GetRibbonCount();
    public native int DeviceErrorClear();
    
    static {
        try {
            System.load("C:\\Program Files (x86)\\Technote Ltd\\Javelin\\JavelinDll.dll");
            System.load("C:\\Program Files (x86)\\Technote Ltd\\Javelin\\Javelin.dll");
            System.out.println("Lib loaded well");
        } catch (Exception e) {
            System.err.println("Issues with library loading");
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AppUI frame = new AppUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AppUI() {
        app = new App(); 
        setTitle("Javelin Printer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 550, 315);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        names = new ArrayList<>(); 
        listModel = new DefaultListModel<>();
        
        JList<String> nameList = new JList<>(listModel);
        JScrollPane scrollpane = new JScrollPane(nameList);
        scrollpane.setBounds(10, 70, 420, 128);
        contentPane.add(scrollpane);
        
        nameField = new JTextField(20);
        nameField.setBounds(98, 26, 309, 20);
        contentPane.add(nameField);
        
        JLabel nameLabel = new JLabel("ENTER NAME:");
        nameLabel.setBounds(10, 27, 78, 17);
        contentPane.add(nameLabel);
        nameLabel.setFont(new Font("Dialog", Font.BOLD, 12));
        
        JLabel countLabel = new JLabel("PRINT COUNT:");
        countLabel.setBounds(10, 50, 100, 17);
        contentPane.add(countLabel);
        countLabel.setFont(new Font("Dialog", Font.BOLD, 12));
        
        countField = new JTextField(5);
        countField.setBounds(110, 50, 40, 20);
        contentPane.add(countField);

        JButton addButton = new JButton("Add Name");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().toUpperCase();
                if (!name.isEmpty()) {
                    listModel.addElement(name);  
                    names.add(name);
                    nameField.setText("");
                    JOptionPane.showMessageDialog(null, "Name added successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Name field cannot be empty.");
                }
            }
        });
        addButton.setBounds(22, 229, 101, 23);
        contentPane.add(addButton);
        addButton.setFont(new Font("Tahoma", Font.BOLD, 11));

        JButton removeButton = new JButton("Remove From List");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = nameList.getSelectedIndex();
                if (selectedIndex != -1) {
                    listModel.remove(selectedIndex);
                    names.remove(selectedIndex);
                    JOptionPane.showMessageDialog(null, "Name removed successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Select a name to remove.");
                }
            }
        });
        removeButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        removeButton.setBounds(152, 229, 141, 23);
        contentPane.add(removeButton);
        
        JButton printButton = new JButton("Print Name");
        printButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String countText = countField.getText();
                int count;
                
                System.out.println(app.ResetDevice("0x00000001"));
                System.out.println(app.GetPrinterStat());
                System.out.println(app.GetFeederStat());
                System.out.println(app.GetRibbonCount());
                System.out.println(app.GetPAN());
                
                try {
                    count = Integer.parseInt(countText);
                    if (count < 1 || count > 20) {
                        JOptionPane.showMessageDialog(null, "Please enter a number between 1 and 20.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number for print count.");
                    return;
                }

                String selectedName = nameList.getSelectedValue();
                if(selectedName != null){
                    for(int i = 0; i <= count; i++){
                        System.out.println(app.PrintLine(selectedName, "01334546"));
                        System.out.println(selectedName);
                    }
                    System.out.println(app.EjectCard());
                }
                else{
                    JOptionPane.showMessageDialog(null, "Please select the name to be printed");
                }
            }
        });
        printButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        printButton.setBounds(332, 225, 129, 30);
        contentPane.add(printButton); 
    }
}