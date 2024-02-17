package com.wable.harmonika.domain.card.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wable.harmonika.domain.card.entity.CardNames;
import com.wable.harmonika.domain.card.entity.Cards;
import com.wable.harmonika.domain.group.entity.Groups;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Cards, Long>, CardRepositoryCustom {

    // 특정 유저가 받은 전체 카드
    @Query("select c from Cards c where c.sid= :sid and c.toUser.id = :toUserId")
    List<Cards> findCardsByToUser(@Param("sid") CardNames sid,
                            @Param("toUserId") Long toUserId);
    // 특정 유저가 받은 카드 and 특정 카드 필터
    @Query("SELECT c FROM Cards c WHERE c.sid IN :Sids AND c.toUser.id = :toUserId")
    public Page<Cards> findAllBySidContainsAndToUserEquals(@Param("Sids") List<Long> Sids,@Param("toUserId") Long toUserId, Pageable pageable);

    @Query("SELECT c FROM Cards c WHERE c.sid IN :Sids AND c.group.id IN :groupIds AND c.toUser.id = :toUserId")
    Page<Cards> findAllBySidContainsAndGroupIdInToUserIdEquals(@Param("Sids") List<Long> Sids, @Param("groupIds") List<Long> groupIds, @Param("toUserId") Long toUserId, Pageable pageable);


    @Query("SELECT c FROM Cards c WHERE c.group.id IN :groupIds AND c.toUser.id = :toUserId")
    public Page<Cards> findAllByGroupIdInAndTOUserIDEquals(@Param("groupIds") List<Long> groupIds, @Param("toUserId") Long toUserId, Pageable pageable);
}
