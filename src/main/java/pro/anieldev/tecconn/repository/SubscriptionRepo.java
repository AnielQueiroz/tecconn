package pro.anieldev.tecconn.repository;

import org.springframework.data.repository.CrudRepository;
import pro.anieldev.tecconn.model.Event;
import pro.anieldev.tecconn.model.Subscription;
import pro.anieldev.tecconn.model.User;

public interface SubscriptionRepo extends CrudRepository<Subscription, Integer> {
    public Subscription findByEventAndSubscriber(Event evt, User user);
}
