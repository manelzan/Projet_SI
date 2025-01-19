package tp.isilB.conferenceles.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.isilB.conferenceles.DTO.EditeurDTO;
import tp.isilB.conferenceles.entities.Editeur;
import tp.isilB.conferenceles.repositries.EditeurRepository;
import tp.isilB.conferenceles.services.EditeurService;

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
    @Autowired
    private EditeurService editeurService;

    @GetMapping("/{id}")
    public ResponseEntity<EditeurDTO> getEditeur(@PathVariable Long id) {
        EditeurDTO editeurDTO = editeurService.getEditeurDTO(id);
        return ResponseEntity.ok(editeurDTO);
    }
}

