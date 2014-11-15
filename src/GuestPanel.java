import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
    private boolean isLuxury;
    private JTextArea availableRooms;

    public GuestPanel(HotelModel hotelModel) {
        copyOfHotelRooms = hotelModel.getData();
        this.hotelModel = hotelModel;
        isLuxury = false;
        availableRooms = new JTextArea("Available Rooms\n");
    }

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

    private void displayMakeReservationOption() {
        // standard and luxury buttons
        JButton standardButton = new JButton("Standard");
        standardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isLuxury = false;
                hotelModel.setFilteredData(null, null, isLuxury);
            }
        });

        JButton luxuryButton = new JButton("Luxury");
        luxuryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isLuxury = true;
                hotelModel.setFilteredData(null, null, isLuxury);
            }
        });

        int rows = 1;
        int columns = 0;

        JPanel buttonPanel = new JPanel(new GridLayout(rows, columns));
        buttonPanel.add(standardButton);
        buttonPanel.add(luxuryButton);

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.SOUTH);

        // check in and check out panels
        JLabel checkInLabel = new JLabel("Check-in:");
        JTextField checkInField = new JTextField();
        JPanel checkInPanel = new JPanel(new GridLayout(rows, columns));
        checkInPanel.add(checkInLabel);
        checkInPanel.add(checkInField);

        JLabel checkOutLabel = new JLabel("Check-out:");
        JTextField checkOutField = new JTextField();
        JPanel checkOutPanel = new JPanel(new GridLayout(rows, columns));
        checkOutPanel.add(checkOutLabel);
        checkOutPanel.add(checkOutField);

        JPanel checkInAndOutPanel = new JPanel(new GridLayout(rows, columns));
        checkInAndOutPanel.add(checkInPanel);
        checkInAndOutPanel.add(checkOutPanel);

        /*
        Make 2 more JButtons |CONFIRMED| and |TRANSACTION DONE| and then add ActionListeners to them
        User can keep making reservations until they click Transaction Done button
        */
        JButton confirmedButton = new JButton("Confirm");
        JButton transactionDoneButton = new JButton("Transaction Done");
        
        ActionListener confirmedButtonListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hotelModel.updateToAddReservation(copyOfHotelRooms.get(0), null, null);
                Room addReservationToGuestRecords = copyOfHotelRooms.get(0);
                addReservationToGuestRecords.setCheckInDate(null);
                addReservationToGuestRecords.setCheckOutDate(null);
                guest.addToGuestReservations(addReservationToGuestRecords);
            }
        };

    }

   
    /**
     * GUI to view or cancel a reservation
     * made by the guest
     */
    private void displayViewOrCancelOption()
    {
        ArrayList<Room> reservationsByGuest = guest.getRoomList();
        final ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
    
        /*
        Loop through the reservationsByGuest ArrayList and create JCheckBoxes out of each element inside the arrayList
        and then add them to the JFrame or the component that is going to be used to display the GUI.(You can use the 
        toStringMethod of the room to name each JCheckBox so that is will display the room name and check in check out dates. 
        Might need two toString methods one for available rooms because that will display only the room name. And another 
        that displays the room name and check in and checkout dates for cancel option
        */
        for( Room room: reservationsByGuest )
        {
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
        cancel.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                for(int i = 0; i < checkBoxes.size(); i++)
                {
                    if( checkBoxes.get(i).isSelected() )
                    {
                        hotelModel.updateToCancelReservation(reservationsByGuest.get(i), reservationsByGuest.get(i).getCheckInDate(), reservationsByGuest.get(i).getCheckOutDate());
                    }
                }
            }
        });

    }

    public void stateChanged(ChangeEvent e) {
    	copyOfHotelRooms = hotelModel.getFilteredData();

        for(int i = 0; i < copyOfHotelRooms.size(); i++) {
            availableRooms.append("\n" + copyOfHotelRooms.get(i).toString());
        }
    }
}
