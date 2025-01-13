package tp.isilB.conferenceles.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tp.isilB.conferenceles.entities.Editeur;
import tp.isilB.conferenceles.repositries.EditeurRepository;

@RestController
@RequestMapping("/editeurs")
public class EditeurController {
    @Autowired
    private EditeurRepository editeurRepository;

    @PostMapping
    public ResponseEntity<Editeur> createEditeur(@Valid @RequestBody Editeur editeur) {
        Editeur savedEditeur = editeurRepository.save(editeur);
        return ResponseEntity.ok(savedEditeur);
    }
}
