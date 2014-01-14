package greatestteam;

import CTFApi.CaptureTheFlagApi;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;
import robocode.*;
import robocode.util.*;

/**
 * PowerfulRobot - a robot by Greatest Team
 */
public class PowerfulRobot extends CaptureTheFlagApi {

    private Point currentDestination;
    boolean flagCaptured = false;
    
    HashMap<String, Point2D.Double> teamMap = new HashMap<String, Point2D.Double>();
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
        System.out.println(getName());
        // Write your logic here
        // After trying out your robot, try uncommenting the next line:
        //setColors(Color.red,Color.blue,Color.green);
back(Math.random()*200);
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
        if(isTeammate(e.getName()))return;
        aim(e);
        
        if (e.getDistance() < 100) {
            setAhead(0);
            execute();
        }
        else {
           // goTo(currentDestination);
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
        setTurnRadarRight(360);
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
        
        public Point2D.Double getPosition() {
                return position;
        }

        public void setPosition(Point2D.Double position) {
                this.position = position;
        }
    }
    
    
    private void aim(ScannedRobotEvent e) {
        double bulletPower = Math.min(3.0, getEnergy());
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
//y = tg(a)*(x-x0)+y0
// 0 = Ax + By + C
// 0 = tg(a)*x + (-1)*y + (y0 - tg(a)*x0)
// d = |Ax+By+C| / sqrt(AA+BB)
// d = |tg(a)*x - y + y0 - tg(a)*x0| / sqrt(tg(a)^2 + 1)
        double aim = getGunHeadingRadians() + Utils.normalRelativeAngle(theta - getGunHeadingRadians());
        int vdir = -1;
        if((aim < Math.PI/2) || (aim > Math.PI*3/2))vdir=1;
        int hdir = -1;
        if(aim < Math.PI)hdir=1;
        double oaim=aim;
        aim = Math.PI/2 - aim;
        double A=Math.tan(aim);
        boolean vertical = false;
        if(Double.isNaN(A)){
            vertical=true;
        }
        double B = -1;
        double C = myY - A * myX;
        boolean dontshoot=false;
        for (Point.Double p : teamMap.values()) {
            int vdir2=-1;
            if(p.y > myY)vdir2=1;
            int hdir2=-1;
            if(p.x > myX)hdir2=1;
            System.out.println(p.x+" "+p.y+" .. "+oaim);
            System.out.println("("+vdir+" "+hdir+") ("+vdir2+" "+hdir2+")");
            // && ((p.x!=myX)&&(p.y!=myY))
            if(((vdir!=vdir2) || (hdir!=hdir2)))continue;
            if(Math.sqrt((myX-p.x)*(myX-p.x)+(myY-p.y)*(myY-p.y))<e.getDistance()){
                if(vertical){
                    if(Math.abs(p.x-myX) < 36){dontshoot=true;break;}
                }
                if((Math.abs(A*p.x+B*p.y+C)/Math.sqrt(A*A+B*B))<36){dontshoot=true;break;}
            }
        }
        System.out.println("");
        if(dontshoot)return;
        setTurnRadarRightRadians(
                Utils.normalRelativeAngle(absoluteBearing - getRadarHeadingRadians()));
        setTurnGunRightRadians(Utils.normalRelativeAngle(theta - getGunHeadingRadians()));
        fire(bulletPower);
    }
}
