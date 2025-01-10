package tp.isilB.conference.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import tp.isilB.conference.entities.Evaluation;
import java.util.List;
public interface EvaluationRepository  extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findBySoumissionId(Long soumissionId);// trouver les evaluation d'une soumission
}
