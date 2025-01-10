package tp.isilB.conferenceles.repositries;

import org.springframework.data.repository.CrudRepository;
import tp.isilB.conferenceles.entities.Auteur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AuteurRepository extends JpaRepository<Auteur, Long> {
    Optional<Auteur> findById(Long id);

}