package tp.isilB.conferenceles.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.isilB.conferenceles.DTO.EvaluationCreationDTO;
import tp.isilB.conferenceles.DTO.EvaluationDTO;
import tp.isilB.conferenceles.entities.*;
import tp.isilB.conferenceles.repositries.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvaluationService {
    @Autowired
    private ConferenceRepository conferenceRepository;


    private final EvaluationRepository evaluationRepository;
    private final SoumissionRepository soumissionRepository;
    private final utilisateurRepository utilisateurRepository;

    public EvaluationService(EvaluationRepository evaluationRepository,
                             SoumissionRepository soumissionRepository,
                             utilisateurRepository utilisateurRepository) {
        this.evaluationRepository = evaluationRepository;
        this.soumissionRepository = soumissionRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    // Conversion Evaluation vers EvaluationDTO
    private EvaluationDTO toDTO(Evaluation evaluation) {
        return new EvaluationDTO(
                evaluation.getId(),
                evaluation.getEvaluateur().getId(),
                evaluation.getSoumission().getId(),
                evaluation.getNote(),
                evaluation.getCommentaires(),
                evaluation.getEtat().toString()
        );
    }

    // Conversion EvaluationCreationDTO vers Evaluation
    private Evaluation toEntity(EvaluationCreationDTO evaluationCreationDTO, Evaluateur evaluateur, Soumission soumission) {
        return new Evaluation(
                null, // ID auto-généré
                evaluateur,
                soumission,
                evaluationCreationDTO.getNote(),
                evaluationCreationDTO.getCommentaires(),
                EtatEvaluation.valueOf(evaluationCreationDTO.getEtat().toUpperCase())
        );
    }

    public EvaluationDTO ajouterEvaluation(EvaluationCreationDTO evaluationCreationDTO) {
        // Vérifications similaires à celles déjà existantes dans votre méthode actuelle

        Evaluateur evaluateur = utilisateurRepository.findById(evaluationCreationDTO.getEvaluateurId())
                .filter(user -> user instanceof Evaluateur)
                .map(user -> (Evaluateur) user)
                .orElseThrow(() -> new RuntimeException("Évaluateur introuvable"));

        Soumission soumission = soumissionRepository.findById(evaluationCreationDTO.getSoumissionId())
                .orElseThrow(() -> new RuntimeException("Soumission introuvable"));

        Evaluation evaluation = toEntity(evaluationCreationDTO, evaluateur, soumission);
        Evaluation savedEvaluation = evaluationRepository.save(evaluation);
        return toDTO(savedEvaluation);
    }

    public List<EvaluationDTO> getEvaluationsBySoumission(Long soumissionId) {
        return evaluationRepository.findBySoumissionId(soumissionId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<EvaluationDTO> getEvaluationsByEvaluateur(Long evaluateurId) {
        return evaluationRepository.findByEvaluateurId(evaluateurId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }



    public List<EvaluationDTO> getEvaluationsByEditeur(Long editeurId) {
        // Récupérer les conférences de l'éditeur
        List<Conference> conferences = conferenceRepository.findByEditeurId(editeurId);

        if (conferences.isEmpty()) {
            throw new RuntimeException("Aucune conférence trouvée pour l'éditeur avec ID : " + editeurId);
        }

        // Récupérer toutes les évaluations associées aux soumissions des conférences
        return conferences.stream()
                .flatMap(conf -> conf.getSoumissions().stream()) // Soumissions des conférences
                .flatMap(soumission -> soumission.getEvaluations().stream()) // Évaluations des soumissions
                .map(this::toDTO) // Convertir en DTO
                .collect(Collectors.toList());
    }
}
