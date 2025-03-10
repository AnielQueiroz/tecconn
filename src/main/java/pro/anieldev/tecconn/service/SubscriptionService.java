package pro.anieldev.tecconn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.anieldev.tecconn.dto.SubscriptionRankingByUser;
import pro.anieldev.tecconn.dto.SubscriptionRankingItem;
import pro.anieldev.tecconn.dto.SubscriptionResponse;
import pro.anieldev.tecconn.exception.EventNotFoundException;
import pro.anieldev.tecconn.exception.SubscriptionConflictException;
import pro.anieldev.tecconn.exception.UserIndicatorNotFoundException;
import pro.anieldev.tecconn.model.Event;
import pro.anieldev.tecconn.model.Subscription;
import pro.anieldev.tecconn.model.User;
import pro.anieldev.tecconn.repository.EventRepo;
import pro.anieldev.tecconn.repository.SubscriptionRepo;
import pro.anieldev.tecconn.repository.UserRepo;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class SubscriptionService {
    @Autowired
    private final EventRepo eventRepo;

    @Autowired
    private final UserRepo userRepo;

    @Autowired
    private final SubscriptionRepo subsRepo;

    public SubscriptionService(EventRepo eventRepo, UserRepo userRepo, SubscriptionRepo subsRepo) {
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
        this.subsRepo = subsRepo;
    }

    public SubscriptionResponse createNewSubscription(String eventName, User user, Integer userId) {
        // Recuperar o evento pelo nome
        Event event = eventRepo.findByPrettyName(eventName);
        if (event == null) {
            throw new EventNotFoundException("Evento " + eventName + " não existe.");
        }

        // Verificar se o usuário já existe no banco
        User userRec = userRepo.findByEmail(user.getEmail());
        if (userRec == null) {
            userRec = userRepo.save(user);
        }

        // Verificar se o userId é null antes de buscar o indicador
        User indicator = null;
        if (userId != null) {
            indicator = userRepo.findById(userId).orElse(null);
            if (indicator == null) {
                throw new UserIndicatorNotFoundException("O usuário que o indicou não existe.");
            }
        }

        // Criar a inscrição
        Subscription subs = new Subscription();
        subs.setEvent(event);
        subs.setSubscriber(userRec);
        subs.setIndication(indicator);

        // Verificar se o usuário já está inscrito no evento
        Subscription alreadySubcribed = subsRepo.findByEventAndSubscriber(event, userRec);
        if (alreadySubcribed != null) {
            throw new SubscriptionConflictException("Você já está inscrito neste evento.");
        }

        // Salvar a inscrição
        Subscription res = subsRepo.save(subs);

        return new SubscriptionResponse(res.getSubscriptionNumber(), "https://anieldev.pro/" + res.getEvent().getPrettyName() + "/" + userRec.getId());
    }

    public List<SubscriptionRankingItem> getCompleteRanking(String prettyName) {
        Event event = eventRepo.findByPrettyName(prettyName);
        if (event == null) {
            throw new EventNotFoundException("Não foi possível gerar o ranking, esse evento " + prettyName +  "não existe.");
        }

        return subsRepo.generateRanking(event.getEventId());
    }

    public SubscriptionRankingByUser getRankingByUser(String prettyName, Integer userId) {
        List<SubscriptionRankingItem> ranking = getCompleteRanking(prettyName);

        SubscriptionRankingItem item = ranking.stream().filter(i-> i.userId().equals(userId)).findFirst().orElse(null);
        if (item == null) {
            throw new UserIndicatorNotFoundException("Não há inscrições para este usuário");
        }

        int position = IntStream.range(0, ranking.size()).filter(pos-> ranking.get(pos).userId().equals(userId)).findFirst().orElseThrow();

        return new SubscriptionRankingByUser(item, position+1);
    }
}

