package Ejemplos.Challenging;

public class Challenging_Bucles_7_trans {

    public static void main(String[] args) {
        int resultado = sumatorio(1, 10);
        System.out.println(resultado);
    }

    public static int sumatorio(int x, int y) {
        System.out.println("Empieza bucle WHILE:");
        bucle1: if (x <= 10) {
            Object[] result = method_loop_1(x, y);
            x = (Integer) result[0];
            y = (Integer) result[1];
        }
        return x;
    }

    public static Object[] method_loop_1(int x, int y) {
        {
            x++;
            bucle2: if (y <= 10) {
                Object[] result = method_loop_2(y);
                y = (Integer) result[0];
            }
            break;
        }
        if (x <= 10) {
            return method_loop_1(x, y);
        }
        return new Object[] { x, y };
    }

    public static Object[] method_loop_2(int y) {
        {
            y--;
            break bucle1;
        }
        if (y <= 10) {
            return method_loop_2(y);
        }
        return new Object[] { y };
    }
}
