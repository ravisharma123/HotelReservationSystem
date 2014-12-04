import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 * Version 2014-12-03
 ********************************************************/
public class HotelReservationSystemTester {
    /**
     * The main method.
     * @param args the args
     */
    public static void main(String[] args) {
        HotelModel hotelModel = Manager.load();

        // no save data exists so make empty rooms
        if(hotelModel == null) {
            //20 rooms 10 regular and 10 luxury
            ArrayList<Room> rooms = new ArrayList<Room>();
            for (int i = 1; i <=20; i++) {
                if (i <= 10) {
                    rooms.add( new Room(false, i) );
                }
                else {
                    rooms.add( new Room(true, i) );
                }
            }

            hotelModel = new HotelModel(rooms);
        }

        // make manager after setting up hotel model
        final Manager manager = new Manager(hotelModel);

        //CREATE FRAME / CENTER ON SCREEN
        final JFrame frame = new JFrame("Hotel Reservation System");
        final int width = 600;
        final int height = 600;
        frame.setSize(width, height);
        frame.setLayout(new BorderLayout());

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation( dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2 );

        // make the selection panel and add to the frame
        int rows = 0;
        int columns = 1;

        final JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new GridLayout(rows, columns));

        final JPanel managerPanel = new ManagerPanel(manager);
        final GuestPanel guestPanel = new GuestPanel(hotelModel);

        final JButton backButton = new JButton("Go back to user selection");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backButton.setVisible(false);

                frame.remove(managerPanel);
                frame.remove(guestPanel);

                frame.add(selectionPanel, BorderLayout.CENTER);
                selectionPanel.repaint();
                selectionPanel.revalidate();
            }
        });
        backButton.setVisible(false);

        // open manager panel and add a back button
        JButton managerButton = new JButton("Manager");
        managerButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                frame.remove(selectionPanel);
                frame.add(managerPanel, BorderLayout.CENTER);
                backButton.setVisible(true);
                frame.revalidate();
                frame.repaint();
            }
        });

        // open guest panel and add a back button
        JButton guestButton = new JButton("Guest");
        guestButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                frame.remove(selectionPanel);
                guestPanel.displayLogin();
            	frame.add(guestPanel, BorderLayout.CENTER);
                backButton.setVisible(true);
                frame.revalidate();
            }
        });

        frame.add(backButton, BorderLayout.NORTH);
        selectionPanel.add(managerButton);
        selectionPanel.add(guestButton);
        frame.add(selectionPanel, BorderLayout.CENTER);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
