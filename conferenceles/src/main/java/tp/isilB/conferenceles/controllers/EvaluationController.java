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


    /**
     * Récupérer toutes les évaluations accessibles à un éditeur.
     *
     * @param editeurId L'ID de l'éditeur.
     * @return Liste des évaluations pour les soumissions de l'éditeur.
     */
    @GetMapping("/editeur/{editeurId}")
    public ResponseEntity<List<Evaluation>> getEvaluationsForEditeur(@PathVariable Long editeurId) {
        List<Evaluation> evaluations = evaluationService.getEvaluationsByEditeur(editeurId);

        if (evaluations.isEmpty()) {
            return ResponseEntity.ok(List.of()); // Retourne une liste vide si aucune évaluation
        }

        return ResponseEntity.ok(evaluations);
    }
}
