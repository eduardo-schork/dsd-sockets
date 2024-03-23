package adapters.web_socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;
import constants.OperationsConstant;

public class WebSocketAdapter {
    private Socket socket;
    private DataOutputStream stream;
    private Scanner scanner = new Scanner(System.in);
    private Boolean isFirstConnection = true;

    public WebSocketAdapter(String host, int port) throws Exception {

        if (isFirstConnection) {
            try {
                this.socket = new Socket(host, port);
                this.stream = new DataOutputStream(this.socket.getOutputStream());
                this.startupSocket(host, port);
            } catch (Exception e) {
                System.out.println("Não foi possível conectar com " + host + ":" + port);
                System.out.println(e.getMessage());
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public DataOutputStream getStream() {
        return stream;
    }

    private void startupSocket(String host, int port) throws Exception {

        selectModelOperation(host, port);

        InputStream inputStream = this.getSocket().getInputStream();

        byte[] rawDataBytes = new byte[1024];

        int readBytesQuantity = 0;

        readBytesQuantity = inputStream.read(rawDataBytes);
        String response = new String(rawDataBytes, 0, readBytesQuantity);

        System.out.println(response);
        System.out.println("\n");
        isFirstConnection = false;
        startupSocket(host, port);

    }

    private void selectModelOperation(String host, int port) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println(
                "\n" +
                        "Selecione o modelo que deseja gerir:\n " +
                        "1 - Pessoa\n " +
                        "2 - Pessoa Física\n " +
                        "3 - Pessoa Jurídica\n " +
                        "4 - Carteira\n " +
                        "5 - Sair");

        String modelToHandle = scanner.next();

        switch (modelToHandle) {
            case "1":
            case "2":
            case "3":
                selectDefaultOptionsOperation();
                break;
            case "4":
                selectDefaultOptionsOperation();
                selectCarteiraOptionsOperation();
                break;
            case "5":
                throw new Exception("Fim da execução");
            default:
                System.out.println("Opção inválida, digite novamente.");
                isFirstConnection = true;
                try {
                    startupSocket(host, port);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }

        if (!isFirstConnection) {
            this.socket = new Socket(host, port);
            this.stream = new DataOutputStream(this.socket.getOutputStream());
        }

        String operation = scanner.next();

        switch (modelToHandle) {
            case "1":
                handlePersonOperation(operation, host, port);
                break;
            case "2":
                handlePhysicalPersonOperation(operation, host, port);
                break;
            case "3":
                handleLegalPersonOperation(operation, host, port);
                break;
            case "4":
                handleWalletOperation(operation, host, port);
                break;
            default:
                System.out.println("Opção inválida, digite novamente.");
                isFirstConnection = true;
                try {
                    startupSocket(host, port);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }
    }

    private String requestParamToUser(String requestTitle) {
        System.out.println(requestTitle);
        String userInput = scanner.nextLine();

        return userInput;
    }

    private String createMessagePayload(String model, String operation, String params) {
        String message = "model=" + model + ";operation=" + operation + ";" + params;

        return message;
    }

    private void sendMessagePayload(String model, String operation, String params) throws IOException {
        String message = createMessagePayload(model, operation, params);

        this.getStream().writeUTF(message);
    }

    private void handlePersonOperation(String option, String host, int port) throws IOException {
        String params = "";
        String operation = "";

        switch (option) {
            case "1": {
                String cpf = requestParamToUser("Insira o CPF");
                String name = requestParamToUser("Insira o Nome");
                String address = requestParamToUser("Insira o Endereço");

                params = "cpf=" + cpf + ";name=" + name + ";address=" + address;
                operation = OperationsConstant.INSERT;
                break;
            }
            case "2": {
                String cpf = requestParamToUser("Insira o CPF");
                String name = requestParamToUser("Insira o Nome");
                String address = requestParamToUser("Insira o Endereço");

                params = "cpf=" + cpf + ";name=" + name + ";address=" + address;
                operation = OperationsConstant.UPDATE;
                break;
            }
            case "3": {
                String cpf = requestParamToUser("Insira o CPF");
                params = "cpf=" + cpf;
                operation = OperationsConstant.DELETE;
                break;
            }
            case "4": {
                String cpf = requestParamToUser("Insira o CPF");
                params = "cpf=" + cpf;
                operation = OperationsConstant.GET;
                break;
            }
            case "5": {
                operation = OperationsConstant.LIST;
                break;
            }
            default: {
                System.out.println("Opção inválida, digite novamente.");
                isFirstConnection = true;
                try {
                    startupSocket(host, port);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        sendMessagePayload("person", operation, params);
    }

    private void handleLegalPersonOperation(String option, String host, int port) throws IOException {
        String params = "";
        String operation = "";

        switch (option) {
            case "1": {
                String cpf = requestParamToUser("Insira o CPF");
                String name = requestParamToUser("Insira o Nome");
                String address = requestParamToUser("Insira o Endereço");
                String cnpj = requestParamToUser("Insira o CNPJ");

                params = "cpf=" + cpf + ";name=" + name + ";address=" + address + ";cnpj=" + cnpj;
                operation = OperationsConstant.INSERT;
                break;
            }
            case "2": {
                String cpf = requestParamToUser("Insira o CPF");
                String name = requestParamToUser("Insira o Nome");
                String address = requestParamToUser("Insira o Endereço");
                String cnpj = requestParamToUser("Insira o CNPJ");

                params = "cpf=" + cpf + ";name=" + name + ";address=" + address + ";cnpj=" + cnpj;
                operation = OperationsConstant.UPDATE;
                break;
            }
            case "3": {
                String cpf = requestParamToUser("Insira o CPF");
                params = "cpf=" + cpf;
                operation = OperationsConstant.DELETE;
                break;
            }
            case "4": {
                String cpf = requestParamToUser("Insira o CPF");
                params = "cpf=" + cpf;
                operation = OperationsConstant.GET;
                break;
            }
            case "5": {
                operation = OperationsConstant.LIST;
                break;
            }
            default: {
                System.out.println("Opção inválida, digite novamente.");
                isFirstConnection = true;
                try {
                    startupSocket(host, port);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        sendMessagePayload("legal_person", operation, params);
    }

    private void handlePhysicalPersonOperation(String option, String host, int port) throws IOException {
        String params = "";
        String operation = "";

        switch (option) {
            case "1": {
                String cpf = requestParamToUser("Insira o CPF");
                String name = requestParamToUser("Insira o Nome");
                String address = requestParamToUser("Insira o Endereço");
                String email = requestParamToUser("Insira o Email");

                params = "cpf=" + cpf + ";name=" + name + ";address=" + address + ";email=" + email;
                operation = OperationsConstant.INSERT;
                break;
            }
            case "2": {
                String cpf = requestParamToUser("Insira o CPF");
                String name = requestParamToUser("Insira o Nome");
                String address = requestParamToUser("Insira o Endereço");
                String email = requestParamToUser("Insira o Email");

                params = "cpf=" + cpf + ";name=" + name + ";address=" + address + ";email=" + email;
                operation = OperationsConstant.UPDATE;
                break;
            }
            case "3": {
                String cpf = requestParamToUser("Insira o CPF");
                params = "cpf=" + cpf;
                operation = OperationsConstant.DELETE;
                break;
            }
            case "4": {
                String cpf = requestParamToUser("Insira o CPF");
                params = "cpf=" + cpf;
                operation = OperationsConstant.GET;
                break;
            }
            case "5": {
                operation = OperationsConstant.LIST;
                break;
            }
            default: {
                System.out.println("Opção inválida, digite novamente.");
                isFirstConnection = true;
                try {
                    startupSocket(host, port);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        sendMessagePayload("physical_person", operation, params);
    }

    private void handleWalletOperation(String option, String host, int port) throws IOException {
        String params = "";
        String operation = "";

        switch (option) {
            case "1": {
                String walletName = requestParamToUser("Insira o nome da carteira:");
                String walletResponsible = requestParamToUser("Insira o CPF do responsável:");
                String walletInitialSalary = requestParamToUser("Insira a faixa salarial inicial:");
                String walletFinalSalary = requestParamToUser("Insira a faixa salarial final:");

                params = "name=" + walletName + ";responsible=" + walletResponsible + ";initialSalary="
                        + walletInitialSalary + ";finalSalary=" + walletFinalSalary;
                operation = OperationsConstant.INSERT;
                break;
            }
            case "2": {
                String walletName = requestParamToUser("Insira o nome da carteira:");
                String walletNewName = requestParamToUser("Insira o novo nome da carteira:");
                String walletResponsible = requestParamToUser("Insira o CPF do responsável:");
                String walletInitialSalary = requestParamToUser("Insira a faixa salarial inicial:");
                String walletFinalSalary = requestParamToUser("Insira a faixa salarial final:");

                params = "name=" + walletName + ";newName=" + walletNewName + ";responsible=" + walletResponsible
                        + ";initialSalary=" + walletInitialSalary + ";finalSalary=" + walletFinalSalary;

                operation = OperationsConstant.UPDATE;
                break;
            }
            case "3": {
                String walletName = requestParamToUser("Insira o nome da carteira:");
                operation = OperationsConstant.DELETE;
                params = "name=" + walletName;
                break;
            }
            case "4": {
                String walletName = requestParamToUser("Insira o nome da carteira:");
                operation = OperationsConstant.GET;
                params = "name=" + walletName;
                break;
            }
            case "5": {
                operation = OperationsConstant.LIST;
                break;
            }
            case "6": {
                String walletName = requestParamToUser("Insira o nome da carteira:");
                String walletResponsible = requestParamToUser("Insira o CPF do cliente:");

                params = "name=" + walletName + ";cpf=" + walletResponsible;
                operation = OperationsConstant.ADD;
                break;

            }
            case "7": {
                String walletName = requestParamToUser("Insira o nome da carteira:");
                String walletResponsible = requestParamToUser("Insira o CPF do cliente:");
                params = "name=" + walletName + ";cpf=" + walletResponsible;
                operation = OperationsConstant.REMOVE;
                break;
            }
            default: {
                System.out.println("Opção inválida, digite novamente.");
                isFirstConnection = true;
                try {
                    startupSocket(host, port);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        sendMessagePayload("wallet", operation, params);
    }

    private void selectDefaultOptionsOperation() {
        System.out.println(
                "Por favor, escolha a operação: \n 1 - INSERT \n 2 - UPDATE\n 3 - DELETE \n 4 - GET \n 5 - LIST");
    }

    private void selectCarteiraOptionsOperation() {
        System.out.println(" 6 - ADD PESSOA\n 7 - REMOVE PESSOA \n");
    }
}
