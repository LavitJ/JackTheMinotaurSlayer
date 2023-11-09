package Main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class GameWindow extends JFrame{
    JFrame frame;

    public GameWindow(GamePanel gamePanel){
        frame = new JFrame();
        frame.add(gamePanel);
        frame.pack();
        frame.setResizable(false);
        frame.setTitle("Jack The Minotaur Slayer");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowGainedFocus(WindowEvent e) {
                gamePanel.getGame().windowLostFocus();     
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
            
        });
    }
}
