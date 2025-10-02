package project_5headers.com.team_project.mapper;

import project_5headers.com.team_project.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    // 사용자 추가
    int addUser(User user);

    // userId로 사용자 조회
    Optional<User> getUserByUserId(Integer userId);

    // email로 사용자 조회
    Optional<User> getUserByEmail(String email);

    // username으로 사용자 조회
    Optional<User> getUserByUsername(String username);


    // 이름 + 이메일로 사용자 조회
    Optional<User> getUserByNameAndEmail(String name, String email);

    // 전체 사용자 조회
    List<User> getUserList();

    // 사용자 정보 수정
    int updateUser(User user);

    // 비밀번호 변경
    int updatePassword(User user);

    // 사용자 삭제
    int removeUserById(Integer userId);

    // 이미지 업데이트하기
    int updateProfileImg(User user);


}
