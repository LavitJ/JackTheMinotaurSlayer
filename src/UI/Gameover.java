package UI;

import java.awt.*;
import java.awt.event.KeyEvent;

import Gamestate.Gamestate;
import Gamestate.Playing;
import Main.Game;
public class Gameover {

    private Playing playing;
    private Font font1, font2;
    public Gameover(Playing playing) {
        this.playing = playing;
        this.font1 = new Font("Arial", Font.PLAIN, 30);
        this.font2 = new Font("Arial", Font.PLAIN, 20);
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.Game_Width, Game.Game_Height);

        g.setColor(Color.red);
        g.setFont(font1);
        g.drawString("Game Over!", (Game.Game_Width / 2) - 50, 250);
        g.setColor(Color.white);
        g.setFont(font2);
        g.drawString("Press esc to enter Main Menu!", (Game.Game_Width / 2) - 110, 350);

    }

}
