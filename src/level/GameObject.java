package level;

import java.awt.Rectangle;

public class GameObject extends Rectangle {
    private String label;

    public GameObject(int x, int y, int w, int h) {
        super(x, y, w, h);
        this.label = "";
    }

    public GameObject(int x, int y, int w, int h, String label) {
        super(x, y, w, h);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
