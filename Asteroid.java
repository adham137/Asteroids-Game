package asteroids;

import javafx.scene.shape.Polygon;

import java.util.Random;

public class Asteroid extends shape{
    private double rotationalMovement;
    public Asteroid(int x, int y) {
        super(PolygonFactory.createPolygon(),x, y);

        Random rnd = new Random();

        super.getShape().setRotate(rnd.nextInt(360)); // rotates the asteroid with a random amount as it "spawns"

        int accelerationAmount = 1 + rnd.nextInt(10); // provides a random acceleration trajectory to the asteroid
        for (int i = 0; i < accelerationAmount; i++) {
            accelerate();
        }

        this.rotationalMovement = 0.5 - rnd.nextDouble();

    }

    public void move() {
        super.move();
        super.getShape().setRotate(super.getShape().getRotate() + rotationalMovement); //rotates the asteroid by a small amount(rotational movement)
    }                                                                                  //every time move() method is called.
}
