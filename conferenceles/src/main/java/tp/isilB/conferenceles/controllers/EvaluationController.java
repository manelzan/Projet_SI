package tp.isilB.conferenceles.controllers;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.isilB.conferenceles.DTO.EvaluationCreationDTO;
import tp.isilB.conferenceles.DTO.EvaluationDTO;
import tp.isilB.conferenceles.entities.EtatEvaluation;
import tp.isilB.conferenceles.entities.Evaluation;
import tp.isilB.conferenceles.services.EvaluationService;
import java.util.List;
@RestController
@RequestMapping("/evaluations")
public class EvaluationController {
    @Autowired
    private EvaluationService evaluationService;

    @PostMapping
    public ResponseEntity<EvaluationDTO> ajouterEvaluation(@Valid @RequestBody EvaluationCreationDTO evaluationCreationDTO) {
        EvaluationDTO nouvelleEvaluation = evaluationService.ajouterEvaluation(evaluationCreationDTO);
        return ResponseEntity.ok(nouvelleEvaluation);
    }


    @GetMapping("/soumission/{soumissionId}")
    public List<EvaluationDTO> getEvaluationsBySoumission(@PathVariable Long soumissionId) {
        return evaluationService.getEvaluationsBySoumission(soumissionId);
    }

    @GetMapping("/evaluateur/{evaluateurId}")
    public List<EvaluationDTO> getEvaluationsByEvaluateur(@PathVariable Long evaluateurId) {
        return evaluationService.getEvaluationsByEvaluateur(evaluateurId);
    }

    /**
     * Récupérer toutes les évaluations accessibles à un éditeur.
     *
     * @param editeurId L'ID de l'éditeur.
     * @return Liste des évaluations pour les soumissions de l'éditeur.
     */
    @GetMapping("/editeur/{editeurId}")
    public ResponseEntity<List<EvaluationDTO>> getEvaluationsForEditeur(@PathVariable Long editeurId) {
        List<EvaluationDTO> evaluations = evaluationService.getEvaluationsByEditeur(editeurId);

        if (evaluations.isEmpty()) {
            return ResponseEntity.ok(List.of()); // Liste vide si aucune évaluation trouvée
        }

            return ResponseEntity.ok(evaluations);
    }

/*

    @PostMapping("/{evaluationId}/evaluer")
    public ResponseEntity<Evaluation> evaluerSoumission(
            @PathVariable Long evaluationId,
            @RequestParam int note,
            @RequestParam String commentaires,
            @RequestParam EtatEvaluation etat) {
        Evaluation evaluation = evaluationService.evaluerSoumission(evaluationId, note, commentaires, etat);
        return ResponseEntity.ok(evaluation);
    }
*/


}






