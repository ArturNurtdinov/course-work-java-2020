package server.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.data.GroupRepository;
import server.domain.Group;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/groups", produces = "application/json")
@CrossOrigin("*")
public class GroupController {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @GetMapping
    public Iterable<Group> allGroups() {
        return groupRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable long id) {
        return groupRepository.findById(id).map(it ->
                new ResponseEntity<>(it, HttpStatus.OK)).orElse(
                new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/{id}", consumes = "application/json")
    public void updateGroup(@PathVariable long id, @Valid @RequestBody Group group) {
        if (group.getId() != id) {
            throw new IllegalStateException("Given id doesn't match the id in the path");
        }

        groupRepository.save(group);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Group createGroup(@RequestBody Group group) {
        return groupRepository.save(group);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroup(@Valid @PathVariable long id) {
        try {
            groupRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ignored) {
        }
    }
}
