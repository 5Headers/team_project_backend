package project_5headers.com.team_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> addBookmark(@RequestBody Bookmark bookmark, PrincipalUser principalUser) {
        return ResponseEntity.ok(bookmarkService.addBookmark(bookmark, principalUser));
    }

    @GetMapping("/{bookmarkId}")
    public ResponseEntity<?> getBookmarkById(@PathVariable Integer bookmarkId) {
        return ResponseEntity.ok(bookmarkService.getBookmarkById(bookmarkId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getBookmarksByUser(@PathVariable Integer userId, PrincipalUser principalUser) {
        return ResponseEntity.ok(bookmarkService.getBookmarksByUserId(userId, principalUser));
    }

    @PostMapping("/remove/{bookmarkId}")
    public ResponseEntity<?> removeBookmark(@PathVariable Integer bookmarkId, PrincipalUser principalUser) {
        return ResponseEntity.ok(bookmarkService.removeBookmarkById(bookmarkId, principalUser));
    }
}
