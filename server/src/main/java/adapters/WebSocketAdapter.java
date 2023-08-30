package adapters;

import constants.OperationsConstant;
import repositories.IBaseRepository;
import repositories.person.PersonRepositoryImpl;
import repositories.person.legal_person.LegalPersonRepositoryImpl;
import repositories.person.physical_person.PhysicalPersonRepositoryImpl;
import repositories.wallet.WalletRepositoryImpl;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class WebSocketAdapter {
    DataInputStream dataInputStream;

    public WebSocketAdapter(int port) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Esperando conexões");
        serverSocket.setReuseAddress(true);
        Socket socket = serverSocket.accept();

        System.out.println("Conexão estabelecida");
        this.dataInputStream = new DataInputStream(socket.getInputStream());

        while (true) {
            String message = this.dataInputStream.readUTF();
            String response = this.executeOperation(message);

            System.out.println("Resposta: \n" + response);

            socket.getOutputStream().write(response.getBytes());
        }
    }

    private String executeOperation(String message) throws Exception {
        System.out.println("Requisição recebida: " + message);

        String response;
        HashMap<String, String> params = this.getParams(message);
        IBaseRepository repository;

        switch (params.get("modelo")) {
            case "pessoa_fisica":
                repository = new PhysicalPersonRepositoryImpl();
                break;
            case "pessoa_juridica":
                repository = new LegalPersonRepositoryImpl();
                break;
            case "pessoa":
                repository = new PersonRepositoryImpl();
                break;
            case "wallet":
                repository = new WalletRepositoryImpl();
                break;
            default:
                throw new Exception("Modelo não reconhecido.");
        }

        switch (params.get("operacao")) {
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
