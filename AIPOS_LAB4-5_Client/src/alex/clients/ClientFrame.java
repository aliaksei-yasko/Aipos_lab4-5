package alex.clients;

import alex.classes.Weapon;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Alexei Yasko
 * Class that provide grafical user interface for manipulation
 * whis the data and cooperation whit the service
 */
public class ClientFrame extends JFrame{

    private JTable resultTable;
    private DefaultTableModel resultTabelModel;
    private JButton viewAllButton;
    private JButton saveChangesButton;
    private JButton deleteButton;
    private JButton newButton;
    private JButton setEndPointButton;
    private JRadioButton rpcRadioButton;
    private JRadioButton restfullRadioButton;
    private ButtonGroup serviceRadioGroup;
    private JTextArea textServiceMessage;

    private JTextField nameTextField;
    private JTextField typeTextField;
    private JTextField endPointTextField;
    private JFormattedTextField weightTextField;
    private JFormattedTextField lengthTextField;
    private JFormattedTextField caliberTextField;
    private JFormattedTextField bulletSpeadTextField;

    private Performer performer;
    private Weapon[] currentWeapons = null;
    private boolean newAdded = false;

    /** Constructor */
    public ClientFrame(){
        this.createCommonTable();
        this.initComponent();

        performer = new RestfulPerformer();
        endPointTextField.setText(performer.getEndPoint());

        this.setSize(900, 500);
        this.setLocation(new Point(200, 200));
        this.setTitle("Web Client");
    }

    /**
     * Function that initiate frame component
     */
    private void initComponent(){
        saveChangesButton = new JButton("Save changes");
        saveChangesButton.addActionListener(new SaveActionHandler());
        viewAllButton = new JButton("View all");
        viewAllButton.setMaximumSize(saveChangesButton.getPreferredSize());
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new DeleteActionHandler());
        deleteButton.setMaximumSize(saveChangesButton.getPreferredSize());
        newButton = new JButton("New");
        newButton.addActionListener(new NewActionHandler());
        newButton.setMaximumSize(saveChangesButton.getPreferredSize());
        viewAllButton.addActionListener(new ViewAllActionHandler());
        NumberFormat number = NumberFormat.getNumberInstance();
        setEndPointButton = new JButton("Set endpoint");
        setEndPointButton.addActionListener(new SetEndPointHandler());
        setEndPointButton.setMaximumSize(saveChangesButton.getPreferredSize());

        rpcRadioButton = new JRadioButton("RPC service");
        rpcRadioButton.addActionListener(new RadioButtonHandler());
        restfullRadioButton = new JRadioButton("RESTfull service");
        restfullRadioButton.addActionListener(new RadioButtonHandler());
        restfullRadioButton.setSelected(true);
        serviceRadioGroup = new ButtonGroup();
        serviceRadioGroup.add(restfullRadioButton);
        serviceRadioGroup.add(rpcRadioButton);
        Box radioButtonBox = Box.createVerticalBox();
        radioButtonBox.add(restfullRadioButton);
        radioButtonBox.add(Box.createVerticalStrut(10));
        radioButtonBox.add(rpcRadioButton);
        radioButtonBox.add(Box.createVerticalStrut(10));
        radioButtonBox.setBorder(BorderFactory.createTitledBorder("Services"));
        
        nameTextField = new JTextField();
        nameTextField.setEditable(false);
        typeTextField = new JTextField();
        lengthTextField = new JFormattedTextField(number);
        lengthTextField.setInputVerifier(new FormattesTextFieldVerifier());
        lengthTextField.setValue(0);
        weightTextField = new JFormattedTextField(number);
        weightTextField.setValue(0);
        weightTextField.setInputVerifier(new FormattesTextFieldVerifier());
        caliberTextField = new JFormattedTextField(number);
        caliberTextField.setValue(0);
        caliberTextField.setInputVerifier(new FormattesTextFieldVerifier());
        bulletSpeadTextField = new JFormattedTextField(number);
        bulletSpeadTextField.setInputVerifier(new FormattesTextFieldVerifier());
        bulletSpeadTextField.setValue(0);
        endPointTextField = new JTextField();

