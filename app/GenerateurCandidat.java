package kyc.app;

import kyc.model.Nom;
import java.util.List;
import java.util.Map;

public interface GenerateurCandidat {
    Map<Nom, List<Nom>> genererCandidats(List<Nom> listeGauche, List<Nom> listeDroite);
}
