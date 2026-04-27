package kyc.app;

import kyc.model.Nom;
import java.util.List;

public interface GenerateurCandidat {
    List<Nom> genererCandidats(List<Nom> listSanctionnes);
}
