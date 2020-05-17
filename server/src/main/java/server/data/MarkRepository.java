package server.data;

import org.springframework.data.repository.CrudRepository;
import server.domain.Mark;

public interface MarkRepository extends CrudRepository<Mark, Long> {
}
