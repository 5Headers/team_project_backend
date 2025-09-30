package project_5headers.com.team_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project_5headers.com.team_project.entity.Bookmark;
import project_5headers.com.team_project.service.BookmarkService;
import project_5headers.com.team_project.security.model.PrincipalUser;

@RestController
@RequestMapping("/bookmark")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @PostMapping("/add")
    public ResponseEntity<?> addBookmark(
            @RequestBody Bookmark bookmark,
            @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(bookmarkService.addBookmark(bookmark, principalUser));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getBookmarksByUser(
            @PathVariable Integer userId,
            @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(bookmarkService.getBookmarksByUserId(userId, principalUser));
    }

    @DeleteMapping("/remove/{bookmarkId}")
    public ResponseEntity<?> removeBookmark(
            @PathVariable Integer bookmarkId,
            @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(bookmarkService.removeBookmarkById(bookmarkId, principalUser));
    }

    @PostMapping("/toggle/{estimateId}")
    public ResponseEntity<?> toggleBookmark(
            @PathVariable Integer estimateId,
            @AuthenticationPrincipal PrincipalUser principalUser) {  // 🔹 @AuthenticationPrincipal 추가
        return ResponseEntity.ok(bookmarkService.toggleBookmark(estimateId, principalUser));
    }
}
