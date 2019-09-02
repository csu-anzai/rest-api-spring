package net.openu.demorestapi.events;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class EventValidator {
  public void validate(EventDto eventDto, Errors errors){
    if(eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice()!=0){
      errors.rejectValue("basePrice","wrongValue","BasePrice is Wrong.");
      errors.rejectValue("maxPrice","wrongValue","MaxPrice is Wrong.");
    }

    LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
    if(endEventDateTime.isBefore(eventDto.getBeginEventDateTime())||
        endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime())||
        endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())){
      errors.rejectValue("endEventDateTime","wrongValue","endEventDateTime is Wrong.");

      //TODO

    }
  }

}
