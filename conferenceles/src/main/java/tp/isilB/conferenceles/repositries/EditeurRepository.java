package tp.isilB.conferenceles.repositries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tp.isilB.conferenceles.entities.Editeur;

import java.util.Optional;
@Repository
public interface
EditeurRepository extends JpaRepository<Editeur, Long>{
    Optional<Editeur> findById(Long id);
}
