import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Solution to group project 2 for CS151-01.
 * Copyright(C) Luke Sieben, Nathan Kong, and Ravi Sharma
 * Version 2014-11-11
 */
public class HotelReservationSystemTester {

    public static void main(String[]args) {
        // setup data
        
        Manager manager = new Manager("Bob");

        //20 rooms 10 regular and 10 luxury
        ArrayList<Room> rooms = new ArrayList<Room>();
        for (int i = 1; i <= 20; i++)
        {
        	if ( i < 10 )
        	{
        		rooms.add( new Room(80, "regular") );
        	}
        	rooms.add( new Room(200, "luxury") );
        }
        
                
        HotelRoomsDataModel dataOfRooms = new HotelRoomsDataModel(rooms);

        final GuestView guestView = new GuestView(dataOfRooms);
        final ManagerView managerView = new ManagerView(dataOfRooms);

        dataOfRooms.attach(guestView);
        dataOfRooms.attach(managerView);

        // make the frame
        int width = 600;
        int height = 600;

        final JFrame frame = new JFrame("Hotel Reservation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);

        // make the buttons
        JButton managerButton = new JButton("Manager");
        managerButton.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                frame.add(managerView);
            }
        });

        JButton guestButton = new JButton("Guest");
        guestButton.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                frame.add(guestView);
            }
        });

        // make the panel
        int rows = 0;
        int columns = 1;

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(rows, columns));
        panel.add(managerButton);
        panel.add(guestButton);
        frame.add(panel);
    }
}
