import java.awt.BorderLayout;
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
        int userID;

        // label to force user to enter a number for user id
        retryLogin:
        try {
            userID = Integer.parseInt(JOptionPane.showInputDialog("Enter your user ID: "));
        }
        catch(NumberFormatException e) {
            break retryLogin;
        }

        // get username
        String username;
    	if(!hotelModel.hasUserID(userID)) { // make a username if hotel data model does not have the given user id
            username = JOptionPane.showInputDialog("Enter your username: ");
            if(username == null) {
                username = "null";
            }
        }
        else {
            username = hotelModel.getUsername(userID);
        }

        // ADD CODE TO ADD THIS GUEST TO THE HOTEL MODEL
        guest = new Guest(userID, username);

        displayReservationOptions();
    }
    
    /** 
     * Use GUI to make 2 buttons one is
     * |MAKE RESERVATION| and other is
     * |VIEW/CANCEL RESERVATION|
     * and then add ActionListeners to them
     * so that when they are clicked
     * appropriate method is called. (coded already)
     */
    public JPanel displayCreateView()
    {
        JButton makeReservationButton = new JButton("Make Reservation");
        JButton viewOrCancelReservationButton = new JButton("View/Cancel Reservation");
        
        makeReservationButton.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                displayMakeReservationDialog();
            }
        });
        
        viewOrCancelReservationButton.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                viewOrCancelReservation();
            }
        });
        
    	JPanel createView = new JPanel();
    	createView.setLayout( new BorderLayout() );
        createView.add(makeReservationButton, BorderLayout.NORTH);
        createView.add(viewOrCancelReservationButton, BorderLayout.SOUTH);


        return createView;
    }

    public void displayMakeReservationDialog()
    {
        /*Use GUI JTextField to get the checkin date and another JTextField to get the checkout date
          convert those dates that you recieved by the JTextField into a Calendar Object (remeber to use .getText() method of JTextField
          it is a String so convert it into a Calendar object. Then check if the checkin date is before the current real date if yes throw 
          an error message.
        */
        /* Make 2 Buttons one called |Standard| and other called |Luxuary| then add action listeners that will set the String variable 
           isLuxury to the appropriate String. (coded already)
          
         */
        JButton standardButton = new JButton("Standard");
        JButton luxuaryButton = new JButton("Luxury");

        ActionListener standardButtonListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isLuxury = false;
                hotelModel.setFilteredData(null, null, isLuxury);
            }
        };
        
        ActionListener luxuryButtonListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                isLuxury = true;
                hotelModel.setFilteredData(null, null, isLuxury);
            }
        };

        luxuaryButton.addActionListener(luxuryButtonListener);
        standardButton.addActionListener(standardButtonListener);
        
        /*
        Make 2 more JButtons |CONFIRMED| and |TRANSACTION DONE| and then add ActionListeners to them
        User can keep making reservations until they click Transaction Done button
        */
        JButton confirmedButton = new JButton("Confirm");
        JButton transactionDoneButton = new JButton("Transaction Done");
        
        ActionListener confirmedButtonListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                hotelModel.updateToAddReservation(copyOfHotelRooms.get(0), null, null);
                Room addReservationToGuestRecords = copyOfHotelRooms.get(0);
                addReservationToGuestRecords.setCheckInDate(null);
                addReservationToGuestRecords.setCheckOutDate(null);
                guest.addToGuestReservations(addReservationToGuestRecords);
            }
        };

    } // make reservation 

   
    /**
     * GUI to view or cancel a reservation
     * made by the guest
     */
    public void viewOrCancelReservation()
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

    }// view or cancel reservation
  
    
    public void stateChanged(ChangeEvent e) {
    	copyOfHotelRooms = hotelModel.getFilteredData();
        for(int i = 0; i < copyOfHotelRooms.size(); i++)
        {
            availableRooms.append("\n" + copyOfHotelRooms.get(i).toString() );
        }
    }

}
