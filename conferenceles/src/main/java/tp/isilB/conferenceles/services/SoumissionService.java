package tp.isilB.conferenceles.services;


import org.springframework.stereotype.Service;
import tp.isilB.conferenceles.entities.Auteur;
import tp.isilB.conferenceles.entities.Evaluateur;
import tp.isilB.conferenceles.entities.Soumission;
import tp.isilB.conferenceles.entities.utilisateur;
import tp.isilB.conferenceles.repositries.utilisateurRepository;
import tp.isilB.conferenceles.repositries.SoumissionRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoumissionService {
    private final SoumissionRepository soumissionRepository;
    private final utilisateurRepository utilisateurRepository;

    public SoumissionService(SoumissionRepository soumissionRepository, utilisateurRepository utilisateurRepository) {
        this.soumissionRepository = soumissionRepository;
        this.utilisateurRepository = utilisateurRepository;

    }

    public void affecterEvaluateurs(Soumission soumission, List<Evaluateur> evaluateurs) {
        for (Evaluateur evaluateur : evaluateurs) {
            if (soumission.getAuteur().getId().equals(evaluateur.getId())) {
                throw new RuntimeException("Un évaluateur ne peut pas évaluer une soumission dont il est l'auteur.");
            }
        }

        // Supprimer les doublons dans la liste des évaluateurs
        List<Evaluateur> uniqueEvaluateurs = evaluateurs.stream().distinct().collect(Collectors.toList());

        soumission.setEvaluateurs(uniqueEvaluateurs);
        soumissionRepository.save(soumission);
    }

    public Soumission ajouterSoumission(Long utilisateurId, Soumission soumission) {
        utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        if (!utilisateur.getRoles().contains("Auteur")) {
            throw new RuntimeException("Cet utilisateur n'a pas le rôle d'Auteur.");
        }
        soumission.setAuteur((Auteur) utilisateur);
        return soumissionRepository.save(soumission);
    }
}
