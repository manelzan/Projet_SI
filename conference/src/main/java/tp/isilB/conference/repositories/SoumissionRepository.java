package tp.isilB.conference.repositories;

import tp.isilB.conference.entities.Soumission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoumissionRepository extends JpaRepository<Soumission, Long>  {
    List<Soumission> findByAuteurId(Long auteurId);
}
