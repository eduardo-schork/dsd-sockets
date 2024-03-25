package repositories.person;

import adapters.LocalStorageAdapter;
import models.Person;
import repositories.wallet.IWalletRepository;
import repositories.wallet.WalletRepositoryImpl;

import java.util.HashMap;
import java.util.Map;

import static utils.FilterHashMapByValue.filterByValue;

public class PersonRepositoryImpl implements IPersonRepository {

    public Person findPersonByParams(HashMap<String, String> params) {
        return LocalStorageAdapter.people.get(params.get("cpf"));
    }

    public String insert(HashMap<String, String> params) {
        if (findPersonByParams(params) != null) {
            return "Pessoa com mesmo CPF já cadastrada";
        }

        Person person = new Person(params.get("cpf"), params.get("name"), params.get("address"));
        LocalStorageAdapter.people.put(person.getCpf(), person);

        return "Pessoa cadastrada com sucesso";
    }

    public String update(HashMap<String, String> params) {
        Person person = this.findPersonByParams(params);
        if (person == null) {
            return "Pessoa não encontrada";
        }

        person.setName(params.get("name"));
        person.setAddress(params.get("address"));
        return "Pessoa atualizada com sucesso";
    }

    public String get(HashMap<String, String> params) {
        if (LocalStorageAdapter.people.isEmpty()) {
            return "Sem pessoas cadastradas";
        }

        Person person = this.findPersonByParams(params);
        if (person == null) {
            return "Pessoa não encontrada";
        }

        return person.toString();
    }

    public String delete(HashMap<String, String> params) {
        if (LocalStorageAdapter.people.isEmpty()) {
            return "Sem pessoas cadastradas";
        }

        Person person = this.findPersonByParams(params);
        if (person == null) {
            return "Pessoa não encontrada";
        }

        String customerCPF = person.getCpf();

        IWalletRepository walletRepository = new WalletRepositoryImpl();
        walletRepository.removePersonIfExists(customerCPF);

        LocalStorageAdapter.people.remove(customerCPF);

        return "Pessoa removida com sucesso";
    }

    public String list(HashMap<String, String> params) {
        Map<String, Person> filteredMap = filterByValue(
                LocalStorageAdapter.people,
                value -> value instanceof Person);

        if (filteredMap.isEmpty()) {
            return "0";
        }

        StringBuilder response = new StringBuilder();
        if (filteredMap.size() < 10) {
            response.append("0");
        }

        response.append(filteredMap.size());

        for (Person person : filteredMap.values()) {
            response.append("\n").append(person.toString());
        }

        return response.toString();
    }

    public String add(HashMap<String, String> params) {
        return null;
    }

    public String remove(HashMap<String, String> params) {
        return null;
    }

}
