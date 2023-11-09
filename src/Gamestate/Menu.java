package Gamestate;

import Main.Game;
import UI.MenuButton;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu extends State implements Statemethod{

    private MenuButton[] buttons = new MenuButton[2];
    private BufferedImage BG;
    private Font font;

    public Menu(Game game){
        super(game);
        loadButtons();
        loadBackground();
    }

    private void loadBackground() {
        BG = LoadSave.GetSpriteAtlas(LoadSave.MENU_BG);
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(Game.Game_Width / 2,Game.Game_Height / 4, 0, Gamestate.PLAYING);
        buttons[1] = new MenuButton((Game.Game_Width / 2),(Game.Game_Height / 4) + 150, 1, Gamestate.QUIT);
    }

    @Override
    public void update() {
        for (MenuButton mb : buttons){
            mb.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(BG, 0, 0, 1280, 820, null);

        for (MenuButton mb : buttons){
            mb.draw(g);
        }

        drawName(g);
    }

    public void drawName(Graphics g){
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("res/Minecraft.ttf"));
            font = font.deriveFont(50f);
            g.setFont(font);
            g.setColor(new Color(240,141,69));
            g.drawString("Jack The Minotaur Slayer", (Game.Game_Width / 4) + 20, 100);
        }
        catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons){
            if (isIn(e, mb)){
                mb.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton mb : buttons){
            if (isIn(e, mb)){
                if (mb.isMousePressed())
                    mb.applyGamestate();
                break;
            }
        }

        resetButtons();
    }

    private void resetButtons() {
        for (MenuButton mb : buttons){
            mb.resetBools();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton mb : buttons){
            mb.setMouseOver(false);
        }

        for (MenuButton mb : buttons){
            if (isIn(e, mb)){
                mb.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            Gamestate.state = Gamestate.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
