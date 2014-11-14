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
 * Version 2014-11-11
 *********************************************************/
public class GuestPanel extends JPanel implements ChangeListener {
    private Guest guest;
    private ArrayList<Room> copyOfHotelRooms;
    private HotelRoomsDataModel hotelDataModel;
    private String typeOfRoom;
    private JTextArea availableRooms;
    

    public GuestPanel(HotelRoomsDataModel setHotelDataModel)
    {
        guest = null;
        copyOfHotelRooms = setHotelDataModel.getData();
        hotelDataModel = setHotelDataModel;
        typeOfRoom = "";
        availableRooms = new JTextArea("Available Rooms\n");
        
    }

    /**
     * this runs the Guest view
     */
    public void run()
    {
    	//login screen
    	JPanel userLogPanel = userLogIn();
    	
        //checks to see if the user is in the system.
    	Guest g1 = guest;
        Guest guest = hotelDataModel.display(g1);
        
        if( guest == null )
        {
        	//guestView.displayForFirstTimeUser();
        	displayForFirstTimeUser();
        }
        
       //View/create reservation

    	
    }
    /**
     * User login GUI
     * 
     * @return the Panel to insert User name
     */
    public JPanel userLogIn()
    {

    	JLabel nameLabel = new JLabel("User Name: ");
    	JLabel IdLabel = new JLabel("User ID: ");
    	final JTextField UserName = new JTextField("User Name");
        final JTextField UserID = new JTextField("User ID");
        JButton logInButton = new JButton("Login"); // submit button to log in
           
        logInButton.addActionListener(new ActionListener()
   	         {
   	            public void actionPerformed(ActionEvent event)
   	            {
   	            	String theUserName = UserName.getName();
   	             	int theUserID = Integer.parseInt( UserID.getText() );
   	             	guest = new Guest(theUserName, theUserID);
   	            }
   	         });

    	JPanel logIn = new JPanel();
    	JPanel logInSouth = new JPanel();
        logIn.setLayout( new BorderLayout() );
        logInSouth.setLayout( new BorderLayout() );
        logInSouth.add(IdLabel, BorderLayout.WEST);
        logInSouth.add(UserID, BorderLayout.CENTER);
        logInSouth.add(logInButton, BorderLayout.EAST);
        logIn.add(nameLabel, BorderLayout.WEST);
        logIn.add(UserName, BorderLayout.CENTER);
        logIn.add(logInSouth, BorderLayout.SOUTH);
    	
    	return logIn;
    }

    /**
     * GUI for a new user input
     * new user inputs info for new user
     * 
     * @return the JPanel to view the log in
     */
    public JPanel displayForFirstTimeUser()
    {
    	
        final JTextField getUserName = new JTextField("Enter New User Name");
        final JTextField getUserID = new JTextField("Enter New User ID");
    	JLabel nameLabel = new JLabel("User Name: ");
    	JLabel IdLabel = new JLabel("User ID: ");
        JButton newUserButton = new JButton("Submit"); // submit button to add users
        
        newUserButton.addActionListener(new ActionListener()
   	         {
   	            public void actionPerformed(ActionEvent event)
   	            {
   	            	String newUserName = getUserName.getText();
   	            	int newUserID = Integer.parseInt( getUserID.getText() );
   	             guest = new Guest(newUserName, newUserID);
   	             }
   	         });
                
        hotelDataModel.addGuest(guest);
        
        JPanel firstTimeUserPanel = new JPanel();
        JPanel firstUserSouthPanel = new JPanel();
        firstTimeUserPanel.setLayout( new BorderLayout() );
        firstUserSouthPanel.setLayout( new BorderLayout() );
        firstUserSouthPanel.add(IdLabel, BorderLayout.WEST);
        firstUserSouthPanel.add(getUserID, BorderLayout.CENTER);
        firstUserSouthPanel.add(newUserButton, BorderLayout.EAST);
        firstTimeUserPanel.add(nameLabel, BorderLayout.WEST);
        firstTimeUserPanel.add(getUserName, BorderLayout.CENTER);
        firstTimeUserPanel.add(firstUserSouthPanel, BorderLayout.SOUTH);        
        
        return firstTimeUserPanel;
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
    	JPanel createView = new JPanel();
        JButton makeReservationButton = new JButton("Make Reservation");
        JButton viewOrCancelReservationButton = new JButton("View/Cancel Reservation");
        
        ActionListener makeReservationListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                displayMakeReservationDialog();
            }
        };
        
        ActionListener viewOrCancelReservationListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                viewOrCancelReservation();
            }
        };
        
        createView.add(makeReservationButton);
        createView.add(viewOrCancelReservationButton);
        //makeReservationButton.addActionListener(makeReservationListener);
        //viewOrCancelReservationButton.addActionListener(viewOrCancelReservationListener);
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
           typeOfRoom to the appropriate String. (coded already)
          
         */
        JButton standardButton = new JButton("Standard");
        JButton luxuaryButton = new JButton("Luxury");

        ActionListener standardButtonListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                typeOfRoom = "standard";
                hotelDataModel.setFilteredData(null, null, typeOfRoom);
            }
        };
        
        ActionListener luxuryButtonListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                typeOfRoom = "luxury";
                hotelDataModel.setFilteredData(null, null, typeOfRoom);
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
                hotelDataModel.updateToAddReservation(copyOfHotelRooms.get(0), null, null);
                Room addReservationToGuestRecords = copyOfHotelRooms.get(0);
                addReservationToGuestRecords.setCheckInDate(null);
                addReservationToGuestRecords.setCheckOutDate(null);
                guest.addToGuestReservations(addReservationToGuestRecords);
            }
        };

    } // make reservation 

   

    public void viewOrCancelReservation()
    {
        ArrayList<Room> reservationsByGuest = guest.getGuestReservations();
        ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
    
        /*
        Loop through the reservationsByGuest ArrayList and create JCheckBoxes out of each element inside the arrayList
        and then add them to the JFrame or the component that is going to be used to display the GUI.(You can use the 
        toStringMethod of the room to name each JCheckBox so that is will display the room name and check in check out dates. 
        Might need two toString methods one for available rooms because that will display only the room name. And another 
        that displays the room name and check in and checkout dates for cancel option
        */
        for(int i = 0; i<reservationsByGuest.size();i++)
        {
            JCheckBox checkBox = new JCheckBox( reservationsByGuest.get(i).cancelToString() );
            //frame/component.add(checkBox);
            checkBoxes.add(checkBox);
        }
        
        /*
        Add a JButton |CANCEL| so that all the check boxes that were selected then their appropriate reservations are deleted from data
        model.(remember we are not deleting a room from data model but we are deleting from the bookingDates of the room. 
        */
        JButton cancel = new JButton("Cancel");
        ActionListener cancelButtonListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                for(int i = 0; i < checkBoxes.size(); i++)
                {
                    if( checkBoxes.get(i).isSelected() )
                    {
                        hotelDataModel.updateToCancelReservation( reservationsByGuest.get(i), reservationsByGuest.get(i).getCheckInDate(),  reservationsByGuest.get(i).getCheckOutDate() );
                    }
                }
            }
        };
       
        cancel.addActionListener(cancelButtonListener);

    }// view or cancel reservation
  
    
    public void stateChanged(ChangeEvent e)
    {
    	copyOfHotelRooms = hotelDataModel.getFilteredData();
        for(int i = 0; i < copyOfHotelRooms.size(); i++)
        {
            availableRooms.append("\n" + copyOfHotelRooms.get(i).toString() );
        }
    }

}
