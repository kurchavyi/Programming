package Organization;

/**
 * Class for describing field Coordinates of element
 */
public class Coordinates {
    /**
     * field x, Field value must be greater than -340, Field cannot be null
     */
    private Integer x;
    /**
     * field y, The field cannot be null
     */
    private Double y;

    public Coordinates(){}
    public Coordinates(Integer x, Double y){
        this.x = x;
        this.y = y;
    }

    /**
     * @return field y
     */
    public Double getY() {
        return y;
    }

    /**
     * @return field x
     */
    public Integer getX() {
        return x;
    }

    /**
     * @param x to which you need to replace
     */
    public void setX(Integer x) {
        this.x = x;
    }

    /**
     * @param y to which you need to replace
     */
    public void setY(Double y) {
        this.y = y;
    }

    /**
     * Method for printing this field into a string representation
     */
    @Override
    public String toString() {
        return "{\n  x: " + this.getX().toString()+ "\n" + "  y: " + this.getY().toString() + " }";
    }
}