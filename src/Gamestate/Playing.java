package Gamestate;

import Entity.Enemy;
import Entity.Entity;
import Entity.Player;
import Main.Game;
import UI.Gameover;
import UI.Winthegame;
import level.LevelManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class Playing extends State implements Statemethod{
    private boolean gameOver, winTheGame;
    private Entity player;
    private ArrayList<Enemy> enemies;
    private LevelManager levelManager;
    private Gameover gameOverOverlay;
    private Winthegame winTheGameOverlay;
    private int Time = 60;
    private Timer timer;
    private Font font;
    private boolean timerStarted;

    public Playing (Game game) {
        super(game);
        initClasses();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        enemies = new ArrayList<>();
        addEmemies();
        player = new Player(this, 0, 200, (int) (50), (int) (80));
        gameOverOverlay = new Gameover(this);
        winTheGameOverlay = new Winthegame(this);
        font = new Font("Arial", Font.PLAIN, 30);
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                decrementTime();
            }

        }, 1000, 1000);
    }

    private void decrementTime() {
        if (Time > 0) {
            Time--;
        }
        else {
            ((Player)player).setDead(true);
            gameOver = true;
        }
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

   public Player getPlayer() {
       return (Player)player;
   }

    public ArrayList<Enemy> getEnemies(){
        return enemies;
    }

    public LevelManager getLevelManager(){

            return levelManager;

    }
    public void addEmemies(){
        enemies.add(new Enemy(this, 300, 390, (int) (80), (int) (90), 200, 600));
        enemies.add(new Enemy(this, 800, 190, (int) (80), (int) (90), 770, 930));
        enemies.add(new Enemy(this, 100, 0, (int) (80), (int) (90), 50, 150));
        enemies.add(new Enemy(this, 700, 390, (int) (80), (int) (90), 700, 1000));
        enemies.add(new Enemy(this, 500, 165, (int) (80), (int) (90), 480, 600));
    }

    @Override
    public void update() {
        if (Gamestate.state == Gamestate.PLAYING && !timerStarted) {
            startTimer();
            timerStarted = true;
        }


        (( Player)player).update();

        for (Enemy enemy : enemies){
            enemy.update();
        }

        removeEnemy();

        if (enemies.isEmpty() && !gameOver){
            winTheGame = true;
            stopTimer();
        }
        else if (gameOver){
            stopTimer();
        }
    }

    public void removeEnemy(){
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            if (enemy.isRemoveAble()) {
                iterator.remove();
            }
        }
    }

    public void resetGame() {
        enemies.clear();
        addEmemies();
        gameOver = false;
        winTheGame = false;
        player = new Player(this, 0, 200, (int) (50), (int) (80));
        Time = 60;
        timerStarted = false;
    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g);
        player.render(g);

        for (Enemy enemy : enemies) {
            enemy.render(g);
        }

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(String.valueOf(Time), Game.Game_Width / 2, 50);

        if (gameOver){
            gameOverOverlay.draw(g);
        }
        else if (winTheGame){
            winTheGameOverlay.draw(g);
        }

    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver && Gamestate.state == Gamestate.PLAYING){
            if (e.getButton() == MouseEvent.BUTTON1)
                ((Player)player).setAttack(true);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W){
            ((Player)player).setUp(true);
        }
        else if (e.getKeyCode() == KeyEvent.VK_A){
            ((Player)player).setLeft(true);
        }
        else if (e.getKeyCode() == KeyEvent.VK_S){
            ((Player)player).setDown(true);
        }
        else if (e.getKeyCode() == KeyEvent.VK_D){
            ((Player)player).setRight(true);
        }
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE && gameOver || e.getKeyCode() == KeyEvent.VK_ESCAPE && winTheGame){
            resetGame();
            Gamestate.state = Gamestate.MENU;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W){
            ((Player)player).setUp(false);
        }
        else if (e.getKeyCode() == KeyEvent.VK_A){
            ((Player)player).setLeft(false);
        }
        else if (e.getKeyCode() == KeyEvent.VK_S){
            ((Player)player).setDown(false);
        }
        else if (e.getKeyCode() == KeyEvent.VK_D){
            ((Player)player).setRight(false);
        }
    }

}
