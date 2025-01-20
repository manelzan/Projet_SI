
package tp.isilB.conferenceles.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.isilB.conferenceles.entities.Conference;
import tp.isilB.conferenceles.entities.Editeur;
import tp.isilB.conferenceles.entities.Soumission;
import tp.isilB.conferenceles.repositries.EditeurRepository;
import tp.isilB.conferenceles.services.ConferenceService;

import jakarta.validation.Valid;
import tp.isilB.conferenceles.services.SoumissionService;

import java.util.List;
import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/conferences")
public class ConferenceController {

    @Autowired
   private ConferenceService conferenceService;

    @Autowired
    private EditeurRepository editeurRepository;


    @PostMapping("/{editeurId}")
    public ResponseEntity<Conference> createConference(@PathVariable Long editeurId, @RequestBody Conference conference) {
       Editeur editeur = editeurRepository.findById(editeurId)
               .orElseThrow(() -> new RuntimeException("Éditeur introuvable avec l'ID : " + editeurId));

       conference.setEditeur(editeur);
        Conference createdConference = conferenceService.createConference(editeurId, conference);
        return ResponseEntity.ok(createdConference);
    }

    @GetMapping
    public List<Conference> getAllConferences() {
      return conferenceService.getAllConferences();
    }
    @GetMapping("/editeur/{editeurId}")
    public List<Conference> getConferencesByEditeur(@PathVariable Long editeurId) {
        System.out.println("Requête pour les conférences de l'éditeur ID : " + editeurId);
        return conferenceService.getConferencesByEditeur(editeurId);
    }

       @PutMapping("/{id}")
        public ResponseEntity<Conference> updateConference(@PathVariable Long id, @RequestBody Conference conference) {
            Conference updatedConference = conferenceService.updateConference(id, conference);
            return ResponseEntity.ok(updatedConference);
        }

        @PatchMapping("/{id}/etat")
        public ResponseEntity<Conference> updateConferenceState(@PathVariable Long id, @RequestBody String nouvelEtat) {
            Conference updatedConference = conferenceService.updateConferenceState(id, nouvelEtat);
            return ResponseEntity.ok(updatedConference);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteConference(@PathVariable Long id) {
             conferenceService.deleteConference(id);
            return ResponseEntity.noContent().build();
        }

    @Autowired
    private SoumissionService soumissionService;

    @PostMapping("/{conferenceId}/soumissions")
    public ResponseEntity<Soumission> addSoumissionToConference(
            @PathVariable Long conferenceId,
            @RequestBody Soumission soumission) {
        Soumission savedSoumission = soumissionService.addSoumissionToConference(conferenceId, soumission);
        return ResponseEntity.ok(savedSoumission);
    }
    }
