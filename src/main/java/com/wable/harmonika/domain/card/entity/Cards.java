package com.wable.harmonika.domain.card.entity;

import com.wable.harmonika.domain.group.entity.Groups;
import com.wable.harmonika.domain.user.entity.Users;
import com.wable.harmonika.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "card")
public class Cards extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CardNames sid;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private Users fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private Users toUser;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Groups group;

    private String content;

    private boolean isVisible;



    public Cards(CardNames sid, String content, boolean isVisible) {
        this.sid = sid;
        this.content = content;
        this.isVisible = isVisible;
    }

    // 빌더 클래스 정의
    public static class Builder {
        private CardNames sid;
        private Users fromUser;
        private String fromUserName;
        private Users toUser;
        private String toUserName;
        private Groups group;
        private String content;
        private boolean isVisible;

        public Builder sid(CardNames sid) {
            this.sid = sid;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder isVisible(boolean isVisible) {
            this.isVisible = isVisible;
            return this;
        }

        public Cards build() {
            return new Cards(sid, content, isVisible);
        }
    }
}
