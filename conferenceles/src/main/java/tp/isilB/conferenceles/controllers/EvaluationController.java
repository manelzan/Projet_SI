package tp.isilB.conferenceles.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.isilB.conferenceles.entities.Evaluation;
import tp.isilB.conferenceles.services.EvaluationService;
import java.util.List;
@RestController
@RequestMapping("/evaluations")
public class EvaluationController {
    @Autowired
    private EvaluationService evaluationService;

    // Ajouter une évaluation
    @PostMapping("/{evaluateurId}/{soumissionId}")
    public ResponseEntity<Evaluation> ajouterEvaluation(@PathVariable Long evaluateurId,
                                                        @PathVariable Long soumissionId,
                                                        @RequestBody Evaluation evaluation) {
        Evaluation nouvelleEvaluation = evaluationService.ajouterEvaluation(evaluateurId, soumissionId, evaluation);
        return ResponseEntity.ok(nouvelleEvaluation);
    }

    // Récupérer les évaluations d'une soumission
    @GetMapping("/soumission/{soumissionId}")
    public List<Evaluation> getEvaluationsBySoumission(@PathVariable Long soumissionId) {
        return evaluationService.getEvaluationsBySoumission(soumissionId);
    }

    // Récupérer les évaluations d'un évaluateur
    @GetMapping("/evaluateur/{evaluateurId}")
    public List<Evaluation> getEvaluationsByEvaluateur(@PathVariable Long evaluateurId) {
        return evaluationService.getEvaluationsByEvaluateur(evaluateurId);
    }
}
