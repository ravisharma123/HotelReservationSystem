import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*********************************************************
 * Shows the Guest views for user input to make a reservation
 * and to store the information in to the data model
 *
 * Solution to group project 2 for CS151-01.
 * Copyright(C) Luke Sieben, Nathan Kong, and Ravi Sharma
 * Version 2014-12-03
 *********************************************************/
/*
 The GuestPanel class contains the View component of the MVC Pattern since it is updated everytime
 more specifically it is the availableRooms JTextArea that is being updated in this panel so that is the view 
 This class also contains the multiple controller components of the MVC Pattern. 1) the actionListener 
 that the Timer t uses to update instantaneously when user is reserving a room. 2) the actionListener used to 
 add a guest to the system is another controller that changes the hotel model to add a guest. 3) the last controller
 is the anonymous action listener for canceling reservations which changes the hotel model to free vacancy 
*/
public class GuestPanel extends JPanel implements ChangeListener {
    private Guest guest;
    private ArrayList<Room> copyOfHotelRooms;
    private HotelModel hotelModel;
    private JTextArea availableRooms;
    private boolean isLuxury;
    private Calendar checkInCalendar;
    private Calendar checkOutCalendar;
    private Timer t; //Timer ONLY uses the controller(actionListener) to update in real time
                     //*actual contoller is actionListener that is passed into the timer*

    /**
     * Creates a guest panel.
     * @param hotelModel the hotel model
     */
    public GuestPanel(HotelModel hotelModel) {
        copyOfHotelRooms = hotelModel.getData();
        this.hotelModel = hotelModel;
        availableRooms = new JTextArea("Available Rooms\n");
        this.t = new Timer(10, null);
    }

    /**
     * Resets some instance variables.
     */
      public void reset() {
    	guest = null;
        isLuxury = false;
        checkInCalendar = null;
        checkOutCalendar = null;
        t.stop();
    }

