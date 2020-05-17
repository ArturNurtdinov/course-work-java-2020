package server.data;

import org.springframework.data.repository.CrudRepository;
import server.domain.Subject;

public interface SubjectRepository extends CrudRepository<Subject, Long> {
}
