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
 * Version 2014-11-14
 ********************************************************/
public class HotelReservationSystemTester {

    public static void main(String[] args) {
        HotelModel hotelModel = Manager.load();

        // no save data exists so make empty rooms
        if(hotelModel == null) {
            //20 rooms 10 regular and 10 luxury
            ArrayList<Room> rooms = new ArrayList<Room>();
            for (int i = 1; i <= 20; i++) {
                if (i < 10) {
                    rooms.add( new Room(80, "regular", i) );
                }
                else {
                    rooms.add( new Room(200, "luxury", i) );
                }
            }

            hotelModel = new HotelModel(rooms);
        }

        // make manager after setting up hotel model
        final Manager manager = new Manager(hotelModel);
        
        final GuestPanel guestPanel = new GuestPanel(hotelModel);
        //final ManagerPanel managerPanel = new ManagerPanel(dataOfRooms);                          comment out to get Guest View going
        
        //attach listeners
        hotelModel.attach(guestPanel);
        //dataOfRooms.attach(managerPanel);							comment out to get Guest view going

        //CREATE FRAME / CENTER ON SCREEN
        final JFrame frame = new JFrame("Hotel Reservation System");
        final int width = 600;
        final int height = 600;
        frame.setSize(width, height);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation( dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2 );

        //BUTTONS
        //RUN THE MANAGER VIEW
        JButton managerButton = new JButton("Manager");
        managerButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                frame.add(new ManagerPanel(manager));
            }
        });

        //RUN THE GUEST VIEW
        JButton guestButton = new JButton("Guest");
        guestButton.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
            	//frame.add( guestPanel.userLogIn() ); // just for testing
            	guestPanel.run();
            }
        });

        // make the panel and add to the frame
        int rows = 0;
        int columns = 1;

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(rows, columns));
        mainPanel.add(managerButton);
        mainPanel.add(guestButton);
        frame.add(mainPanel);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