    /**
     * Display login.
     */
    public void displayLogin() {
        removeAll();

        reset();

        // ActionListener uses the userIDField so make it here
        final JTextField userIDField = new JTextField();
        //This actionListener is the controller that updates to add a guest to the hotel model
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String str = userIDField.getText();
                if(str.matches("\\d+")) {
                    int userID = Integer.parseInt(str);

                    // get username
                    String username;
                    if(hotelModel.hasGuestID(userID) >= 0) {
                        int location = hotelModel.hasGuestID(userID);
                        guest = hotelModel.getGuestList().get(location);
                    }
                    else {
                        username = JOptionPane.showInputDialog("Create a username for this user ID: ");

                        // cancel option was selected or username is empty
                        if(username == null || username.equals("")) {
                            return;
                        }

                        guest = new Guest(userID, username);
                        hotelModel.updateToAddGuest(guest);
                    }

                    displayReservationOptions();
                }
            }
        };

        // user ID panel
        int rows = 1;
        int columns = 0;
        JLabel userIDLabel = new JLabel("Enter/create a user ID (can only contain numbers):");
        userIDField.addActionListener(actionListener);

        JPanel userIDPanel = new JPanel(new GridLayout(rows, columns));
        userIDPanel.add(userIDLabel);
        userIDPanel.add(userIDField);

        // login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(actionListener);

        setLayout(new BorderLayout());
        add(userIDPanel, BorderLayout.NORTH);
        add(loginButton, BorderLayout.CENTER);
    }

    /**
     * Display reservation options.
     */
    private void displayReservationOptions() {
        removeAll();

        int rows = 0;
        int columns = 1;
        setLayout(new GridLayout(rows, columns));

        final JButton makeReservationButton = new JButton("Make a Reservation");
        makeReservationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(makeReservationButton);

                displayMakeReservationOption();

                revalidate();
            }
        });

        final JButton viewOrCancelReservationButton = new JButton("View/Cancel a Reservation");
        viewOrCancelReservationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(viewOrCancelReservationButton);

                displayViewOrCancelOption();

                revalidate();
            }
        });

        add(makeReservationButton);
        add(viewOrCancelReservationButton);

        revalidate();
    }

    /**
     * Display make reservation options.
     */
    private void displayMakeReservationOption() {
        removeAll();

        // check in and check out panels
        JLabel checkInLabel = new JLabel("Check-in date (in mm/dd/yyyy format):");
        final JTextField checkInField = new JTextField();
        JPanel checkInPanel = new JPanel(new GridLayout(0, 2));
        checkInPanel.add(checkInLabel);
        checkInPanel.add(checkInField);

        JLabel checkOutLabel = new JLabel("Check-out date (in mm/dd/yyyy format):");
        final JTextField checkOutField = new JTextField();
        JPanel checkOutPanel = new JPanel(new GridLayout(0, 2));
        checkOutPanel.add(checkOutLabel);
        checkOutPanel.add(checkOutField);

        // standard and luxury radio buttons
        final String standard = "Standard";
        JRadioButton standardRadioButton = new JRadioButton(standard);
        standardRadioButton.setActionCommand(standard);
        standardRadioButton.setSelected(true);

        final String luxury = "Luxury";
        JRadioButton luxuryRadioButton = new JRadioButton(luxury);
        luxuryRadioButton.setActionCommand(luxury);

        ButtonGroup group = new ButtonGroup();
        group.add(standardRadioButton);
        group.add(luxuryRadioButton);

        ActionListener groupListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isLuxury = e.getActionCommand().equals(luxury);
            }
        };

        standardRadioButton.addActionListener(groupListener);
        luxuryRadioButton.addActionListener(groupListener);

        JPanel standardOrLuxuryButtonPanel = new JPanel(new GridLayout(0, 2));
        standardOrLuxuryButtonPanel.add(standardRadioButton);
        standardOrLuxuryButtonPanel.add(luxuryRadioButton);

        // available room text area
        final JTextArea availableRoomsArea = new JTextArea();
        availableRoomsArea.setEditable(false);

        final JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!copyOfHotelRooms.isEmpty()) {
                    hotelModel.updateToAddReservation(hotelModel.getAvailableRoomInfo().get(0), checkInCalendar, checkOutCalendar);
                    Room addReservationToGuestRecords = new Room(copyOfHotelRooms.get(0).isLuxury(),copyOfHotelRooms.get(0).getRoomNumber());
                    addReservationToGuestRecords.setCheckInDate(checkInCalendar);              
                    addReservationToGuestRecords.setCheckOutDate(checkOutCalendar);
                    guest.addToGuestReservations(addReservationToGuestRecords);
                    availableRoomsArea.revalidate();
                }
            }
        });
        confirmButton.setEnabled(false);
        //The actionListener is the controller component of the MVC pattern that adds a reservation to the hotel model
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String checkInDate = checkInField.getText();
                String checkOutDate = checkOutField.getText();
                availableRoomsArea.setText("");

                if(checkInDate.matches("\\d+/\\d+/\\d+") && checkOutDate.matches("\\d+/\\d+/\\d+")) {
                    String[] checkInDateArr = checkInDate.split("/");
                    int checkInYear = Integer.parseInt(checkInDateArr[2]);
                    int checkInMonth = Integer.parseInt(checkInDateArr[0]) - 1;
                    int checkInDay = Integer.parseInt(checkInDateArr[1]);

                    String[] checkOutDateArr = checkOutDate.split("/");
                    int checkOutYear = Integer.parseInt(checkOutDateArr[2]);
                    int checkOutMonth = Integer.parseInt(checkOutDateArr[0]) - 1;
                    int checkOutDay = Integer.parseInt(checkOutDateArr[1]);
                    Calendar tempCheckOut = Calendar.getInstance();
                    tempCheckOut.set(checkOutYear, checkOutMonth, 1);
                    Calendar tempCheckIn = Calendar.getInstance();
                    tempCheckIn.set(checkInYear, checkInMonth, 1);
                    if(checkOutDay>tempCheckOut.getActualMaximum(Calendar.DATE) || checkInDay>tempCheckIn.getActualMaximum(Calendar.DATE)|| checkInMonth>11 || checkInMonth<1 || checkOutMonth>11 || checkOutMonth<1){
                        availableRoomsArea.append("Invalid date(s)");
                        confirmButton.setEnabled(false);
                    }
                    else{

                    checkInCalendar = new GregorianCalendar(checkInYear, checkInMonth, checkInDay);
                    checkOutCalendar = new GregorianCalendar(checkOutYear, checkOutMonth, checkOutDay);
                    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    Calendar currentDay = Calendar.getInstance();
                    currentDay.add(Calendar.DATE, -1);
                    Room tempRoom = new Room(false, 0);
                    ArrayList<Calendar> cal = tempRoom.getDates(checkInCalendar, checkOutCalendar);

                    if (checkOutCalendar.before(checkInCalendar)) {
                        availableRoomsArea.append("Check-Out date is prior to the Check-In date");
                        confirmButton.setEnabled(false);
                    }
                    else if (checkInCalendar.before(currentDay)) {
                        availableRoomsArea.append("Dates occur prior to today's date");
                        confirmButton.setEnabled(false);
                    }
                    else if (cal.size() > 60) {
                        availableRoomsArea.append("Reservations in excess of 60 days");
                        confirmButton.setEnabled(false);
                    }
                    else {
                        hotelModel.setFilteredData(checkInCalendar, checkOutCalendar, isLuxury);
                        if (!hotelModel.getAvailableRoomInfo().isEmpty()) {
                            for (int i = 0; i < hotelModel.getAvailableRoomInfo().size(); i++) {
                            	availableRoomsArea.append( hotelModel.getAvailableRoomInfo().get(i).getRoomNumber() + "\n" );
                            }
                            confirmButton.setEnabled(true);
                        }
                        else {
                            String roomType = isLuxury ? luxury : standard;

                            availableRoomsArea.append("There are no " + roomType + " rooms available during this duration");
                            confirmButton.setEnabled(false);
                        }
                    }
                }
                }
            }
        };

        final int DELAY = 10; // 1000ms = 1s
        this.t = new Timer(DELAY, actionListener); //actionListener is passed into the timer for instantaneous update
        t.start();

        JButton transactionDoneButton = new JButton("Transaction Done");
        transactionDoneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                t.stop();
            	displayReceipt();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	t.stop();
                displayReservationOptions();
            }
        });

        JPanel infoPanel = new JPanel(new GridLayout(0, 1));
        infoPanel.add(checkInPanel);
        infoPanel.add(checkOutPanel);
        infoPanel.add(standardOrLuxuryButtonPanel);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 0));
        buttonPanel.add(confirmButton);
        buttonPanel.add(transactionDoneButton);
        buttonPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(infoPanel, BorderLayout.NORTH);
        add(availableRoomsArea, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        revalidate();
    }

    /**
     * Display receipt.
     */
    /*This GuestPanel class is the client of the Strategy Pattern since it is invoking the pattern here
      and makes the call to guest.getReceipt(new ConcreteStrategy()) depending on which radio button is selected
    */
    private void displayReceipt() {
        removeAll();

        final JTextArea receiptArea = new JTextArea();
        receiptArea.setEditable(false);

        // standard and luxury radio buttons
        final String simple = "Simple";
        JRadioButton simpleRadioButton = new JRadioButton(simple);
        simpleRadioButton.setActionCommand(simple);
        simpleRadioButton.setSelected(true);

        // use Simple receipt view first
        if ( !guest.getRoomList().isEmpty() )
        {	receiptArea.setText(guest.getReceipt(new SimpleReceipt()));	}
        else
        {	receiptArea.setText("No Reservations were made");	}

        final String comprehensive = "Comprehensive";
        JRadioButton comprehensiveRadioButton = new JRadioButton(comprehensive);
        comprehensiveRadioButton.setActionCommand(comprehensive);

        ButtonGroup group = new ButtonGroup();
        group.add(simpleRadioButton);
        group.add(comprehensiveRadioButton);

        ActionListener groupListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String actionCommand = e.getActionCommand();

                if(actionCommand.equals(simple)) {
                    //Strategy Pattern
                    if ( !guest.getRoomList().isEmpty() )
                    {	receiptArea.setText(guest.getReceipt(new SimpleReceipt()));	}
                    else
                    {	receiptArea.setText("No Reservations were made");	}
                }
                else {
                    //Strategy Pattern
                    if ( !guest.getRoomList().isEmpty() )
                    {	receiptArea.setText(guest.getReceipt(new ComprehensiveReceipt()));	}
                    else
                    {	receiptArea.setText("No Reservations were made");	}
                }
            }
        };

        simpleRadioButton.addActionListener(groupListener);
        comprehensiveRadioButton.addActionListener(groupListener);

        JPanel simpleOrComprehensiveButtonPanel = new JPanel(new GridLayout(0, 2));
        simpleOrComprehensiveButtonPanel.add(simpleRadioButton);
        simpleOrComprehensiveButtonPanel.add(comprehensiveRadioButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayMakeReservationOption();
            }
        });
        setLayout(new BorderLayout());
        add(simpleOrComprehensiveButtonPanel, BorderLayout.NORTH);
        add(receiptArea, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        revalidate();
    }

    /**
     * Display view or cancel options.
     */
    private void displayViewOrCancelOption() {
        removeAll();

        final ArrayList<Room> reservationsByGuest = guest.getRoomList();
        final ArrayList<JCheckBox> checkBoxList = new ArrayList<JCheckBox>();

        for(Room room: reservationsByGuest) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
            String checkin = dateFormat.format( room.getCheckInDate().getTime() );
            String checkout = dateFormat.format( room.getCheckOutDate().getTime() );

            String reservationDescription = room.getType() + " Room " + room.getRoomNumber() + ": " + checkin + " to " + checkout;

            JCheckBox checkBox = new JCheckBox(reservationDescription);
            checkBoxList.add(checkBox);
            add(checkBox);
        }
        //This is the third controller that is used to cancel reservations and change the hotel model so that vacancy is freed
        JButton confirmButton = new JButton("Delete Checked");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for(int i = checkBoxList.size() - 1; i >= 0; i--) {
                    JCheckBox checkBox = checkBoxList.get(i);

                    if(checkBox.isSelected()) {
                        hotelModel.updateToCancelReservation(reservationsByGuest.get(i).getRoomNumber(), reservationsByGuest.get(i).getCheckInDate(), reservationsByGuest.get(i).getCheckOutDate());
                        guest.removeFromGuestReservations(reservationsByGuest.get(i));
                        checkBoxList.remove(checkBox);
                        remove(checkBox);
                        revalidate();
                    }
                }
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayReservationOptions();
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 0));
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel);

        revalidate();
    }

    /**
     * Changes the room availability when a change has been made to the HotelModel.
     * @param e the ChangeEvent
     */
    public void stateChanged(ChangeEvent e) {
        copyOfHotelRooms = hotelModel.getAvailableRoomInfo();

        for(int i = 0; i < copyOfHotelRooms.size(); i++) {
            availableRooms.append("\n" + copyOfHotelRooms.get(i).toString());
        }
    }
}