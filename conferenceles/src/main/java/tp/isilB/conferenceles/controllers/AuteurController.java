package tp.isilB.conferenceles.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.isilB.conferenceles.entities.Auteur;
import tp.isilB.conferenceles.repositries.AuteurRepository;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/auteurs")
public class AuteurController {
    @Autowired
    private AuteurRepository auteurRepository;

    @GetMapping
    public List<Auteur> getAllAuteurs() {
        return auteurRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auteur> getAuteurById(@PathVariable Long id) {
        return auteurRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Auteur> createAuteur(@Valid @RequestBody Auteur auteur) {
        Auteur createdAuteur = auteurRepository.save(auteur);
        return ResponseEntity.ok(createdAuteur);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuteur(@PathVariable Long id) {
        if (!auteurRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        auteurRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
