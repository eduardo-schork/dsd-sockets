package adapters;

import models.LegalPerson;
import models.Person;
import models.PhysicalPerson;
import models.Wallet;

import java.util.HashMap;

public class LocalStorageAdapter {

    public static HashMap<String, Person> people = new HashMap<>();
    public static HashMap<String, PhysicalPerson> physicalPeople = new HashMap<>();
    public static HashMap<String, LegalPerson> legalPeople = new HashMap<>();
    public static HashMap<String, Wallet> wallets = new HashMap<>();

}
