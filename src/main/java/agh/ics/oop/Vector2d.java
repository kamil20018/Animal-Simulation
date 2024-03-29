package agh.ics.oop;
import static java.lang.System.out;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return "(" + x + ", " + y + ")";
    }

    public boolean precedes(Vector2d other){
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other){
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d upperRight(Vector2d other){
        int biggerX = Math.max(this.x, other.x);
        int biggerY = Math.max(this.y, other.y);

        return new Vector2d(biggerX, biggerY);
    }

    public Vector2d lowerLeft(Vector2d other){
        int lowerX = Math.min(this.x, other.x);
        int lowerY = Math.min(this.y, other.y);

        return new Vector2d(lowerX, lowerY);
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public Vector2d opposite(){
        return new Vector2d(-this.x, -this.y);
    }

    @Override
    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }
    @Override
    public int hashCode(){
        int result = 17;
        result = result * 31 + this.x;
        result = result * 31 + this.y;
        return result;
    }
}
