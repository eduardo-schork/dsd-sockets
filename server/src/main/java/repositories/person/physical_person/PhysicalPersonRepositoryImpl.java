package repositories.person.physical_person;

import adapters.LocalStorageAdapter;
import models.LegalPerson;
import models.Person;
import models.PhysicalPerson;
import repositories.person.PersonRepositoryImpl;

import java.util.HashMap;
import java.util.Map;

import static utils.FilterHashMapByValue.filterByValue;

public class PhysicalPersonRepositoryImpl extends PersonRepositoryImpl implements IPhysicalPersonRepository {
    @Override
    public String insert(HashMap<String, String> params) {
        if (findPersonByParams(params) != null) {
            return "Pessoa com mesmo CPF já cadastrada";
        }
        PhysicalPerson person = new PhysicalPerson(
                params.get("cpf"),
                params.get("name"),
                params.get("address"),
                params.get("email"));

        LocalStorageAdapter.people.put(person.getCpf(), person);

        return "Pessoa cadastrada com sucesso";
    }

    @Override
    public String update(HashMap<String, String> params) {
        PhysicalPerson person = (PhysicalPerson) this.findPersonByParams(params);
        if (person == null || !(person instanceof PhysicalPerson)) {
            return "Pessoa não encontrada";
        }

        person.setName(params.get("name"));
        person.setAddress(params.get("address"));
        person.setEmail(params.get("email"));
        return "Pessoa atualizada com sucesso";
    }

    @Override
    public String list(HashMap<String, String> params) {
        Map<String, Person> filteredMap = filterByValue(
                LocalStorageAdapter.people,
                value -> value instanceof PhysicalPerson);

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
        if (person == null || !(person instanceof PhysicalPerson)) {
            return "Pessoa não encontrada";
        }

        return person.toString();
    }
}
