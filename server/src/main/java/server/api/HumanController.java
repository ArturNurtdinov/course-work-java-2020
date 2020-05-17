package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.data.GroupRepository;
import server.data.HumanRepository;
import server.domain.Human;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/people", produces = "application/json")
@CrossOrigin("*")
public class HumanController {
    private HumanRepository humanRepository;

    @Autowired
    public HumanController(HumanRepository humanRepository, GroupRepository groupRepository) {
        this.humanRepository = humanRepository;
    }

    @GetMapping
    public Iterable<Human> getHumans() {
        return humanRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Human> getHumanById(@PathVariable long id) {
        return humanRepository.findById(id).map(it -> new ResponseEntity<>(it, HttpStatus.OK)).orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PutMapping(path =  "/{id}")
    public void updateHuman(@PathVariable long id, @Valid @RequestBody Human human) {
        if (human.getId() != id) {
            throw new IllegalStateException("Given id doesn't match the id in the path");
        }

        humanRepository.save(human);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Human createHuman(@RequestBody Human human) {
        if (human.getType() == 'S' || human.getType() == 'T') {
            return humanRepository.save(human);
        } else {
            throw new IllegalStateException("Given type is not T or S");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHuman(@Valid @PathVariable long id) {
        try {
            humanRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ignored) {
        }
    }
}
