package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Vector2dTest {
    @Test
    public void equalsTest(){
        Vector2d test1 = new Vector2d(2, 3);
        Vector2d test2 = new Vector2d(2, 3);
        Vector2d test3 = new Vector2d(-2, -3);

        Assertions.assertTrue(test1.equals(test2));
        Assertions.assertFalse(test1.equals(test3));
        Assertions.assertFalse(test2.equals(test3));
    }

    @Test
    public void toStringTest(){
        Vector2d test1 = new Vector2d(2, 3);
        Vector2d test2 = new Vector2d(-2, -3);
        Vector2d test3 = new Vector2d(-123456789, 987654321);

        Assertions.assertTrue(test1.toString().equals("(2, 3)"));
        Assertions.assertTrue(test2.toString().equals("(-2, -3)"));
        Assertions.assertTrue(test3.toString().equals("(-123456789, 987654321)"));
    }

    @Test
    public void precedesTest(){
        Vector2d test1 = new Vector2d(1, 1);
        Vector2d test2 = new Vector2d(0, 1);
        Vector2d test3 = new Vector2d(1, 0);
        Vector2d test4 = new Vector2d(0, 0);

        Assertions.assertTrue(test1.precedes(test1));
        Assertions.assertTrue(test2.precedes(test1));
        Assertions.assertFalse(test1.precedes(test2));

        Assertions.assertFalse(test2.precedes(test3));
        Assertions.assertFalse(test3.precedes(test2));

        Assertions.assertTrue(test4.precedes(test1));
        Assertions.assertFalse(test1.precedes(test4));
    }

    @Test
    public void followsTest(){
        Vector2d test1 = new Vector2d(1, 1);
        Vector2d test2 = new Vector2d(0, 1);
        Vector2d test3 = new Vector2d(1, 0);
        Vector2d test4 = new Vector2d(0, 0);

        Assertions.assertTrue(test1.follows(test1));
        Assertions.assertFalse(test2.follows(test1));
        Assertions.assertTrue(test1.follows(test2));

        Assertions.assertFalse(test2.follows(test3));
        Assertions.assertFalse(test3.follows(test2));

        Assertions.assertFalse(test4.follows(test1));
        Assertions.assertTrue(test1.follows(test4));
    }

    @Test
    public void upperRightTest(){
        Vector2d test1 = new Vector2d(0, 0);
        Vector2d test2 = new Vector2d(-5, 0);
        Vector2d test3 = new Vector2d(0, -4);
        Vector2d test4 = new Vector2d(-3, -2);
        Vector2d test5 = new Vector2d(0, 1);

        Assertions.assertTrue(test1.upperRight(test2).equals(new Vector2d(0, 0)));
        Assertions.assertTrue(test2.upperRight(test3).equals(new Vector2d(0, 0)));
        Assertions.assertTrue(test3.upperRight(test4).equals(new Vector2d(0, -2)));
        Assertions.assertTrue(test4.upperRight(test5).equals(new Vector2d(0, 1)));
        Assertions.assertTrue(test1.upperRight(test1).equals(new Vector2d(0, 0)));
    }

    @Test
    public void lowerLeftTest(){
        Vector2d test1 = new Vector2d(0, 0);
        Vector2d test2 = new Vector2d(-5, 0);
        Vector2d test3 = new Vector2d(0, -4);
        Vector2d test4 = new Vector2d(-3, -2);
        Vector2d test5 = new Vector2d(0, 1);

        Assertions.assertTrue(test1.lowerLeft(test2).equals(new Vector2d(-5, 0)));
        Assertions.assertTrue(test1.lowerLeft(test3).equals(new Vector2d(0, -4)));
        Assertions.assertTrue(test2.lowerLeft(test3).equals(new Vector2d(-5, -4)));
        Assertions.assertTrue(test3.lowerLeft(test4).equals(new Vector2d(-3, -4)));
        Assertions.assertTrue(test5.lowerLeft(test5).equals(new Vector2d(0, 1)));
    }

    @Test
    public void addTest(){
        Vector2d test1 = new Vector2d(1, 10);
        Vector2d test2 = new Vector2d(9, -1);
        Vector2d test3 = new Vector2d(-4, -8);
        Vector2d test4 = new Vector2d(-6, 5);

        Assertions.assertTrue(test1.add(test2).equals(new Vector2d(10, 9)));
        Assertions.assertTrue(test2.add(test3).equals(new Vector2d(5, -9)));
        Assertions.assertTrue(test3.add(test4).equals(new Vector2d(-10, -3)));
        Assertions.assertTrue(test4.add(test4).equals(new Vector2d(-12, 10)));

        Assertions.assertFalse(test1.add(test4).equals(new Vector2d(21, 37)));
    }

    @Test
    public void subtractTest(){
        Vector2d test1 = new Vector2d(1, 10);
        Vector2d test2 = new Vector2d(9, -1);
        Vector2d test3 = new Vector2d(-4, -8);
        Vector2d test4 = new Vector2d(-6, 5);

        Assertions.assertTrue(test1.subtract(test2).equals(new Vector2d(-8, 11)));
        Assertions.assertTrue(test2.subtract(test3).equals(new Vector2d(13, 7)));
        Assertions.assertTrue(test4.subtract(test3).equals(new Vector2d(-2, 13)));
        Assertions.assertTrue(test4.subtract(test4).equals(new Vector2d(0, 0)));

        Assertions.assertFalse(test2.subtract(test4).equals(new Vector2d(123, 345)));
    }

    @Test
    public void oppositeTest(){
        Vector2d test1 = new Vector2d(0, 0);
        Vector2d test2 = new Vector2d(-5, 4);
        Vector2d test3 = new Vector2d(3, -6);
        Vector2d test4 = new Vector2d(-10, -11);

        Assertions.assertTrue(test1.opposite().equals(new Vector2d(0, 0)));
        Assertions.assertTrue(test2.opposite().equals(new Vector2d(5, -4)));
        Assertions.assertTrue(test3.opposite().equals(new Vector2d(-3, 6)));
        Assertions.assertTrue(test4.opposite().equals(new Vector2d(10, 11)));

        Assertions.assertFalse(test4.opposite().equals(new Vector2d(19, 19)));
    }

}
