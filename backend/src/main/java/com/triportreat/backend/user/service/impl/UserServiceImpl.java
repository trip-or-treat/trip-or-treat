package com.triportreat.backend.user.service.impl;


import com.triportreat.backend.place.error.handler.exception.UserNotFoundException;
import com.triportreat.backend.user.domain.UserNicknameRequestDto;
import com.triportreat.backend.user.entity.User;
import com.triportreat.backend.user.error.exception.UserNicknameExistException;
import com.triportreat.backend.user.repository.UserRepository;
import com.triportreat.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void updateUserNickname(Long userId, UserNicknameRequestDto userNicknameRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (user.getNickname().equals(userNicknameRequestDto.getNickname())) {
            return;
        }

        if (userRepository.existsByNickname(userNicknameRequestDto.getNickname())) {
            throw new UserNicknameExistException();
        } else {
            user.updateNickname(userNicknameRequestDto.getNickname());
        }
    }
}
