package agh.ics.oop;
import static java.lang.System.out;



public class World {
    public static void main(String[] args) {

        OptionsParser parser = new OptionsParser();
        MoveDirection[] directions = parser.parse("f b b l r asd l");

        Animal cat = new Animal();
        for(MoveDirection dir: directions){
            cat.move(dir);
        }
    }
}
