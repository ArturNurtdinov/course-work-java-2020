package server.data;

import org.springframework.data.repository.CrudRepository;
import server.domain.Group;

public interface GroupRepository extends CrudRepository<Group, Long> {
}
