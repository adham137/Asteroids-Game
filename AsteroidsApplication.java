package asteroids;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class AsteroidsApplication extends Application {
    public static int WIDTH = 1000;
    public static int HEIGHT = 500 ;

    private int points = 0;

    public void start(Stage window){
        //pane and scene creations
        Pane mainPane = new Pane();
        mainPane.setPrefSize(WIDTH,HEIGHT);
        Scene scene = new Scene(mainPane);
        Text text = new Text(10, 20, "Points: "+this.points);
        mainPane.getChildren().add(text);

        //ship and asteroid creations
        Ship ship = new Ship(WIDTH/2,HEIGHT/2);
        mainPane.getChildren().add(ship.getShape());

        ArrayList<Asteroid> asteroids = new ArrayList<>();
        for(int i=0 ; i<5 ; i++){

            Asteroid asteroid = new Asteroid(new Random().nextInt(WIDTH / 3),new Random().nextInt(HEIGHT));
            asteroids.add(asteroid);
        }
        asteroids.stream().forEach((asteroid)->{
            mainPane.getChildren().add(asteroid.getShape());
        });

        ArrayList<Projectile> projectiles = new ArrayList<>();



        //hashmap of all keys and weather they are pressed or no.
        HashMap<KeyCode,Boolean> pressedKeys = new HashMap<>();
        scene.setOnKeyPressed((event)->{
            pressedKeys.put(event.getCode(),true);
        });                  //sets the values of the keys
        scene.setOnKeyReleased((event)->{
            pressedKeys.put(event.getCode(),false);
        });





        new AnimationTimer() {

            @Override
            public void handle(long now) { //Animator

                //Creation of projectiles when SPACE is pressed and limited to 3 projectiles
                if(pressedKeys.getOrDefault(KeyCode.SPACE , false)&& projectiles.stream().filter(p->p.getIsAlive()==true).count() < 3){
                    Projectile projectile = new Projectile((int)ship.getShape().getTranslateX(),(int)ship.getShape().getTranslateY());
                    projectile.getShape().setRotate(ship.getShape().getRotate());

                    projectile.accelerate();



                    projectiles.add(projectile);
                    mainPane.getChildren().add(projectile.getShape());

                }

                //If left key is pressed , rotate left
                if(pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                    ship.turnLeft();

                }

                //If right key is pressed , rotate right
                if(pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                    ship.turnRight();

                }

                //If forward key is pressed , accelerate
                if(pressedKeys.getOrDefault(KeyCode.UP, false)) {
                    ship.accelerate();

                }

                asteroids.forEach(asteroid -> {
                    if(asteroid.collide(ship)&&asteroid.getIsAlive()==true){
                        stop();
                    }
                }); //stopping animator if ship collides with asteroids

                projectiles.forEach(projectile -> {
                    asteroids.forEach((asteroid -> {
                        if(projectile.collide(asteroid) && projectile.getIsAlive()==true && asteroid.getIsAlive()==true){
                            projectile.setAlive(false);
                            asteroid.setAlive(false);
                            points += 1000;
                            text.setText("Points: "+points);
                        }
                    }));
                }); //sets the projectile and asteroid that collide as "not alive" & updates the points

                asteroids.forEach((asteroid -> {
                    if(asteroid.getIsAlive()==false){
                        mainPane.getChildren().remove(asteroid.getShape());

                    }
                }));  //removes asteroids if they are "not alive"
                projectiles.forEach((projectile -> {
                    if(projectile.getIsAlive()==false){
                        mainPane.getChildren().remove(projectile.getShape());

                    }
                })); //removes projectiles if they are "not alive"

                if(Math.random() < 0.005) {
                    Asteroid asteroid = new Asteroid(WIDTH, HEIGHT);
                    if(!asteroid.collide(ship)) {
                        asteroids.add(asteroid);
                        mainPane.getChildren().add(asteroid.getShape());
                    }
                } //adds an asteroid 5% of the times the animator object is called , only "spawns" an asteroid if it does not collide with the ship

                ship.move();
                asteroids.forEach(asteroid -> {
                    asteroid.move();
                });
                projectiles.forEach((projectile -> {projectile.move();}));
            }
        }.start();

        //Stage creation
        window.setTitle("Asteriods");
        window.setScene(scene);
        window.show();
    }
    public static void main(String[] args) {
        launch(AsteroidsApplication.class);
    }


}
