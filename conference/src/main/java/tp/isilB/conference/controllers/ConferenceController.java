package tp.isilB.conference.controllers;

import tp.isilB.conference.entities.Conference;
import tp.isilB.conference.services.ConferenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/conferences")
public class ConferenceController {

    @Autowired
    private ConferenceService conferenceService;

    @GetMapping
    public List<Conference> getAllConferences() {
        return conferenceService.getAllConferences();
    }

    @PostMapping
    public ResponseEntity<?> createConference(@RequestBody Conference conference) {
        // Vérification des dates
        if (conference.getDateDebut().isBefore(LocalDate.now())) {
            return ResponseEntity.badRequest().body("La date de début doit être dans le futur ou aujourd'hui.");
        }
        if (conference.getDateFin().isBefore(conference.getDateDebut())) {
            return ResponseEntity.badRequest().body("La date de fin doit être après la date de début.");
        }

        // Enregistrer la conférence
        Conference createdConference = conferenceService.createConference(conference);
        return ResponseEntity.ok(createdConference);
    }

    @PutMapping("/{id}/etat")
    public ResponseEntity<Conference> updateEtat(@PathVariable Long id, @RequestBody String nouvelEtat) {
        Conference updatedConference = conferenceService.updateEtat(id, nouvelEtat);
        return ResponseEntity.ok(updatedConference);
    }
}
