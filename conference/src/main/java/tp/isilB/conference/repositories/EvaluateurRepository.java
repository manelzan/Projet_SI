package tp.isilB.conference.repositories;


import tp.isilB.conference.entities.Evaluateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface EvaluateurRepository extends JpaRepository<Evaluateur, Long>{
}
