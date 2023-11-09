package Entity;

import Gamestate.Playing;
import Main.Game;
import level.GameObject;
import utilz.Constants;
import utilz.Constants.PlayerConstants;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    private static final float GRAVITY = 1f;
    private static final float JUMP_FORCE = -80f;
    private Playing playing;
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 15;
    private int playerAction = PlayerConstants.Idle;
    private boolean moving = false, attacking = false, hurt = false, dead = false;
    private boolean aniIndexChangeAble = true;
    private boolean canAttack = true;
    private int attackDamage = 5;
    private int attackRange = 70;
    private boolean left, up, right, down;
    private float playerSpeed = 1.0f;
    private Long startJumpTime;
    private long lastJumpTime;
    private float jumpTime = 300000000f;
    private int maxHp = 100;
    private int currentHp = maxHp;
    private long IFrame = 1500;
    private long lastHurtTime;
    private long attackCooldown = 1000;
    private long lastAttackTime;
    private BufferedImage healthBar;
    private int healthBarWidth = 250;
    private int healthBarHeight = 40;
    private int healthWidth = healthBarWidth;

    public Player(Playing playing, float x, float y, int width, int height) {
        super(x, y, width, height);
        this.playing = playing;
        loadAnimation();
    }

    public void update() {
        updateHealthBar();
        updateJumpPosition();
        updatePosition();
        updateHitbox();
        updateHurtAnimation();
        updateAttackCooldown();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int) x, (int) y, null);
        drawHitbox(g);
        drawHealthBar(g);
    }

    protected void drawHitbox(Graphics g){
        g.setColor(new Color(0, 0, 0, 0));
        g.drawRect(hitbox.x + 40, hitbox.y + 50, hitbox.width, hitbox.height);
    }

    public void drawHealthBar(Graphics g) {
        g.drawImage(healthBar, 30, 20, healthBarWidth, healthBarHeight, null);
        g.setColor(Color.RED);
        g.fillRect(70, 40, healthWidth, 8);
    }
    private void updateHealthBar() {
        healthWidth = (int) ((currentHp / (float) maxHp) * (healthBarWidth - 45));
    }

    protected void loadAnimation() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[6][8];

        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j * 130, i * 130, 110, 130);
            }
        }

        healthBar = LoadSave.GetSpriteAtlas(LoadSave.HEALTH_BAR);
    }

    public void updateJumpPosition(){
        if (startJumpTime != null) {
            long time = System.nanoTime() - startJumpTime;
            float deltaTime = (time - lastJumpTime) / 100000000f;
            if (lastJumpTime == startJumpTime) {
                deltaTime = 0;
            }
            lastJumpTime = time;

            float timeSec = time / 100000000f;
            y += (float) (deltaTime * JUMP_FORCE * Math.sin((timeSec) * (90 / (jumpTime / 100000000f)) * 0.0174532925));

            if (time > jumpTime) {
                startJumpTime = null;
                lastJumpTime = 0;
            }
        }
    }

    public void updatePosition() {
        if (!dead){
            moving = false;

            float newX = x;
            float newY = y;

            if (left && !right) {
                newX -= playerSpeed;
                moving = true;
            } else if (right && !left) {
                newX += playerSpeed;
                moving = true;
            }
            if (up && !down && OnGround()) {
                startJumpTime = System.nanoTime();
                lastJumpTime = startJumpTime;
            }
            if (down && OnGround()) {
                y += 100;
            }

            if (!OnGround()) {
                newY += GRAVITY;
            }

            if (!checkObjectCollisions(newX, newY)) {
                x = newX;
                y = newY;
            } else {
                newY = y;
            }

            if (x + (width + 40) >= Game.Game_Width) {
                x = Game.Game_Width - (width + 40);
            } else if (x < -40) {
                x = -40;
            }

            if (y < -60) {
                y = -60;
            }
        }
    }

    private boolean OnGround() {
        int playerY = (int) this.y + this.height + 51;

        for (GameObject object : playing.getLevelManager().getObjects()) {
            if (isPlayerOnObject(playerY, object)) {
                return true;
            }
        }

        return false;
    }

    private boolean isPlayerOnObject(int playerY, GameObject object) {
        int offset = 100;
        return this.x + offset >= object.x && this.x <= object.x + object.width - 40 &&
                playerY >= object.y && playerY <= object.y + object.height;
    }

    protected boolean checkObjectCollisions(float newX, float newY) {
        Rectangle newHitbox = new Rectangle((int) newX + 41, (int) newY + 51, hitbox.width, hitbox.height);

        for (Rectangle object : playing.getLevelManager().getObjects()) {
            if (newHitbox.intersects(object)) {
                return true;
            }
        }

        return false;
    }


    public void setAnimation() {
        int startAnimation = playerAction;

        if (moving)
            playerAction = PlayerConstants.Run;
        else
            playerAction = PlayerConstants.Idle;

        if (attacking)
            playerAction = PlayerConstants.Attack1;
        else if (hurt)
            playerAction = PlayerConstants.Hurt;
        else if (dead)
            playerAction = PlayerConstants.Dead;


        if (startAnimation != playerAction)
            resetAnimationTick();
    }

    private void resetAnimationTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updateAnimationTick() {
        aniTick++;

        if (aniTick >= aniSpeed && aniIndexChangeAble) {
            aniTick = 0;
            aniIndex++;

            if (dead && (aniIndex >= Constants.PlayerConstants.getAnimationAmount(PlayerConstants.Dead))){
                aniIndexChangeAble = false;
            }

            if (aniIndex >= PlayerConstants.getAnimationAmount(playerAction) && aniIndexChangeAble) {
                aniIndex = 0;
                attacking = false;
            }
        }
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void resetDirection() {
        left = false;
        up = false;
        right = false;
        down = false;
    }

    public void setAttack(boolean attack) {
        if (!hurt && canAttack) {
            this.attacking = attack;

            if (attacking) {
                canAttack = false;
                performAttack();
                lastAttackTime = System.currentTimeMillis();
            }
        }
    }

    private void performAttack() {
        for (Enemy enemy : playing.getEnemies()) {
            float distanceX = Math.abs(this.x - enemy.x);
            float distanceY = Math.abs(this.y - enemy.y);

            if (distanceX <= attackRange && distanceY <= attackRange) {
                enemy.reduceHealth(attackDamage);
            }
        }
    }

    private void updateAttackCooldown() {
        if (!canAttack && System.currentTimeMillis() - lastAttackTime >= attackCooldown) {
            canAttack = true;
            lastAttackTime = System.currentTimeMillis();
        }
    }

    public void reduceHealth(int damage) {
        if (!hurt  && currentHp > 0){
            currentHp -= damage;
            //System.out.println(currentHp);
            lastHurtTime = System.currentTimeMillis();

            if (currentHp > 0) {
                hurt = true;
            }
            else {
                dead = true;
                playing.setGameOver(true);
            }
        }
    }

    private void updateHurtAnimation() {
        if (System.currentTimeMillis() - lastHurtTime > IFrame){
            hurt = false;
        }
    }

    public void setDead(boolean dead){
        this.dead = dead;
    }

}