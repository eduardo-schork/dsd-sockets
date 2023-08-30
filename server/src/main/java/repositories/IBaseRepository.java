package repositories;

import java.util.HashMap;

public interface IBaseRepository {
    String insert(HashMap<String, String> params);

    String update(HashMap<String, String> params);

    String get(HashMap<String, String> params);

    String delete(HashMap<String, String> params);

    String list(HashMap<String, String> params);

    String add(HashMap<String, String> params);

    String remove(HashMap<String, String> params);

}
