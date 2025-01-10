package tp.isilB.conference.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.validation.Valid;


import tp.isilB.conference.entities.Evaluateur;
import tp.isilB.conference.repositories.EvaluateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluateurs")

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
