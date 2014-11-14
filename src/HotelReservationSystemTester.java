import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/*********************************************************
 * Runs the hotel reservation system for 
 * booking rooms and searching for rooms.
 * Managers can access and view the rooms per date
 * 
 * 
 * Solution to group project 2 for CS151-01.
 * Copyright(C) Luke Sieben, Nathan Kong, and Ravi Sharma
 * Version 2014-11-11
 ********************************************************/
public class HotelReservationSystemTester {

    public static void main(String[] args) {
        // setup data
        
        Manager manager = new Manager("Bob");

        //20 rooms 10 regular and 10 luxury
        ArrayList<Room> rooms = new ArrayList<Room>();
        for (int i = 1; i <= 20; i++)
        {
        	if ( i < 10 )
        	{	rooms.add( new Room(80, "regular") );  	}
        	else
        	{	rooms.add( new Room(200, "luxury") );	}
        }
        
        
        HotelRoomsDataModel dataOfRooms = new HotelRoomsDataModel(rooms);
        final GuestPanel guestPanel = new GuestPanel(dataOfRooms);
        //final ManagerPanel managerPanel = new ManagerPanel(dataOfRooms);                          comment out to get Guest View going
        
        //load the room data/////////////////////////////////////////////////////////
        
        
        //attach listener
        dataOfRooms.attach(guestPanel);
        //dataOfRooms.attach(managerPanel);							comment out to get Guest view going

        // make the frame dimensions
        final JFrame frame = new JFrame("Hotel Reservation System");
        
        int width = 300;
        int height = 300;
        frame.setSize(width, height);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation( dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2 );

        // make the buttons
        JButton managerButton = new JButton("Manager");
        managerButton.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                //frame.add(managerPanel);
            }
        });

        JButton guestButton = new JButton("Guest");
        guestButton.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                //cardLayout.show( guestPanel.userLogIn() );
            	frame.add( guestPanel.userLogIn() ); // just for testing
            }
        });

        // make the panel and add to the frame
        int rows = 0;
        int columns = 1;

        JPanel mainMenu = new JPanel();
        mainMenu.setLayout(new GridLayout(rows, columns));
        mainMenu.add(managerButton);
        mainMenu.add(guestButton);
        frame.add(mainMenu);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
