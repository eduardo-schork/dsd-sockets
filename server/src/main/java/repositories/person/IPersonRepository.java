package repositories.person;

import models.Person;
import repositories.IBaseRepository;

import java.util.HashMap;

public interface IPersonRepository extends IBaseRepository {
    Person findPersonByParams(HashMap<String, String> params);

}
