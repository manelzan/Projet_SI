package tp.isilB.conference.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.isilB.conference.entities.Evaluation;
import tp.isilB.conference.services.EvaluationService;
import java.util.List;
@RestController
@RequestMapping("/evaluations")
public class EvaluationController {
    @Autowired
    private EvaluationService evaluationService;
    // ENDPOINT POUR CREER UNE EVALUATION
    @PostMapping
    public ResponseEntity<Evaluation> createEvaluation(@RequestBody Evaluation evaluation) {
        Evaluation createdEvaluation = evaluationService.createEvaluation(evaluation);
        return ResponseEntity.status(201).body(createdEvaluation);
    }
    // ENDPOINT POUR RECUPERER LES EVALUATION D'UNE SOUMISSION
    @GetMapping("/soumissions/{soumissionId}")
    public List<Evaluation> getEvaluationBySoumission(@PathVariable Long soumissionId) {
        return evaluationService.getEvaluationBySoumission(soumissionId);
    }
}
