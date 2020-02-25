package Ejemplos.Basic;

public class Ejemplo_Bucles_2_trans {

    public static void main(String[] args) {
        System.out.println("Empieza bucle WHILE anidado a otro WHILE:");
        int x = 1;
        char y = 'a';
        if (x <= 10) {
            Object[] result = method_loop_1(x, y);
            x = (Integer) result[0];
            y = (Character) result[1];
        }
        System.out.println();
    }

    public static Object[] method_loop_1(int x, char y) {
        {
            System.out.print(" " + x);
            y = 'a';
            if (y <= 'c') {
                Object[] result = method_loop_2(y);
                y = (Character) result[0];
            }
            x++;
        }
        if (x <= 10) {
            return method_loop_1(x, y);
        }
        return new Object[] { x, y };
    }

    public static Object[] method_loop_2(char y) {
        {
            System.out.print(" " + y);
            y++;
        }
        if (y <= 'c') {
            return method_loop_2(y);
        }
        return new Object[] { y };
    }
}
