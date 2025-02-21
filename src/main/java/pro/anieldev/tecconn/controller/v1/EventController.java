package pro.anieldev.tecconn.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.anieldev.tecconn.model.Event;
import pro.anieldev.tecconn.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {
    @Autowired
    private EventService service;

    @PostMapping("/new")
    public Event addNewEvent(@RequestBody Event newEvent) {
        return service.addNewEvent(newEvent);
    }

    @GetMapping("/")
    public List<Event> getAllEvents() {
        return service.getAllEvents();
    }

    @GetMapping("/{prettyName}")
    public ResponseEntity<Event> getEventByPrettyName(@PathVariable String prettyName) {
        Event evt = service.getByPrettyName(prettyName);

        if (evt != null) {
            return ResponseEntity.ok().body(evt);
        }
        return ResponseEntity.notFound().build();
    }
}
