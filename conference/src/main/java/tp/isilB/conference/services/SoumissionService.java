package  tp.isilB.conference.services;


import org.springframework.stereotype.Service;
import  tp.isilB.conference.entities.Evaluateur;
import  tp.isilB.conference.entities.Soumission;
import  tp.isilB.conference.repositories.SoumissionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoumissionService {
    private final SoumissionRepository soumissionRepository;

    public SoumissionService(SoumissionRepository soumissionRepository) {
        this.soumissionRepository = soumissionRepository;
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
}
