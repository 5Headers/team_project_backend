package project_5headers.com.team_project.controller;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import project_5headers.com.team_project.dto.ApiRespDto;
//import project_5headers.com.team_project.entity.User;
//import project_5headers.com.team_project.service.UserService;
//
//@RestController
//@RequestMapping("/users")
public class UserController {

//    @Autowired
//    private UserService userService;
//
//    // ----------- 회원가입 부분 -----------------
//
//    @PostMapping
//    public ResponseEntity<ApiRespDto<?>> addUser(@RequestBody User user) {
//        ApiRespDto<?> response = userService.addUser(user);
//        return ResponseEntity.ok(response);
//    }
//
//
//    // ----------- 단일 조회하기 부분 ---------------
//    @GetMapping("/{userId}")
//    public ResponseEntity<ApiRespDto<?>> getUserById(@PathVariable Integer userId) {
//        ApiRespDto<?> response = userService.getUserById(userId);
//        return ResponseEntity.ok(response);
//    }
//
//    // ----------- 이메일 조회하기 부분 --------------
//    @GetMapping("/email/{email}")
//    public ResponseEntity<ApiRespDto<?>> getUserByEmail(@PathVariable String email) {
//        ApiRespDto<?> response = userService.getUserByEmail(email);
//        return ResponseEntity.ok(response);
//    }
//
//    // ---------- 사용자명 조회하기 부분 -------------
//    @GetMapping("/username/{username}")
//    public ResponseEntity<ApiRespDto<?>> getUserByUsername(@PathVariable String username) {
//        ApiRespDto<?> response = userService.getUserByUsername(username);
//        return ResponseEntity.ok(response);
//    }
//
//    // ----------- 전체 사용자 조회하기 부분 ----------------
//    @GetMapping
//    public ResponseEntity<ApiRespDto<?>> getUserList() {
//        ApiRespDto<?> response = userService.getUserList();
//        return ResponseEntity.ok(response);
//    }
//
//    // ----------- 비밀번호 변경하기 부분 -----------------------
//    @PutMapping("/{userId}/password")
//    public ResponseEntity<ApiRespDto<?>> changePassword(
//            @PathVariable Integer userId,
//            @RequestParam String oldPassword,
//            @RequestParam String newPassword
//    ) {
//       ApiRespDto<?> response = userService.changePassword(userId, oldPassword, newPassword);
//       return ResponseEntity.ok(response);
//    }
//
//
//    // ---------- 사용자 삭제하기 부분 ------------------------
//    @DeleteMapping("/{userId}")
//    public ResponseEntity<ApiRespDto<?>> removeUser(@PathVariable Integer userId) {
//        ApiRespDto<?> response = userService.removeUser(userId);
//        return ResponseEntity.ok(response);
//    }
}
