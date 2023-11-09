package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Gamestate.Gamestate;
import Main.GamePanel;

public class KeyboardInput implements KeyListener{
    private GamePanel gamePanel;

    public KeyboardInput (GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
     }

    @Override
    public void keyPressed(KeyEvent e) {
        if (Gamestate.state == Gamestate.MENU){
            gamePanel.getGame().getMenu().keyPressed(e);
        }
        else if (Gamestate.state == Gamestate.PLAYING){
            gamePanel.getGame().getPlaying().keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (Gamestate.state == Gamestate.MENU){
            gamePanel.getGame().getMenu().keyReleased(e);
        }
        else if (Gamestate.state == Gamestate.PLAYING){
            gamePanel.getGame().getPlaying().keyReleased(e);
        }
    }
    
}
