package level;

import Main.Game;
import utilz.LoadSave;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    private Game game;
    private BufferedImage[] Stage;
    private List<GameObject> objects;

    public LevelManager(Game game){
        this.game = game;
        Stage = new BufferedImage[1];
        Stage[0] = LoadSave.GetSpriteAtlas(LoadSave.LEVEL1_ATLAS);

        objects = new ArrayList<>();
        addObject();
    }

    public void addObject(){
            objects.add(new GameObject(670, 80, 270, 5, "platform"));
            objects.add(new GameObject(470, 155, 150, 5, "platform"));
            objects.add(new GameObject(115, 198, 140, 5, "platform"));
            objects.add(new GameObject(1110, 210, 135, 5, "platform"));
            objects.add(new GameObject(320, 275, 105, 5, "platform"));
            objects.add(new GameObject(545, 275, 155, 5, "platform"));
            objects.add(new GameObject(838, 300, 203, 5, "platform"));
            objects.add(new GameObject(455, 392, 105, 5, "platform"));
            objects.add(new GameObject(220, 395, 130, 5, "platform"));
            objects.add(new GameObject(1115, 410, 110, 5, "platform"));
            objects.add(new GameObject(0, 500, 1250, 204, "ground"));
    }

    public void draw (Graphics g){
        g.drawImage(Stage[0], 0, 0, Game.Game_Width , Game.Game_Height, null);

        for (Rectangle object : objects) {
            g.setColor(new Color(0, 0, 0, 0));
            g.drawRect(object.x, object.y, object.width, object.height);
        }

    }

    public List<GameObject> getObjects() {
        return objects;
    }

}
