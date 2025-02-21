package pro.anieldev.tecconn.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.anieldev.tecconn.dto.ErrorMessage;
import pro.anieldev.tecconn.dto.SubscriptionResponse;
import pro.anieldev.tecconn.exception.EventNotFoundException;
import pro.anieldev.tecconn.exception.SubscriptionConflictException;
import pro.anieldev.tecconn.exception.UserIndicatorNotFoundException;
import pro.anieldev.tecconn.model.User;
import pro.anieldev.tecconn.service.SubscriptionService;

@RestController
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subsService;

    @PostMapping({"/create/{prettyName}", "/create/{prettyName}/{userId}"})
    public ResponseEntity<?> createSubscription(@PathVariable String prettyName, @RequestBody User subscriber, @PathVariable(required = false) Integer userId) {
        try {
            SubscriptionResponse res = subsService.createNewSubscription(prettyName, subscriber, userId);

            if (res != null) {
                return ResponseEntity.ok(res);
            }

        } catch (EventNotFoundException | UserIndicatorNotFoundException ex) {
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        }
        catch (SubscriptionConflictException ex) {
            return ResponseEntity.status(409).body(new ErrorMessage(ex.getMessage()));
        }

        return ResponseEntity.badRequest().body(new ErrorMessage("Falha ao se inscrever no evento."));
    }
}
