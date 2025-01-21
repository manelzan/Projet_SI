
package tp.isilB.conferenceles.services;
import org.springframework.stereotype.Service;
import tp.isilB.conferenceles.DTO.ConferenceDTO;
import tp.isilB.conferenceles.entities.Conference;
import tp.isilB.conferenceles.entities.Editeur;
import tp.isilB.conferenceles.repositries.ConferenceRepository;
import tp.isilB.conferenceles.repositries.EditeurRepository;

import java.time.LocalDate;
import java.util.List;
import tp.isilB.conferenceles.DTO.ConferenceDTO;import tp.isilB.conferenceles.DTO.ConferenceDTO;
@Service
public class ConferenceService {

    private final ConferenceRepository conferenceRepository;
    private final EditeurRepository editeurRepository;

    public ConferenceService(ConferenceRepository conferenceRepository, EditeurRepository editeurRepository) {
        this.conferenceRepository = conferenceRepository;
        this.editeurRepository = editeurRepository;
    }

    // Récupérer toutes les conférences
    public List<ConferenceDTO> getAllConferences() {
        return conferenceRepository.findAll().stream()
                .map(this::toDTO) // Correctement lié à la méthode privée toDTO
                .toList();
    }
    // Créer une conférence par un éditeur
    public Conference createConference(Long editeurId, Conference conference) {
        Editeur editeur = editeurRepository.findById(editeurId)
                .orElseThrow(() -> new RuntimeException("Éditeur introuvable"));
        conference.setEditeur(editeur);
        validateConferenceDates(conference);
        return conferenceRepository.save(conference);
    }

    public ConferenceDTO createConference(Long editeurId, ConferenceDTO conferenceDTO) {
        Editeur editeur = editeurRepository.findById(editeurId)
                .orElseThrow(() -> new RuntimeException("Éditeur introuvable"));

        Conference conference = fromDTO(conferenceDTO);
        conference.setEditeur(editeur);
        validateConferenceDates(conference);

        Conference savedConference = conferenceRepository.save(conference);
        return toDTO(savedConference);
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
    public ConferenceDTO updateConference(Long conferenceId, ConferenceDTO updatedConferenceDTO) {
        Conference existingConference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new RuntimeException("Conférence introuvable"));

        existingConference.setTitre(updatedConferenceDTO.getTitre());
        existingConference.setDateDebut(updatedConferenceDTO.getDateDebut());
        existingConference.setDateFin(updatedConferenceDTO.getDateFin());
        existingConference.setThematique(updatedConferenceDTO.getThematique());
        existingConference.setEtat(updatedConferenceDTO.getEtat());

        validateConferenceDates(existingConference);

        Conference updatedConference = conferenceRepository.save(existingConference);
        return toDTO(updatedConference);
    }

    // Modifier l'état d'une conférence
    public Conference updateConferenceState(Long conferenceId, String nouvelEtat) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new RuntimeException("Conférence introuvable"));
        conference.setEtat(nouvelEtat);
        return conferenceRepository.save(conference);
    }

    // Récupérer les conférences par éditeur

    public List<ConferenceDTO> getConferencesByEditeur(Long editeurId) {
        return conferenceRepository.findAll().stream()
                .filter(conference -> conference.getEditeur().getId().equals(editeurId))
                .map(this::toDTO)
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
    private ConferenceDTO toDTO(Conference conference) {
        if (conference == null) {
            throw new IllegalArgumentException("La conférence ne peut pas être null");
        }
        return new ConferenceDTO(
                conference.getId(),
                conference.getTitre(),
                conference.getDateDebut(),
                conference.getDateFin(),
                conference.getThematique(),
                conference.getEtat()
        );
    }

    private Conference fromDTO(ConferenceDTO conferenceDTO) {
        if (conferenceDTO == null) {
            throw new IllegalArgumentException("Le DTO ne peut pas être null");
        }
        Conference conference = new Conference();
        conference.setId(conferenceDTO.getId());
        conference.setTitre(conferenceDTO.getTitre());
        conference.setDateDebut(conferenceDTO.getDateDebut());
        conference.setDateFin(conferenceDTO.getDateFin());
        conference.setThematique(conferenceDTO.getThematique());
        conference.setEtat(conferenceDTO.getEtat());
        return conference;
    }
}
