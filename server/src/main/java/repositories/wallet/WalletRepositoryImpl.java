package repositories.wallet;

import adapters.LocalStorageAdapter;
import models.Person;
import models.Wallet;
import repositories.person.IPersonRepository;
import repositories.person.PersonRepositoryImpl;

import java.util.HashMap;

public class WalletRepositoryImpl implements IWalletRepository {

    public Wallet findWalletByParams(HashMap<String, String> params) {
        return LocalStorageAdapter.wallets.get(params.get("name"));
    }

    public String insert(HashMap<String, String> params) {
        if (findWalletByParams(params) != null) {
            return "Carteira com mesmo nome já cadastrada";
        }

        Person responsavel = LocalStorageAdapter.people.get(params.get("responsible"));

        if (responsavel != null) {
            String nameParams = params.get("name");
            Double initialSalaryParams = Double.parseDouble(params.get("initialSalary"));
            Double finalSalaryParams = Double.parseDouble(params.get("finalSalary"));

            HashMap<String, Person> clientes = new HashMap<>();

            Wallet wallet = new Wallet(nameParams, responsavel, initialSalaryParams, finalSalaryParams,
                    clientes);

            LocalStorageAdapter.wallets.put(wallet.getName(), wallet);
            return "Carteira cadastrada comm sucesso";
        }

        return "Erro na criação de Carteira";
    }

    public String update(HashMap<String, String> params) {
        Wallet wallet = this.findWalletByParams(params);

        if (wallet == null) {
            return "Carteira não encontrada";
        }

        Person responsavel = LocalStorageAdapter.people.get(params.get("responsible"));

        if (responsavel == null) {
            return "Responsável informado não encontrado";
        }

        String oldName = wallet.getName();

        String nameParams = params.get("newName");
        Double initialSalaryParams = Double.parseDouble(params.get("initialSalary"));
        Double finalSalaryParams = Double.parseDouble(params.get("finalSalary"));

        wallet.setName(nameParams);
        wallet.setResponsible(responsavel);
        wallet.setInitialSalary(initialSalaryParams);
        wallet.setFinalSalary(finalSalaryParams);

        LocalStorageAdapter.wallets.remove(oldName);
        LocalStorageAdapter.wallets.put(wallet.getName(), wallet);
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

        LocalStorageAdapter.wallets.remove(wallet.getName());
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

        wallet.addCustomer(person);
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

        wallet.removeCustomer(person);
        return "Cliente removido da Carteira";
    }

}
