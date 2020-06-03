package at.tugraz.asdafternoon3.ui;

import at.tugraz.asdafternoon3.FlatApplication;
import at.tugraz.asdafternoon3.businesslogic.CleaningTaskCompletedDAO;
import at.tugraz.asdafternoon3.data.*;
import at.tugraz.asdafternoon3.businesslogic.CleaningScheduleDAO;
import at.tugraz.asdafternoon3.businesslogic.FlatDAO;
import at.tugraz.asdafternoon3.businesslogic.RoommateDAO;
import at.tugraz.asdafternoon3.database.DatabaseConnection;
import at.tugraz.asdafternoon3.ui.table.CleaningScheduleTableModel;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CleaningScheduleUI {
    private JPanel contentPane;
    private JPanel mainPenal;
    private JPanel headerPain;
    private JButton btBack;
    private JTable tWeekly;
    private JButton btEditMonthly;
    private JButton btAddMonthly;
    private JButton btDeleteMonthly;
    private JButton exportButton;
    private JTable tMonthly;
    private JLabel lWeeklyTableHeader;
    private JLabel lMonthlyTableHeader;
    private JButton btEditWeekly;
    private JButton btAddWeeklyOrMonthly;
    private JButton btDeleteWeekly;
    private JPanel monthlyPanel;
    private JButton btCompletedWeekly;
    private JButton btCompetedMonthly;
    private JLabel lHeader;
    private Flat currentFlat;

    private final List<CleaningSchedule> weeklyCleaningSchedules = new ArrayList<>();
    private final List<CleaningSchedule> monthlyCleaningSchedules = new ArrayList<>();
    private final List<CleaningSchedule> completedWeeklyCleaningSchedules = new ArrayList<>();
    private final List<CleaningSchedule> completedMonthlyCleaningSchedules = new ArrayList<>();
    private final List<CleaningSchedule> uncompletedWeeklyCleaningSchedules = new ArrayList<>();
    private final List<CleaningSchedule> uncompletedMonthlyCleaningSchedules = new ArrayList<>();

    private WindowListener onCloseDialogListener = new WindowListener() {
        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {
        }

        @Override
        public void windowClosed(WindowEvent e) {
            refillData();
        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    };

    public CleaningScheduleUI(Flat flat) {
        initLocalizations();
        currentFlat = flat;

        refillData();


        btEditWeekly.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                CleaningScheduleDialog dialog = new CleaningScheduleDialog(uncompletedWeeklyCleaningSchedules.get(tWeekly.getSelectedRow()), true, flat);
                dialog.setSize(300, 300);
                dialog.addWindowListener(onCloseDialogListener);
                dialog.setVisible(true);
            }
        });


        btDeleteWeekly.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CleaningSchedule cs = uncompletedWeeklyCleaningSchedules.get(tWeekly.getSelectedRow());
                CleaningScheduleDAO cs_dao = null;
                try {
                    cs_dao = DatabaseConnection.getInstance().createDao(CleaningScheduleDAO.class);
                    cs_dao.delete(cs);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(contentPane, "Couldn't establish database connection");
                }
                refillData();
            }
        });

        btBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FlatApplication.get().setContentPane(new FlatOverview(currentFlat).getContentPane());
            }
        });
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FlatApplication.get().setContentPane(new CleaningScheduleExportView(currentFlat).getContentPane());
            }
        });

        btEditMonthly.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                CleaningScheduleDialog dialog = new CleaningScheduleDialog(uncompletedMonthlyCleaningSchedules.get(tMonthly.getSelectedRow()), true, flat);
                dialog.setSize(300, 300);
                dialog.addWindowListener(onCloseDialogListener);
                dialog.setVisible(true);
            }
        });

        btAddWeeklyOrMonthly.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CleaningScheduleDialog dialog = new CleaningScheduleDialog(new CleaningSchedule(), false, flat);
                dialog.setSize(300, 300);
                dialog.addWindowListener(onCloseDialogListener);
                dialog.setVisible(true);
            }
        });

        btDeleteMonthly.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CleaningSchedule cs = uncompletedMonthlyCleaningSchedules.get(tMonthly.getSelectedRow());
                CleaningScheduleDAO cs_dao = null;
                try {
                    cs_dao = DatabaseConnection.getInstance().createDao(CleaningScheduleDAO.class);
                    cs_dao.delete(cs);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(contentPane, "Couldn't establish database connection");
                }
                refillData();
            }

        });
        btCompletedWeekly.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                CleaningTaskCompletedDAO ctc_dao = null;

                try {
                    ctc_dao = DatabaseConnection.getInstance().createDao(CleaningTaskCompletedDAO.class);
                    ctc_dao.create(new CleaningTaskCompleted(uncompletedWeeklyCleaningSchedules.get(tWeekly.getSelectedRow()), LocalDateTime.now()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(contentPane, "Couldn't establish database connection");
                }
                refillData();
            }
        });
        btCompetedMonthly.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                CleaningTaskCompletedDAO ctc_dao = null;

                try {
                    ctc_dao = DatabaseConnection.getInstance().createDao(CleaningTaskCompletedDAO.class);
                    ctc_dao.create(new CleaningTaskCompleted(uncompletedMonthlyCleaningSchedules.get(tMonthly.getSelectedRow()), LocalDateTime.now()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(contentPane, "Couldn't establish database connection");
                }
                refillData();
            }
        });
    }

    private void initLocalizations() {
        lHeader.setText(Localization.getInstance().getCurrent().getString("cleaningschedule.ui.header"));
        lWeeklyTableHeader.setText(Localization.getInstance().getCurrent().getString("cleaningschedule.ui.weekly.tableheader"));
        lMonthlyTableHeader.setText(Localization.getInstance().getCurrent().getString("cleaningschedule.ui.monthly.tableheader"));
        btAddWeeklyOrMonthly.setText(Localization.getInstance().getCurrent().getString("cleaningschedule.ui.add.button"));
        btCompletedWeekly.setText(Localization.getInstance().getCurrent().getString("cleaningschedule.ui.weekly.setcompleted.button"));
        btEditWeekly.setText(Localization.getInstance().getCurrent().getString("cleaningschedule.ui.weekly.edit.button"));
        btDeleteWeekly.setText(Localization.getInstance().getCurrent().getString("cleaningschedule.ui.weekly.delete.button"));
        btCompetedMonthly.setText(Localization.getInstance().getCurrent().getString("cleaningschedule.ui.monthly.setcompleted.button"));
        btEditMonthly.setText(Localization.getInstance().getCurrent().getString("cleaningschedule.ui.monthly.edit.button"));
        btDeleteMonthly.setText(Localization.getInstance().getCurrent().getString("cleaningschedule.ui.monthly.delete.button"));
        btBack.setText(Localization.getInstance().getCurrent().getString("frame.button.back"));
        exportButton.setText(Localization.getInstance().getCurrent().getString("cleaningschedule.ui.export.button"));
    }

    private void refillData() {
        weeklyCleaningSchedules.clear();
        completedMonthlyCleaningSchedules.clear();
        completedWeeklyCleaningSchedules.clear();
        monthlyCleaningSchedules.clear();
        uncompletedWeeklyCleaningSchedules.clear();
        uncompletedMonthlyCleaningSchedules.clear();

        try {
            CleaningScheduleDAO cleaningScheduleDAO = new CleaningScheduleDAO(DatabaseConnection.getInstance().getSessionFactory());
            RoommateDAO roommateDAO = new RoommateDAO(DatabaseConnection.getInstance().getSessionFactory());
            FlatDAO flatDAO = new FlatDAO(DatabaseConnection.getInstance().getSessionFactory());

            List<Roommate> roommates = flatDAO.getRoommates(currentFlat);

            for (Roommate roommate : roommates) {
                weeklyCleaningSchedules.addAll(roommateDAO.getCleaningSchedules(roommate, CleaningIntervall.WEEKLY));
                monthlyCleaningSchedules.addAll(roommateDAO.getCleaningSchedules(roommate, CleaningIntervall.MONTHLY));

                completedWeeklyCleaningSchedules.addAll(
                        roommateDAO.getCompletedCleaningSchedules(
                                roommate, CleaningIntervall.WEEKLY,
                                LocalDateTime.now().minusDays(LocalDateTime.now().getDayOfWeek().getValue()),
                                LocalDateTime.now().plusDays(7 - LocalDateTime.now().getDayOfWeek().getValue())
                        ));
                completedMonthlyCleaningSchedules.addAll(
                        roommateDAO.getCompletedCleaningSchedules(
                                roommate, CleaningIntervall.MONTHLY,
                                LocalDateTime.now().minusDays(LocalDateTime.now().getDayOfMonth()),
                                LocalDateTime.now().plusDays(31 - LocalDateTime.now().getDayOfMonth())
                        ));
            }
            for (CleaningSchedule cs : monthlyCleaningSchedules) {
                if (!completedMonthlyCleaningSchedules.contains(cs))
                    uncompletedMonthlyCleaningSchedules.add(cs);
            }
            for (CleaningSchedule cs : weeklyCleaningSchedules) {
                if (!completedWeeklyCleaningSchedules.contains(cs))
                    uncompletedWeeklyCleaningSchedules.add(cs);
            }

            CleaningScheduleTableModel weeklyModel = new CleaningScheduleTableModel(uncompletedWeeklyCleaningSchedules);
            tWeekly.setModel(weeklyModel);

            CleaningScheduleTableModel monthlyModel = new CleaningScheduleTableModel(uncompletedMonthlyCleaningSchedules);
            tMonthly.setModel(monthlyModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JPanel getContentPane() {
        return contentPane;
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
        contentPane.setBackground(new Color(-14078925));
        headerPain = new JPanel();
        headerPain.setLayout(new BorderLayout(0, 0));
        headerPain.setBackground(new Color(-14078925));
        contentPane.add(headerPain, BorderLayout.NORTH);
        btBack = new JButton();
        btBack.setBackground(new Color(-12816512));
        btBack.setForeground(new Color(-2103318));
        btBack.setText("Back");
        headerPain.add(btBack, BorderLayout.WEST);
        lHeader = new JLabel();
        lHeader.setBackground(new Color(-14078925));
        Font lHeaderFont = this.$$$getFont$$$(null, -1, 20, lHeader.getFont());
        if (lHeaderFont != null) lHeader.setFont(lHeaderFont);
        lHeader.setForeground(new Color(-4145152));
        lHeader.setText("Cleaning schedule");
        headerPain.add(lHeader, BorderLayout.CENTER);
        exportButton = new JButton();
        exportButton.setBackground(new Color(-12816512));
        exportButton.setForeground(new Color(-2103318));
        exportButton.setText("Export");
        headerPain.add(exportButton, BorderLayout.EAST);
        mainPenal = new JPanel();
        mainPenal.setLayout(new BorderLayout(0, 0));
        mainPenal.setBackground(new Color(-14078925));
        mainPenal.setEnabled(true);
        contentPane.add(mainPenal, BorderLayout.CENTER);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        panel1.setBackground(new Color(-14078925));
        mainPenal.add(panel1, BorderLayout.CENTER);
        tWeekly = new JTable();
        tWeekly.setBackground(new Color(-14078925));
        tWeekly.setEnabled(true);
        tWeekly.setForeground(new Color(-2103318));
        tWeekly.setGridColor(new Color(-12816512));
        panel1.add(tWeekly, BorderLayout.CENTER);
        lWeeklyTableHeader = new JLabel();
        lWeeklyTableHeader.setBackground(new Color(-14078925));
        lWeeklyTableHeader.setForeground(new Color(-4145152));
        lWeeklyTableHeader.setText("Weekly (uncompleted)");
        panel1.add(lWeeklyTableHeader, BorderLayout.NORTH);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-14078925));
        panel1.add(panel2, BorderLayout.SOUTH);
        btEditWeekly = new JButton();
        btEditWeekly.setBackground(new Color(-12816512));
        btEditWeekly.setForeground(new Color(-2103318));
        btEditWeekly.setText("Edit Weekly");
        panel2.add(btEditWeekly, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        btDeleteWeekly = new JButton();
        btDeleteWeekly.setBackground(new Color(-12816512));
        btDeleteWeekly.setForeground(new Color(-2103318));
        btDeleteWeekly.setText("Delete Weekly");
        panel2.add(btDeleteWeekly, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btCompletedWeekly = new JButton();
        btCompletedWeekly.setBackground(new Color(-12816512));
        btCompletedWeekly.setForeground(new Color(-2103318));
        btCompletedWeekly.setText("Set Completed");
        panel2.add(btCompletedWeekly, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        panel3.setBackground(new Color(-14078925));
        mainPenal.add(panel3, BorderLayout.SOUTH);
        tMonthly = new JTable();
        tMonthly.setBackground(new Color(-14078925));
        tMonthly.setForeground(new Color(-2103318));
        tMonthly.setGridColor(new Color(-12816512));
        panel3.add(tMonthly, BorderLayout.CENTER);
        lMonthlyTableHeader = new JLabel();
        lMonthlyTableHeader.setForeground(new Color(-4145152));
        lMonthlyTableHeader.setText("Monthly(uncompleted)");
        panel3.add(lMonthlyTableHeader, BorderLayout.NORTH);
        monthlyPanel = new JPanel();
        monthlyPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        monthlyPanel.setBackground(new Color(-14078925));
        contentPane.add(monthlyPanel, BorderLayout.SOUTH);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setBackground(new Color(-14078925));
        monthlyPanel.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btEditMonthly = new JButton();
        btEditMonthly.setBackground(new Color(-12816512));
        btEditMonthly.setForeground(new Color(-2103318));
        btEditMonthly.setText("Edit Monthly");
        panel4.add(btEditMonthly, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        btDeleteMonthly = new JButton();
        btDeleteMonthly.setBackground(new Color(-12816512));
        btDeleteMonthly.setForeground(new Color(-2103318));
        btDeleteMonthly.setText("Delete Monthly");
        panel4.add(btDeleteMonthly, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btCompetedMonthly = new JButton();
        btCompetedMonthly.setBackground(new Color(-12816512));
        btCompetedMonthly.setForeground(new Color(-2103318));
        btCompetedMonthly.setText("Set Completed");
        panel4.add(btCompetedMonthly, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btAddWeeklyOrMonthly = new JButton();
        btAddWeeklyOrMonthly.setBackground(new Color(-12816512));
        btAddWeeklyOrMonthly.setForeground(new Color(-2103318));
        btAddWeeklyOrMonthly.setText("Add Weekly or Monthly");
        panel4.add(btAddWeeklyOrMonthly, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
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
