package kyc.app;

import kyc.model.Nom;
import java.util.List;

public interface GenerateurCandidat {
    List<Nom> genererCandidats(Nom nomClient, List<Nom> listSanctionnes);
}
