import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Solution to group project 2 for CS151-01.
 * Copyright(C) Luke Sieben, Nathan Kong, and Ravi Sharma
 * Version 2014-12-03
 */
public class ManagerPanel extends JPanel {
    public static final String[] MONTH_NAMES = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};
    public static final String[] VERY_SHORT_WEEK_NAMES = {"S", "M", "T", "W", "T", "F", "S"};
    private Manager manager;
    private JTextArea roomInfoTextArea;
    private JPanel westPanel;
    private JPanel calendarPanel;
    private Calendar currentCalendar;

    /**
     * Creates a manager panel.
     * @param manager the manager
     */
    public ManagerPanel(Manager manager) {
        this.manager = manager;

        // make this date's calendar
        currentCalendar = new GregorianCalendar();
        Date date = currentCalendar.getTime();
        currentCalendar.setTime(date);

        roomInfoTextArea = new JTextArea();
        roomInfoTextArea.setEditable(false); // do not allow people to edit the text

        setLayout(new BorderLayout());
        add(getButtonPanel(), BorderLayout.SOUTH);

        // west panel that holds calendar stuff
        westPanel = new JPanel();
        westPanel.setLayout(new BorderLayout());
        westPanel.add(getMonthAndYearComboBoxPanel(currentCalendar), BorderLayout.NORTH);

        calendarPanel = getCalendarPanel(currentCalendar);
        westPanel.add(calendarPanel, BorderLayout.CENTER);

        updateCalendarPanel();
        add(westPanel, BorderLayout.WEST);

        add(roomInfoTextArea, BorderLayout.CENTER);
    }

    /**
     * Gets the button panel.
     * @return the button panel
     */
    private JPanel getButtonPanel() {
        // buttonPanel
        JPanel buttonPanel = new JPanel();

        int rows = 1;
        int columns = 0;
        buttonPanel.setLayout(new GridLayout(rows, columns));

        JButton loadButton = new JButton("Load");
        loadButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                manager.load();
            }
        });

        JButton saveAndQuitButton = new JButton("Save And Quit");
        saveAndQuitButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                manager.save();

                System.exit(0);
            }
        });

        buttonPanel.add(loadButton);
        buttonPanel.add(saveAndQuitButton);

        return buttonPanel;
    }

    /**
     * Shows the room info on a particular calendar day.
     * @param calendar the calendar
     */
    private void showRoomInfoOnDay(Calendar calendar) {
        roomInfoTextArea.setText(manager.getRoomInfoOnDay(calendar));
    }

    /**
     * Shows the month and year drop downs.
     * @param calendar the calendar
     * @return the month and year drop down panel
     */
    private JPanel getMonthAndYearComboBoxPanel(Calendar calendar) {
        // make the months combo box
        final JComboBox<String> monthComboBox = new JComboBox<>(MONTH_NAMES);
        monthComboBox.setSelectedIndex(currentCalendar.get(Calendar.MONTH));
        monthComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String monthName = monthComboBox.getSelectedItem().toString();

                // get the right month value based on the month name
                for(int i = 0; i < MONTH_NAMES.length; i++) {
                    if(MONTH_NAMES[i].equals(monthName)) {
                        currentCalendar.set(Calendar.MONTH, i);

                        break;
                    }
                }

                updateCalendarPanel();
            }
        });

        // make years combo box
        int year = calendar.get(Calendar.YEAR);
        int numOfYears = 10;
        List<Integer> yearList = new ArrayList<>();
        for(int i = 0; i < numOfYears; i++) {
            yearList.add(year + i);
        }

        final JComboBox<Integer> yearComboBox = new JComboBox<>(yearList.toArray(new Integer[yearList.size()]));
        yearComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int year = Integer.parseInt(yearComboBox.getSelectedItem().toString());

                currentCalendar.set(Calendar.YEAR, year);

                updateCalendarPanel();
            }
        });

        int rows = 1;
        int columns = 0;

        JPanel monthAndYearComboBoxPanel = new JPanel();
        monthAndYearComboBoxPanel.setLayout(new GridLayout(rows, columns));

        monthAndYearComboBoxPanel.add(monthComboBox);
        monthAndYearComboBoxPanel.add(yearComboBox);

        return monthAndYearComboBoxPanel;
    }

    /**
     * Updates the center panel's center panel to use the current calendar's year and month.
     */
    private void updateCalendarPanel() {
        westPanel.remove(calendarPanel);

        calendarPanel = getCalendarPanel(currentCalendar);
        westPanel.add(calendarPanel, BorderLayout.CENTER);

        westPanel.revalidate();

    }

    /**
     * Creates a calendar panel.
     * @param calendar the calendar to use
     * @return the calendar panel
     */
    private JPanel getCalendarPanel(Calendar calendar) {
        JPanel calendarPanel = new JPanel();
        calendarPanel.setLayout(new GridLayout(0, VERY_SHORT_WEEK_NAMES.length));

        // make short week names at top of calendar
        for(String veryShortWeekName : VERY_SHORT_WEEK_NAMES) {
            calendarPanel.add(new JLabel(veryShortWeekName));
        }

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);

        // make a new Calendar object with the fields from the given calendar with current dayOfMonth = 1,  so we can start the calendar on the right dayOfWeek
        Calendar newCalendar = new GregorianCalendar(year, month, 1);

        int firstDayOfWeek = newCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        int numberOfDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        int[] dayOfMonthArr = getDaysOfMonthArray(firstDayOfWeek, numberOfDaysInMonth);
        for(int i = 0; i < dayOfMonthArr.length; i++) {
            final int day = dayOfMonthArr[i];
            JButton button = new JButton(dayOfMonthArr[i] + "");
            button.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    showRoomInfoOnDay(new GregorianCalendar(year, month, day));
                }
            });

            if(day == 0) { // don't show empty days
                button.setVisible(false);
            }

            calendarPanel.add(button);
        }

        return calendarPanel;
    }

    /**
     * Gets a days of month list where each elements number is that day.
     * @param firstDayOfWeek the first day of the week
     * @param numberOfDaysInMonth the day of month array with empty days for spacing marked by 0
     * @return the days of month list
     * precondition: 0 <= firstDayOfWeek <= 6,
     *               1 <= numberOfDaysInMonth <= 31
     * postcondition: day of month array will have empty days for spacing marked by 0
     */
    private int[] getDaysOfMonthArray(int firstDayOfWeek, int numberOfDaysInMonth) {
        int[] calendarTable = new int[firstDayOfWeek + numberOfDaysInMonth];

        // add "empty" days
        for(int i = 0; i < firstDayOfWeek; i++) {
            calendarTable[i] = 0;
        }

        for(int i = firstDayOfWeek; i < numberOfDaysInMonth + firstDayOfWeek; i++) {
            calendarTable[i] = i - firstDayOfWeek + 1;
        }

        return calendarTable;
    }
}
