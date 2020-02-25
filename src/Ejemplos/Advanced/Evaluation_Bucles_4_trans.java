package Ejemplos.Advanced;

public class Evaluation_Bucles_4_trans {

    public static void main(String[] args) {
        String[] fruits = new String[] { "Orange", "Apple", "Pear", "Strawberry" };
        String[] animals = new String[] { "Bear", "Monkey" };
        int x = 1;
        if (x <= 3) {
            Object[] result = method_loop_1(fruits, animals, x);
            fruits = (String[]) result[0];
            animals = (String[]) result[1];
            x = (Integer) result[2];
        }
        if (x <= 6) {
            Object[] result = method_loop_2(animals, x);
            animals = (String[]) result[0];
            x = (Integer) result[1];
        }
        System.out.println();
    }

    public static Object[] method_loop_1(String[] fruits, String[] animals, int x) {
        {
            System.out.println(" " + x);
            for (String fruit : fruits) {
                System.out.println(fruit);
                for (String animal : animals) {
                    System.out.println(animal);
                }
            }
            x++;
        }
        if (x <= 3) {
            return method_loop_1(fruits, animals, x);
        }
        return new Object[] { fruits, animals, x };
    }

    public static Object[] method_loop_2(String[] animals, int x) {
        {
            for (String animal : animals) {
                System.out.println(animal);
                x++;
            }
        }
        if (x <= 6) {
            return method_loop_2(animals, x);
        }
        return new Object[] { animals, x };
    }
}
