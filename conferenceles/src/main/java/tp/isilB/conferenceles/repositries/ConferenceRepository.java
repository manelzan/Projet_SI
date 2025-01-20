package tp.isilB.conferenceles.repositries;

import org.springframework.data.jpa.repository.JpaRepository;
import tp.isilB.conferenceles.entities.Conference;

import java.util.List;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {
    List<Conference> findByEditeurId(Long editeurId);
}
