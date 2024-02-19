package com.wable.harmonika.domain.card.api;

import com.wable.harmonika.domain.card.dto.*;
import com.wable.harmonika.domain.card.entity.CardNames;
import com.wable.harmonika.domain.card.entity.Cards;
import com.wable.harmonika.domain.card.service.CardService;
import com.wable.harmonika.domain.profile.entity.Profiles;
import com.wable.harmonika.domain.profile.service.ProfileService;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.domain.user.service.UserService;
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
import java.util.stream.Collectors;

@Tag(name = "유저 API", description = "계정 API")
@Slf4j
@RestController
@RequestMapping("/v1/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    private final ProfileService profileService;
    private final UserService userService;

    @PostMapping()
    public void createCards(Users user, @Valid @RequestBody CardsRequest request) throws Exception {
//        profileService.save(new Profiles().set);
        cardService.create(request, user);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<Cards> getCards(@PathVariable(name = "cardId") Long id) throws Exception {
        Cards cards =  cardService.findById(id);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @RequestMapping(value="/", method=RequestMethod.PUT)
    public void updateCards(Users user, @Valid UpdateCardsRequest request) throws Exception {
//        cardService.update();
    }

    @RequestMapping(value="/received", method=RequestMethod.GET)
    public ResponseEntity<List<CardsResponse>> listReceivedCards(Users user, @Valid ListCardsRequest request) throws Exception {
        List<CardsDto> cards =  cardService.findAllReceivedCards(request);
        List<CardsResponse> collect = cards.stream()
                .map(m -> new CardsResponse(m))
                .collect(Collectors.toList());

        return new ResponseEntity<>(collect, HttpStatus.OK);
    }

    @RequestMapping(value="/sent", method=RequestMethod.GET)
    public ResponseEntity<List<Cards>> listSentCards(Users user, @Valid ListCardsRequest request) throws Exception {
        List<Cards> cards =  cardService.findAllSentCards(request);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @RequestMapping(value="/", method=RequestMethod.GET)
    public ResponseEntity<List<Cards>> listCardsByGroup(Users user, @Valid ListCardsRequest request) throws Exception {
        List<Cards> cards =  cardService.findAllCardsByGroup(request);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }
}
