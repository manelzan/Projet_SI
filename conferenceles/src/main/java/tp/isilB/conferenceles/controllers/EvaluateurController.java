package tp.isilB.conferenceles.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tp.isilB.conferenceles.entities.Evaluateur;
import tp.isilB.conferenceles.repositries.EvaluateurRepository;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/evaluateurs")
public class EvaluateurController {
    @Autowired
    private EvaluateurRepository evaluateurRepository;

    @GetMapping
    public List<Evaluateur> getAllEvaluateurs() {
        return evaluateurRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Evaluateur> createEvaluateur(@Valid @RequestBody Evaluateur evaluateur) {
        Evaluateur createdEvaluateur = evaluateurRepository.save(evaluateur);
        return ResponseEntity.ok(createdEvaluateur);
    }
}
