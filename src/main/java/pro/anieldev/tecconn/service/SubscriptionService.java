package pro.anieldev.tecconn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pro.anieldev.tecconn.dto.SubscriptionResponse;
import pro.anieldev.tecconn.exception.EventNotFoundException;
import pro.anieldev.tecconn.exception.SubscriptionConflictException;
import pro.anieldev.tecconn.model.Event;
import pro.anieldev.tecconn.model.Subscription;
import pro.anieldev.tecconn.model.User;
import pro.anieldev.tecconn.repository.EventRepo;
import pro.anieldev.tecconn.repository.SubscriptionRepo;
import pro.anieldev.tecconn.repository.UserRepo;

@Service
public class SubscriptionService {
    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SubscriptionRepo subsRepo;

    public SubscriptionResponse createNewSubscription(String eventName, User user) {
        // recuperar o evento pelo nome
        Event event = eventRepo.findByPrettyName(eventName);
        if (event == null) {
            throw new EventNotFoundException("Evento " + eventName + " não existe.");
        }
        User userRec = userRepo.findByEmail(user.getEmail());
        if (userRec == null) {
            userRec = userRepo.save(user);
        }

        Subscription subs = new Subscription();
        subs.setEvent(event);
        subs.setSubscriber(userRec);

        Subscription alreadySubcribed = subsRepo.findByEventAndSubscriber(event, userRec);

        if (alreadySubcribed != null) {
            throw new SubscriptionConflictException("Você já está inscrito neste evento.");
        }

        Subscription res = subsRepo.save(subs);

        return new SubscriptionResponse(res.getSubscriptionNumber(), "https://anieldev.pro/" + res.getEvent().getPrettyName() + "/" + userRec.getId());
    }
}
