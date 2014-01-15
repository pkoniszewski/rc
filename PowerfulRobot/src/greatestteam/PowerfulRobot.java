package greatestteam;

import CTFApi.CaptureTheFlagApi;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;
import robocode.*;
import robocode.util.*;

/**
 * PowerfulRobot - a robot by Greatest Team
 */
public class PowerfulRobot extends CaptureTheFlagApi {

    private Random random = new Random();
    private MoveCompleteCondition moveCompleteContidion = new MoveCompleteCondition(this);
    private Point currentDestination;
    boolean flagCaptured = false;
    HashMap<String, Point2D.Double> teamMap = new HashMap<String, Point2D.Double>();
    String flagCarrier = "";
    boolean up, left;

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
        //back(Math.random() * 200);
        addCustomEvent(new Condition("RobotStopped") {

            @Override
            public boolean test() {
                return (getVelocity()==0);
            }
        });
        if (this.getX() < 450) {
            left = true;
        } else {
            left = false;
        }

        if (this.getY() < 600) {
            up = false;
        } else {
            up = true;
        }

        //drugi w wezyku
        if ((getY() > 450 && getY() < 550) || (getY() > 650 && getY() < 750)) {
            for (int i = 0; i < 15; i++) {
                doNothing();
            }
        }

        //trzeci w wezyku
        if (getY() > 550 && getY() < 650) {
            for (int i = 0; i < 30; i++) {
                doNothing();
            }
        }


        if (up) {
            makeMove(new Point((int) this.getX(), 1170));
              waitFor(new CustomMoveCompleteCondition(this, currentDestination));
        } else {
            makeMove(new Point((int) this.getX(), 70));
              waitFor(new CustomMoveCompleteCondition(this, currentDestination));
        }
        //back(Math.random()*200);
        
        sendMessage(MessageType.SIMPLE_POSITION);
        
