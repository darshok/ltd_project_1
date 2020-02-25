package Ejemplos.Advanced;

public class Evaluation_Bucles_3_trans {

    public static void main(String[] args) throws Exception {
        int x = 5;
        if (x > 0) {
            Object[] result = method_loop_1(x);
            x = (Integer) result[0];
        }
    }

    public static Object[] method_loop_1(int x) {
        {
            System.out.println("1");
            if (x > 50) {
                x--;
                System.out.println("2");
            } else if (x > 0) {
                System.out.println("3");
                if (x > 100) {
                    x++;
                    System.out.println("4");
                } else {
                    x--;
                    System.out.println("5");
                    if (x > 1) {
                        Object[] result = method_loop_2(x);
                        x = (Integer) result[0];
                    }
                    System.out.println("7");
                }
            }
        }
        if (x > 0) {
            return method_loop_1(x);
        }
        return new Object[] { x };
    }

    public static Object[] method_loop_2(int x) {
        {
            x--;
            System.out.println("6");
            Exception e = new ArrayIndexOutOfBoundsException();
            throw e;
        }
        if (x > 1) {
            return method_loop_2(x);
        }
        return new Object[] { x };
    }
}
