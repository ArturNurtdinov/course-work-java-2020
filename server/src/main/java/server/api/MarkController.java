package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.data.MarkRepository;
import server.domain.Mark;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/marks", produces = "application/json")
@CrossOrigin("*")
public class MarkController {
    private MarkRepository markRepository;

    @Autowired
    public MarkController(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    @GetMapping
    public Iterable<Mark> getMarks() {
        return markRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Mark> getMarkById(@PathVariable long id) {
        return markRepository.findById(id).map(it -> new ResponseEntity<>(it, HttpStatus.OK)).orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/{id}")
    public void updateHuman(@PathVariable long id, @Valid @RequestBody Mark mark) {
        if (mark.getId() != id) {
            throw new IllegalStateException("Given id doesn't match the id in the path");
        }

        markRepository.save(mark);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mark createHuman(@RequestBody Mark mark) {
        return markRepository.save(mark);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHuman(@Valid @PathVariable long id) {
        try {
            markRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ignored) {
        }
    }
}
