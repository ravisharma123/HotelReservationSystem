import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 * Version 2014-11-14
 *********************************************************/
public class GuestPanel extends JPanel implements ChangeListener {
    private Guest guest;
    private ArrayList<Room> copyOfHotelRooms;
    private HotelModel hotelModel;
    private JTextArea availableRooms;
    private boolean isLuxury;
    private Calendar checkInCalendar;
    private Calendar checkOutCalendar;

    public GuestPanel(HotelModel hotelModel) {
        copyOfHotelRooms = hotelModel.getData();
        this.hotelModel = hotelModel;
        isLuxury = false;
        availableRooms = new JTextArea("Available Rooms\n");
    }

    /**
     * Display login.
     */
    public void displayLogin() {
        removeAll();

        // user ID panel
        int rows = 1;
        int columns = 0;

        JLabel userIDLabel = new JLabel("Enter/create a user ID (can only contain numbers):");
        JTextField userIDField = new JTextField();
        JPanel userIDPanel = new JPanel(new GridLayout(rows, columns));
        userIDPanel.add(userIDLabel);
        userIDPanel.add(userIDField);

        // login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String str = userIDField.getText();
                if(str.matches("\\d+")) {
                    int userID = Integer.parseInt(str);

                    // get username
                    String username;
                    if(hotelModel.hasUserID(userID)) {
                        username = hotelModel.getUsername(userID);
                    }
                    else {
                        username = JOptionPane.showInputDialog("Create a username for this user ID: ");

                        if(username == null) {
                            username = "null";
                        }
                    }

                    guest = new Guest(userID, username);

                    displayReservationOptions();
                }
            }
        });

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
        JTextField checkInField = new JTextField();
        JPanel checkInPanel = new JPanel(new GridLayout(0, 2));
        checkInPanel.add(checkInLabel);
        checkInPanel.add(checkInField);

        JLabel checkOutLabel = new JLabel("Check-out date (in mm/dd/yyyy format):");
        JTextField checkOutField = new JTextField();
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
            @Override
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
        JTextArea availableRoomsArea = new JTextArea();
        availableRoomsArea.setEditable(false);

        JButton showAvailableRoomsButton = new JButton("Show Available Room");
        showAvailableRoomsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String checkInDate = checkInField.getText();
                String checkOutDate = checkOutField.getText();
                
                if(checkInDate.matches("\\d+/\\d+/\\d+") && checkOutDate.matches("\\d+/\\d+/\\d+")) {
                    String[] checkInDateArr = checkInDate.split("/");
                    int checkInYear = Integer.parseInt(checkInDateArr[2]);
                    int checkInMonth = Integer.parseInt(checkInDateArr[0]) - 1;
                    int checkInDay = Integer.parseInt(checkInDateArr[1]);

                    String[] checkOutDateArr = checkOutDate.split("/");
                    int checkOutYear = Integer.parseInt(checkOutDateArr[2]);
                    int checkOutMonth = Integer.parseInt(checkOutDateArr[0]) - 1;
                    int checkOutDay = Integer.parseInt(checkOutDateArr[1]);

                    checkInCalendar = new GregorianCalendar(checkInYear, checkInMonth, checkInDay);
                    checkOutCalendar = new GregorianCalendar(checkOutYear, checkOutMonth, checkOutDay);
                    
                    hotelModel.setFilteredData(checkInCalendar, checkOutCalendar, isLuxury);

                    availableRoomsArea.setText(hotelModel.getAvailableRoomInfo());
                }
            }
        });

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // THIS STUFF LOOKS REALLY NASTY AND NEEDS REWORKING
                hotelModel.updateToAddReservation(copyOfHotelRooms.get(0), null, null);

                Room addReservationToGuestRecords = copyOfHotelRooms.get(0);
                addReservationToGuestRecords.setCheckInDate(null);
                addReservationToGuestRecords.setCheckOutDate(null);

                guest.addToGuestReservations(addReservationToGuestRecords);
            }
        });

        JButton transactionDoneButton = new JButton("Transaction Done");
        transactionDoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayReceipt();
            }
        });

        JPanel confirmedOrTransactionDonePanel = new JPanel(new GridLayout(1, 0));
        confirmedOrTransactionDonePanel.add(confirmButton);
        confirmedOrTransactionDonePanel.add(transactionDoneButton);

        JPanel infoPanel = new JPanel(new GridLayout(0, 1));
        infoPanel.add(checkInPanel);
        infoPanel.add(checkOutPanel);
        infoPanel.add(standardOrLuxuryButtonPanel);
        infoPanel.add(showAvailableRoomsButton);

        setLayout(new BorderLayout());
        add(infoPanel, BorderLayout.NORTH);
        add(availableRoomsArea, BorderLayout.CENTER);
        add(confirmedOrTransactionDonePanel, BorderLayout.SOUTH);

        revalidate();
    }

    /**
     * Display receipt.
     */
    private void displayReceipt() {
        removeAll();

        JTextArea receiptArea = new JTextArea();
        receiptArea.setEditable(false);

        JButton simpleButton = new JButton("Simple");
        simpleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                receiptArea.setText(hotelModel.getSimpleReceipt());
            }
        });

        JButton comprehensiveButton = new JButton("Comprehensive");
        comprehensiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                receiptArea.setText(hotelModel.getComprehensiveReceipt());
            }
        });

        JPanel simpleOrComprehensiveButtonPanel = new JPanel(new GridLayout(1, 0));
        simpleOrComprehensiveButtonPanel.add(simpleButton);
        simpleOrComprehensiveButtonPanel.add(comprehensiveButton);

        setLayout(new BorderLayout());
        add(simpleOrComprehensiveButtonPanel, BorderLayout.NORTH);
        add(receiptArea, BorderLayout.CENTER);

        revalidate();
    }

    /**
     * Display view or cancel options.
     */
    private void displayViewOrCancelOption() {
        removeAll();

        ArrayList<Room> reservationsByGuest = guest.getRoomList();
        final ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
    
        /*
        Loop through the reservationsByGuest ArrayList and create JCheckBoxes out of each element inside the arrayList
        and then add them to the JFrame or the component that is going to be used to display the GUI.(You can use the 
        toStringMethod of the room to name each JCheckBox so that is will display the room name and check in check out dates. 
        Might need two toString methods one for available rooms because that will display only the room name. And another 
        that displays the room name and check in and checkout dates for cancel option
        */
        for(Room room: reservationsByGuest) {
            //add a String description of the room specs -> Luxury Room 10: date to date
        	String reservationDescription = ( room.getType() + " Room " + room.getRoomNumber() + ": " + room.getCheckInDate().getTime().toString() + " to " + room.getCheckOutDate().getTime().toString() );

        	//JCheckBox checkBox = new JCheckBox( room.cancelToString() );
        	JCheckBox checkBox = new JCheckBox( reservationDescription );
            //frame/component.add(checkBox);
            checkBoxes.add(checkBox);           
        }
        
        //create vertical check boxes
        JPanel viewDeletePanel = new JPanel();
        //viewDeletePanel.setLayout( new BoxLayout() );
        
        
        /*
        Add a JButton |CANCEL| so that all the check boxes that were selected then their appropriate reservations are deleted from data
        model.(remember we are not deleting a room from data model but we are deleting from the bookingDates of the room. 
        */
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < checkBoxes.size(); i++) {
                    if( checkBoxes.get(i).isSelected() ) {
                        hotelModel.updateToCancelReservation(reservationsByGuest.get(i), reservationsByGuest.get(i).getCheckInDate(), reservationsByGuest.get(i).getCheckOutDate());
                    }
                }
            }
        });

        revalidate();
    }

    public void stateChanged(ChangeEvent e) {
    	copyOfHotelRooms = hotelModel.getFilteredData();

        for(int i = 0; i < copyOfHotelRooms.size(); i++) {
            availableRooms.append("\n" + copyOfHotelRooms.get(i).toString());
        }
    }
}
