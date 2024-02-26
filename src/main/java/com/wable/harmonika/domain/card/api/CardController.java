package com.wable.harmonika.domain.card.api;

import com.wable.harmonika.domain.card.dto.*;
import com.wable.harmonika.domain.card.entity.CardNames;
import com.wable.harmonika.domain.card.entity.Cards;
import com.wable.harmonika.domain.card.service.CardService;
import com.wable.harmonika.domain.profile.dto.GroupProfileDto;
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
import software.amazon.awssdk.profiles.Profile;

import java.util.HashMap;
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
    @PostMapping()
    public void createCards(Users user, @Valid @RequestBody CardsRequest request) throws Exception {
        // 보낸 유저 아이디는 토큰에서 가져오기
        request.setFromUserId(user.getUserId());
        cardService.create(request);
    }

    @RequestMapping(method=RequestMethod.PUT)
    public void updateCards(Users user, @Valid @RequestBody UpdateCardsRequest request) throws Exception {
        cardService.update(request);
    }

    @RequestMapping(value="/received", method=RequestMethod.GET)
    public ResponseEntity<List<CardsDto>> listReceivedCards(Users user, @Valid ListCardsRequest request) throws Exception {
        // 받을 유저의 아이디 설정
        request.setUserId(user.getUserId());
        // 받은 카드 조회
        List<CardsDto> cards =  cardService.findAllReceivedCards(request);

        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @RequestMapping(value="/sent", method=RequestMethod.GET)
    public ResponseEntity<List<CardsDto>> listSentCards(Users user, @Valid ListCardsRequest request) throws Exception {
        request.setUserId(user.getUserId());
        // 보낸 카드 조회
        List<CardsDto> cards =  cardService.findAllSentCards(request);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<CardsDto>> listCardsByGroup(Users user, @Valid ListCardsByGroupRequest request) throws Exception {
        List<CardsDto> cards =  cardService.findAllCardsByGroup(request);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }
}
