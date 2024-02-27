package com.wable.harmonika.domain.group;

import com.wable.harmonika.domain.user.entity.Users;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class GroupUserBirthdayListResponse {

    private List<UserBirthday> userBirthdays;

    public GroupUserBirthdayListResponse(List<UserBirthday> userBirthdays) {
        this.userBirthdays = userBirthdays;
    }

    public static class UserBirthday {

        private Long userId;
        private String name;
        private Integer month;
        private Integer day;

        public UserBirthday(Long userId, String name, Integer month, Integer day) {
            this.userId = userId;
            this.name = name;
            this.month = month;
            this.day = day;
        }

        public static UserBirthday of(Users user) {
            LocalDate birth = user.getBirth();
            return new UserBirthday(user.getId(), user.getName(), birth.getMonthValue(),
                    birth.getDayOfMonth());
        }
    }
}
