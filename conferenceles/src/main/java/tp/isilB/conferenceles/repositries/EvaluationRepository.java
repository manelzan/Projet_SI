package tp.isilB.conferenceles.repositries;
import org.springframework.data.jpa.repository.JpaRepository;
import tp.isilB.conferenceles.entities.Evaluation;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findBySoumissionId(Long soumissionId);
    List<Evaluation> findByEvaluateurId(Long evaluateurId);



}