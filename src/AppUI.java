import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.util.ArrayList;

public class AppUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField nameField;
    private JTextField countField;
    private DefaultListModel<String> listModel;
    private ArrayList<String> names;

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
            String workingDir = System.getProperty("user.dir");
            String dllPath1 = workingDir + "\\JavelinDll.dll";
            String dllPath2 = workingDir + "\\Javelin.dll";
            System.load(dllPath1);
            System.load(dllPath2);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to load printer drivers. Please check the DLL paths.");
            System.exit(1);
        }
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AppUI frame = new AppUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AppUI() {
        setTitle("Javelin Card Printer");
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
        addButton.addActionListener(e -> {
            String name = nameField.getText().toUpperCase();
            if (!name.isEmpty()) {
                listModel.addElement(name);
                names.add(name);
                nameField.setText("");
                JOptionPane.showMessageDialog(null, "Name added successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Name field cannot be empty.");
            }
        });
        addButton.setBounds(22, 229, 101, 23);
        contentPane.add(addButton);
        addButton.setFont(new Font("Tahoma", Font.BOLD, 11));

        JButton removeButton = new JButton("Remove From List");
        removeButton.addActionListener(e -> {
            int selectedIndex = nameList.getSelectedIndex();
            if (selectedIndex != -1) {
                listModel.remove(selectedIndex);
                names.remove(selectedIndex);
                JOptionPane.showMessageDialog(null, "Name removed successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Select a name to remove.");
            }
        });
        removeButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        removeButton.setBounds(152, 229, 141, 23);
        contentPane.add(removeButton);

        JButton printButton = new JButton("Print Name");
        printButton.addActionListener(e -> {
            String countText = countField.getText();
            int count;

            try {
                count = Integer.parseInt(countText);
                if (count > 20) {
                    JOptionPane.showMessageDialog(null, "Please enter a number below 20.");
                    countField.setText("");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for print count.");
                countField.setText("");
                return;
            }

            String selectedName = nameList.getSelectedValue();
            if (selectedName != null) {
                System.out.println(GetPrinterStat());
                System.out.println(ResetDevice("0x00000001"));
                System.out.println(GetFeederStat());
                System.out.println(GetRibbonCount());
                System.out.println(GetPAN());

                for (int i = 0; i < count; i++) {
                    System.out.println(PrintLine(selectedName, "Test Accn"));
                    System.out.println(selectedName);
                }

                System.out.println(EjectCard());
                JOptionPane.showMessageDialog(null, "Please remove the printed card from the tray.");
                nameField.setText("");
                countField.setText("");
                nameList.clearSelection();
            } else {
                JOptionPane.showMessageDialog(null, "Please select the name to be printed.");
            }
        });

        printButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        printButton.setBounds(332, 225, 129, 30);
        contentPane.add(printButton);
    }
}
