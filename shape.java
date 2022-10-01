package asteroids;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class shape {

    private Polygon shape;
    private Point2D movement;
    private boolean isAlive;

    public shape(Polygon polygon ,int x, int y) {
        this.shape = polygon;
        this.shape.setTranslateX(x);
        this.shape.setTranslateY(y);
        this.isAlive=true;

        this.movement = new Point2D(0, 0);
    }

    public Polygon getShape() {
        return shape;
    }

    public void turnLeft() {
        this.shape.setRotate(this.shape.getRotate() - 5);
    }

    public void turnRight() {
        this.shape.setRotate(this.shape.getRotate() + 5);
    }

    public void move() {
        this.shape.setTranslateX(this.shape.getTranslateX() + this.movement.getX());
        this.shape.setTranslateY(this.shape.getTranslateY() + this.movement.getY());

        this.checkShapeIsInsideScreen();
        }

    public void accelerate() {
        double changeX = Math.cos(Math.toRadians(this.shape.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.shape.getRotate()));

        changeY*=0.05;
        changeX*=0.05;

        this.movement = this.movement.add(changeX, changeY);
    }
    public boolean collide(shape other) {
        Shape collisionArea = Shape.intersect(this.shape, other.getShape());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }

    public Point2D getMovement() {
        return movement;
    }

    public void setMovement(Point2D movement) {
        this.movement = movement;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    private void checkShapeIsInsideScreen(){
        if (this.shape.getTranslateX() < 0) {
            this.shape.setTranslateX(this.shape.getTranslateX() + AsteroidsApplication.WIDTH);
        }

        if (this.shape.getTranslateX() > AsteroidsApplication.WIDTH) {
            this.shape.setTranslateX(this.shape.getTranslateX() % AsteroidsApplication.WIDTH);
        }

        if (this.shape.getTranslateY() < 0) {
            this.shape.setTranslateY(this.shape.getTranslateY() + AsteroidsApplication.HEIGHT);
        }

        if (this.shape.getTranslateY() > AsteroidsApplication.HEIGHT) {
            this.shape.setTranslateY(this.shape.getTranslateY() % AsteroidsApplication.HEIGHT);
        }
    }
}