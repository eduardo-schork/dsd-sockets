package repositories.wallet;

import models.Wallet;
import repositories.IBaseRepository;

import java.util.HashMap;

public interface IWalletRepository extends IBaseRepository {
    Wallet findWalletByParams(HashMap<String, String> params);

    void removePersonIfExists(String cpf);
}
