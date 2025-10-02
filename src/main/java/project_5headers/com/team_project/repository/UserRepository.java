package project_5headers.com.team_project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import project_5headers.com.team_project.entity.User;
import project_5headers.com.team_project.mapper.UserMapper;

import java.util.Optional;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private UserMapper userMapper;

    // 새로운 사용자 추가
    public Optional<User> addUser(User user) {
        try {
            userMapper.addUser(user);
        } catch (DuplicateKeyException e) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    // userId로 사용자 조회
    public Optional<User> getUserByUserId(Integer userId) {
        return userMapper.getUserByUserId(userId);
    }

    // username으로 사용자 조회
    public Optional<User> getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    // email로 사용자 조회
    public Optional<User> getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    // 전체 사용자 조회
    public List<User> getUserList() {
        return userMapper.getUserList();
    }

    // 사용자 정보 수정
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    // 비밀번호 변경
    public int changePassword(User user) {
        return userMapper.updatePassword(user);
    }

    // 사용자 삭제
    public int removeUserById(Integer userId) {
        return userMapper.removeUserById(userId);
    }
    // 이름 + 이메일로 사용자 조회
    public Optional<User> getUserByNameAndEmail(String name, String email) {
        return userMapper.getUserByNameAndEmail(name, email);
    }

    // 프로필 이미지 변경
    public int changeProfileImg(User user) {

        return userMapper.updateProfileImg(user);
    }


}
