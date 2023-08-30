import adapters.web_socket.WebSocketAdapter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Insira o ip do host (ex: 127.0.0.1):");
        String host = scanner.next();

        System.out.println("Insira a porta do host (ex: 3001):");
        int port = scanner.nextInt();

        new WebSocketAdapter(host, port);
    }

}