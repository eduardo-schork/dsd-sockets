package repositories.wallet;

import adapters.LocalStorageAdapter;
import models.Person;
import models.Wallet;
import repositories.person.IPersonRepository;
import repositories.person.PersonRepositoryImpl;

import java.util.HashMap;

public class WalletRepositoryImpl implements IWalletRepository {

    public Wallet findWalletByParams(HashMap<String, String> params) {
        return LocalStorageAdapter.wallets.get(params.get("nome"));
    }

    public String insert(HashMap<String, String> params) {
        if (findWalletByParams(params) != null) {
            return "Carteira com mesmo nome já cadastrada";
        }

        Person responsavel = LocalStorageAdapter.people.get(params.get("responsavel"));
        if (responsavel != null) {
            String nomeParams = params.get("nome");
            Double faixaSalarialInicialParams = Double.parseDouble(params.get("faixaSalarialInicial"));
            Double faixaSalarialFinalParams = Double.parseDouble(params.get("faixaSalarialFinal"));

            HashMap<String, Person> clientes = new HashMap<>();

            Wallet wallet = new Wallet(nomeParams, responsavel, faixaSalarialInicialParams, faixaSalarialFinalParams,
                    clientes);

            LocalStorageAdapter.wallets.put(wallet.getNome(), wallet);
            return "Carteira cadastrada comm sucesso";
        }
        return "Erro na criação de Carteira";
    }

    public String update(HashMap<String, String> params) {
        Wallet wallet = this.findWalletByParams(params);

        if (wallet == null) {
            return "Carteira não encontrada";
        }

        Person responsavel = LocalStorageAdapter.people.get(params.get("responsavel"));

        if (responsavel == null) {
            return "Responsável informado não encontrado";
        }

        String oldName = wallet.getNome();

        String nomeParams = params.get("novoNome");
        Double faixaSalarialInicialParams = Double.parseDouble(params.get("faixaSalarialInicial"));
        Double faixaSalarialFinalParams = Double.parseDouble(params.get("faixaSalarialFinal"));

        wallet.setNome(nomeParams);
        wallet.setResponsavel(responsavel);
        wallet.setFaixaSalarialInicial(faixaSalarialInicialParams);
        wallet.setFaixaSalarialFinal(faixaSalarialFinalParams);

        LocalStorageAdapter.wallets.remove(oldName);
        LocalStorageAdapter.wallets.put(wallet.getNome(), wallet);
        return "Carteira atualizada com sucesso";
    }

    public String get(HashMap<String, String> params) {
        if (LocalStorageAdapter.wallets.isEmpty()) {
            return "Sem Carteiras cadastradas";
        }

        Wallet wallet = this.findWalletByParams(params);

        if (wallet == null) {
            return "Carteira não encontrada";
        }

        return wallet.toString();
    }

    public String delete(HashMap<String, String> params) {
        if (LocalStorageAdapter.wallets.isEmpty()) {
            return "Sem Carteiras cadastradas";
        }
        Wallet wallet = this.findWalletByParams(params);

        if (wallet == null) {
            return "Carteira não encontrada";
        }

        LocalStorageAdapter.wallets.remove(wallet.getNome());
        return "Carteira removida com sucesso";
    }

    public String list(HashMap<String, String> params) {
        if (LocalStorageAdapter.wallets.isEmpty()) {
            return "0";
        }

        StringBuilder reponse = new StringBuilder();
        if (LocalStorageAdapter.wallets.size() < 10) {
            reponse.append("0");
        }

        reponse.append(LocalStorageAdapter.wallets.size());

        for (Wallet wallet : LocalStorageAdapter.wallets.values()) {
            reponse.append("\n").append(wallet.toString());
        }

        return reponse.toString();
    }

    public String add(HashMap<String, String> params) {
        if (LocalStorageAdapter.wallets.isEmpty()) {
            return "Sem Carteiras cadastradas";
        }

        if (LocalStorageAdapter.people.isEmpty()) {
            return "Sem pessoas cadastradas";
        }

        IPersonRepository peopleRepository = new PersonRepositoryImpl();
        Person person = peopleRepository.findPersonByParams(params);

        if (person == null) {
            return "Pessoa não encontrada";
        }

        Wallet wallet = this.findWalletByParams(params);
        if (wallet == null) {
            return "Carteira não encontrada";
        }

        wallet.addCliente(person);
        return "Cliente adicionado a Carteira";
    }

    public String remove(HashMap<String, String> params) {
        if (LocalStorageAdapter.wallets.isEmpty()) {
            return "Sem Carteiras cadastradas";
        }
        if (LocalStorageAdapter.people.isEmpty()) {
            return "Sem Pessoas cadastradas";
        }

        IPersonRepository peopleRepository = new PersonRepositoryImpl();
        Person person = peopleRepository.findPersonByParams(params);

        if (person == null) {
            return "Pessoa não encontrada";
        }

        Wallet wallet = this.findWalletByParams(params);
        if (wallet == null) {
            return "Carteira não encontrada";
        }

        wallet.removeCliente(person);
        return "Cliente removido da Carteira";
    }

}
