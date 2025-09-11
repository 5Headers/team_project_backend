package project_5headers.com.team_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project_5headers.com.team_project.dto.ApiRespDto;
import project_5headers.com.team_project.entity.User;
import project_5headers.com.team_project.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // 새로운 사용자 추가
    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> addUser(User user) {
        // 비밀번호 암호화
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        Optional<User> savedUser = userRepository.addUser(user);
        if (savedUser.isEmpty()) {
            return new ApiRespDto<>("failed", "이미 존재하는 사용자입니다.", null);
        }
        return new ApiRespDto<>("success", "사용자 등록 완료", savedUser.get());
    }

    // userId로 사용자 조회
    public ApiRespDto<?> getUserById(Integer userId) {
        Optional<User> user = userRepository.getUserByUserId(userId);
        return user
                .map(value -> new ApiRespDto<>("success", "조회 성공", value))
                .orElseGet(() -> new ApiRespDto<>("failed", "사용자가 존재하지 않습니다.", null));
    }

    // email로 사용자 조회
    public ApiRespDto<?> getUserByEmail(String email) {
        Optional<User> user = userRepository.getUserByEmail(email);
        return user
                .map(value -> new ApiRespDto<>("success", "조회 성공", value))
                .orElseGet(() -> new ApiRespDto<>("failed", "사용자가 존재하지 않습니다.", null));
    }

    // username으로 사용자 조회
    public ApiRespDto<?> getUserByUsername(String username) {
        Optional<User> user = userRepository.getUserByUsername(username);
        return user
                .map(value -> new ApiRespDto<>("success", "조회 성공", value))
                .orElseGet(() -> new ApiRespDto<>("failed", "사용자가 존재하지 않습니다.", null));
    }

    // 전체 사용자 조회
    public ApiRespDto<?> getUserList() {
        List<User> users = userRepository.getUserList();
        return new ApiRespDto<>("success", "조회 성공", users);
    }

    // 비밀번호 변경
    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> changePassword(Integer userId, String oldPassword, String newPassword) {
        Optional<User> optionalUser = userRepository.getUserByUserId(userId);
        if (optionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "사용자가 존재하지 않습니다.", null);
        }

        User user = optionalUser.get();

        // 기존 비밀번호 확인
        if (!bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            return new ApiRespDto<>("failed", "기존 비밀번호가 일치하지 않습니다.", null);
        }

        // 새 비밀번호가 기존과 같은지 체크
        if (bCryptPasswordEncoder.matches(newPassword, user.getPassword())) {
            return new ApiRespDto<>("failed", "새 비밀번호는 기존과 달라야 합니다.", null);
        }

        // 변경
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        int result = userRepository.changePassword(user);
        if (result != 1) {
            return new ApiRespDto<>("failed", "비밀번호 변경 실패", null);
        }
        return new ApiRespDto<>("success", "비밀번호가 변경되었습니다.", null);
    }


    // 사용자 삭제
    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> removeUser(Integer userId) {
        int result = userRepository.removeUserById(userId);
        if (result != 1) {
            return new ApiRespDto<>("failed", "사용자 삭제 실패", null);
        }
        return new ApiRespDto<>("success", "사용자가 삭제되었습니다.", null);
    }

}
