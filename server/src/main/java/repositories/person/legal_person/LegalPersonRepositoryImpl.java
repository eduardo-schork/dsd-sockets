package repositories.person.legal_person;

import adapters.LocalStorageAdapter;
import models.LegalPerson;
import models.Person;
import models.PhysicalPerson;
import repositories.person.PersonRepositoryImpl;
import repositories.wallet.IWalletRepository;
import repositories.wallet.WalletRepositoryImpl;

import java.util.HashMap;
import java.util.Map;

import static utils.FilterHashMapByValue.filterByValue;

public class LegalPersonRepositoryImpl extends PersonRepositoryImpl implements ILegalPersonRepository {
    @Override
    public String insert(HashMap<String, String> params) {
        if (findPersonByParams(params) != null) {
            return "Pessoa com mesmo CPF já cadastrada";
        }

        LegalPerson person = new LegalPerson(
                params.get("cpf"),
                params.get("name"),
                params.get("address"),
                params.get("cnpj"));

        LocalStorageAdapter.people.put(person.getCpf(), person);

        return "Pessoa cadastrada com sucesso";
    }

    @Override
    public String update(HashMap<String, String> params) {
        Person person = (Person) this.findPersonByParams(params);

        if (person == null || !(person instanceof LegalPerson)) {
            return "Pessoa jurídica não encontrada";
        }

        person.setName(params.get("name"));
        person.setAddress(params.get("address"));
        ((LegalPerson) person).setCnpj(params.get("cnpj"));
        return "Pessoa atualizada com sucesso";
    }

    @Override
    public String list(HashMap<String, String> params) {
        Map<String, Person> filteredMap = filterByValue(
                LocalStorageAdapter.people,
                value -> value instanceof LegalPerson);

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

    @Override
    public String get(HashMap<String, String> params) {
        if (LocalStorageAdapter.people.isEmpty()) {
            return "Sem pessoas cadastradas";
        }

        Person person = this.findPersonByParams(params);
        if (person == null || !(person instanceof LegalPerson)) {
            return "Pessoa jurídica não encontrada";
        }

        return person.toString();
    }

    @Override
    public String delete(HashMap<String, String> params) {
        if (LocalStorageAdapter.people.isEmpty()) {
            return "Sem pessoas cadastradas";
        }

        Person person = this.findPersonByParams(params);
        if (person == null || !(person instanceof LegalPerson)) {
            return "Pessoa jurídica não encontrada";
        }

        String customerCPF = person.getCpf();

        IWalletRepository walletRepository = new WalletRepositoryImpl();
        walletRepository.removePersonIfExists(customerCPF);

        LocalStorageAdapter.people.remove(customerCPF);

        return "Pessoa removida com sucesso";
    }
}
