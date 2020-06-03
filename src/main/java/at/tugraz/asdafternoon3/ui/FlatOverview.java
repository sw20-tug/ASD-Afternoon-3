package at.tugraz.asdafternoon3.ui;

import at.tugraz.asdafternoon3.FlatApplication;
import at.tugraz.asdafternoon3.data.Flat;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FlatOverview {
    private JPanel contentPane;
    private JPanel main;
    private JPanel BasicOverview;
    private JPanel Navigation;
    private JButton roomMateButton;
    private JButton shoppingListButton;
    private JButton cleaningScheduleButton;
    private JButton financeFurnitureAndEquipmentButton;
    private JButton financeFlatButton;
    private JTextArea taName;
    private JTextArea taSize;
    private JTextArea taAddress;
    private JButton selectFlatButton;
    private JLabel lbTitleFlat;
    private JLabel lbName;
    private JLabel lbSize;
    private JLabel lbAddress;
    private JComboBox languageBox;
    private JButton setLanguageButton;

    private ArrayList languages;

    public FlatOverview(Flat flat) {
        languages = new ArrayList<String>();
        languages.add("Chinese");
        languages.add("German");
        languages.add("English");
        languages.add("Italian");
        languages.add("Latin");
        languages.add("Russian");
        languages.add("Spanish");
        DefaultComboBoxModel cbm = new DefaultComboBoxModel();
        cbm.addAll(languages);
        languageBox.setModel(cbm);

        initLocalizations();
        setFlatInformation(flat);


        roomMateButton.addActionListener(e ->
                FlatApplication.get().setContentPane(new RoommateOverview(flat).getContentPane()));
        selectFlatButton.addActionListener(e ->
                FlatApplication.get().setContentPane(new FlatList(flat).getContentPane()));
        financeFurnitureAndEquipmentButton.addActionListener(e ->
                FlatApplication.get().setContentPane(new FinanceOverview(flat).getContentPane()));
        financeFlatButton.addActionListener(e ->
                FlatApplication.get().setContentPane(new FinanceFlatOverview(flat).getContentPane()));
        shoppingListButton.addActionListener(e ->
                FlatApplication.get().setContentPane(new ShoppingList(flat).getContentPane()));
        cleaningScheduleButton.addActionListener(e ->
                FlatApplication.get().setContentPane(new CleaningScheduleUI(flat).getContentPane()));
        setLanguageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onSetLanguage();
            }
        });
    }

    private void onSetLanguage() {
        Localization.getInstance().setLocale((String) languageBox.getSelectedItem());
        System.out.println("Selected language: " + (String) languageBox.getSelectedItem());
        initLocalizations();
    }

    private void initLocalizations() {
        lbTitleFlat.setText(Localization.getInstance().getCurrent().getString("flatoverview.title"));
        lbName.setText(Localization.getInstance().getCurrent().getString("flatoverview.name"));
        lbSize.setText(Localization.getInstance().getCurrent().getString("flatoverview.size"));
        lbAddress.setText(Localization.getInstance().getCurrent().getString("flatoverview.address"));

        roomMateButton.setText(Localization.getInstance().getCurrent().getString("flatoverview.roommates.button"));
        shoppingListButton.setText(Localization.getInstance().getCurrent().getString("flatoverview.shoppinglist.button"));
        cleaningScheduleButton.setText(Localization.getInstance().getCurrent().getString("flatoverview.cleaningschedule.button"));
        financeFurnitureAndEquipmentButton.setText(Localization.getInstance().getCurrent().getString("flatoverview.financefurniture.button"));
        financeFlatButton.setText(Localization.getInstance().getCurrent().getString("flatoverview.finance.button"));
        selectFlatButton.setText(Localization.getInstance().getCurrent().getString("flatoverview.selectflat.button"));
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public void setFlatInformation(Flat flat) {
        taName.setText(flat.getName());
        taAddress.setText(flat.getAddress());
        taSize.setText(flat.getSize().toString());
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
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.setMaximumSize(new Dimension(400, 400));
        contentPane.setMinimumSize(new Dimension(400, 400));
        contentPane.setPreferredSize(new Dimension(1200, 750));
        main = new JPanel();
        main.setLayout(new BorderLayout(0, 0));
        contentPane.add(main, BorderLayout.CENTER);
        BasicOverview = new JPanel();
        BasicOverview.setLayout(new GridLayoutManager(8, 4, new Insets(0, 0, 0, 0), -1, -1));
        BasicOverview.setBackground(new Color(-14078925));
        BasicOverview.setMaximumSize(new Dimension(300, 300));
        BasicOverview.setMinimumSize(new Dimension(300, 300));
        BasicOverview.setPreferredSize(new Dimension(300, 300));
        main.add(BasicOverview, BorderLayout.NORTH);
        lbName = new JLabel();
        lbName.setForeground(new Color(-4145152));
        lbName.setText("Name:");
        BasicOverview.add(lbName, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(68, 18), null, 0, false));
        lbSize = new JLabel();
        lbSize.setForeground(new Color(-4145152));
        lbSize.setText("Size:");
        BasicOverview.add(lbSize, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(68, 18), null, 0, false));
        lbAddress = new JLabel();
        lbAddress.setForeground(new Color(-4145152));
        lbAddress.setText("Address:");
        BasicOverview.add(lbAddress, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(68, 18), null, 0, false));
        lbTitleFlat = new JLabel();
        lbTitleFlat.setBackground(new Color(-14078925));
        Font lbTitleFlatFont = this.$$$getFont$$$(null, -1, 28, lbTitleFlat.getFont());
        if (lbTitleFlatFont != null) lbTitleFlat.setFont(lbTitleFlatFont);
        lbTitleFlat.setForeground(new Color(-4145152));
        lbTitleFlat.setText("Flat");
        BasicOverview.add(lbTitleFlat, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        BasicOverview.add(spacer1, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 35), new Dimension(358, 35), new Dimension(-1, 35), 35, false));
        final Spacer spacer2 = new Spacer();
        BasicOverview.add(spacer2, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 10), new Dimension(358, 10), new Dimension(-1, 10), 10, false));
        final Spacer spacer3 = new Spacer();
        BasicOverview.add(spacer3, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 10), new Dimension(358, 10), new Dimension(-1, 10), 10, false));
        taName = new JTextArea();
        taName.setBackground(new Color(-12632257));
        taName.setEnabled(false);
        taName.setForeground(new Color(-2103318));
        taName.setText("");
        BasicOverview.add(taName, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(358, 50), null, 0, false));
        taSize = new JTextArea();
        taSize.setBackground(new Color(-12632257));
        taSize.setEnabled(false);
        taSize.setForeground(new Color(-2103318));
        BasicOverview.add(taSize, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(358, 50), null, 0, false));
        taAddress = new JTextArea();
        taAddress.setBackground(new Color(-12632257));
        taAddress.setEnabled(false);
        taAddress.setForeground(new Color(-2103318));
        BasicOverview.add(taAddress, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(358, 50), null, 0, false));
        final Spacer spacer4 = new Spacer();
        BasicOverview.add(spacer4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 5, false));
        final Spacer spacer5 = new Spacer();
        BasicOverview.add(spacer5, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 5, false));
        final Spacer spacer6 = new Spacer();
        BasicOverview.add(spacer6, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 5, false));
        languageBox = new JComboBox();
        languageBox.setBackground(new Color(-12632257));
        languageBox.setForeground(new Color(-2103318));
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        languageBox.setModel(defaultComboBoxModel1);
        BasicOverview.add(languageBox, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        setLanguageButton = new JButton();
        setLanguageButton.setBackground(new Color(-12816512));
        setLanguageButton.setForeground(new Color(-2103318));
        setLanguageButton.setText("Set Language");
        BasicOverview.add(setLanguageButton, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Navigation = new JPanel();
        Navigation.setLayout(new GridLayoutManager(13, 3, new Insets(0, 0, 0, 0), -1, -1));
        Navigation.setBackground(new Color(-14078925));
        main.add(Navigation, BorderLayout.CENTER);
        shoppingListButton = new JButton();
        shoppingListButton.setBackground(new Color(-12816512));
        shoppingListButton.setForeground(new Color(-2103318));
        shoppingListButton.setText("Shopping list");
        Navigation.add(shoppingListButton, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cleaningScheduleButton = new JButton();
        cleaningScheduleButton.setBackground(new Color(-12816512));
        cleaningScheduleButton.setForeground(new Color(-2103318));
        cleaningScheduleButton.setText("Cleaning schedule");
        Navigation.add(cleaningScheduleButton, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        financeFurnitureAndEquipmentButton = new JButton();
        financeFurnitureAndEquipmentButton.setBackground(new Color(-12816512));
        financeFurnitureAndEquipmentButton.setForeground(new Color(-2103318));
        financeFurnitureAndEquipmentButton.setText("Finance furniture and equipment");
        Navigation.add(financeFurnitureAndEquipmentButton, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        financeFlatButton = new JButton();
        financeFlatButton.setBackground(new Color(-12816512));
        financeFlatButton.setForeground(new Color(-2103318));
        financeFlatButton.setText("Finance flat");
        Navigation.add(financeFlatButton, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        roomMateButton = new JButton();
        roomMateButton.setBackground(new Color(-12816512));
        roomMateButton.setForeground(new Color(-2103318));
        roomMateButton.setText("Roommates");
        Navigation.add(roomMateButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        Navigation.add(spacer7, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 5, false));
        final Spacer spacer8 = new Spacer();
        Navigation.add(spacer8, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 35), new Dimension(-1, 35), new Dimension(-1, 35), 35, false));
        final Spacer spacer9 = new Spacer();
        Navigation.add(spacer9, new GridConstraints(10, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 20), new Dimension(-1, 20), new Dimension(-1, 20), 20, false));
        final Spacer spacer10 = new Spacer();
        Navigation.add(spacer10, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 20), new Dimension(-1, 20), new Dimension(-1, 20), 20, false));
        final Spacer spacer11 = new Spacer();
        Navigation.add(spacer11, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 20), new Dimension(-1, 20), new Dimension(-1, 20), 20, false));
        final Spacer spacer12 = new Spacer();
        Navigation.add(spacer12, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 20), new Dimension(-1, 20), new Dimension(-1, 20), 20, false));
        final Spacer spacer13 = new Spacer();
        Navigation.add(spacer13, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 20), new Dimension(-1, 20), new Dimension(-1, 20), 20, false));
        selectFlatButton = new JButton();
        selectFlatButton.setBackground(new Color(-12816512));
        selectFlatButton.setForeground(new Color(-2103318));
        selectFlatButton.setText("Select flat");
        Navigation.add(selectFlatButton, new GridConstraints(11, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer14 = new Spacer();
        Navigation.add(spacer14, new GridConstraints(12, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 10), new Dimension(-1, 10), new Dimension(-1, 10), 10, false));
        final Spacer spacer15 = new Spacer();
        Navigation.add(spacer15, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 5, false));
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
        return contentPane;
    }

}
