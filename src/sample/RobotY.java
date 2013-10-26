package sample;

import java.awt.Color;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class RobotY extends AdvancedRobot
{
        Enemy enemy = new Enemy();
        public static double PI = Math.PI;
             
        public void run()
        {
        	
                setAdjustGunForRobotTurn( true );
        setAdjustRadarForGunTurn( true );
        this.setColors(Color.red, Color.blue, Color.yellow, Color.black, Color.green);
        setTurnRadarRightRadians(2 * PI);
                while(true){
                        
                        this.setTurnGunRight(360);
                        
                        this.execute();
                        this.setTurnGunLeft(360);
                        
                        this.execute();
//                        turnGunLeft(360);
//                        
//                        turnGunLeft(this.getGunTurnRemaining()+10);
                        if(this.getGunTurnRemaining()==0)
                        	this.fire(1);
                        
                }
    }
             
    public void onScannedRobot(ScannedRobotEvent e)
    {
            enemy.update(e,this);
        double Offset = rectify( enemy.direction - getRadarHeadingRadians() );
        setTurnRadarRightRadians( Offset * 1.5);
    }
  //角度修正方法，重要
    public double rectify ( double angle )
    {
        if ( angle < -Math.PI )
            angle += 2*Math.PI;
        if ( angle > Math.PI )
            angle -= 2*Math.PI;
        return angle;
    }
    public class Enemy {
            public double x,y;
            public String name = null;
            public double headingRadian = 0.0D;
        public double bearingRadian = 0.0D;
        public double distance = 1000D;
        public double direction = 0.0D;
        public double velocity = 0.0D;
        public double prevHeadingRadian = 0.0D;
        public double energy = 100.0D;
        
        
        public void update(ScannedRobotEvent e,AdvancedRobot me){
                name = e.getName();
                headingRadian = e.getHeadingRadians();
                bearingRadian = e.getBearingRadians();
                this.energy = e.getEnergy();
                this.velocity = e.getVelocity();
                this.distance = e.getDistance();
                direction = bearingRadian + me.getHeadingRadians();
                x = me.getX() + Math.sin( direction ) * distance;
                y= me.getY() + Math.cos( direction ) * distance;
        }
    }
} 