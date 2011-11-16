package alex.classes;

/**
 *
 * @author Alexei Yasko
 * Class that represented weapon
 */
public class Weapon {
    private String type = "";
    private String name = "";
    private double weight = 0;
    private double length = 0;
    private double caliber = 0;
    private double speadOfTheBullet = 0;

    @Override
    public String toString(){
        return type + " " + name + " " + Double.toString(weight) + " " +
                Double.toString(length) + " " + Double.toString(caliber) + " " +
                Double.toString(speadOfTheBullet) + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Weapon other = (Weapon) obj;
        if ((this.type == null) ? (other.type != null) : !this.type.equals(other.type)) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (Double.doubleToLongBits(this.weight) != Double.doubleToLongBits(other.weight)) {
            return false;
        }
        if (Double.doubleToLongBits(this.length) != Double.doubleToLongBits(other.length)) {
            return false;
        }
        if (Double.doubleToLongBits(this.caliber) != Double.doubleToLongBits(other.caliber)) {
            return false;
        }
        if (Double.doubleToLongBits(this.speadOfTheBullet) != Double.doubleToLongBits(other.speadOfTheBullet)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.weight) ^ (Double.doubleToLongBits(this.weight) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.length) ^ (Double.doubleToLongBits(this.length) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.caliber) ^ (Double.doubleToLongBits(this.caliber) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.speadOfTheBullet) ^ (Double.doubleToLongBits(this.speadOfTheBullet) >>> 32));
        return hash;
    }

    public String getType()
    { return type; }

    public String getName()
    { return name; }

    public double getWeight()
    { return weight; }

    public double getLength()
    { return length; }

    public double getCaliber()
    { return caliber; }


    public double getSpeadOfTheBullet()
    { return speadOfTheBullet; }


    public void setType(String aType)
    { type = aType; }

    public void setName(String aName)
    { name = aName; }

    public void setWeight(double aWeight)
    { weight = aWeight; }

    public void setLength(double aLength)
    { length = aLength; }

    public void setCaliber(double aCaliber)
    { caliber = aCaliber; }


    public void setSpeadOfTheBullet(double aSpeadOfTheBullet)
    { speadOfTheBullet = aSpeadOfTheBullet; }
}
