package greatestteam;


import CTFApi.CaptureTheFlagApi;
import robocode.*;
//import java.awt.Color;

/**
 * PowerfulRobot - a robot by (your name here)
 */
public class PowerfulRobot extends CaptureTheFlagApi
{
	/**
	 * Note that CaptureTheFlagApi inherits TeamRobot, so users can directly use functions of TeamRobot.
	 */
	 
	/**
	 * run: PowerfulRobot's default behaviour
	 */
        @Override
	public void run() {
		/**
		 * registerMe() needs to be called for every robot. Used for initialisation. 
		 */
		
		registerMe(); 
		
		// Write your logic here
		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:
		//setColors(Color.red,Color.blue,Color.green);
		
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			ahead(100);
			turnGunRight(360);
			back(100);
			turnGunRight(360);
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
        @Override
	public void onScannedRobot(ScannedRobotEvent e) {
		fire(1);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
        @Override
	public void onHitByBullet(HitByBulletEvent e) {
		turnLeft(90 - e.getBearing());
	}
	
        @Override
	public void onHitObject(HitObjectEvent event) {
	/**
	* onHitObject: What to do when you hit an object. (obstacle,flag or wall)
	*/
	}
	
        @Override
	public void onHitObstacle(HitObstacleEvent e) {
		// Replace the next 3 lines with any behavior you would like
		back(20);
		turnRight(30);
		ahead(30);
	}
	
        @Override
	public void onHitWall(HitWallEvent e) {
		// Replace the next 3 lines with any behavior you would like
		back(20);
		turnRight(30);
		ahead(30);
	}
	
        @Override
	public void onScannedObject(ScannedObjectEvent e) {
		if (e.getObjectType().equals("flag")) {
			e.getBearing();
		}
	}
	
}
