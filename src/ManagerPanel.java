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
 * Version 2014-11-11
 */
public class ManagerPanel extends JPanel {
    public static final String SEPARATOR = System.getProperty("line.separator");
    public static final String[] MONTH_NAMES = {"January", "February", "March", "April", "May", "June", //should theses be ENUMs?
            "July", "August", "September", "October", "November", "December"};
//    public static final String[] VERY_SHORT_WEEK_NAMES = {"S", "M", "T", "W", "T", "F", "S"}; // Should these be ENUMS
    private Manager manager;
    private HotelRoomsDataModel hotelRoomsDataModel;
    private JTextArea roomInfoTextArea;
    private JPanel centerPanel;
    private Calendar currentCalendar;

    public ManagerPanel(Manager manager, HotelRoomsDataModel hotelRoomsDataModel)
    {
        this.manager = manager;
        this.hotelRoomsDataModel = hotelRoomsDataModel;

        // make this date's calendar
        Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        calendar.setTime(date);

        roomInfoTextArea = new JTextArea();
        showRoomInfoOnDay(calendar);

        setLayout(new BorderLayout());
        add(getButtonPanel(), BorderLayout.SOUTH);

        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(getMonthAndYearComboBoxPanel(calendar), BorderLayout.NORTH);
        centerPanel.add(getCalendarPanel(calendar), BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        add(roomInfoTextArea, BorderLayout.EAST);
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
        hotelRoomsDataModel.setFilteredData(calendar, calendar, ""); // don't care about type of room, and should only use starting date
        roomInfoTextArea.setText(hotelRoomsDataModel.getFilteredData().toString());
    }

    private JPanel getMonthAndYearComboBoxPanel(Calendar calendar) {
        // make the months combo box
        final JComboBox<String> monthComboBox = new JComboBox<>(MONTH_NAMES);
        monthComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String month = monthComboBox.getSelectedItem().toString();

                // update month of calendar --> currentCalendar.set();
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

                // updated year of calendar --> updateCalendarPanel();
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
     * Creates a calendar panel.
     * @param calendar the calendar to use
     * @return the calendar panel
     */
    private JPanel getCalendarPanel(Calendar calendar) {
        JPanel calendarPanel = new JPanel();
        calendarPanel.setLayout(new GridLayout(0, VERY_SHORT_WEEK_NAMES.length));

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
