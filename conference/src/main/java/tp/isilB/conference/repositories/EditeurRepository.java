package tp.isilB.conference.repositories;

import tp.isilB.conference.entities.Editeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditeurRepository extends JpaRepository<Editeur, Long>{
}
