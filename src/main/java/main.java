import java.util.Scanner;
public class main {

        public void main(String[] args) {
            final int SLICE = 4;
            Scanner scanner = new Scanner(System.in);

            int T = scanner.nextInt();

            // TODO: Retorne o número de pizzas necessárias para atender o pedido.
            for (int i = 0; i < T; i++) {
                int N = scanner.nextInt();
                int X = scanner.nextInt();

                int totalFatias = N * X;
                int pizzas = (totalFatias + 3) / 4;

                System.out.println(pizzas);
            }

        }
    }

