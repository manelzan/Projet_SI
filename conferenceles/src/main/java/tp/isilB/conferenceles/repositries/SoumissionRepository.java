package tp.isilB.conferenceles.repositries;
import org.springframework.data.jpa.repository.JpaRepository;
import tp.isilB.conferenceles.entities.Soumission;

import java.util.List;

public interface     SoumissionRepository  extends JpaRepository<Soumission, Long>{
    List<Soumission> findByAuteurId(Long auteurId);

}
