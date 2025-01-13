package tp.isilB.conferenceles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import tp.isilB.conferenceles.entities.*;
import tp.isilB.conferenceles.services.*;
import tp.isilB.conferenceles.repositries.*;

import java.time.LocalDate;
import java.util.*;

@SpringBootApplication
public class ConferencelesApplication implements CommandLineRunner {
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

	@Autowired
	private utilisateurRepository utilisateurRepository;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		// Création des auteurs
		Auteur auteur1 = auteurRepository.save(new Auteur("etudiant1", "nomEtd1", "usthb"));
		Auteur auteur2 = auteurRepository.save(new Auteur("etudiant2", "nomEtd2", "ESI"));
		Auteur auteur3 = auteurRepository.save(new Auteur("etudiant3", "nomEtd3", "Alger"));
		Auteur auteur4 = auteurRepository.save(new Auteur("etudiant4", "nomEtd4", "Alger"));

		// Création des éditeurs
		Editeur editeur1 = editeurRepository.save(new Editeur("Editeur 1", "Prenom Editeur 1", "editeur1@example.com"));
		Editeur editeur2 = editeurRepository.save(new Editeur("Editeur 2", "Prenom Editeur 2", "editeur2@example.com"));




		// Créer une conférence avec un éditeur
		Conference conference1 = conferenceRepository.save(new Conference(null,"Conférence IA",LocalDate.of(2025, 1, 15), LocalDate.of(2025, 1, 16), "IA", "ouverte", editeur1 ,null));
		System.out.println("Conférence créée : " + conference1.getTitre());
		Conference conference2 = conferenceRepository.save(new Conference(null, "Conférence sur le Big Data", LocalDate.of(2025, 2, 5), LocalDate.of(2026, 2, 10), "Big Data", "ouverte", editeur2, null));
		// Création des soumissions
		Soumission soumission2 = soumissionRepository.save(
				new Soumission(null, "Titre 2", "Résumé 2", "document2.pdf", conference2, new ArrayList<>(Arrays.asList(auteur2)))
		);

		Soumission soumission3 = soumissionRepository.save(
				new Soumission(null, "Titre 3", "Résumé 3", "document3.pdf", conference2, new ArrayList<>(Arrays.asList(auteur3)))
		);
		Soumission soumission1 = soumissionRepository.save(
				new Soumission(null, "Titre 1", "Résumé 1", "document1.pdf", conference1, new ArrayList<>(Collections.singletonList(auteur1)))
		);


		// Création des évaluateurs
		Evaluateur evaluateur1 = evaluateurRepository.save(new Evaluateur("Evaluateur 1", "Prenom Evaluateur 1","evaluateur1@example.com"));
		Evaluateur evaluateur2 = evaluateurRepository.save(new Evaluateur("Evaluateur 2","Prenom Evaluateur 2 ","evaluateur2@example.com"));
		Evaluateur evaluateur3 = evaluateurRepository.save(new Evaluateur("Evaluateur 3", "Prenom Evaluateur 3", "evaluateur1@example.com"));

		// Assignation des évaluateurs aux soumissions
		soumission1.setEvaluateurs(new ArrayList<>(Arrays.asList(evaluateur1, evaluateur2)));
		soumissionRepository.save(soumission1);

		soumission2.setEvaluateurs(new ArrayList<>(Arrays.asList(evaluateur2)));
		soumissionRepository.save(soumission2); }







	public static void main(String[] args) {
		SpringApplication.run(ConferencelesApplication.class, args);
	}

}

