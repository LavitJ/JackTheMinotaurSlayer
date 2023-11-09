package utilz;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;


public class LoadSave {

    public static final String PLAYER_ATLAS = "Player.png";
    public static final String ENEMY_ATLAS = "Minotaur.png";
    public static final String LEVEL1_ATLAS = "Level_1.png";
    public static final String HEALTH_BAR = "health_bar.png";
    public static final String MENU_BUTTON = "Button.png";
    public static final String MENU_BG = "Menu_BG.png";

    public static BufferedImage GetSpriteAtlas(String fileName){
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);

    	try {
    		img = ImageIO.read(is);

    	}
    	catch (Exception e){
    		e.printStackTrace();
    	}

        return img;
    }


}
