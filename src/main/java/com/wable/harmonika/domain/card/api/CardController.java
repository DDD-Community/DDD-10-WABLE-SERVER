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
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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

@Tag(name = "카드 API", description = "카드 API")
@Slf4j
@RestController
@RequestMapping("/v1/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
    @Operation(summary = "카드 만들기", description = "카드 만들기")
    @PostMapping()
    public void createCards(@Parameter(hidden = true)  Users user, @Valid @RequestBody CardsRequest request) throws Exception {
        // 보낸 유저 아이디는 토큰에서 가져오기
        request.setFromUserId(user.getUserId());
        cardService.create(request);
    }
    @Operation(summary = "카드 수정", description = "카드 수정은 내용과 주제 정도만 수정이 가능합니다.")
    @RequestMapping(method=RequestMethod.PUT)
    public void updateCards(@Parameter(hidden = true)  Users user, @Valid @RequestBody UpdateCardsRequest request) throws Exception {
        cardService.update(request);
    }
    @Operation(summary = "내가 받은 카드 조회하기", description = "내가 받은 카드를 조회 시, 그룹별(groupIds)/주제별(sids)로 조회할 수 있습니다. ")
    @RequestMapping(value="/received", method=RequestMethod.GET)
    public ResponseEntity<List<CardsDto>> listReceivedCards(@Parameter(hidden = true)  Users user, @Valid ListCardsRequest request) throws Exception {
        // 받을 유저의 아이디 설정
        request.setUserId(user.getUserId());
        // 받은 카드 조회
        List<CardsDto> cards =  cardService.findAllReceivedCards(request);

        return new ResponseEntity<>(cards, HttpStatus.OK);
    }
    @Operation(summary = "내가 보낸 카드 조회하기", description = "내가 보낸 카드를 조회 시, 그룹별(groupIds)/주제별(sids)로 조회할 수 있습니다. ")
    @RequestMapping(value="/sent", method=RequestMethod.GET)
    public ResponseEntity<List<CardsDto>> listSentCards(@Parameter(hidden = true)  Users user, @Valid ListCardsRequest request) throws Exception {
        request.setUserId(user.getUserId());
        // 보낸 카드 조회
        List<CardsDto> cards =  cardService.findAllSentCards(request);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }
    @Parameters({
            @io.swagger.v3.oas.annotations.Parameter(name = "cardId", description = "카드 아이디", required = true)
    })
    @Operation(summary = "그룹에서 주고 받은 카드 조회하기", description = "그룹에서 주고 받은 카드를 조회할 수 있다.")
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<CardsDto>> listCardsByGroup(@Parameter(hidden = true)  Users user, @Valid ListCardsByGroupRequest request) throws Exception {
        List<CardsDto> cards =  cardService.findAllCardsByGroup(request);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }
}
