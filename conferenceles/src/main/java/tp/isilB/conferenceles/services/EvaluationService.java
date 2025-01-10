package tp.isilB.conferenceles.services;
import org.springframework.stereotype.Service;
import tp.isilB.conferenceles.entities.Evaluateur;
import tp.isilB.conferenceles.entities.Evaluation;
import tp.isilB.conferenceles.entities.Soumission;
import tp.isilB.conferenceles.entities.utilisateur;
import tp.isilB.conferenceles.repositries.EvaluateurRepository;
import tp.isilB.conferenceles.repositries.EvaluationRepository;
import tp.isilB.conferenceles.repositries.SoumissionRepository;
import tp.isilB.conferenceles.repositries.utilisateurRepository;

import java.util.List;
@Service
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final SoumissionRepository soumissionRepository;
    private final utilisateurRepository utilisateurRepository;

    public EvaluationService(EvaluationRepository evaluationRepository,
                             SoumissionRepository soumissionRepository,
                             utilisateurRepository utilisateurRepository) {
        this.evaluationRepository = evaluationRepository;
        this.soumissionRepository = soumissionRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public Evaluation ajouterEvaluation(Long evaluateurId, Long soumissionId, Evaluation evaluation) {
        // Récupérer l'utilisateur correspondant à l'ID donné
        utilisateur utilisateur = utilisateurRepository.findById(evaluateurId)
                .orElseThrow(() -> new RuntimeException("Évaluateur introuvable"));

        // Vérification : l'utilisateur doit avoir le rôle 'Evaluateur'
        if (!utilisateur.getRoles().contains("Evaluateur")) {
            throw new RuntimeException("Cet utilisateur n'a pas le rôle d'évaluateur.");
        }

        // Récupérer la soumission
        Soumission soumission = soumissionRepository.findById(soumissionId)
                .orElseThrow(() -> new RuntimeException("Soumission introuvable"));

        // Vérification : l'évaluateur ne peut pas être l'auteur de la soumission
        if (soumission.getAuteur().getId().equals(utilisateur.getId())) {
            throw new RuntimeException("Un évaluateur ne peut pas évaluer une soumission dont il est l'auteur.");
        }

        // Affecter l'évaluateur et la soumission à l'évaluation
        evaluation.setEvaluateur((Evaluateur) utilisateur);
        evaluation.setSoumission(soumission);

        // Sauvegarder l'évaluation
        return evaluationRepository.save(evaluation);
    }

    // Méthode pour récupérer les évaluations d'une soumission
    public List<Evaluation> getEvaluationsBySoumission(Long soumissionId) {
        return evaluationRepository.findBySoumissionId(soumissionId);
    }

    // Méthode pour récupérer les évaluations d'un évaluateur
    public List<Evaluation> getEvaluationsByEvaluateur(Long evaluateurId) {
        return evaluationRepository.findByEvaluateurId(evaluateurId);
    }

}