        JLabel nameLabel = new JLabel("Название: ");
        JLabel typeLabel = new JLabel("Тип: ");
        JLabel weightLabel = new JLabel("Масса,г: ");
        JLabel lengthLabel = new JLabel("Длина,мм: ");
        JLabel caliberLabel = new JLabel("Калибр,мм: ");
        JLabel bulletSpeadLabel = new JLabel("Скорость пули,м/с: ");

        GridLayout gridLayout = new GridLayout(2, 6, 10, 10);

        JPanel textBoxPanel = new JPanel();

        Border textBoxBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10));

        textBoxPanel.setBorder(textBoxBorder);
        textBoxPanel.setLayout(gridLayout);
        textBoxPanel.add( nameLabel);
        textBoxPanel.add(nameTextField);
        textBoxPanel.add(typeLabel);
        textBoxPanel.add(typeTextField);
        textBoxPanel.add(weightLabel);
        textBoxPanel.add(weightTextField);
        textBoxPanel.add(lengthLabel);
        textBoxPanel.add(lengthTextField);
        textBoxPanel.add(caliberLabel);
        textBoxPanel.add(caliberTextField);
        textBoxPanel.add(bulletSpeadLabel);
        textBoxPanel.add(bulletSpeadTextField);

        textServiceMessage = new JTextArea(6, 30);
        textServiceMessage.setEditable(false);

        JScrollPane scrolPaneTabel = new JScrollPane(resultTable);
        JScrollPane scrollPaneText = new JScrollPane(textServiceMessage);

        Box mainBox = Box.createHorizontalBox();
        Box tableTextBox = Box.createVerticalBox();
        Box buttonBox = Box.createVerticalBox();

        tableTextBox.add(Box.createVerticalStrut(10));
        tableTextBox.add(endPointTextField);
        tableTextBox.add(Box.createVerticalStrut(10));
        tableTextBox.add(scrolPaneTabel);
        tableTextBox.add(Box.createVerticalStrut(10));
        tableTextBox.add(textBoxPanel);
        tableTextBox.add(Box.createVerticalStrut(10));
        tableTextBox.add(scrollPaneText);

        buttonBox.add(radioButtonBox);
        buttonBox.add(Box.createVerticalStrut(20));
        buttonBox.add(setEndPointButton);
        buttonBox.add(Box.createVerticalStrut(20));
        buttonBox.add(viewAllButton);
        buttonBox.add(Box.createVerticalStrut(20));
        buttonBox.add(saveChangesButton);
        buttonBox.add(Box.createVerticalStrut(20));
        buttonBox.add(newButton);
        buttonBox.add(Box.createVerticalStrut(20));
        buttonBox.add(deleteButton);

        mainBox.add(tableTextBox);
        mainBox.add(Box.createHorizontalStrut(10));
        mainBox.add(buttonBox);
        mainBox.add(Box.createHorizontalStrut(10));

        this.add(mainBox);
    }
    /** Creation table for browse information */
    private void createCommonTable(){
        String[] columnNames = new String[]{
            "Название",
            "Тип",
            "Масса,г",
            "Длина,мм",
            "Калибр,мм",
            "Скорость пули,м/с"
        };

        /* Forbid cell edditing */
        resultTabelModel = new DefaultTableModel(columnNames, 0)
        {
            @Override
            public boolean isCellEditable(int row, int col)
            {
                return false;
            }
        };

        resultTable = new JTable(resultTabelModel);
        resultTable.setAutoCreateRowSorter(true);
        resultTable.getSelectionModel().addListSelectionListener(new ListSelectionHandler());
    }

    /**
     * This function return array of string
     * that's include all atributes of weapon
     * @param weapon current weapon object
     * @return result array
     */
    private Object[] getWeaponObjects(Weapon weapon){
        Object[] objects = new Object[]{
            weapon.getName(),
            weapon.getType(),
            weapon.getWeight(),
            weapon.getLength(),
            weapon.getCaliber(),
            weapon.getSpeadOfTheBullet()
        };
        return objects;
    }

    /** Function that create records in the table model and
     * display information in the table
     * @param weapons array of weapon objects
     */
    private void displayWeaponsInTable(Weapon[] weapons){
        if(weapons == null) return;
        /* Clear all table */
        while (resultTabelModel.getRowCount() > 0){
            resultTabelModel.removeRow(0);
        }
        /* Add records to the table */
        for(Weapon weapon : weapons){
            resultTabelModel.addRow(this.getWeaponObjects(weapon));
        }
    }

    /**
     * Handler for setting endpoint string
    */
    private class SetEndPointHandler implements ActionListener{

        /**
         * This function set performer endpoint
         * @param event event object
        */
        @Override
        public void actionPerformed(ActionEvent e) {
            performer.setEndPoint(endPointTextField.getText());
            JOptionPane.showMessageDialog(null, "Конечная точка была установлена",
                                "Информация", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Handler for choise type of service
    */
    private class RadioButtonHandler implements ActionListener{

        /**
         * This function create nesessary performer object
         * @param event event object
        */
        @Override
        public void actionPerformed(ActionEvent e) {
            JRadioButton radioButton = (JRadioButton)e.getSource();
            if (radioButton.getText().equals("RESTfull service")) {
                performer = new RestfulPerformer();
                endPointTextField.setText(performer.getEndPoint());
                return;
            }
            if (radioButton.getText().equals("RPC service")) {
                performer = new RPCPerformer();
                endPointTextField.setText(performer.getEndPoint());
                return;
            }

        }

    }

    /**
     * Handler for clicking on "viewAllButton"
    */
    private class ViewAllActionHandler implements ActionListener{
        /**
         * This function getting information
         * from service and display them into table
         * @param event event object
        */
        @Override
        public void actionPerformed(ActionEvent event){
            try {
                /* Perfom request */
                setCursor(new Cursor(Cursor.WAIT_CURSOR));
                Weapon[] weapons = performer.getAllInformation();
                if (weapons == null){
                    JOptionPane.showMessageDialog(null, "Ошибка при просмотре на стороне сервиса",
                                "Просмотр", JOptionPane.ERROR_MESSAGE);
                    textServiceMessage.append("Service can't connect to data base.\n");
                    return;
                }
                else textServiceMessage.append("Ok\n");

                currentWeapons = weapons;

                displayWeaponsInTable(weapons);

            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(null, "Ошибка подключения",
                                "Ошибка", JOptionPane.ERROR_MESSAGE);
                textServiceMessage.append(ex.getLocalizedMessage() + "\n");
            }
            finally{
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

        }
    }

    /**
     * Handler for clicking on "deleteButton"
    */
    private class DeleteActionHandler implements ActionListener{

        /** Function that delete nesessary weapon
         * from service base
         * @param e event object
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (true){
                try {
                    setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    newAdded = false;
                    Weapon deletedWeapon = new Weapon();
                    deletedWeapon.setName(nameTextField.getText());
                    boolean result = performer.deleteWeapon(deletedWeapon);
                    if (result == true){
                        JOptionPane.showMessageDialog(null, "Оружие успешно удалено",
                                "Удаление", JOptionPane.INFORMATION_MESSAGE);
                        textServiceMessage.append("Record was deleted.\n");
                    } else{
                        JOptionPane.showMessageDialog(null, "Ошибка в удалении на стороне сервиса",
                                "Удаление", JOptionPane.ERROR_MESSAGE);
                        textServiceMessage.append("Wasn't deleted. Error in service.\n");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка подключения",
                                "Ошибка", JOptionPane.ERROR_MESSAGE);
                    textServiceMessage.append(ex.getLocalizedMessage() + "\n");
                } finally{
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

            } else{
                 JOptionPane.showMessageDialog(null, "Удаляемого оружия не существует.",
                                "Ошибка", JOptionPane.ERROR_MESSAGE);
                 textServiceMessage.append("Wasn't deleted.\n");
            }
        }
    }

    /**
     * Handler for clicking on "deleteButton"
    */
    private class NewActionHandler implements ActionListener{

        /** Function that allow create new weapon
         * @param e event object
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            nameTextField.setEditable(true);
            newAdded = true;
        }

    }

    /**
     * Handler for clicking on "saveButton"
    */
    private class SaveActionHandler implements ActionListener{

        /**
         * Function that provide saving data that was changed
         * and saving added weapon in the service base
         * @param e event object
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            /* Verify input data */
            if (nameTextField.getText().equals("") || typeTextField.getText().equals("")
                    || weightTextField.getValue().toString().equals("0")
                    || lengthTextField.getValue().toString().equals("0")
                    || caliberTextField.getValue().toString().equals("0")
                    || bulletSpeadTextField.getValue().toString().equals("0")){
                JOptionPane.showMessageDialog(null, "Введены некоректные данные.",
                                "Ошибка", JOptionPane.ERROR_MESSAGE);
                textServiceMessage.append("Incorrect save.\n");
                return;
            }

            /* Create weapon object */
            Weapon updateWeapon = new Weapon();
            updateWeapon.setName(nameTextField.getText());
            updateWeapon.setType(typeTextField.getText());
            updateWeapon.setWeight(Double.parseDouble(weightTextField.getValue().toString()));
            updateWeapon.setLength(Double.parseDouble(lengthTextField.getValue().toString()));
            updateWeapon.setCaliber(Double.parseDouble(caliberTextField.getValue().toString()));
            updateWeapon.setSpeadOfTheBullet(Double.parseDouble(bulletSpeadTextField.getValue().toString()));

            if(newAdded == true){
                /* Added new weapon in service base */
                try {
                    setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    boolean result = performer.addNewWeapon(updateWeapon);
                    if (result){
                        JOptionPane.showMessageDialog(null, "Оружие успешно добавлено",
                                "Добавление", JOptionPane.INFORMATION_MESSAGE);
                        textServiceMessage.append("Record was added.\n");
                    } else{
                        JOptionPane.showMessageDialog(null, "Ошибка в добавлении на стороне сервиса",
                                "Добавление", JOptionPane.ERROR_MESSAGE);
                        textServiceMessage.append("Wasn't added. Error in service.\n");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка подключения",
                                "Ошибка", JOptionPane.ERROR_MESSAGE);
                    textServiceMessage.append(ex.getLocalizedMessage() + "\n");
                } finally{
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            } else{
                /* Update recorn in the service base */
                 try {
                    setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    boolean result = performer.updateWeapon(updateWeapon);
                    if (result){
                        JOptionPane.showMessageDialog(null, "Запись успешно обновлена",
                                "Обновление", JOptionPane.INFORMATION_MESSAGE);
                        textServiceMessage.append("Record was updated.\n");
                    } else{
                        JOptionPane.showMessageDialog(null, "Ошибка в обновлении на стороне сервиса",
                                "Обновление", JOptionPane.ERROR_MESSAGE);
                        textServiceMessage.append("Wasn't updated. Error in service.\n");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка подключения",
                                "Ошибка", JOptionPane.ERROR_MESSAGE);
                    textServiceMessage.append(ex.getLocalizedMessage() + "\n");
                } finally{
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            newAdded = false;
            nameTextField.setEditable(false);
        }

    }

    /**
     * Handler for changed selection in the table list
    */
    private class ListSelectionHandler implements ListSelectionListener{

        /**
         * Function that performed when selection in the table changed
         * @param e event object
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {
            int selectedRow = resultTable.getSelectedRow();

            if(selectedRow == -1) return;
            newAdded = false;
            /* Set value in text field */
            nameTextField.setEditable(false);
            nameTextField.setText(resultTable.getValueAt(selectedRow, 0).toString());
            typeTextField.setText(resultTable.getValueAt(selectedRow, 1).toString());
            weightTextField.setValue(resultTable.getValueAt(selectedRow, 2));
            lengthTextField.setValue(resultTable.getValueAt(selectedRow, 3));
            caliberTextField.setValue(resultTable.getValueAt(selectedRow, 4));
            bulletSpeadTextField.setValue(resultTable.getValueAt(selectedRow, 5));
            textServiceMessage.append("Selection performed\n");
        }
    }

    /**
     * Handler for verifying input formated data
    */
    private class FormattesTextFieldVerifier extends InputVerifier{

        /**
         * Function that verify input data in text field
         * @param input verifying component
         * @return result verifying value
         */
        @Override
        public boolean verify(JComponent input) {
            JFormattedTextField textF = (JFormattedTextField)input;
            if(textF.isEditValid()){
                textF.setBackground(Color.WHITE);
                return true;
            }else{
                textF.setBackground(Color.pink);
                return false;
            }
        }

    }
}

