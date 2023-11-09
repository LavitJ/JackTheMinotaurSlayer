package UI;

import Gamestate.Gamestate;
import Main.Game;
import utilz.LoadSave;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static utilz.Constants.UI.Buttons;

public class MenuButton {
    private int x, y, rowIndex, index;
    private Gamestate state;
    private int xOffsetCenter = Buttons.B_WIDTH / 2;
    private BufferedImage[] imgs;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;


    public MenuButton (int xPos, int yPos, int rowIndex, Gamestate state) {
        this.x = xPos;
        this.y = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImgs();
        initBounds();

    }

    private void initBounds() {
        bounds = new Rectangle(x - xOffsetCenter, y, Buttons.B_WIDTH, Buttons.B_HEIGHT);
    }

    private void loadImgs() {
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTON);
        for (int i = 0; i < imgs.length; i++)
            imgs[i] = temp.getSubimage(i * Buttons.B_WIDTH_DEFAULT, rowIndex * Buttons.B_HEIGHT_DEFAULT, Buttons.B_WIDTH_DEFAULT, Buttons.B_HEIGHT_DEFAULT);
    }

    public void draw(Graphics g) {
        g.drawImage(imgs[index], x - xOffsetCenter, y, Buttons.B_WIDTH, Buttons.B_HEIGHT, null);
    }

    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }


    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void applyGamestate() {
        Gamestate.state = state;
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }
}
