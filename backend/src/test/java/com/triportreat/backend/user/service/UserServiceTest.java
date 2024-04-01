package com.triportreat.backend.user.service;

import com.triportreat.backend.dummy.DummyObject;
import com.triportreat.backend.file.error.exception.NotImageFileException;
import com.triportreat.backend.file.service.FileService;
import com.triportreat.backend.place.error.handler.exception.UserNotFoundException;
import com.triportreat.backend.user.domain.UserNicknameRequestDto;
import com.triportreat.backend.user.entity.User;
import com.triportreat.backend.user.error.exception.UserNicknameExistException;
import com.triportreat.backend.user.repository.UserRepository;
import com.triportreat.backend.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import static com.triportreat.backend.common.response.FailMessage.NOT_IMAGE_FILE;
import static com.triportreat.backend.common.response.FailMessage.USER_NICKNAME_EXIST;
import static com.triportreat.backend.common.response.FailMessage.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest extends DummyObject {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FileService fileService;

    @Nested
    @DisplayName("사용자 닉네임 변경")
    class PutUserNickname {

        @Test
        @DisplayName("성공")
        void updateUserNickname() {

            //given
            User user = User.builder().id(1L).nickname("nickname").build();
            UserNicknameRequestDto userNicknameRequestDto = UserNicknameRequestDto.builder().nickname("newNickname").build();

            when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
            when(userRepository.existsByNickname(userNicknameRequestDto.getNickname())).thenReturn(false);

            //when
            userService.updateUserNickname(user.getId(), userNicknameRequestDto);

            //then
            assertThat(user.getNickname()).isEqualTo(userNicknameRequestDto.getNickname());
        }

        @Test
        @DisplayName("실패 - 사용자 정보가 존재하지 않을 때 예외 발생")
        void updateUserNickname_UserNotFoundException() {

            //given
            User user = User.builder().id(1L).nickname("nickname").build();
            UserNicknameRequestDto userNicknameRequestDto = UserNicknameRequestDto.builder().nickname("newNickname").build();

            when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

            //when & then
            assertThatThrownBy(() -> userService.updateUserNickname(user.getId(), userNicknameRequestDto))
                    .isInstanceOf(UserNotFoundException.class)
                    .hasMessage(USER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("실패 - 이미 존재하는 닉네임일 때 예외 발생")
        void updateUserNickname_UserNicknameExistException() {

            //given
            User user = User.builder().id(1L).nickname("nickname").build();
            User updateUser = User.builder().id(2L).nickname("updateNickname").build();

            UserNicknameRequestDto userNicknameRequestDto = UserNicknameRequestDto.builder().nickname(user.getNickname()).build();

            when(userRepository.findById(updateUser.getId())).thenReturn(Optional.of(updateUser));
            when(userRepository.existsByNickname(userNicknameRequestDto.getNickname())).thenReturn(true);

            //when & then
            assertThatThrownBy(() -> userService.updateUserNickname(updateUser.getId(), userNicknameRequestDto))
                    .isInstanceOf(UserNicknameExistException.class)
                    .hasMessage(USER_NICKNAME_EXIST.getMessage());
        }

        @Test
        @DisplayName("실패 - 닉네임이 현재 닉네임과 같을 때 업데이트를 수행하지 않음")
        void updateNickname_NochangeIfSameNickname() {

            //given
            User user = User.builder().id(1L).nickname("nickname").build();
            UserNicknameRequestDto userNicknameRequestDto = UserNicknameRequestDto.builder().nickname("nickname").build();

            when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

            //when
            userService.updateUserNickname(user.getId(), userNicknameRequestDto);

            //then
            verify(userRepository, times(0)).existsByNickname(userNicknameRequestDto.getNickname());
        }
    }

    @Nested
    @DisplayName("사용자 프로필 사진 변경")
    class UpdateUserProfileImage {

        @Test
        @DisplayName("성공")
        void updateUserProfile_Success() {
            // given
            User user = createMockUser(1L, "user1");
            MultipartFile imageFile = new MockMultipartFile("image.jpeg", "profile.jpeg", "image/jpeg", new byte[10]);

            when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
            when(fileService.deleteAndUploadFile(any(), anyString())).thenReturn("UUID_profile.jpeg");

            // when
            String newUrl = userService.updateUserProfile(imageFile, 1L);

            // then
            assertTrue(StringUtils.hasText(newUrl));
        }

        @Test
        @DisplayName("실패 - 이미지 파일이 아닐경우")
        void updateUserProfile_NotImageFileException() {
            // given
            User user = createMockUser(1L, "user1");
            MultipartFile imageFile = new MockMultipartFile("image.jpeg", "profile.pdf", "image/pdf", new byte[10]);

            when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
            doThrow(NotImageFileException.class).when(fileService).deleteAndUploadFile(any(), anyString());

            // when
            // then
            assertThatThrownBy(() -> userService.updateUserProfile(imageFile, 1L))
                    .isInstanceOf(NotImageFileException.class)
                    .hasMessage(NOT_IMAGE_FILE.getMessage());
        }
    }
}
