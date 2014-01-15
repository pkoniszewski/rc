/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Zolci;

import java.awt.Point;
import robocode.AdvancedRobot;
import robocode.Condition;

/**
 *
 * @author Marcin
 */
public class CustomMoveCompleteCondition extends Condition {

    private AdvancedRobot robot;
    private Point destination;

    
    public CustomMoveCompleteCondition(AdvancedRobot robot, Point destination) {
        super();
        this.robot = robot;
        this.destination = destination;
    }

    /**
     * Tests if the robot has stopped moving.
     *
     * @return {@code true} if the robot has stopped moving; {@code false}
     * otherwise
     */
    @Override
    public boolean test() {
        return ((Math.abs(robot.getX() - destination.getX()) < 5) && (Math.abs(robot.getY() - destination.getY()) < 5) );
    }

    /**
     * Called by the system in order to clean up references to internal objects.
     *
     * @since 1.4.3
     */
    @Override
    public void cleanup() {
        robot = null;
    }
}
