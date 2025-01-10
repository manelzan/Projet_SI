package tp.isilB.conference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import tp.isilB.conference.entities.*;
import tp.isilB.conference.services.*;
import tp.isilB.conference.repositories.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class ConferenceApplication implements CommandLineRunner {
	@Autowired
	private AuteurRepository auteurRepository;

	@Autowired
	private SoumissionRepository soumissionRepository;

	@Autowired
	private ConferenceRepository conferenceRepository;

	@Autowired
	private EvaluateurRepository evaluateurRepository;

	@Autowired
	private EditeurRepository editeurRepository; // Ajout d'EditeurRepository

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		// Création des auteurs
		Auteur auteur1 = auteurRepository.save(new Auteur("etudiant1", "nomEtd1", "usthb"));
		Auteur auteur2 = auteurRepository.save(new Auteur("etudiant2", "nomEtd2", "ESI"));
		Auteur auteur3 = auteurRepository.save(new Auteur("etudiant3", "nomEtd3", "Alger"));
		Auteur auteur4 = auteurRepository.save(new Auteur("etudiant4", "nomEtd4", "Alger"));

		// Création des éditeurs
		Editeur editeur1 = editeurRepository.save(new Editeur(null, "Editeur1", "editeur1@example.com", null));
		Editeur editeur2 = editeurRepository.save(new Editeur(null, "Editeur2", "editeur2@example.com", null));

		// Création des conférences
		Conference conference1 = conferenceRepository.save(
				new Conference(null, "Conférence sur l'IA", LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 15), "IA", "ouverte", editeur1, null)
		);
		Conference conference2 = conferenceRepository.save(
				new Conference(null, "Conférence sur le Big Data", LocalDate.of(2025, 2, 5), LocalDate.of(2025, 2, 10), "Big Data", "ouverte", editeur2, null)
		);

		// Création des soumissions
		Soumission soumission1 = soumissionRepository.save(
				new Soumission("Titre 1", "Résumé 1", "document1.pdf", Arrays.asList(auteur1), conference1)
		);
		Soumission soumission2 = soumissionRepository.save(
				new Soumission("Titre 2", "Résumé 2", "document2.pdf", Arrays.asList(auteur3), conference1)
		);
		Soumission soumission3 = soumissionRepository.save(
				new Soumission("Titre 3", "Résumé 3", "document3.pdf", Arrays.asList(auteur2), conference2)
		);

		// Création des évaluateurs
		Evaluateur evaluateur1 = evaluateurRepository.save(new Evaluateur(null, "Evaluateur 1", "evaluateur1@example.com", null));
		Evaluateur evaluateur2 = evaluateurRepository.save(new Evaluateur(null, "Evaluateur 2", "evaluateur2@example.com", null));

		// Ajout des évaluations aux soumissions
		Evaluation eval1 = new Evaluation(evaluateur1, "Travail excellent", 5, soumission1);
		Evaluation eval2 = new Evaluation(evaluateur2, "Travail satisfaisant", 3, soumission2);

// Ajout des évaluations aux listes de soumissions
		soumission1.getEvaluations().add(eval1);
		soumission2.getEvaluations().add(eval2);

// Sauvegarde des soumissions après ajout des évaluations
		soumissionRepository.save(soumission1);
		soumissionRepository.save(soumission2);

	}


	public static void main(String[] args) {
		SpringApplication.run(ConferenceApplication.class, args);
	}

}

