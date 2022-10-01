package asteroids;

import javafx.scene.shape.Polygon;

public class Projectile extends shape {

    public Projectile(int x, int y) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
    }

    public void accelerate() {
        double changeX = Math.cos(Math.toRadians(this.getShape().getRotate()));
        double changeY = Math.sin(Math.toRadians(this.getShape().getRotate()));

        changeY*=2;
        changeX*=2;

        this.setMovement(this.getMovement().add(changeX, changeY)) ;
    }
}