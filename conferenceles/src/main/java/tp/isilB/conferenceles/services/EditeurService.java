package tp.isilB.conferenceles.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tp.isilB.conferenceles.DTO.ConferenceDTO;
import tp.isilB.conferenceles.DTO.EditeurDTO;
import tp.isilB.conferenceles.entities.Editeur;
import tp.isilB.conferenceles.entities.Evaluateur;
import tp.isilB.conferenceles.entities.Soumission;
import tp.isilB.conferenceles.repositries.EditeurRepository;
import tp.isilB.conferenceles.repositries.EvaluateurRepository;
import tp.isilB.conferenceles.repositries.SoumissionRepository;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EditeurService {
    @Autowired
    private EditeurRepository editeurRepository;
    @Autowired
    private SoumissionRepository soumissionRepository;
    @Autowired
    private EvaluateurRepository evaluateurRepository;


    public Editeur createEditeur(Editeur editeur) {
        return editeurRepository.save(editeur);
    }


    public List<Editeur> getAllEditeurs() {
        return editeurRepository.findAll();
    }


    public Optional<Editeur> getEditeurById(Long id) {
        return editeurRepository.findById(id);
    }

    /**
     * Met à jour un éditeur existant.
     *
     * @param id      L'ID de l'éditeur à mettre à jour.
     * @param editeur Les nouvelles informations de l'éditeur.
     * @return L'éditeur mis à jour.
     * @throws RuntimeException si l'éditeur n'existe pas.
     */
    public Editeur updateEditeur(Long id, Editeur editeur) {
        Editeur existingEditeur = editeurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Éditeur introuvable avec l'ID : " + id));

        existingEditeur.setNom(editeur.getNom());
        existingEditeur.setPrenom(editeur.getPrenom());
        existingEditeur.setEmail(editeur.getEmail());

        return editeurRepository.save(existingEditeur);
    }


    public void deleteEditeur(Long id) {
        if (!editeurRepository.existsById(id)) {
            throw new RuntimeException("Éditeur introuvable avec l'ID : " + id);
        }
        editeurRepository.deleteById(id);
    }






    public EditeurDTO getEditeurDTO(Long editeurId) {
        Editeur editeur = editeurRepository.findById(editeurId)
                .orElseThrow(() -> new RuntimeException("Editeur introuvable"));

        List<ConferenceDTO> conferenceDTOs = editeur.getConferences().stream()
                .map(conference -> new ConferenceDTO(
                        conference.getId(),
                        conference.getTitre(),
                        conference.getDateDebut(), // Directement LocalDate
                        conference.getDateFin(),   // Directement LocalDate
                        conference.getThematique(),
                        conference.getEtat()
                ))
                .collect(Collectors.toList());

        return new EditeurDTO(
                editeur.getId(),
                editeur.getNom(),
                editeur.getPrenom(),
                editeur.getEmail(),
                conferenceDTOs
        );
    }

    public void affecterEvaluateurs(Long editeurId, Long soumissionId, List<Long> evaluateurIds) {
        // Vérifier que l'éditeur existe
        Editeur editeur = editeurRepository.findById(editeurId)
                .orElseThrow(() -> new RuntimeException("Éditeur introuvable avec ID : " + editeurId));

        // Vérifier que la soumission existe
        Soumission soumission = soumissionRepository.findById(soumissionId)
                .orElseThrow(() -> new RuntimeException("Soumission introuvable avec ID : " + soumissionId));

        // Vérifier que l'éditeur est associé à la conférence de la soumission
        if (!soumission.getConference().getEditeur().getId().equals(editeurId)) {
            throw new RuntimeException("Cet éditeur ne peut pas affecter cette soumission.");
        }

        // Récupérer les évaluateurs à partir de leurs IDs
        List<Evaluateur> evaluateurs = evaluateurRepository.findAllById(evaluateurIds);

        if (evaluateurs.isEmpty() || evaluateurs.size() != evaluateurIds.size()) {
            throw new RuntimeException("Un ou plusieurs évaluateurs sont introuvables.");
        }

        // Vérifiez que les évaluateurs ne sont pas auteurs de la soumission
        for (Evaluateur evaluateur : evaluateurs) {
            if (soumission.getAuteurs().stream().anyMatch(auteur -> auteur.getId().equals(evaluateur.getId()))) {
                throw new RuntimeException("Un évaluateur ne peut pas évaluer une soumission dont il est auteur.");
            }
        }

        // Affecter les évaluateurs à la soumission
        soumission.getEvaluateurs().addAll(evaluateurs);

        // Sauvegarder la soumission mise à jour
        soumissionRepository.save(soumission);
    }


}