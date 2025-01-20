package tp.isilB.conferenceles.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tp.isilB.conferenceles.entities.Editeur;
import tp.isilB.conferenceles.repositries.EditeurRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EditeurService {
    @Autowired
    private EditeurRepository editeurRepository;

    /**
     * Crée un nouvel éditeur.
     *
     * @param editeur Les informations de l'éditeur à créer.
     * @return L'éditeur créé.
     */
    public Editeur createEditeur(Editeur editeur) {
        return editeurRepository.save(editeur);
    }

    /**
     * Récupère tous les éditeurs.
     *
     * @return La liste de tous les éditeurs.
     */
    public List<Editeur> getAllEditeurs() {
        return editeurRepository.findAll();
    }

    /**
     * Récupère un éditeur par son ID.
     *
     * @param id L'ID de l'éditeur à récupérer.
     * @return L'éditeur correspondant ou une exception s'il n'existe pas.
     */
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

    /**
     * Supprime un éditeur par son ID.
     *
     * @param id L'ID de l'éditeur à supprimer.
     * @throws RuntimeException si l'éditeur n'existe pas.
     */
    public void deleteEditeur(Long id) {
        if (!editeurRepository.existsById(id)) {
            throw new RuntimeException("Éditeur introuvable avec l'ID : " + id);
        }
        editeurRepository.deleteById(id);
    }
}
