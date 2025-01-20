package tp.isilB.conferenceles.services;
import org.springframework.stereotype.Service;
import tp.isilB.conferenceles.entities.*;
import tp.isilB.conferenceles.repositries.utilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import tp.isilB.conferenceles.entities.utilisateur;

import java.util.Set;
@Service
public class UtilisateurService {
    @Autowired
    private utilisateurRepository utilisateurRepository;

    public utilisateur ajouterRoles(Long utilisateurId, Set<String> nouveauxRoles) {
        utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec ID : " + utilisateurId));

        utilisateur.getRoles().addAll(nouveauxRoles); // Ajoute les nouveaux rôles
        return utilisateurRepository.save(utilisateur);
    }
}
