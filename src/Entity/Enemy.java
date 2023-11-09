package Entity;

import Gamestate.Playing;
import level.GameObject;
import utilz.Constants;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends Entity {
    private Playing playing;
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 15;
    private boolean aniIndexChangeAble = true;
    private boolean moving = false, attacking = false, dead = false, hurt = false;
    private int enemyAction = Constants.EnemyConstants.Idle;
    private float enemySpeed = 0.5f;
    private boolean moveable = false;
    private boolean moveRight;
    private float minX , maxX;
    private long startTime;
    private long pauseDuration = 2000000000;
    private float attackRangeX = 90, attackRangeY = 50;
    private int attackDamage = 15;
    private boolean canAttack = true;
    private static final float GRAVITY = 1f;
    private long lastAttackTime;
    private long dyingAnimationTime = 1000;
    private long dyingStartTime;
    private long attackCoolDown = 10000;
    private boolean removeAble = false;
    private int maxHp = 10;
    private int hp = maxHp;

    public Enemy(Playing playing, float x, float y, int width, int height, float minX, float maxX) {
        super(x, y, width, height);
        this.playing = playing;
        this.minX = minX;
        this.maxX = maxX;
        this.moveRight = true;
        loadAnimation();
    }

    public void update() {
        updatePosition();
        updateHitbox();
        checkPlayerDistance();
        updateDyingAnimation();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        if (aniIndexChangeAble) {
            g.drawImage(animations[enemyAction][aniIndex], (int) x, (int) y, null);
        }
        drawHitbox(g);
    }

    protected void loadAnimation() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.ENEMY_ATLAS);
        animations = new BufferedImage[5][6];

        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j * 173, i * 118, 155, 116);
            }
        }
    }

    public void setAnimation() {
        int startAnimation = enemyAction;

        if (moving)
            enemyAction = Constants.EnemyConstants.Walk;
        else
            enemyAction = Constants.EnemyConstants.Idle;

        if (attacking)
            enemyAction = Constants.EnemyConstants.Attack;
        else if (dead){
            enemyAction = Constants.EnemyConstants.Dying;
        }


        if (startAnimation != enemyAction)
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

                if (dead && (aniIndex >= Constants.EnemyConstants.getAnimationAmount(Constants.EnemyConstants.Dying))){
                    aniIndexChangeAble = false;
                }

                if (aniIndex >= Constants.EnemyConstants.getAnimationAmount(enemyAction) && aniIndexChangeAble) {
                    aniIndex = 0;
                    attacking = false;
                }
        }
    }

    public void updatePosition() {
        float newX = x;
        float newY = y;

        if (System.nanoTime() - startTime > pauseDuration) {
            moving = true;
            moveable = true;
            startTime = System.nanoTime();
        }
        else {
            moving = false;
        }

        if (moveable && OnGround()){
            if (moveRight) {
                newX += enemySpeed;
                moving = true;
            } else {
                newX -= enemySpeed;
                moving = true;
            }
            if (newX <= minX || newX >= maxX) {
                moveRight = !moveRight;
                moveable = false;
            }
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


    }

    private boolean OnGround() {
        int enemyY = (int) this.y + this.height + 18;

        for (GameObject object : playing.getLevelManager().getObjects()) {
            if (isEnemyOnObject(enemyY, object)) {
                return true;
            }
        }

        return false;
    }

    private boolean isEnemyOnObject(int enemyY, GameObject object) {
        int offset = 100;
        return this.x + offset >= object.x && this.x <= object.x + object.width - 40 &&
                enemyY >= object.y && enemyY <= object.y + object.height;
    }

    protected boolean checkObjectCollisions(float newX, float newY) {
        Rectangle newHitbox = new Rectangle((int) newX, (int) newY + 18, hitbox.width, hitbox.height);

        for (Rectangle object : playing.getLevelManager().getObjects()) {
            if (newHitbox.intersects(object)) {
                return true;
            }
        }

        return false;
    }

    protected void drawHitbox(Graphics g){
        g.setColor(new Color(0,0,0,0));
        g.drawRect(hitbox.x +50, hitbox.y + 17, hitbox.width, hitbox.height);
    }

    public void checkPlayerDistance() {
        float distanceX = Math.abs(playing.getPlayer().x - this.x);
        float distanceY = Math.abs(playing.getPlayer().y - this.y);

        if (distanceX <= attackRangeX && distanceY <= attackRangeY && canAttack) {
            attacking = true;
            moveable = false;
            lastAttackTime = System.currentTimeMillis();
            attackPlayer(playing.getPlayer());
            canAttack = false;
        }
        else {
            if (System.currentTimeMillis() - lastAttackTime >= attackCoolDown) {
                canAttack = true;
            }
        }
    }

    protected void reduceHealth(int damage){
        if (hp > 0) {
            hp -= damage;
            //System.out.println(hp);
        }

        if (hp <= 0) {
            dead = true;
            dyingStartTime = System.currentTimeMillis();
            enemySpeed = 0;
        }
    }

    private void updateDyingAnimation() {
        if (dead){
            long deltaTime = System.currentTimeMillis() - dyingStartTime;
            if (deltaTime > dyingAnimationTime) {
                removeAble = true;
            }
        }
    }

    private void attackPlayer(Player player) {
        player.reduceHealth(attackDamage);
    }

    public boolean isRemoveAble() {
        return removeAble;
    }

}
