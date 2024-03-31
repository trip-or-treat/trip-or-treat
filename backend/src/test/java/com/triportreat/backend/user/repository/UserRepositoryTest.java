package com.triportreat.backend.user.repository;

import com.triportreat.backend.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("닉네임 존재 여부 조회")
    void existsByNickname() {

        //given
        String notExistNickname = "notExistNickname";
        String nickname = "NickName";

        User user = User.builder().id(1L).nickname(nickname).build();
        userRepository.save(user);

        //when
        boolean exists = userRepository.existsByNickname(nickname);
        boolean notExists = userRepository.existsByNickname(notExistNickname);

        //then
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();

    }
}
