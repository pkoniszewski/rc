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
            // Replace the next 4 lines with any behavior you would like
            //ahead(100);
            //turnGunRight(360);
            //back(100);
            //turnGunRight(360);
            
            goTo(new Point(50, 50));
            goTo(new Point(50, 1150));
            goTo(new Point(850, 1150));
            goTo(new Point(850, 50));
        }
    }

    /**
     * onScannedRobot: What to do when you see another robot
     */
    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        boolean teammateSpotted = false;
        
        for (String teammate : getTeammates()) {
            if (teammate.equals(e.getName())) {
                teammateSpotted = true;
                break;
            }
        }
        
        if (!teammateSpotted) {
            fire(1);
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
        back(20);
        turnRight(90);
        goTo(currentDestination);
    }

    @Override
    public void onHitWall(HitWallEvent e) {
        // Replace the next 3 lines with any behavior you would like
        back(20);
        turnRight(90);
        goTo(currentDestination);
    }

    @Override
    public void onScannedObject(ScannedObjectEvent e) {
        if (e.getObjectType().equals("flag")) {
            e.getBearing();
        }
    }

    private void goTo(Point destination) {
       
        currentDestination = new Point(destination);
        
        double currentX = getX();
        double currentY = getY();
        
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
        
        for (int i = 0; i < 50; i+=5) {
                turnLeft(5);
                ahead(2);
            }
        
        while (Math.abs(getX() - destination.x) > 20  || Math.abs(getY() - destination.y) > 20) {
            
            for (int i = 0; i < 100; i+=5) {
                turnRight(5);
                ahead(2);
            }
            for (int i = 0; i < 100; i+=5) {
                turnLeft(5);
                ahead(2);
            }
        }
        
        //stop();
    }
}
