package tp.isilB.conferenceles.services;
import org.springframework.stereotype.Service;
import tp.isilB.conferenceles.entities.Conference;
import tp.isilB.conferenceles.repositries.ConferenceRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ConferenceService {
    private final ConferenceRepository conferenceRepository;

    public ConferenceService(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    // Récupérer toutes les conférences
    public List<Conference> getAllConferences() {
        return conferenceRepository.findAll();
    }

    // Créer une conférence avec validation
    public Conference createConference(Conference conference) {
        validateConferenceDates(conference);
        return conferenceRepository.save(conference);
    }

    // Mettre à jour l'état d'une conférence
    public Conference updateEtat(Long id, String nouvelEtat) {
        Conference conference = conferenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conférence introuvable"));
        conference.setEtat(nouvelEtat);
        return conferenceRepository.save(conference);
    }

    // Méthode privée pour valider les dates de conférence
    private void validateConferenceDates(Conference conference) {
        if (conference.getDateDebut().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La date de début doit être dans le futur ou aujourd'hui.");
        }
        if (conference.getDateFin().isBefore(conference.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin doit être après la date de début.");
        }
    }


}
