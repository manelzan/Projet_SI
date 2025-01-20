
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
    private ConferenceService conferenceService;

    @PostMapping("/{editeurId}")
    public ResponseEntity<Conference> createConference(@PathVariable Long editeurId, @RequestBody Conference conference) {
        Conference createdConference = conferenceService.createConference(editeurId, conference);
        return ResponseEntity.ok(createdConference);
    }

    @GetMapping
    public List<Conference> getAllConferences() {
        return conferenceService.getAllConferences();
    }

    @GetMapping("/editeur/{editeurId}")
    public List<Conference> getConferencesByEditeur(@PathVariable Long editeurId) {
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
}
