package Main;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import Entity.Enemy;
import Entity.Player;
import Gamestate.Gamestate;
import UI.MenuButton;
import level.LevelManager;

import Gamestate.Playing;
import Gamestate.Menu;

public class Game implements Runnable{
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread thread;
    private Playing playing;
    private Menu menu;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    public final static int Tiles_Default_Size = 32;
    public final static float Scale = 1.5f;
    public final static int Tiles_In_Width = 26;
    public final static int Tiles_In_Height = 14;
    public final static int Tiles_Size = (int) (Tiles_Default_Size * Scale);
    public final static int Game_Width = Tiles_Size * Tiles_In_Width;
    public final static int Game_Height = Tiles_Size * Tiles_In_Height;

    public Game(){
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();

        startGame();
    }

    private void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
    }

    public void startGame(){
        thread = new Thread(this);
        thread.start();
    }

    public void update(){

        if (Gamestate.state == Gamestate.MENU){
            menu.update();
        }
        else if (Gamestate.state == Gamestate.PLAYING){
            playing.update();
        }
        else if (Gamestate.state == Gamestate.QUIT){
            System.exit(0);
        }
    }

    public void render(Graphics g){

        if (Gamestate.state == Gamestate.MENU){
            menu.draw(g);
        }
        else if (Gamestate.state == Gamestate.PLAYING) {
            playing.draw(g);
        }
    }

    @Override
    public void run(){
        double timePerUpdate = 1000000000 / UPS_SET;
        double timePerFrame = 1000000000 / FPS_SET;
        
        int frame = 0;
        int update = 0;

        double deltaU = 0;
        double deltaF = 0;
        
        long previousTime = System.nanoTime();
        long lastCheck = System.currentTimeMillis();

        while (true){
            long currentTime = System.nanoTime();
            
            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1){
                update();
                update++;
                deltaU--;
            }

            if (deltaF >= 1){
                gamePanel.repaint();
                deltaF--;
                frame++;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
//                System.out.println("FPS: "+ frame + " | UPS: " + update);
                frame = 0;
                update = 0;
            }
        }
    }
    public void windowLostFocus(){
        if (Gamestate.state == Gamestate.PLAYING){
            playing.getPlayer().resetDirection();
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying(){
        return playing;
    }


}
