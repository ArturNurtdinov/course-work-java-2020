package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.data.SubjectRepository;
import server.domain.Subject;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/subjects", produces = "application/json")
@CrossOrigin("*")
public class SubjectController {
    private SubjectRepository subjectRepository;

    @Autowired
    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @GetMapping
    public Iterable<Subject> getMarks() {
        return subjectRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Subject> getMarkById(@PathVariable long id) {
        return subjectRepository.findById(id).map(it -> new ResponseEntity<>(it, HttpStatus.OK)).orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/{id}")
    public void updateHuman(@PathVariable long id, @Valid @RequestBody Subject subject) {
        if (subject.getId() != id) {
            throw new IllegalStateException("Given id doesn't match the id in the path");
        }

        subjectRepository.save(subject);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Subject createHuman(@RequestBody Subject subject) {
        return subjectRepository.save(subject);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHuman(@Valid @PathVariable long id) {
        try {
            subjectRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ignored) {
        }
    }
}
