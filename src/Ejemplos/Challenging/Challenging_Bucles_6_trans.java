package Ejemplos.Challenging;

public class Challenging_Bucles_6_trans {

    public static void main(String[] args) throws Exception {
        int resultado = sumatorio(1);
        System.out.println(resultado);
    }

    public static int sumatorio(int x) {
        System.out.println("Empieza bucle WHILE:");
        try {
            if (x <= 10) {
                Object[] result = method_loop_1(x);
                x = (Integer) result[0];
            }
        } catch (Exception E) {
            System.out.println("Ha habido una excepción");
        }
        return 42;
    }

    public static Object[] method_loop_1(int x) {
        {
            x++;
            x /= 0;
        }
        if (x <= 10) {
            return method_loop_1(x);
        }
        return new Object[] { x };
    }
}
