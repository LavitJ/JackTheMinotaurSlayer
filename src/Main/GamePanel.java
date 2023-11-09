package Main;

import javax.swing.JPanel;

import inputs.KeyboardInput;
import inputs.MouseInput;

import java.awt.Dimension;
import java.awt.Graphics;

public class GamePanel extends JPanel{
    private MouseInput mouseinput;
    private Game game;

    public GamePanel(Game game){
        this.game = game;
        setPanelSize();
        
        mouseinput = new MouseInput(this);
        addKeyListener(new KeyboardInput(this));
        addMouseListener(mouseinput);
        addMouseMotionListener(mouseinput);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        game.render(g);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(Game.Game_Width, Game.Game_Height);
        setPreferredSize(size);
    }

    public Game getGame(){
        return game;
    }
}
