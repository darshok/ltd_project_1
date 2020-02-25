package Ejemplos.Advanced;

public class Evaluation_Bucles_5_trans {

    public static void main(String[] args) {
        int x = 10;
        if (x > 1) {
            System.out.println("Empieza bucle WHILE:");
            if (x > 2) {
                Object[] result = method_loop_1(x);
                x = (Integer) result[0];
            }
        }
    }

    public static Object[] method_loop_1(int x) {
        {
            System.out.println("Empieza bucle WHILE:");
            if (x > 3) {
                Object[] result = method_loop_2(x);
                x = (Integer) result[0];
            }
            x--;
        }
        if (x > 2) {
            return method_loop_1(x);
        }
        return new Object[] { x };
    }

    public static Object[] method_loop_2(int x) {
        {
            if (x > 4) {
                if (x > 100) {
                    Object[] result = method_loop_3(x);
                    x = (Integer) result[0];
                }
            }
            x--;
            System.out.print(" x: " + x);
        }
        if (x > 3) {
            return method_loop_2(x);
        }
        return new Object[] { x };
    }

    public static Object[] method_loop_3(int x) {
        {
        }
        if (x > 100) {
            return method_loop_3(x);
        }
        return new Object[] { x };
    }
}
