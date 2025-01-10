package tp.isilB.conferenceles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.isilB.conferenceles.entities.Evaluateur;
import tp.isilB.conferenceles.entities.Soumission;
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
}
