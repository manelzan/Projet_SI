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

    @PostMapping("/{id}/affecter-evaluateurs")
    public ResponseEntity<Soumission> affecterEvaluateurs(@PathVariable Long id, @RequestBody List<Long> evaluateurIds) {
        Soumission soumission = soumissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Soumission introuvable"));

        List<Evaluateur> evaluateurs = evaluateurRepository.findAllById(evaluateurIds);
        if (evaluateurs.size() != evaluateurIds.size()) {
            throw new RuntimeException("Un ou plusieurs évaluateurs n'existent pas.");
        }

        soumissionService.affecterEvaluateurs(soumission, evaluateurs);

        return ResponseEntity.ok(soumission);
    }



    @PostMapping("/{soumissionId}/affecter-evaluateurs")
    public ResponseEntity<?> affecterEvaluateursParEditeur(
            @PathVariable Long soumissionId,
            @RequestBody List<Long> evaluateurIds,
            @RequestParam Long editeurId) {

        // Vérification de l'existence de l'éditeur
        Editeur editeur = editeurRepository.findById(editeurId)
                .orElseThrow(() -> new RuntimeException("Éditeur introuvable"));

        // Récupérer la soumission par son ID
        Soumission soumission = soumissionRepository.findById(soumissionId)
                .orElseThrow(() -> new RuntimeException("Soumission introuvable"));

        // Récupérer la liste des évaluateurs par leurs IDs
        List<Evaluateur> evaluateurs = evaluateurRepository.findAllById(evaluateurIds);
        if (evaluateurs.size() != evaluateurIds.size()) {
            throw new RuntimeException("Un ou plusieurs évaluateurs n'existent pas.");
        }

        // Vérification qu'aucun évaluateur ne soit auteur de la soumission
        for (Evaluateur evaluateur : evaluateurs) {
            // Vérifiez si l'auteur de l'évaluateur est dans la liste des auteurs de la soumission
            boolean isAuteur = soumission.getAuteurs().stream()
                    .anyMatch(auteur -> auteur.equals(evaluateur.getAuteur()));

            if (isAuteur) {
                throw new RuntimeException("Un évaluateur ne peut pas évaluer une soumission dont il est auteur.");
            }
        }

        // Appeler le service pour affecter les évaluateurs à la soumission
        soumissionService.affecterEvaluateursParEditeur(soumission, evaluateurs, editeur);

        return ResponseEntity.ok(soumission);
    }


    // Ajouter un auteur à une soumission
    @PostMapping("/{soumissionId}/auteurs/{auteurId}")
    public ResponseEntity<Soumission> addAuteurToSoumission(
            @PathVariable Long soumissionId,
            @PathVariable Long auteurId) {
        Soumission updatedSoumission = soumissionService.addAuteurToSoumission(soumissionId, auteurId);
        return ResponseEntity.ok(updatedSoumission);
    }

    // Ajouter un évaluateur à une soumission
    @PostMapping("/{soumissionId}/evaluateurs/{evaluateurId}")
    public ResponseEntity<Soumission> addEvaluateurToSoumission(
            @PathVariable Long soumissionId,
            @PathVariable Long evaluateurId) {
        Soumission updatedSoumission = soumissionService.addEvaluateurToSoumission(soumissionId, evaluateurId);
        return ResponseEntity.ok(updatedSoumission);
    }

}