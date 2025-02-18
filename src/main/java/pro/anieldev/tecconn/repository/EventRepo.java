package pro.anieldev.tecconn.repository;

import org.springframework.data.repository.CrudRepository;
import pro.anieldev.tecconn.model.Event;

public interface EventRepo extends CrudRepository<Event, Integer> {
    public Event findByPrettyName(String prettyName);
}
