package greatestteam;

import CTFApi.CaptureTheFlagApi;
import java.awt.Point;
import robocode.*;

/**
 * PowerfulRobot - a robot by Greatest Team
 */
public class PowerfulRobot extends CaptureTheFlagApi {

    private Point currentDestination;
    
    /**
     * run: PowerfulRobot's default behaviour
     */
    @Override
    public void run() {
        /**
         * registerMe() needs to be called for every robot. Used for
         * initialisation.
         */
        registerMe();

        // Write your logic here
        // After trying out your robot, try uncommenting the next line:
        //setColors(Color.red,Color.blue,Color.green);

        while (true) {
            goTo(new Point(50, 50));
            waitFor(new MoveCompleteCondition(this));
            goTo(new Point(50, 1150));
            waitFor(new MoveCompleteCondition(this));
            goTo(new Point(850, 1150));            
            waitFor(new MoveCompleteCondition(this));
            goTo(new Point(850, 50));
            waitFor(new MoveCompleteCondition(this));
        }
    }

    /**
     * onScannedRobot: What to do when you see another robot
     */
    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        fire(1);
        
        if (e.getDistance() < 100) {
            setAhead(0);
            execute();
        }
        else {
            goTo(currentDestination);
        }
    }

    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    @Override
    public void onHitByBullet(HitByBulletEvent e) {
//        turnLeft(90 - e.getBearing());
    }

    @Override
    public void onHitObject(HitObjectEvent event) {
        /**
         * onHitObject: What to do when you hit an object. (obstacle,flag or
         * wall)
         */
    }

    @Override
    public void onHitObstacle(HitObstacleEvent e) {
        // Replace the next 3 lines with any behavior you would like
        setBack(20);
        turnRight(90);
        setAhead(40);
        goTo(currentDestination);
    }

    @Override
    public void onHitWall(HitWallEvent e) {
        // Replace the next 3 lines with any behavior you would like
        setBack(20);
        turnRight(90);
        setAhead(40);
        goTo(currentDestination);
    }

    @Override
    public void onHitRobot(HitRobotEvent event) {
        // Replace the next 3 lines with any behavior you would like
        setBack(20);
        turnRight(90);
        setAhead(40);
        goTo(currentDestination);
    }
    
    @Override
    public void onScannedObject(ScannedObjectEvent e) {
        if (e.getObjectType().equals("flag")) {
            e.getBearing();
        }
    }

    
    
    private void goTo(Point destination) {
        
        double currentX = getX();
        double currentY = getY();
        
        currentDestination = new Point(destination);

        System.out.println("current x, y: " + currentX + "," + currentY);

        double azimuthTg = Math.abs(destination.x - currentX) / Math.abs(destination.y - currentY);
        double relativeAzimuthInRad = Math.atan(azimuthTg);
        double relativeAzimuthInDeg = relativeAzimuthInRad * 180 / Math.PI;
        
        System.out.println("relative azimuth in deg: " + relativeAzimuthInDeg);
        
        double heading = getHeading();
        double azimuthInDeg;
        
        if (currentX > destination.x) {
            relativeAzimuthInDeg += 90; 
        }
        else {
            relativeAzimuthInDeg = 90 - relativeAzimuthInDeg;
        }
        
        if (currentY > destination.y) {
            relativeAzimuthInDeg *= -1;
        }
        
        azimuthInDeg = 90 - relativeAzimuthInDeg - heading;
        
        if (azimuthInDeg < -180) {
            azimuthInDeg = 360 + azimuthInDeg;
        }
        
        if (azimuthInDeg > 180) {
            azimuthInDeg = 360 - azimuthInDeg;
        }
        
        System.out.println("azimuth: " + azimuthInDeg);
        
        if (azimuthInDeg >= 0) {
            turnRight(azimuthInDeg);
        }
        else {
            turnLeft(-azimuthInDeg);
        }
        
        double distance = Math.sqrt(Math.pow(destination.x - currentX, 2) + Math.pow(destination.y - currentY, 2));

        setAhead(distance);
        execute();
    }
}
