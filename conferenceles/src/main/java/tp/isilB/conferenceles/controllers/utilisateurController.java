package tp.isilB.conferenceles.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.isilB.conferenceles.entities.Auteur;
import tp.isilB.conferenceles.entities.utilisateur;
import tp.isilB.conferenceles.repositries.utilisateurRepository;
import tp.isilB.conferenceles.entities.Editeur;
import tp.isilB.conferenceles.entities.Evaluateur;
import tp.isilB.conferenceles.repositries.AuteurRepository;
import tp.isilB.conferenceles.repositries.EditeurRepository;
import tp.isilB.conferenceles.repositries.EvaluateurRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/utilisateurs")
public class utilisateurController {
    @Autowired
    private utilisateurRepository utilisateurRepository;

    @Autowired
    private AuteurRepository auteurRepository;

    @Autowired
    private EditeurRepository editeurRepository;

    @Autowired
    private EvaluateurRepository evaluateurRepository;


    @PostMapping()
    public ResponseEntity<utilisateur> createUtilisateur(@RequestBody utilisateur utilisateur) {
        if (utilisateur.getRoles() == null || utilisateur.getRoles().isEmpty()) {
            utilisateur.setRoles(new HashSet<>(Set.of("ROLE_USER"))); // Rôle par défaut si aucun n'est fourni
        }
        utilisateur savedUser = utilisateurRepository.save(utilisateur);
        return ResponseEntity.ok(savedUser);
    }
    // Ajouter un auteur
    @PostMapping("/auteur")
    public ResponseEntity<Auteur> ajouterAuteur(@RequestBody Auteur auteur) {
        Auteur savedAuteur = auteurRepository.save(auteur);
        return ResponseEntity.ok(savedAuteur);
    }

    // Ajouter un éditeur
    @PostMapping("/editeur")
    public ResponseEntity<Editeur> ajouterEditeur(@RequestBody Editeur editeur) {
        Editeur savedEditeur = editeurRepository.save(editeur);
        return ResponseEntity.ok(savedEditeur);
    }

    // Ajouter un évaluateur
    @PostMapping("/evaluateur")
    public ResponseEntity<Evaluateur> ajouterEvaluateur(@RequestBody Evaluateur evaluateur) {
        Evaluateur savedEvaluateur = evaluateurRepository.save(evaluateur);
        return ResponseEntity.ok(savedEvaluateur);
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<utilisateur> addRoleToUtilisateur(@PathVariable Long id, @RequestBody String role) {
        Optional<utilisateur> utilisateurOpt = utilisateurRepository.findById(id);
        if (utilisateurOpt.isPresent()) {
            utilisateur utilisateur = utilisateurOpt.get();
            utilisateur.getRoles().add(role);
            utilisateurRepository.save(utilisateur);
            return ResponseEntity.ok(utilisateur);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/roles/multiple")
    public ResponseEntity<utilisateur> addRolesToUtilisateur(@PathVariable Long id, @RequestBody List<String> roles) {
        Optional<utilisateur> utilisateurOpt = utilisateurRepository.findById(id);
        if (utilisateurOpt.isPresent()) {
            utilisateur utilisateur = utilisateurOpt.get();
            utilisateur.getRoles().addAll(roles); // Ajoute plusieurs rôles
            utilisateurRepository.save(utilisateur);
            return ResponseEntity.ok(utilisateur);
        }
        return ResponseEntity.notFound().build();
    }

    // Récupérer un utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<utilisateur> getUtilisateurById(@PathVariable Long id) {
        Optional<utilisateur> utilisateurOpt = utilisateurRepository.findById(id);
        return utilisateurOpt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Supprimer un utilisateur par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        if (utilisateurRepository.existsById(id)) {
            utilisateurRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<utilisateur> updateUtilisateur(@PathVariable Long id, @RequestBody utilisateur utilisateurDetails) {
        Optional<utilisateur> utilisateurOpt = utilisateurRepository.findById(id);
        if (utilisateurOpt.isPresent()) {
            utilisateur utilisateur = utilisateurOpt.get();

            // Mettre à jour les champs nécessaires
            utilisateur.setNom(utilisateurDetails.getNom());
            utilisateur.setEmail(utilisateurDetails.getEmail());
            // Vous pouvez mettre à jour les rôles si nécessaire
            utilisateur.getRoles().clear(); // Si vous souhaitez remplacer les rôles
            utilisateur.getRoles().addAll(utilisateurDetails.getRoles());

            // Sauvegarder l'utilisateur mis à jour
            utilisateurRepository.save(utilisateur);

            return ResponseEntity.ok(utilisateur);
        }
        return ResponseEntity.notFound().build();
    }




}
