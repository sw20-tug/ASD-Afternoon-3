package at.tugraz.asdafternoon3.ui;

import at.tugraz.asdafternoon3.businesslogic.CleaningScheduleDAO;
import at.tugraz.asdafternoon3.businesslogic.RoommateDAO;
import at.tugraz.asdafternoon3.data.CleaningIntervall;
import at.tugraz.asdafternoon3.data.CleaningSchedule;
import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.data.Roommate;
import at.tugraz.asdafternoon3.database.DatabaseConnection;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class CleaningScheduleDialog extends JDialog {
    private JPanel contentPanel;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel pSpacer;
    private JPanel pButtons;
    private JPanel pHeader;
    private JPanel pInput;
    private JTextField tfName;
    private JTextField tfStart;
    private JTextField tfMarkable;
    private JTextField tfRoommate;
    private JComboBox<CleaningIntervall> cbIntervall;
    private JComboBox<Roommate> cbRoommate;
    private JLabel tHeader;
    private JLabel tName;
    private JLabel tStart;
    private JLabel tInterval;
    private JLabel tPerson;
    private Flat flat;

    private CleaningSchedule cleaningSchedule;
    private Boolean shouldBeChanged;

    private List<Roommate> roommateList = new ArrayList<>();
    private List<Roommate> roommateList2 = new ArrayList<>();

    public CleaningScheduleDialog(CleaningSchedule cleaning_schedule, Boolean should_be_changed, Flat flat) {
        this.cleaningSchedule = cleaning_schedule;
        this.shouldBeChanged = should_be_changed;
        this.flat = flat;
        setContentPane(contentPanel);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        initLocalizations();
        initializeComboboxes();


        if (shouldBeChanged) {
            changeCleaningSchedule();
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm");
            tfStart.setText(LocalDateTime.now().format(formatter));
        }

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void initLocalizations() {
        tHeader.setText(Localization.getInstance().getCurrent().getString("cleaningschedule.dialog.header"));
        tName.setText(Localization.getInstance().getCurrent().getString("cleaningschedule.dialog.name"));
        tInterval.setText(Localization.getInstance().getCurrent().getString("cleaningschedule.dialog.interval"));
        tPerson.setText(Localization.getInstance().getCurrent().getString("cleaningschedule.dialog.person"));
        tStart.setText(Localization.getInstance().getCurrent().getString("cleaningschedule.dialog.start"));
        buttonOK.setText(Localization.getInstance().getCurrent().getString("frame.button.ok"));
        buttonCancel.setText(Localization.getInstance().getCurrent().getString("frame.button.cancel"));
    }

    private void onOK() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm");
        LocalDateTime ldt = null;

        Roommate roommate = (Roommate) cbRoommate.getSelectedItem();
        CleaningScheduleDAO cs_dao = null;

        try {
            cs_dao = DatabaseConnection.getInstance().createDao(CleaningScheduleDAO.class);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(contentPanel, "Couldn't establish database connection");
        }


        if (cs_dao != null) {

            if (!shouldBeChanged) {

                try {
                    ldt = LocalDateTime.parse(tfStart.getText(), formatter);
                    CleaningSchedule cs = new CleaningSchedule(tfName.getText(), ldt, roommate, (CleaningIntervall) cbIntervall.getSelectedItem());

                    if (!cs_dao.validate(cs))
                        throw new Exception("Cleaning schedule item to add/modify is not valid!");

                    cs_dao.create(cs);
                } catch (DateTimeParseException e)
                {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(contentPanel, "Couldn't create cleaning schedule\n" + "Date was not valid!");
                    return;
                }
                catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(contentPanel, "Couldn't create cleaning schedule\n" + e.getMessage());
                    return;
                }

            } else {
                try {
                    ldt = LocalDateTime.parse(tfStart.getText(), formatter);
                    CleaningSchedule cs = new CleaningSchedule(tfName.getText(), ldt, roommate, (CleaningIntervall) cbIntervall.getSelectedItem());

                    cleaningSchedule.setIntervall((CleaningIntervall) cbIntervall.getSelectedItem());
                    cleaningSchedule.setName(tfName.getText());
                    cleaningSchedule.setRoommate(roommate);
                    cleaningSchedule.setStartTime(LocalDateTime.parse(tfStart.getText(), formatter));

                    if (!cs_dao.validate(cs))
                        throw new Exception("Cleaning schedule item to add/modify is not valid!");

                    cs_dao.update(cleaningSchedule);
                } catch (DateTimeParseException e)
                {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(contentPanel, "Couldn't create cleaning schedule\n" + "Date was not valid!");
                    return;
                }
                catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(contentPanel, "Couldn't create cleaning schedule\n" + e.getMessage());
                    return;
                }
            }
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();

    }

    private void initializeComboboxes() {

        try {
            roommateList = DatabaseConnection.getInstance().createDao(RoommateDAO.class).getAll();
        } catch (Exception ex) {

        }
        for (int counter = 0; counter < roommateList.size(); counter++) {
            if (roommateList.get(counter).getFlat().getId() == flat.getId()) {

                roommateList2.add(roommateList.get(counter));
            }

        }

        cbRoommate.setModel(new DefaultComboBoxModel(roommateList2.toArray()));

        ArrayList<CleaningIntervall> list = new ArrayList<>();
        list.add(CleaningIntervall.WEEKLY);
        list.add(CleaningIntervall.MONTHLY);
        //Intervall
        cbIntervall.setModel(new DefaultComboBoxModel(list.toArray()));
        cbIntervall.setSelectedItem(cleaningSchedule.getIntervall());


    }

    private void changeCleaningSchedule() {
        tfName.setText(cleaningSchedule.getName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm");
        tfStart.setText(cleaningSchedule.getStartTime().format(formatter));

        cbRoommate.setSelectedItem(cleaningSchedule.getRoommate());
        pack();
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        contentPanel.setBackground(new Color(-14078925));
        pSpacer = new JPanel();
        pSpacer.setLayout(new BorderLayout(0, 0));
        pSpacer.setBackground(new Color(-14078925));
        contentPanel.add(pSpacer, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        pButtons = new JPanel();
        pButtons.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        pButtons.setBackground(new Color(-14078925));
        pSpacer.add(pButtons, BorderLayout.CENTER);
        buttonOK = new JButton();
        buttonOK.setBackground(new Color(-12816512));
        buttonOK.setForeground(new Color(-2103318));
        buttonOK.setText("OK");
        pButtons.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setBackground(new Color(-12816512));
        buttonCancel.setForeground(new Color(-2103318));
        buttonCancel.setText("Cancel");
        pButtons.add(buttonCancel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pHeader = new JPanel();
        pHeader.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        pHeader.setBackground(new Color(-14078925));
        contentPanel.add(pHeader, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tHeader = new JLabel();
        Font tHeaderFont = this.$$$getFont$$$(null, -1, 20, tHeader.getFont());
        if (tHeaderFont != null) tHeader.setFont(tHeaderFont);
        tHeader.setForeground(new Color(-4145152));
        tHeader.setText("Test dialog");
        pHeader.add(tHeader, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pInput = new JPanel();
        pInput.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        pInput.setBackground(new Color(-14078925));
        pHeader.add(pInput, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tName = new JLabel();
        tName.setForeground(new Color(-4145152));
        tName.setText("Name");
        pInput.add(tName, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfName = new JTextField();
        tfName.setBackground(new Color(-12632257));
        tfName.setForeground(new Color(-2103318));
        pInput.add(tfName, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfStart = new JTextField();
        tfStart.setBackground(new Color(-12632257));
        tfStart.setForeground(new Color(-2103318));
        pInput.add(tfStart, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tInterval = new JLabel();
        tInterval.setForeground(new Color(-4145152));
        tInterval.setText("Intervall");
        pInput.add(tInterval, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tStart = new JLabel();
        tStart.setForeground(new Color(-4145152));
        tStart.setText("Start");
        pInput.add(tStart, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tPerson = new JLabel();
        tPerson.setForeground(new Color(-4145152));
        tPerson.setText("Person");
        pInput.add(tPerson, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbIntervall = new JComboBox();
        cbIntervall.setBackground(new Color(-12632257));
        cbIntervall.setForeground(new Color(-2103318));
        pInput.add(cbIntervall, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbRoommate = new JComboBox();
        cbRoommate.setBackground(new Color(-12632257));
        cbRoommate.setForeground(new Color(-2103318));
        pInput.add(cbRoommate, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
