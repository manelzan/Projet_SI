package tp.isilB.conference.services;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tp.isilB.conference.entities.Evaluation;
import tp.isilB.conference.entities.Soumission;
import tp.isilB.conference.entities.Evaluateur;
import tp.isilB.conference.repositories.EvaluationRepository;
import tp.isilB.conference.repositories.SoumissionRepository;

import java.util.List;
@Service
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;
    private final SoumissionRepository soumissionRepository;
    public EvaluationService(EvaluationRepository evaluationRepository, SoumissionRepository soumissionRepository) {
        this.evaluationRepository = evaluationRepository;
        this.soumissionRepository = soumissionRepository;
    }
    // creer un evaluation
    @Transactional
    public Evaluation createEvaluation(Evaluation evaluation){
        // verifier si la soumission existe
        Soumission soumission=soumissionRepository.findById(evaluation.getSoumission().getId()).orElseThrow(()->new RuntimeException("Soumission introuvable"));
        // verifier que l'evaluateur a ete assigne a la soumission

        return evaluationRepository.save(evaluation);
    }
// recuperer les evaluation d'une soumission
    public List<Evaluation> getEvaluationBySoumission(Long soumissionId){
        return evaluationRepository.findBySoumissionId(soumissionId);
    }


}
