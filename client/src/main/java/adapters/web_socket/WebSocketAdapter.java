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

    public WebSocketAdapter(String host, int port) throws Exception {
        this.startupSocket(host, port);
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

        byte[] dadosBrutos = new byte[1024];

        int qtdBytesLidos = 0;

        //while (qtdBytesLidos >= 0) {
        qtdBytesLidos = inputStream.read(dadosBrutos);
        String response = new String(dadosBrutos, 0, qtdBytesLidos);

        System.out.println(response);
        System.out.println("\n");

        startupSocket(host,port);
        //}
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
                        "5 - Sair"
        );

        int modelToHandle = scanner.nextInt();

        switch (modelToHandle) {
            case 1:
            case 2:
            case 3:
                selectDefaultOptionsOperation();
                break;
            case 4:
                selectDefaultOptionsOperation();
                selectCarteiraOptionsOperation();
                break;
            case 5:
                throw new Exception("Fim da execução");
            default:
                System.out.println("Opção inválida.");
                selectModelOperation(host,port);
        }

        this.socket = new Socket(host, port);
        this.stream = new DataOutputStream(this.socket.getOutputStream());

        int operation = scanner.nextInt();

        switch (modelToHandle) {
            case 1:
                handlePersonOperation(operation);
                break;
            case 2:
                handlePhysicalPersonOperation(operation);
                break;
            case 3:
                handleLegalPersonOperation(operation);
                break;
            case 4:
                handleWalletOperation(operation);
                break;
            default:
                System.out.println("Opção inválida.");
                selectModelOperation(host,port);
        }
    }

    private String requestParamToUser(String requestTitle) {
        System.out.println(requestTitle);
        String userInput = scanner.nextLine();

        return userInput;
    }

    private String createMessagePayload(String model, String operation, String params) {
        String message = "modelo=" + model +";operacao=" + operation + ";" + params;

        return message;
    }

    private void sendMessagePayload(String model, String operation, String params) throws IOException {
        String message = createMessagePayload(model, operation, params);

        this.getStream().writeUTF(message);
    }

    private void handlePersonOperation(int option) throws IOException {
        String params = "";
        String operation = "";

        switch (option) {
            case 1 : {
                String cpf = requestParamToUser("Insira o CPF");
                String nome = requestParamToUser("Insira o Nome");
                String endereco = requestParamToUser("Insira o Endereço");

                params = "cpf=" + cpf + ";nome=" + nome + ";endereco=" + endereco;
                operation = OperationsConstant.INSERT;
                break;
            }
            case 2: {
                String cpf = requestParamToUser("Insira o CPF");
                String nome = requestParamToUser("Insira o Nome");
                String endereco = requestParamToUser("Insira o Endereço");

                params = "cpf=" + cpf + ";nome=" + nome + ";endereco=" + endereco;
                operation = OperationsConstant.UPDATE;
                break;
            }
            case 3: {
                String cpf = requestParamToUser("Insira o CPF");
                params = "cpf=" + cpf;
                operation = OperationsConstant.DELETE;
                break;
            }
            case 4: {
                String cpf = requestParamToUser("Insira o CPF");
                params = "cpf=" + cpf;
                operation = OperationsConstant.GET;
                break;
            }
            case 5: {
                operation = OperationsConstant.LIST;
                break;
            }
            default: {
                System.out.println("Opção inválida pressione enter.");
                break;
            }
        }

        sendMessagePayload("pessoa", operation, params);
    }
    private void handleLegalPersonOperation(int option) throws IOException {
        String params = "";
        String operation = "";

        switch (option) {
            case 1 : {
                String cpf = requestParamToUser("Insira o CPF");
                String nome = requestParamToUser("Insira o Nome");
                String endereco = requestParamToUser("Insira o Endereço");
                String cnpj = requestParamToUser("Insira o CNPJ");

                params = "cpf=" + cpf + ";nome=" + nome + ";endereco=" + endereco + ";cnpj=" + cnpj;
                operation = OperationsConstant.INSERT;
                break;
            }
            case 2: {
                String cpf = requestParamToUser("Insira o CPF");
                String nome = requestParamToUser("Insira o Nome");
                String endereco = requestParamToUser("Insira o Endereço");
                String cnpj = requestParamToUser("Insira o CNPJ");

                params = "cpf=" + cpf + ";nome=" + nome + ";endereco=" + endereco + ";cnpj=" + cnpj;
                operation = OperationsConstant.UPDATE;
                break;
            }
            case 3: {
                String cpf = requestParamToUser("Insira o CPF");
                params = "cpf=" + cpf;
                operation = OperationsConstant.DELETE;
                break;
            }
            case 4: {
                String cpf = requestParamToUser("Insira o CPF");
                params = "cpf=" + cpf;
                operation = OperationsConstant.GET;
                break;
            }
            case 5: {
                operation = OperationsConstant.LIST;
                break;
            }
            default: {
                System.out.println("Opção inválida pressione enter.");
                break;
            }
        }

        sendMessagePayload("pessoa_juridica", operation, params);
    }
    private void handlePhysicalPersonOperation(int option) throws IOException {
        String params = "";
        String operation = "";

        switch (option) {
            case 1 : {
                String cpf = requestParamToUser("Insira o CPF");
                String nome = requestParamToUser("Insira o Nome");
                String endereco = requestParamToUser("Insira o Endereço");
                String email = requestParamToUser("Insira o Email");

                params = "cpf=" + cpf + ";nome=" + nome + ";endereco=" + endereco + ";email=" + email;
                operation = OperationsConstant.INSERT;
                break;
            }
            case 2: {
                String cpf = requestParamToUser("Insira o CPF");
                String nome = requestParamToUser("Insira o Nome");
                String endereco = requestParamToUser("Insira o Endereço");
                String email = requestParamToUser("Insira o Email");

                params = "cpf=" + cpf + ";nome=" + nome + ";endereco=" + endereco + ";email=" + email;
                operation = OperationsConstant.UPDATE;
                break;
            }
            case 3: {
                String cpf = requestParamToUser("Insira o CPF");
                params = "cpf=" + cpf;
                operation = OperationsConstant.DELETE;
                break;
            }
            case 4: {
                String cpf = requestParamToUser("Insira o CPF");
                params = "cpf=" + cpf;
                operation = OperationsConstant.GET;
                break;
            }
            case 5: {
                operation = OperationsConstant.LIST;
                break;
            }
            default: {
                System.out.println("Opção inválida pressione enter.");
                break;
            }
        }

        sendMessagePayload("pessoa_fisica", operation, params);
    }
    private void handleWalletOperation(int option) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String params = "";
        String operation = "";
        switch (option) {
            case 1: {
                String walletName = requestParamToUser("Insira o nome da carteira:");
                String walletResponsible = requestParamToUser("Insira o CPF do responsável:");
                String walletInitialSalary = requestParamToUser("Insira a faixa salarial inicial:");
                String walletFinalSalary = requestParamToUser("Insira a faixa salarial final:");

                params = "nome=" + walletName+ ";responsavel="+ walletResponsible+ ";faixaSalarialInicial="+walletInitialSalary+";faixaSalarialFinal="+walletFinalSalary;
                operation = OperationsConstant.INSERT;
                break;
            }
            case 2: {
                String walletName = requestParamToUser("Insira o nome da carteira:");
                String walletNewName = requestParamToUser("Insira o novo nome da carteira:");
                String walletResponsible = requestParamToUser("Insira o CPF do responsável:");
                String walletInitialSalary = requestParamToUser("Insira a faixa salarial inicial:");
                String walletFinalSalary = requestParamToUser("Insira a faixa salarial final:");

                params = "nome=" + walletName+ ";novoNome="+walletNewName + ";responsavel="+ walletResponsible+ ";faixaSalarialInicial="+walletInitialSalary+";faixaSalarialFinal="+walletFinalSalary;

                operation = OperationsConstant.UPDATE;
                break;
            }
            case 3: {
                String walletName = requestParamToUser("Insira o nome da carteira:");
                operation = OperationsConstant.DELETE;
                params = "nome=" + walletName;
                break;
            }
            case 4: {
                String walletName = requestParamToUser("Insira o nome da carteira:");
                operation = OperationsConstant.GET;
                params = "nome=" + walletName;
                break;
            }
            case 5: {
                operation = OperationsConstant.LIST;
                break;
            }
            case 6: {
                String walletName = requestParamToUser("Insira o nome da carteira:");
                String walletResponsible = requestParamToUser("Insira o CPF do cliente:");

                params = "nome=" + walletName + ";cpf=" + walletResponsible;
                operation = OperationsConstant.ADD;
                break;

            }
            case 7:{
                String walletName = requestParamToUser("Insira o nome da carteira:");
                String walletResponsible = requestParamToUser("Insira o CPF do cliente:");
                params = "nome=" + walletName + ";cpf=" + walletResponsible;
                operation = OperationsConstant.REMOVE;
                break;
            }
            default: {
                System.out.println("Opção inválida pressione enter.");
                break;
            }
        }

        sendMessagePayload("wallet", operation, params);
    }

    private void selectDefaultOptionsOperation() {
        System.out.println("Por favor, escolha a operação: \n 1 - INSERT \n 2 - UPDATE\n 3 - DELETE \n 4 - GET \n 5 - LIST");
    }

    private void selectCarteiraOptionsOperation() {
        System.out.println(" 6 - ADD PESSOA\n 7 - REMOVE PESSOA \n");
    }
}
