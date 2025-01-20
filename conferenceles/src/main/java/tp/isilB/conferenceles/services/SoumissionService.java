package tp.isilB.conferenceles.services;


import org.springframework.stereotype.Service;
import tp.isilB.conferenceles.entities.*;
import tp.isilB.conferenceles.repositries.utilisateurRepository;
import tp.isilB.conferenceles.repositries.SoumissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import tp.isilB.conferenceles.repositries.AuteurRepository;
import tp.isilB.conferenceles.repositries.EvaluateurRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoumissionService {


    @Autowired
    private SoumissionRepository soumissionRepository;

    @Autowired
    private AuteurRepository auteurRepository;

    @Autowired
    private EvaluateurRepository evaluateurRepository;


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


    public void affecterEvaluateursParEditeur(Soumission soumission, List<Evaluateur> evaluateurs, Editeur editeur) {
        if (!editeur.getRoles().contains("Editeur")) {
            throw new RuntimeException("Cet utilisateur n'a pas le rôle d'éditeur.");
        }

        // Vérifiez si un évaluateur est également un auteur de la soumission
        for (Evaluateur evaluateur : evaluateurs) {
            if (soumission.getAuteurs().stream().anyMatch(auteur -> auteur.getId().equals(evaluateur.getId()))) {
                throw new RuntimeException("Un évaluateur ne peut pas évaluer une soumission dont il est l'auteur.");
            }
        }

        // Supprimer les doublons dans la liste des évaluateurs
        List<Evaluateur> uniqueEvaluateurs = evaluateurs.stream().distinct().collect(Collectors.toList());

        // Affecter les évaluateurs à la soumission
        soumission.setEvaluateurs(uniqueEvaluateurs);

        // Sauvegarder les changements
        soumissionRepository.save(soumission);
    }


    public Soumission addAuteurToSoumission(Long soumissionId, Long auteurId) {
        Soumission soumission = soumissionRepository.findById(soumissionId)
                .orElseThrow(() -> new RuntimeException("Soumission introuvable avec ID : " + soumissionId));
        Auteur auteur = auteurRepository.findById(auteurId)
                .orElseThrow(() -> new RuntimeException("Auteur introuvable avec ID : " + auteurId));

        // Ajouter l'auteur à la soumission
        soumission.getAuteurs().add(auteur);
        return soumissionRepository.save(soumission);
    }

    public Soumission addEvaluateurToSoumission(Long soumissionId, Long evaluateurId) {
        Soumission soumission = soumissionRepository.findById(soumissionId)
                .orElseThrow(() -> new RuntimeException("Soumission introuvable avec ID : " + soumissionId));
        Evaluateur evaluateur = evaluateurRepository.findById(evaluateurId)
                .orElseThrow(() -> new RuntimeException("Évaluateur introuvable avec ID : " + evaluateurId));

        // Vérifier si l'évaluateur est un des auteurs de la soumission
        if (soumission.getAuteurs().contains(evaluateur)) {
            throw new IllegalArgumentException("Un évaluateur ne peut pas évaluer une soumission dont il est auteur.");
        }

        // Ajouter l'évaluateur à la soumission
        soumission.getEvaluateurs().add(evaluateur);
        return soumissionRepository.save(soumission);
    }
     @Autowired
    private ConferenceRepository conferenceRepository;

    public Soumission addSoumissionToConference(Long conferenceId, Soumission soumission) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new RuntimeException("Conférence introuvable"));

        soumission.setConference(conference);
        return soumissionRepository.save(soumission);
    }
}
