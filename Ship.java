package asteroids;


import javafx.scene.shape.Polygon;

public class Ship extends shape{

    public Ship(int x, int y) {
        super(new Polygon(-10, -10, 20, 0, -10, 10),x,y);
    }

}