import adapters.WebSocketAdapter;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Deseja iniciar o servidor em qual porta? (ex: 3001)");

        int userPortInput = scanner.nextInt();

        new WebSocketAdapter(userPortInput);
    }

}
