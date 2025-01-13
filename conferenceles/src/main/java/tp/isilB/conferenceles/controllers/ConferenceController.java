package tp.isilB.conferenceles.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.isilB.conferenceles.entities.Conference;
import tp.isilB.conferenceles.entities.Editeur;
import tp.isilB.conferenceles.repositries.EditeurRepository;
import tp.isilB.conferenceles.services.ConferenceService;
import tp.isilB.conferenceles.repositries.ConferenceRepository;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/conferences")
public class ConferenceController {

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private EditeurRepository editeurRepository;

    @GetMapping
    public List<Conference> getAllConferences() {
        return conferenceRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createConference(@Valid @RequestBody Conference conference) {
        // Vérifiez que l'éditeur existe
        Long editeurId = conference.getEditeur().getId();
        Editeur editeur = editeurRepository.findById(editeurId)
                .orElseThrow(() -> new RuntimeException("Éditeur introuvable"));

        conference.setEditeur(editeur);
        Conference createdConference = conferenceRepository.save(conference);

        return ResponseEntity.ok(createdConference);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConference(@PathVariable Long id) {
        if (!conferenceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        conferenceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
