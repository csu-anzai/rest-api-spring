package net.openu.demorestapi.events;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.net.URI;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/events" ,produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class EventController {

  private final EventRepository eventRepository;

  private final ModelMapper modelMapper;

  public EventController(EventRepository eventRepository, ModelMapper modelMapper) {
    this.eventRepository = eventRepository;
    this.modelMapper = modelMapper;
  }

  //  @PostMapping("/api/events")
  @PostMapping
  public ResponseEntity createEvents(@RequestBody EventDto eventDto){
    Event event = modelMapper.map(eventDto, Event.class);
    Event newEvent = this.eventRepository.save(event);
    URI createdUri = linkTo(EventController.class).slash(newEvent.getId()).toUri();
//    URI createdUri = linkTo(methodOn(EventController.class).createEvents(null)).slash("{id}").toUri();
    return ResponseEntity.created(createdUri).body(event);
  }

}
