package Ejemplos.Basic;

public class Ejemplo_Bucles_1_t {

    public static void main(String[] args) {
        System.out.println("Empieza bucle WHILE:");
        int x = 1;
        if (x <= 10) {
            Object[] result = method_loop_1(x);
            x = (Integer) result[0];
        }
        System.out.println();
    }

    static Object[] method_loop_1(int x) {
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
