package tp.isilB.conferenceles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.isilB.conferenceles.entities.Editeur;
import tp.isilB.conferenceles.entities.Evaluateur;
import tp.isilB.conferenceles.entities.Soumission;
import tp.isilB.conferenceles.repositries.EditeurRepository;
import tp.isilB.conferenceles.repositries.EvaluateurRepository;
import tp.isilB.conferenceles.repositries.SoumissionRepository;
import tp.isilB.conferenceles.services.SoumissionService;
import tp.isilB.conferenceles.entities.Auteur;


import java.util.List;

@RestController
@RequestMapping("/soumissions")
public class SoumissionController {

    @Autowired
    private SoumissionRepository soumissionRepository;

    @Autowired
    private EvaluateurRepository evaluateurRepository;

    @Autowired
    private SoumissionService soumissionService;

    @Autowired
    private EditeurRepository editeurRepository;

    @GetMapping
    public List<Soumission> getAllSoumissions() {
        return soumissionRepository.findAll();
    }

    @GetMapping("/auteur/{auteurId}")
    public List<Soumission> getSoumissionsByAuteur(@PathVariable Long auteurId) {
        return soumissionRepository.findByAuteurId(auteurId);
    }

    @PostMapping
    public Soumission createSoumission(@RequestBody Soumission soumission) {
        return soumissionRepository.save(soumission);
    }

    @PostMapping("/{soumissionId}/affecter-evaluateurs")

    public ResponseEntity<Soumission> affecterEvaluateurs(
            @PathVariable Long soumissionId,
            @RequestBody List<Long> evaluateurIds,
            @RequestParam(required = false) Long editeurId) {

        // Récupérer la soumission
        Soumission soumission = soumissionRepository.findById(soumissionId)
                .orElseThrow(() -> new RuntimeException("Soumission introuvable"));

        // Vérifier si les évaluateurs existent
        List<Evaluateur> evaluateurs = evaluateurRepository.findAllById(evaluateurIds);
        if (evaluateurs.size() != evaluateurIds.size()) {
            throw new RuntimeException("Un ou plusieurs évaluateurs n'existent pas.");
        }

        // Vérifier qu'aucun évaluateur n'est auteur de cette soumission
        for (Evaluateur evaluateur : evaluateurs) {
            // Parcourez les auteurs de la soumission pour voir si l'évaluateur en fait partie
            for (Auteur auteur : soumission.getAuteurs()) {
                if (auteur.getId().equals(evaluateur.getId())) {
                    throw new RuntimeException("Un évaluateur ne peut pas évaluer une soumission dont il est auteur.");
                }
            }
        }

        // Si un éditeur est spécifié, vérifier que l'éditeur existe et l'affecter
        if (editeurId != null) {
            Editeur editeur = editeurRepository.findById(editeurId)
                    .orElseThrow(() -> new RuntimeException("Éditeur introuvable"));
            // Appeler le service pour affecter les évaluateurs avec l'éditeur
            soumissionService.affecterEvaluateursParEditeur(soumission, evaluateurs, editeur);
        } else {
            // Affecter les évaluateurs sans l'éditeur
            soumissionService.affecterEvaluateurs(soumission, evaluateurs);
        }

        return ResponseEntity.ok(soumission);
    }

}
