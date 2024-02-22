package com.wable.harmonika.domain.card.api;

import com.wable.harmonika.domain.card.dto.CardsRequest;
import com.wable.harmonika.domain.card.dto.CardsResponse;
import com.wable.harmonika.domain.card.dto.ListCardsRequest;
import com.wable.harmonika.domain.card.dto.UpdateCardsRequest;
import com.wable.harmonika.domain.card.entity.CardNames;
import com.wable.harmonika.domain.card.entity.Cards;
import com.wable.harmonika.domain.card.service.CardService;
import com.wable.harmonika.domain.user.entity.Users;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServlet;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.http.HttpStatusCode;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Tag(name = "유저 API", description = "계정 API")
@Slf4j
@RestController
@RequestMapping("/v1/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping()
    public void createCards(@Valid @RequestBody CardsRequest request, Users user) throws Exception {
        System.out.println("user.get() = " + user.getUserId());
        cardService.create(request, user);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<Cards> getCards(@PathVariable(name = "cardId") Long id, Users user) throws Exception {
        Cards cards =  cardService.findById(id);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @RequestMapping(value="/{cardId}", method=RequestMethod.PUT)
    public void updateCards(@Valid UpdateCardsRequest request, Users user) throws Exception {
//        Cards cards =  cardService.update();
    }

    @RequestMapping(value="/received", method=RequestMethod.GET)
    public ResponseEntity<List<Cards>> listReceivedCards(@Valid ListCardsRequest request, Users user) throws Exception {
        List<Cards> cards =  cardService.findAllReceivedCards(request);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @RequestMapping(value="/sent", method=RequestMethod.GET)
    public ResponseEntity<List<Cards>> listSentCards(@Valid ListCardsRequest request, Users user) throws Exception {
        List<Cards> cards =  cardService.findAllSentCards(request);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @RequestMapping(value="/", method=RequestMethod.GET)
    public ResponseEntity<List<Cards>> listCardsByGroup(@Valid ListCardsRequest request, Users user) throws Exception {
        List<Cards> cards =  cardService.findAllCardsByGroup(request);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }
}
