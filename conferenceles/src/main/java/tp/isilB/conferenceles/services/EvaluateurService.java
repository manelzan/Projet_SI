package tp.isilB.conferenceles.services;
import org.springframework.stereotype.Service;
import tp.isilB.conferenceles.entities.Evaluateur;
import tp.isilB.conferenceles.entities.Soumission;
import tp.isilB.conferenceles.repositries.EvaluateurRepository;
import tp.isilB.conferenceles.repositries.SoumissionRepository;


import java.util.List;
@Service

public class EvaluateurService {
    private final SoumissionRepository soumissionRepository;
    private final EvaluateurRepository evaluateurRepository;

    public EvaluateurService(SoumissionRepository soumissionRepository, EvaluateurRepository evaluateurRepository) {
        this.soumissionRepository = soumissionRepository;
        this.evaluateurRepository = evaluateurRepository;
    }

    public void assignerEvaluateurs(Soumission soumission, List<Evaluateur> evaluateurs) {
        // Vérifie si un évaluateur est également auteur de la soumission
        for (Evaluateur evaluateur : evaluateurs) {
            if (soumission.getAuteurs().stream().anyMatch(auteur -> auteur.getId().equals(evaluateur.getId()))) {
                throw new RuntimeException("Un évaluateur ne peut pas évaluer une soumission dont il est l'auteur.");
            }
        }

        // Logique d'affectation (par exemple, sauvegarde des relations)
        for (Evaluateur evaluateur : evaluateurs) {
            evaluateur.getSoumissions().add(soumission); // Ajout de la soumission à l'évaluateur
        }

        soumissionRepository.save(soumission); // Mise à jour de la soumission
    }
}