        while (true) {
            sendMessage(MessageType.SIMPLE_POSITION);
            if (left) {
                if (up) {
                    makeMove(new Point(870, 1170));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(870, 30));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(30, 30));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(30, 1170));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(820, 1120));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(820, 80));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(80, 80));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(80, 1120));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                } else {
                    makeMove(new Point(820, 80));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(820, 1120));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(80, 1120));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(80, 80));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(870, 30));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(870, 1170));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(30, 1170));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(30, 30));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                }
            } else {
                if (up) {
                    makeMove(new Point(30, 1170));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(30, 30));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(870, 30));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(870, 1170));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(80, 1120));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(80, 80));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(820, 80));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(820, 1120));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                } else {
                    makeMove(new Point(80, 80));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(80, 1120));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(820, 1120));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(820, 80));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(30, 30));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(30, 1170));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(870, 1170));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                    makeMove(new Point(870, 30));
                      waitFor(new CustomMoveCompleteCondition(this, currentDestination));
                }
            }
            sendMessage(MessageType.SIMPLE_POSITION);
        }
    }

    private int eventCounter=0;
    @Override
    public void onCustomEvent(CustomEvent event) {
        super.onCustomEvent(event); //To change body of generated methods, choose Tools | Templates.
        Condition cd = event.getCondition();
        if(cd.getName().equals("RobotStopped")){
         //   eventCounter++;
        //    if(eventCounter > 10){
              //  System.out.println("jade dalej");
            setTurnRadarRight(360);
                makeMove(currentDestination);
         //       eventCounter=0;
                
        //    }
        }
    }

    @Override
    public void onRobotDeath(RobotDeathEvent event) {
        super.onRobotDeath(event); //To change body of generated methods, choose Tools | Templates.
        if (isTeammate(event.getName())) {
            teamMap.remove(event.getName());
        }
    }

    /**
     * onScannedRobot: What to do when you see another robot
     */
    @Override
    public void onScannedRobot(ScannedRobotEvent e) {

        if (e.getDistance() < 30) {
            stop();
        } else {
            resume();
        }
        
        System.out.println("scanned robot");

        if (isTeammate(e.getName())) {
            return;
        }

        aim(e);
        makeMove(currentDestination);
    }

    private boolean inFrontOfMeFacingSameDirection(ScannedRobotEvent e) {
        return Math.abs(e.getBearing()) < 30;
    }

    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        setTurnRadarLeft(360);
        execute();
        System.out.println("hit by bullet");
    }

    @Override
    public void onHitObject(HitObjectEvent event) {

        System.out.println("hit object");

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
        } else if (event.getType().equals("base") && getOwnBase().contains(new Point2D.Double(getX(), getY()))) {
            if (flagCaptured) {
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
    
    void sendMessage(MessageType msg)
    {
        StateMessage stateMsg = new StateMessage(new Point2D.Double(getX(), getY()), msg);
        try
        {
            broadcastMessage(stateMsg);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public void onMessageReceived(MessageEvent e) {
        Object objMsg = e.getMessage();

        if (objMsg instanceof StateMessage) {
            StateMessage msg = (StateMessage) objMsg;
            String robot = e.getSender();
            teamMap.put(robot, msg.getPosition());
            MessageType msgType = MessageType.values()[msg.getMessageType()];

            switch (msgType) {
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
                    System.out.println(robot + ": SIMPLE_POSITION");
                    //ahead(100);
                    break;
            }
        }

    }

    @Override
    public void onHitObstacle(HitObstacleEvent e) {
        System.out.println("hit obstacle");

        // Replace the next 3 lines with any behavior you would like
//        setBack(30);
//        turnRight(90);
//        setAhead(30);
      //  makeMove(currentDestination);
    }

    @Override
    public void onHitWall(HitWallEvent e) {
        System.out.println("hit wall");

        // Replace the next 3 lines with any behavior you would like
//        setBack(30);
//        turnRight(90);
//        setAhead(30);
       // makeMove(currentDestination);
    }

    @Override
    public void onHitRobot(HitRobotEvent e) {
        // Replace the next 3 lines with any behavior you would like
//        setBack(20);
//        turnRight(90);
//        setAhead(40);
//        makeMove(currentDestination);
        System.out.println("hit robot");
//        turnLeft(90 - e.getBearing());
//        ahead(30);
      //  makeMove(currentDestination);
    }

    @Override
    public void onScannedObject(ScannedObjectEvent e) {
        System.out.println("scanned object");
        sendMessage(MessageType.SIMPLE_POSITION);
        if (e.getObjectType().equals("flag")) {
            e.getBearing();
        }
        sendMessage(MessageType.SIMPLE_POSITION);
    }

    private void goTo(Point destination) {

        double currentX = getX();
        double currentY = getY();

        currentDestination = new Point(destination);

        double azimuthTg = Math.abs(destination.x - currentX) / Math.abs(destination.y - currentY);
        double relativeAzimuthInRad = Math.atan(azimuthTg);
        double relativeAzimuthInDeg = relativeAzimuthInRad * 180 / Math.PI;

        double heading = getHeading();
        double azimuthInDeg;

        if (currentX > destination.x) {
            relativeAzimuthInDeg += 90;
        } else {
            relativeAzimuthInDeg = 90 - relativeAzimuthInDeg;
        }

        if (currentY > destination.y) {
            relativeAzimuthInDeg *= -1;
        }

        azimuthInDeg = 90 - relativeAzimuthInDeg - heading;

        if (destination.y == currentY) {
            if (currentX < destination.x) {
                azimuthInDeg = 90;
            } else {
                azimuthInDeg = -90;
            }
        }
        
        if (azimuthInDeg < -180) {
            azimuthInDeg = 360 + azimuthInDeg;
        }

        if (azimuthInDeg > 180) {
            azimuthInDeg = 360 - azimuthInDeg;
        }

        if (azimuthInDeg >= 0) {
            turnRight(azimuthInDeg);
        } else {
            turnLeft(-azimuthInDeg);
        }

        double distance = Math.sqrt(Math.pow(destination.x - currentX, 2) + Math.pow(destination.y - currentY, 2));
        setAhead(distance);
        execute();
    }

    public static enum MessageType implements Serializable {

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

        aim = Math.PI / 2 - aim;
        double A = Math.tan(aim);
        double A2 = 0;
        boolean vertical = false;
        boolean horizontal = false;
        if (Double.isNaN(A)) {
            vertical = true;
            A = 0;
        } else {
            if (A == 0) {
                horizontal = true;
            } else {
                A2 = -1 / A;
            }
        }
        double B = -1;
        double C = myY - A * myX;
        double C2 = myY - A2 * myX;
        System.out.println(C + " " + C2);
        boolean dontshoot = false;
        Point.Double mypos = new Point.Double(myX, myY);
        boolean targetside = determineSide(A2, C2, new Point.Double(enemyX, enemyY),
                mypos, horizontal, vertical);
//        System.out.println(targetside+":");
        for (Point.Double p : teamMap.values()) {
            boolean tankside = determineSide(A2, C2, p, mypos, horizontal, vertical);
//            System.out.println(" "+targetside);
            if (targetside != tankside) {
                continue;
            }

            if (Math.sqrt((myX - p.x) * (myX - p.x) + (myY - p.y) * (myY - p.y)) < e.getDistance()) {
                if (vertical) {
                    if (Math.abs(p.x - myX) < 36) {
                        dontshoot = true;
                        break;
                    }
                }
                if ((Math.abs(A * p.x + B * p.y + C) / Math.sqrt(A * A + B * B)) < 36) {
                    dontshoot = true;
                    break;
                }
            }
        }
//        System.out.println("");
        gA=A;gB=B;gC=C;gA2=A2;gC2=C2;
        if (dontshoot) {
            return;
        }
        setTurnRadarRightRadians(
                Utils.normalRelativeAngle(absoluteBearing - getRadarHeadingRadians()));
        setTurnGunRightRadians(Utils.normalRelativeAngle(theta - getGunHeadingRadians()));
        
        waitFor(new Condition("gunRotated") {
            
            @Override
            public boolean test() {
                return getGunTurnRemaining() == 0;
            }
        });
        fire(bulletPower);
    }
    // y = mx+b

    private boolean determineSide(double m, double b, Point.Double p, Point.Double o, boolean horizontal, boolean vertical) {
        if (horizontal) {
            return p.y > b;
        }
        if (vertical) {
            return p.x > o.x;
        }
        if (m > 0) {
            return p.y > m * p.x + b;
        } else {
            return p.y < m * p.x + b;
        }

    }
    double gA,gB,gC,gA2,gC2;
    	public void onPaint(Graphics2D g) {
		// Draw a red cross hair with the center at the current aim
		// coordinate (x,y)
//		g.drawOval(aimX - 15, aimY - 15, 30, 30);
//		g.drawLine(aimX, aimY - 4, aimX, aimY + 4);
//		g.drawLine(aimX - 4, aimY, aimX + 4, aimY);
                //y=Ax+C
                int x1=0;
                int x2=900;
                int y11=(int)(gA*x1+gC);
                int y12=(int)(gA*x2+gC);
                int y21=(int)(gA2*x1+gC2);
                int y22=(int)(gA2*x2+gC2);
                g.setColor(Color.RED);
                g.drawLine(x1, y11, x2, y12);
                g.setColor(Color.GREEN);
                g.drawLine(x1, y21, x2, y22);
                
	}
boolean imSupposedToBeImmobile=false;
    private void makeMove(Point destination) {
        sendMessage(MessageType.SIMPLE_POSITION);
        System.out.println("Heading for: " + destination);
        
        goTo(destination);
        setTurnRadarLeft(360);
        execute();
      
        
//        setAhead(0);
//        execute();
        sendMessage(MessageType.SIMPLE_POSITION);
    }
}
