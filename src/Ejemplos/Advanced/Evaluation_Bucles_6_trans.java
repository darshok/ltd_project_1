package Ejemplos.Advanced;

public class Evaluation_Bucles_6_trans {

    public static void main(String[] args) {
        metodo();
        Clase2 clase2 = new Clase2();
        clase2.main();
    }

    public static void metodo() {
        System.out.println("Empieza bucle WHILE:");
        int x = 1;
        if (x <= 10) {
            Object[] result = method_loop_1(x);
            x = (Integer) result[0];
        }
        System.out.println();
    }

    public static Object[] method_loop_1(int x) {
        {
            System.out.print(" " + x);
            x++;
        }
        if (x <= 10) {
            return method_loop_1(x);
        }
        return new Object[] { x };
    }
}

class Clase2 {

    public void main() {
        System.out.println("Empieza bucle WHILE:");
        int x = 1;
        if (x <= 10) {
            Object[] result = method_loop_2(x);
            x = (Integer) result[0];
        }
        System.out.println();
        metodo();
    }

    public void metodo() {
        System.out.println("Empieza bucle WHILE:");
        int x = 1;
        if (x <= 10) {
            Object[] result = method_loop_3(x);
            x = (Integer) result[0];
        }
        System.out.println();
    }

    public Object[] method_loop_2(int x) {
        {
            System.out.print(" " + x);
            x++;
        }
        if (x <= 10) {
            return method_loop_2(x);
        }
        return new Object[] { x };
    }

    public Object[] method_loop_3(int x) {
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
