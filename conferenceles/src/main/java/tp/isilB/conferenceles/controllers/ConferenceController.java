
package tp.isilB.conferenceles.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.isilB.conferenceles.DTO.ConferenceDTO;
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
    private SoumissionService soumissionService;

    @GetMapping
    public ResponseEntity<List<ConferenceDTO>> getAllConferences() {
        List<ConferenceDTO> conferences = conferenceService.getAllConferences();
        return ResponseEntity.ok(conferences);
    }

    @PostMapping("/{editeurId}")
    public ResponseEntity<ConferenceDTO> createConference(
            @PathVariable Long editeurId,
            @RequestBody ConferenceDTO conferenceDTO) {
        ConferenceDTO createdConference = conferenceService.createConference(editeurId, conferenceDTO);
        return ResponseEntity.ok(createdConference);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConferenceDTO> updateConference(
            @PathVariable Long id,
            @RequestBody ConferenceDTO conferenceDTO) {
        ConferenceDTO updatedConference = conferenceService.updateConference(id, conferenceDTO);
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

    @PostMapping("/{conferenceId}/soumissions")
    public ResponseEntity<Soumission> addSoumissionToConference(
            @PathVariable Long conferenceId,
            @RequestBody Soumission soumission) {
        Soumission savedSoumission = soumissionService.addSoumissionToConference(conferenceId, soumission);
        return ResponseEntity.ok(savedSoumission);
    }
}
