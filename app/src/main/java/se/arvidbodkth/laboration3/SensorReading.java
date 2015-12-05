package se.arvidbodkth.laboration3;

/**
 * Created by Arvid on 2015-12-04.
 *
 */
public class SensorReading {

    private double x, y, z;

    public SensorReading(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double compareReading(SensorReading s){
        return (s.getX()-x + s.getY()-y + s.getZ()-z)/3;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
