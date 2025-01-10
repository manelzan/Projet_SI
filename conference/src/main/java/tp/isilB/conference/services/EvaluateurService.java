package  tp.isilB.conference.services;
import org.springframework.stereotype.Service;
import  tp.isilB.conference.entities.Evaluateur;
import  tp.isilB.conference.entities.Soumission;
import  tp.isilB.conference.repositories.SoumissionRepository;


import java.util.List;

@Service
public class EvaluateurService {
    private final SoumissionRepository soumissionRepository;

    public EvaluateurService(SoumissionRepository soumissionRepository) {
        this.soumissionRepository = soumissionRepository;
    }

    public void assignerEvaluateurs(Soumission soumission, List<Evaluateur> evaluateurs) {
        for (Evaluateur evaluateur : evaluateurs) {
            if (soumission.getAuteur().getId().equals(evaluateur.getId())) {
                throw new RuntimeException("Un évaluateur ne peut pas évaluer une soumission dont il est l'auteur.");
            }
        }
        soumission.setEvaluateurs(evaluateurs);
        soumissionRepository.save(soumission);
    }
}
