
package tp.isilB.conferenceles.services;
import org.springframework.stereotype.Service;
import tp.isilB.conferenceles.entities.Conference;
import tp.isilB.conferenceles.entities.Editeur;
import tp.isilB.conferenceles.repositries.ConferenceRepository;
import tp.isilB.conferenceles.repositries.EditeurRepository;

import java.time.LocalDate;
import java.util.List;
@Service
public class ConferenceService {

    private final ConferenceRepository conferenceRepository;
    private final EditeurRepository editeurRepository;

    public ConferenceService(ConferenceRepository conferenceRepository, EditeurRepository editeurRepository) {
        this.conferenceRepository = conferenceRepository;
        this.editeurRepository = editeurRepository;
    }

    // Récupérer toutes les conférences
    public List<Conference> getAllConferences() {
        return conferenceRepository.findAll();
    }

    // Créer une conférence par un éditeur
    public Conference createConference(Long editeurId, Conference conference) {
        Editeur editeur = editeurRepository.findById(editeurId)
                .orElseThrow(() -> new RuntimeException("Éditeur introuvable"));
        conference.setEditeur(editeur);
        validateConferenceDates(conference);
        return conferenceRepository.save(conference);
    }

    // Mettre à jour une conférence
    public Conference updateConference(Long conferenceId, Conference updatedConference) {
        Conference existingConference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new RuntimeException("Conférence introuvable"));

        existingConference.setTitre(updatedConference.getTitre());
        existingConference.setDateDebut(updatedConference.getDateDebut());
        existingConference.setDateFin(updatedConference.getDateFin());
        existingConference.setThematique(updatedConference.getThematique());
        existingConference.setEtat(updatedConference.getEtat());

        validateConferenceDates(existingConference);
        return conferenceRepository.save(existingConference);
    }

    // Modifier l'état d'une conférence
    public Conference updateConferenceState(Long conferenceId, String nouvelEtat) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new RuntimeException("Conférence introuvable"));
        conference.setEtat(nouvelEtat);
        return conferenceRepository.save(conference);
    }

    // Récupérer les conférences par éditeur
    public List<Conference> getConferencesByEditeur(Long editeurId) {
        return conferenceRepository.findAll().stream()
                .filter(conference -> conference.getEditeur().getId().equals(editeurId))
                .toList();
    }

    // Supprimer une conférence
    public void deleteConference(Long conferenceId) {
        if (!conferenceRepository.existsById(conferenceId)) {
            throw new RuntimeException("Conférence introuvable");
        }
        conferenceRepository.deleteById(conferenceId);
    }

    // Valider les dates de la conférence
    private void validateConferenceDates(Conference conference) {
        if (conference.getDateDebut().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La date de début doit être dans le futur ou aujourd'hui.");
        }
        if (conference.getDateFin().isBefore(conference.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin doit être après la date de début.");
        }
    }
}
