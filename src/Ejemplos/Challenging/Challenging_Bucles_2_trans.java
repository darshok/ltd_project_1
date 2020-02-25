package Ejemplos.Challenging;

public class Challenging_Bucles_2_trans {

    public static void main(String[] args) {
        int resultado = sumatorio(1);
        System.out.println(resultado);
    }

    public static int sumatorio(int x) {
        System.out.println("Empieza bucle WHILE:");
        if (x <= 10) {
            Object[] result = method_loop_1(x);
            x = (Integer) result[0];
        }
        return x;
    }

    public static Object[] method_loop_1(int x) {
        {
            x++;
            break;
        }
        if (x <= 10) {
            return method_loop_1(x);
        }
        return new Object[] { x };
    }
}
