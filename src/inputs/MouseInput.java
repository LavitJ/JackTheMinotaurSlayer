package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Gamestate.Gamestate;
import Main.GamePanel;


public class MouseInput implements MouseListener, MouseMotionListener{
    GamePanel gamePanel;

    public MouseInput(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (Gamestate.state == Gamestate.MENU) {
            gamePanel.getGame().getMenu().mouseMoved(e);
        }
        else if (Gamestate.state == Gamestate.PLAYING) {
            gamePanel.getGame().getPlaying().mouseMoved(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (Gamestate.state == Gamestate.PLAYING) {
            gamePanel.getGame().getPlaying().mouseClicked(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (Gamestate.state == Gamestate.MENU) {
            gamePanel.getGame().getMenu().mousePressed(e);
        }
        else if (Gamestate.state == Gamestate.PLAYING) {
            gamePanel.getGame().getPlaying().mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (Gamestate.state == Gamestate.MENU) {
            gamePanel.getGame().getMenu().mouseReleased(e);
        }
        else if (Gamestate.state == Gamestate.PLAYING) {
            gamePanel.getGame().getPlaying().mouseReleased(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}
