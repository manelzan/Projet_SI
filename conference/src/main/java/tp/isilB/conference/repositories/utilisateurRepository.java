package tp.isilB.conference.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tp.isilB.conference.entities.utilisateur;

public interface utilisateurRepository extends JpaRepository<utilisateur, Long> {
}