package Ejemplos.Basic;

public class Ejemplo_Bucles_5_trans {

    public static void main(String[] args) {
        int x = 0;
        char y = '0';
        System.out.println("Empieza bucle FOR anidado a otro FOR:");
        {
            {
                System.out.print(" " + x);
                {
                    {
                        System.out.print(" " + y);
                    }
                    y = 'a';
                    if (y <= 'c') {
                        Object[] result = method_loop_4(y);
                        y = (Character) result[0];
                    }
                }
            }
            x = 1;
            if (x <= 10) {
                Object[] result = method_loop_1(x, y);
                x = (Integer) result[0];
                y = (Character) result[1];
            }
        }
        System.out.println();
        System.out.println("Empieza bucle WHILE anidado a otro WHILE:");
        x = 1;
        if (x <= 10) {
            Object[] result = method_loop_2(x, y);
            x = (Integer) result[0];
            y = (Character) result[1];
        }
        System.out.println();
        System.out.println("Empieza bucle FOR anidado a bucle DO WHILE:");
        x = 1;
        {
            {
                System.out.print(" " + x);
                {
                    {
                        System.out.print(" " + y);
                    }
                    y = 'a';
                    if (y <= 'c') {
                        Object[] result = method_loop_6(y);
                        y = (Character) result[0];
                    }
                }
                x++;
            }
            if (x <= 10) {
                Object[] result = method_loop_3(x, y);
                x = (Integer) result[0];
                y = (Character) result[1];
            }
        }
        System.out.println();
    }

    public static Object[] method_loop_1(int x, char y) {
        {
            System.out.print(" " + x);
            {
                {
                    System.out.print(" " + y);
                }
                y = 'a';
                if (y <= 'c') {
                    Object[] result = method_loop_4(y);
                    y = (Character) result[0];
                }
            }
        }
        x++;
        if (x <= 10) {
            return method_loop_1(x, y);
        }
        return new Object[] { x, y };
    }

    public static Object[] method_loop_2(int x, char y) {
        {
            System.out.print(" " + x);
            y = 'a';
            if (y <= 'c') {
                Object[] result = method_loop_5(y);
                y = (Character) result[0];
            }
            x++;
        }
        if (x <= 10) {
            return method_loop_2(x, y);
        }
        return new Object[] { x, y };
    }

    public static Object[] method_loop_3(int x, char y) {
        {
            System.out.print(" " + x);
            {
                {
                    System.out.print(" " + y);
                }
                y = 'a';
                if (y <= 'c') {
                    Object[] result = method_loop_6(y);
                    y = (Character) result[0];
                }
            }
            x++;
        }
        if (x <= 10) {
            return method_loop_3(x, y);
        }
        return new Object[] { x, y };
    }

    public static Object[] method_loop_4(char y) {
        {
            System.out.print(" " + y);
        }
        y++;
        if (y <= 'c') {
            return method_loop_4(y);
        }
        return new Object[] { y };
    }

    public static Object[] method_loop_5(char y) {
        {
            System.out.print(" " + y);
            y++;
        }
        if (y <= 'c') {
            return method_loop_5(y);
        }
        return new Object[] { y };
    }

    public static Object[] method_loop_6(char y) {
        {
            System.out.print(" " + y);
        }
        y++;
        if (y <= 'c') {
            return method_loop_6(y);
        }
        return new Object[] { y };
    }
}
