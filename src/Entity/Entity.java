package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Entity {
    protected float x, y;
    protected int width, height;
    protected Rectangle hitbox;

    public Entity(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        initHitbox();
    }

    private void initHitbox() {
        int hitboxX = (int) (x);
        int hitboxY = (int) (y);
        hitbox = new Rectangle(hitboxX, hitboxY , width, height);
    }

    protected void updateHitbox() {
        hitbox.x = (int) x;
        hitbox.y = (int) y; 
    }

    protected void drawHitbox(Graphics g){
        g.setColor(Color.RED);
        g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    public void render(Graphics g) {
        drawHitbox(g);
    }

    abstract protected void loadAnimation();
    abstract protected boolean checkObjectCollisions(float newX, float newY);
}