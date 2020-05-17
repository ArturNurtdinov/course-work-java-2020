package server.data;

import org.springframework.data.repository.CrudRepository;
import server.domain.Human;

public interface HumanRepository extends CrudRepository<Human, Long> {
}
