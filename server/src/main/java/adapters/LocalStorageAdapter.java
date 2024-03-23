package adapters;

import models.Person;
import models.Wallet;

import java.util.HashMap;

public class LocalStorageAdapter {
    public static HashMap<String, Person> people = new HashMap<>();
    public static HashMap<String, Wallet> wallets = new HashMap<>();
}
