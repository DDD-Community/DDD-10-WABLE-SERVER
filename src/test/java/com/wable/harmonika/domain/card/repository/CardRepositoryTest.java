package com.wable.harmonika.domain.card.repository;

import com.wable.harmonika.domain.card.entity.CardNames;
import com.wable.harmonika.domain.card.entity.Cards;
import com.wable.harmonika.domain.group.entity.Groups;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.filter;

@SpringBootTest
@Transactional
class CardRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    CardRepository cardRepository;

    @Test
    public void testMember() {
        Cards cards = new Cards(CardNames.GREETING, "안녕", true);
        Cards savedCards = cardRepository.save(cards);
        Cards findCards = cardRepository.findById(savedCards.getId()).get();
        assertThat(findCards.getId()).isEqualTo(cards.getId());
    }



//    @Test
//    public void testQuery() {
//        Cards card1 = new Cards(CardNames.GREETING, "안녕하세요", true);
//        Cards card2 = new Cards(CardNames.CELEBRATION, "안녕하세요", true);
//
//        cardRepository.save(card1);
//        cardRepository.save(card2);
//
//        List<Cards> cards = cardRepository.findBySid(CardNames.GREETING);
//        assertThat(cards.get(0)).isEqualTo(card1);
//
//    }
//
//    @Test
//    public void testQueryPositionParam() {
//        Cards card1 = new Cards(CardNames.GREETING, "안녕하세요", true);
//        Cards card2 = new Cards(CardNames.CELEBRATION, "안녕하세요", true);
//
//        cardRepository.save(card1);
//        cardRepository.save(card2);
//
//        List<Cards> cards = cardRepository.findCardsByPositionParam(CardNames.GREETING);
//        assertThat(cards.get(0)).isEqualTo(card1);
//    }
//
//    @Test
//    public void testQueryNameParam() {
//        Groups group1 = new Groups();
//
//        Cards card1 = new Cards(CardNames.GREETING, "안녕하세요", true);
//        card1.setGroup();
//        Cards card2 = new Cards(CardNames.CELEBRATION, "안녕하세요", true);
//
//        cardRepository.save(card1);
//        cardRepository.save(card2);
//
//        List<Cards> cards = cardRepository.findAllByGroupIdIn(CardNames.GREETING);
//        assertThat(cards.get(0)).isEqualTo(card1);
//    }

}