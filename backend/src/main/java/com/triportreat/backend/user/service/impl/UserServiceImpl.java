package com.triportreat.backend.user.service.impl;


import com.triportreat.backend.place.error.handler.exception.UserNotFoundException;
import com.triportreat.backend.user.domain.UserInfoResponseDto;
import com.triportreat.backend.user.domain.UserNicknameRequestDto;
import com.triportreat.backend.user.entity.User;
import com.triportreat.backend.user.error.exception.UserNicknameExistException;
import com.triportreat.backend.file.service.FileService;
import com.triportreat.backend.user.repository.UserRepository;
import com.triportreat.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FileService fileService;

    @Transactional
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

    @Transactional
    @Override
    public String updateUserProfile(MultipartFile file, Long userId){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        String newUrl = fileService.deleteAndUploadFile(file, user.getImageThumbnail());
        user.updateProfileImage(newUrl);
        return newUrl;
    }

    @Override
    public UserInfoResponseDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return new UserInfoResponseDto(user);
    }
}
