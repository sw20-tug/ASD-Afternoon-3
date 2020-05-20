package at.tugraz.asdafternoon3.ui;

import at.tugraz.asdafternoon3.FlatApplication;
import at.tugraz.asdafternoon3.data.CleaningIntervall;
import at.tugraz.asdafternoon3.businesslogic.CleaningScheduleDAO;
import at.tugraz.asdafternoon3.businesslogic.FlatDAO;
import at.tugraz.asdafternoon3.businesslogic.RoommateDAO;
import at.tugraz.asdafternoon3.data.CleaningSchedule;
import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.data.Roommate;
import at.tugraz.asdafternoon3.database.DatabaseConnection;
import at.tugraz.asdafternoon3.ui.table.CleaningScheduleTableModel;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CleaningScheduleUI {
    private JPanel contentPane;
    private JPanel mainPenal;
    private JPanel headerPain;
    private JButton btBack;
    private JTable tWeekly;
    private JButton btEdit;
    private JButton btAdd;
    private JButton btDelete;
    private JButton exportButton;
    private JTable tMonthly;
    private JLabel Weekly;
    private JLabel Mothly;
    private Flat currentFlat;

    private final List<CleaningSchedule> weeklyCleaningSchedules = new ArrayList<>();
    private final List<CleaningSchedule> monthlyCleaningSchedules = new ArrayList<>();
    private final List<CleaningSchedule> completedWeeklyCleaningSchedules = new ArrayList<>();
    private final List<CleaningSchedule> completedMonthlyCleaningSchedules = new ArrayList<>();
    private final List<CleaningSchedule> uncompletedWeeklyCleaningSchedules = new ArrayList<>();
    private final List<CleaningSchedule> uncompletedMonthlyCleaningSchedules = new ArrayList<>();

    public CleaningScheduleUI(Flat flat) {

        currentFlat = flat;

        try {
            //TODO check how i get data
            CleaningScheduleDAO cleaningScheduleDAO = new CleaningScheduleDAO(DatabaseConnection.getInstance().getSessionFactory());
            RoommateDAO roommateDAO = new RoommateDAO(DatabaseConnection.getInstance().getSessionFactory());
            FlatDAO flatDAO = new FlatDAO(DatabaseConnection.getInstance().getSessionFactory());

            List<Roommate> roommates = flatDAO.getRoommates(flat);

            for (Roommate roommate : roommates) {
                weeklyCleaningSchedules.addAll(roommateDAO.getCleaningSchedules(roommate, "weekly"));
                monthlyCleaningSchedules.addAll(roommateDAO.getCleaningSchedules(roommate, "monthly"));

                completedWeeklyCleaningSchedules.addAll(
                        roommateDAO.getCompletedCleaningSchedules(
                                roommate, "weekly",
                                LocalDateTime.now().minusDays(LocalDateTime.now().getDayOfWeek().getValue()),
                                LocalDateTime.now().plusDays(7 - LocalDateTime.now().getDayOfWeek().getValue())
                        ));
                completedMonthlyCleaningSchedules.addAll(
                        roommateDAO.getCompletedCleaningSchedules(
                                roommate, "monthly",
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

        btEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: take original data
                LocalDate currentDate = LocalDate.now();
                LocalTime currentTime = LocalTime.now();
                LocalDateTime currentDateAndTime = LocalDateTime.of(currentDate, currentTime);
                Flat testflat = new Flat("ChaosWG", 22, "Ragnitzstraße 102");
                Roommate testmate = new Roommate("Mark Weizenberg", 23, testflat);
                CleaningSchedule cleaning_schedule = new CleaningSchedule("Partykeller", currentDateAndTime, testmate, CleaningIntervall.WEEKLY);

                CleaningScheduleDialog dialog = new CleaningScheduleDialog(cleaning_schedule, true, flat);
                dialog.setSize(300, 300);
                dialog.setVisible(true);
            }
        });
        btAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CleaningScheduleDialog dialog = new CleaningScheduleDialog(null, false, flat);
                dialog.setSize(300, 300);
                dialog.setVisible(true);
            }
        });
        btDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //deleteCleaningScheduleEntry();
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
    }

    public JPanel getContentPane() {
        return contentPane;
    }
/*
    void addCleaningScheduleEntry() {

        try {
            CleaningScheduleEntry newCleaningEntry = new CleaningScheduleEntry(...);
            CleaningScheduleDAO creator = DatabaseConnection.getInstance().createDao(CleaningScheduleDAO.class);

            if (!creator.validate(newCleaningEntry)) {
                JOptionPane.showMessageDialog(contentPane, "Cleaning task data is not valid");
            } else {
                newCleaningEntry = creator.create(newCleaningEntry);
                ((CleaningScheduleTableModel) tWeekly.getModel()).addCleaningEntry(newCleaningEntry);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(contentPane, "Could not create cleaning task");
        }
    }

    void deleteCleaningScheduleEntry() {}

        try {
            int selectedRow = tWeekly.getSelectedRow();
            CleaningScheduleEntry selectedCleaningEntry = ((CleaningScheduleTableModel) tWeekly.getModel()).getElement(selectedRow);

            CleaningScheduleDAO creator = DatabaseConnection.getInstance().createDao(CleaningScheduleDAO.class);
            creator.delete(selectedCleaningEntry);
            ((CleaningScheduleTableModel) tWeekly.getModel()).removeFlat(selectedRow);
            ((CleaningScheduleTableModel) tWeekly.getModel()).fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(contentPane, "Could not delete Cleaning task " + e.getMessage());
        }
    }

    void editCleaningScheduleEntry() {

    }*/

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
        headerPain = new JPanel();
        headerPain.setLayout(new BorderLayout(0, 0));
        contentPane.add(headerPain, BorderLayout.NORTH);
        btBack = new JButton();
        btBack.setText("Back");
        headerPain.add(btBack, BorderLayout.WEST);
        final JLabel label1 = new JLabel();
        label1.setText("Cleaning schedule");
        headerPain.add(label1, BorderLayout.CENTER);
        exportButton = new JButton();
        exportButton.setText("Export");
        headerPain.add(exportButton, BorderLayout.EAST);
        mainPenal = new JPanel();
        mainPenal.setLayout(new BorderLayout(0, 0));
        mainPenal.setEnabled(true);
        contentPane.add(mainPenal, BorderLayout.CENTER);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        mainPenal.add(panel1, BorderLayout.CENTER);
        tWeekly = new JTable();
        tWeekly.setEnabled(false);
        panel1.add(tWeekly, BorderLayout.CENTER);
        Weekly = new JLabel();
        Weekly.setText("Weekly (uncompleted)");
        panel1.add(Weekly, BorderLayout.NORTH);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        mainPenal.add(panel2, BorderLayout.SOUTH);
        tMonthly = new JTable();
        panel2.add(tMonthly, BorderLayout.CENTER);
        Mothly = new JLabel();
        Mothly.setText("Monthly(uncompleted)");
        panel2.add(Mothly, BorderLayout.NORTH);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, BorderLayout.SOUTH);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btEdit = new JButton();
        btEdit.setText("Edit");
        panel4.add(btEdit, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        btAdd = new JButton();
        btAdd.setText("Add");
        panel4.add(btAdd, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        btDelete = new JButton();
        btDelete.setText("Delete");
        panel4.add(btDelete, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
