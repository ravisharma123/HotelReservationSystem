import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ravi Sharma
 */
public class GuestView extends JPanel implements ChangeListener{
    private Guest guest;
    private ArrayList<Room> a;
    private HotelRoomsDataModel hotelDataModel;
    private String typeOfRoom;
    private JTextArea availableRooms;
    public GuestView(HotelRoomsDataModel setHotelDataModel){
        hotelDataModel=setHotelDataModel;
        guest=null;
        a=hotelDataModel.getData();
        typeOfRoom="";
        availableRooms=new JTextArea("Available Rooms\n");
    }
    public void display(Guest setGuest){
        guest=setGuest;
    /*
    Use GUI to make 2 buttons one is |MAKE RESERVATION| and other is |VIEW/CANCEL RESERVATION|
    and then add ActionListeners to them so that when they are clicked appropriate method is called. (coded already)
    */
        JButton makeReservationButton = new JButton("Make Reservation");
        JButton viewOrCancelReservationButton = new JButton("View/Cancel Reservation");
        ActionListener makeReservationListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                displayMakeReservationDialog();
            }
        };
        ActionListener viewOrCancelReservationListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                viewOrCancelReservation();
            }
        };
        makeReservationButton.addActionListener(makeReservationListener);
        viewOrCancelReservationButton.addActionListener(viewOrCancelReservationListener);
    }
    public void displayMakeReservationDialog(){
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
        ActionListener standardButtonListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                typeOfRoom="standard";
                hotelDataModel.setFilteredData(null, null, typeOfRoom);
            }
        };
        ActionListener luxuryButtonListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                typeOfRoom="luxury";
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
        ActionListener confirmedButtonListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                hotelDataModel.updateToAddReservation(a.get(0), null, null);
                Room addReservationToGuestRecords = a.get(0);
                addReservationToGuestRecords.setCheckInDate(null);
                addReservationToGuestRecords.setCheckOutDate(null);
                guest.addToGuestReservations(addReservationToGuestRecords);
            }
        };

    }
    public void displayForFirstTimeUser(){
        JTextField getUserName = new JTextField("Enter New User Name");
        JTextField getUserID = new JTextField("Enter New User ID");
        String newUserName=getUserName.getText();
        int newUserID = Integer.parseInt(getUserID.getText());
        Guest newGuest = new Guest(newUserName,newUserID);
        HotelReservationSystemTester.guests.add(newGuest);
        display(newGuest);
    }
    public void viewOrCancelReservation(){
        ArrayList<Room> reservationsByGuest = guest.getGuestReservations();
        ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
        /*
        Loop through the reservationsByGuest ArrayList and create JCheckBoxes out of each element inside the arrayList
        and then add them to the JFrame or the component that is going to be used to display the GUI.(You can use the 
        toStringMethod of the room to name each JCheckBox so that is will display the room name and check in check out dates. 
        Might need two toString methods one for available rooms because that will display only the room name. And another 
        that displays the room name and check in and checkout dates for cancel option
        */
        for(int i=0; i<reservationsByGuest.size();i++){
            JCheckBox checkBox = new JCheckBox(reservationsByGuest.get(i).cancelToString());
            //frame/component.add(checkBox);
            checkBoxes.add(checkBox);
        }
        /*
        Add a JButton |CANCEL| so that all the check boxes that were selected then their appropriate reservations are deleted from data
        model.(remember we are not deleting a room from data model but we are deleting from the bookingDates of the room. 
        */
        JButton cancel = new JButton("Cancel");
        ActionListener cancelButtonListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<checkBoxes.size();i++){
                    if(checkBoxes.get(i).isSelected()){
                        hotelDataModel.updateToCancelReservation(reservationsByGuest.get(i), reservationsByGuest.get(i).getCheckInDate(),  reservationsByGuest.get(i).getCheckOutDate());
                    }
                }
            }
        };
        cancel.addActionListener(cancelButtonListener);

    }
    @Override
    public void stateChanged(ChangeEvent e) {
        a=hotelDataModel.getFilteredData();
        for(int i=0;i<a.size();i++){
            availableRooms.append("\n"+a.get(i).toString());
        }
    }

}
