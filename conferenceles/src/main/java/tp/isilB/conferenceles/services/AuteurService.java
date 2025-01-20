package tp.isilB.conferenceles.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.isilB.conferenceles.DTO.AuteurDTO;
import tp.isilB.conferenceles.entities.Auteur;
import tp.isilB.conferenceles.repositries.AuteurRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuteurService {
    @Autowired
    private AuteurRepository auteurRepository;

    public List<AuteurDTO> getAllAuteurs() {
        return auteurRepository.findAll().stream()
                .map(auteur -> new AuteurDTO(auteur.getId(), auteur.getNom(), auteur.getPrenom(), auteur.getEmail()))
                .collect(Collectors.toList());
    }

    public AuteurDTO getAuteurById(Long id) {
        Auteur auteur = auteurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auteur introuvable"));
        return new AuteurDTO(auteur.getId(), auteur.getNom(), auteur.getPrenom(), auteur.getEmail());
    }

    public AuteurDTO createAuteur(AuteurDTO auteurDTO) {
        Auteur auteur = new Auteur();
        auteur.setNom(auteurDTO.getNom());
        auteur.setPrenom(auteurDTO.getPrenom());
        auteur.setEmail(auteurDTO.getEmail());

        Auteur savedAuteur = auteurRepository.save(auteur);
        return new AuteurDTO(savedAuteur.getId(), savedAuteur.getNom(), savedAuteur.getPrenom(), savedAuteur.getEmail());
    }

    public void deleteAuteur(Long id) {
        if (!auteurRepository.existsById(id)) {
            throw new RuntimeException("Auteur introuvable");
        }
        auteurRepository.deleteById(id);
    }
}
