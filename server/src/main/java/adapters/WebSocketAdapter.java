package adapters;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import constants.OperationsConstant;
import repositories.IBaseRepository;
import repositories.person.PersonRepositoryImpl;
import repositories.person.legal_person.LegalPersonRepositoryImpl;
import repositories.person.physical_person.PhysicalPersonRepositoryImpl;
import repositories.wallet.WalletRepositoryImpl;

public class WebSocketAdapter {
    DataInputStream dataInputStream;

    public WebSocketAdapter(int port) throws Exception {

        ServerSocket serverSocket = new ServerSocket(port);
        serverSocket.setReuseAddress(true);
        Socket socket = null;

        while (true) {
            try {
                System.out.println("Esperando conexões");
                socket = serverSocket.accept();
                System.out.println("Conexão estabelecida com " + socket.getInetAddress().getHostAddress());

                this.dataInputStream = new DataInputStream(socket.getInputStream());
                String message = this.dataInputStream.readUTF();
                String response = this.executeOperation(message);

                System.out.println("Resposta: \n" + response);

                socket.getOutputStream().write(response.getBytes());
            } catch (IOException e) {
                throw new IOException(e.getMessage());
            } finally {
                socket.close();
            }
        }
    }

    private String executeOperation(String message) throws Exception {
        System.out.println("Requisição recebida: " + message);

        String response;
        HashMap<String, String> params = this.getParams(message);
        IBaseRepository repository;

        switch (params.get("model")) {
            case "physical_person":
                repository = new PhysicalPersonRepositoryImpl();
                break;
            case "legal_person":
                repository = new LegalPersonRepositoryImpl();
                break;
            case "person":
                repository = new PersonRepositoryImpl();
                break;
            case "wallet":
                repository = new WalletRepositoryImpl();
                break;
            default:
                throw new Exception("Modelo não reconhecido.");
        }

        switch (params.get("operation")) {
            case OperationsConstant.INSERT:
                response = repository.insert(params);
                break;
            case OperationsConstant.UPDATE:
                response = repository.update(params);
                break;
            case OperationsConstant.GET:
                response = repository.get(params);
                break;
            case OperationsConstant.DELETE:
                response = repository.delete(params);
                break;
            case OperationsConstant.LIST:
                response = repository.list(params);
                break;
            case OperationsConstant.ADD:
                response = repository.add(params);
                break;
            case OperationsConstant.REMOVE:
                response = repository.remove(params);
                break;
            default:
                throw new Exception("Operação não reconhecida.");
        }
        return response;
    }

    private HashMap<String, String> getParams(String message) {
        String[] parts = message.split(";");
        HashMap<String, String> params = new HashMap<>();

        for (String param : parts) {
            String[] paramParts = param.split("=");
            params.put(paramParts[0], paramParts[1]);
        }
        return params;
    }

}
