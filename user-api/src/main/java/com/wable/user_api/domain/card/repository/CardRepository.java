package com.wable.user_api.domain.card.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wable.user_api.domain.card.entity.CardNames;
import com.wable.user_api.domain.card.entity.Cards;
import com.wable.user_api.domain.group.entity.Groups;
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
}
