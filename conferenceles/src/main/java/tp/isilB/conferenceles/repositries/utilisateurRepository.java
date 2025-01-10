package tp.isilB.conferenceles.repositries;

import org.springframework.data.jpa.repository.JpaRepository;
import tp.isilB.conferenceles.entities.utilisateur;

public interface utilisateurRepository extends JpaRepository<utilisateur, Long> {
}