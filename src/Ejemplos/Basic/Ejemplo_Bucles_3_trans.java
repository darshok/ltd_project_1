package Ejemplos.Basic;

public class Ejemplo_Bucles_3_trans {

    public static void main(String[] args) {
        int x;
        System.out.println("Empieza bucle FOR:");
        {
            {
                System.out.print(" " + x);
            }
            x = 1;
            if (x <= 10) {
                Object[] result = method_loop_1(x);
                x = (Integer) result[0];
            }
        }
        System.out.println();
        System.out.println("Empieza bucle WHILE:");
        x = 1;
        if (x <= 10) {
            Object[] result = method_loop_2(x);
            x = (Integer) result[0];
        }
        System.out.println();
        System.out.println("Empieza bucle DO WHILE:");
        x = 1;
        {
            {
                System.out.print(" " + x);
                x++;
            }
            if (x <= 10) {
                Object[] result = method_loop_3(x);
                x = (Integer) result[0];
            }
        }
        System.out.println();
    }

    public static Object[] method_loop_1(int x) {
        {
            System.out.print(" " + x);
        }
        x++;
        if (x <= 10) {
            return method_loop_1(x);
        }
        return new Object[] { x };
    }

    public static Object[] method_loop_2(int x) {
        {
            System.out.print(" " + x);
            x++;
        }
        if (x <= 10) {
            return method_loop_2(x);
        }
        return new Object[] { x };
    }

    public static Object[] method_loop_3(int x) {
        {
            System.out.print(" " + x);
            x++;
        }
        if (x <= 10) {
            return method_loop_3(x);
        }
        return new Object[] { x };
    }
}
