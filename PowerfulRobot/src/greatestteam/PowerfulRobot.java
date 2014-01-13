package greatestteam;

import CTFApi.CaptureTheFlagApi;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import robocode.*;
import robocode.util.*;
import sun.applet.Main;

/**
 * PowerfulRobot - a robot by Greatest Team
 */
public class PowerfulRobot extends CaptureTheFlagApi {

    private Point currentDestination;
    boolean flagCaptured = false;
    
    HashMap<String, Point2D> teamMap = new HashMap<String, Point2D>();
    String flagCarrier = "";
    
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
back(100);
        while (true) {
            turnRadarRight(360);
//            goTo(new Point(50, 50));
//            waitFor(new MoveCompleteCondition(this));
//            goTo(new Point(50, 1150));
//            waitFor(new MoveCompleteCondition(this));
//            goTo(new Point(850, 1150));            
//            waitFor(new MoveCompleteCondition(this));
//            goTo(new Point(850, 50));
//            waitFor(new MoveCompleteCondition(this));

            StateMessage stateMsg = new StateMessage(new Point2D.Double(getX(), getY()), MessageType.SIMPLE_POSITION);
            try {
                broadcastMessage(stateMsg);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * onScannedRobot: What to do when you see another robot
     */
    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        aim(e);
        
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
        if (event.getType().equals("flag") && getEnemyFlag().distance(getX(), getY()) < 50) {
            flagCaptured = true;
            try {
                StateMessage stateMsg = new StateMessage(new Point2D.Double(getX(), getY()), MessageType.GOT_ENEMY_FLAG);
                broadcastMessage(stateMsg);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (event.getType().equals("flag") && getOwnFlag().distance(getX(), getY()) < 50 && !getOwnBase().contains(new Point2D.Double(getX(), getY()))) {
            try {
                StateMessage stateMsg = new StateMessage(new Point2D.Double(getX(), getY()), MessageType.RETURNED_OUR_FLAG);
                broadcastMessage(stateMsg);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else if (event.getType().equals("base") && getOwnBase().contains(new Point2D.Double(getX(), getY()))) {
            if (flagCaptured)
            {
                flagCaptured = false;
                try {
                    StateMessage stateMsg = new StateMessage(new Point2D.Double(getX(), getY()), MessageType.DELIVERED_ENEMY_FLAG_TO_BASE);
                    broadcastMessage(stateMsg);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onMessageReceived(MessageEvent e)
    {
        Object objMsg = e.getMessage();
                
        if (objMsg instanceof StateMessage)
        {
            StateMessage msg = (StateMessage)objMsg;
            String robot = e.getSender();
            teamMap.put(robot, msg.getPosition());
            MessageType msgType = MessageType.values()[msg.getMessageType()];

            switch (msgType)
            {
                case GOT_ENEMY_FLAG:
                    flagCarrier = robot;
                    System.out.println(flagCarrier + " took the enemy's flag");
                    break;
                case DELIVERED_ENEMY_FLAG_TO_BASE:
                    flagCarrier = "";
                    System.out.println(robot + " delivered the enemy's flag");
                    break;
                case RETURNED_OUR_FLAG:
                    System.out.println(robot + " returned the ours's flag");
                    break;
                case SIMPLE_POSITION:
                    
                    //ahead(100);
                    break;
            }
        }

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
    
    public static enum MessageType implements Serializable
    {
        GOT_ENEMY_FLAG,
        DELIVERED_ENEMY_FLAG_TO_BASE,
        RETURNED_OUR_FLAG,
        SIMPLE_POSITION
    }
    
    public static class StateMessage implements Serializable {
        private static final long serialVersionUID = 7526472295622776147L;

        private Point2D.Double position;

        private int messageType;

        public StateMessage(Point2D.Double position, MessageType msgType) {
                this.position = position;
                this.messageType = msgType.ordinal();
        }

        public int getMessageType() {
            return messageType;
        }

        public void setMessageType(int messageType) {
            this.messageType = messageType;
        }
        
        public Point2D getPosition() {
                return position;
        }

        public void setPosition(Point2D.Double position) {
                this.position = position;
        }
    }
    
    
    private void aim(ScannedRobotEvent e) {
        double bulletPower = Math.min(2.0, getEnergy());
        double myX = getX();
        double myY = getY();
        double absoluteBearing = getHeadingRadians() + e.getBearingRadians();
        double enemyX = getX() + e.getDistance() * Math.sin(absoluteBearing);
        double enemyY = getY() + e.getDistance() * Math.cos(absoluteBearing);
        double enemyHeading = e.getHeadingRadians();
        double enemyVelocity = e.getVelocity();

        double deltaTime = 0;
        double battleFieldHeight = getBattleFieldHeight(),
                battleFieldWidth = getBattleFieldWidth();
        double predictedX = enemyX, predictedY = enemyY;
        while ((++deltaTime) * (20.0 - 3.0 * bulletPower)
                < Point2D.Double.distance(myX, myY, predictedX, predictedY)) {
            predictedX += Math.sin(enemyHeading) * enemyVelocity;
            predictedY += Math.cos(enemyHeading) * enemyVelocity;
            if (predictedX < 18.0
                    || predictedY < 18.0
                    || predictedX > battleFieldWidth - 18.0
                    || predictedY > battleFieldHeight - 18.0) {
                predictedX = Math.min(Math.max(18.0, predictedX),
                        battleFieldWidth - 18.0);
                predictedY = Math.min(Math.max(18.0, predictedY),
                        battleFieldHeight - 18.0);
                break;
            }
        }
        double theta = Utils.normalAbsoluteAngle(Math.atan2(
                predictedX - getX(), predictedY - getY()));

        setTurnRadarRightRadians(
                Utils.normalRelativeAngle(absoluteBearing - getRadarHeadingRadians()));
        setTurnGunRightRadians(Utils.normalRelativeAngle(theta - getGunHeadingRadians()));
        fire(bulletPower);
    }
}
