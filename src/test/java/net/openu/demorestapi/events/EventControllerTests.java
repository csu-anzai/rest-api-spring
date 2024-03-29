package net.openu.demorestapi.events;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
//@WebMvcTest //mock
@SpringBootTest @AutoConfigureMockMvc
public class EventControllerTests {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

//  @MockBean         //mock
//  EventRepository eventRepository;      //mock

  @Test
  public void createEvent() throws Exception {
    Event event = Event.builder()
        .id(100)
        .name("Spring")
        .description("Rest API Development With Spring")
        .beginEnrollmentDateTime(LocalDateTime.of(2019,8,18,14,30))
        .closeEnrollmentDateTime(LocalDateTime.of(2019,8,19,14,30))
        .beginEventDateTime(LocalDateTime.of(2019,8,20,14,30))
        .endEventDateTime(LocalDateTime.of(2019,8,21,14,30))
        .basePrice(100)
        .maxPrice(200)
        .limitOfEnrollment(100)
        .location("강남역 d2 스타텁 팩토리")
        .free(true)
        .offline(false)
        .eventStatus(EventStatus.PUBLISHED)
        .build();
//    event.setId(10);
//    Mockito.when(eventRepository.save(event)).thenReturn(event);  //mock
    mockMvc.perform(post("/api/events/")
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .accept(MediaTypes.HAL_JSON_UTF8)
          .content(objectMapper.writeValueAsString(event))
          )

        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("id").exists())
        .andExpect(header().exists(HttpHeaders.LOCATION))
//        .andExpect(header().exists("Location"))
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaTypes.HAL_JSON_UTF8_VALUE))
        .andExpect(jsonPath("id").value(Matchers.not(100)))
        .andExpect(jsonPath("free").value(Matchers.not(true)))
        .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
//        .andExpect(header().string("Content-Type","application/hal+json;charset=UTF-8"));
    ;

  }


}
