package tp.isilB.conferenceles.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.isilB.conferenceles.DTO.AuteurDTO;
import tp.isilB.conferenceles.entities.Auteur;
import tp.isilB.conferenceles.repositries.AuteurRepository;

import java.util.List;
import java.util.Optional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.isilB.conferenceles.DTO.AuteurDTO;
import tp.isilB.conferenceles.services.AuteurService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auteurs")
public class AuteurController {
    @Autowired
    private AuteurService auteurService;

    @GetMapping
    public List<AuteurDTO> getAllAuteurs() {
        return auteurService.getAllAuteurs();
    }

    @PostMapping
    public ResponseEntity<AuteurDTO> createAuteur(@Valid @RequestBody AuteurDTO auteurDTO) {
        AuteurDTO createdAuteur = auteurService.createAuteur(auteurDTO);
        return ResponseEntity.ok(createdAuteur);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuteur(@PathVariable Long id) {
        try {
            auteurService.deleteAuteur(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<AuteurDTO> getAuteurById(@PathVariable Long id) {
        log.info("Requête pour récupérer l'auteur avec ID : {}", id);
        try {
            AuteurDTO auteurDTO = auteurService.getAuteurById(id);
            return ResponseEntity.ok(auteurDTO);
        } catch (RuntimeException e) {
            log.error("Auteur introuvable avec ID : {}", id, e);
            return ResponseEntity.notFound().build();
        }
    }
}
