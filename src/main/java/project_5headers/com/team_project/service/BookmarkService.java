package project_5headers.com.team_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project_5headers.com.team_project.dto.ApiRespDto;
import project_5headers.com.team_project.entity.Bookmark;
import project_5headers.com.team_project.repository.BookmarkRepository;
import project_5headers.com.team_project.security.model.PrincipalUser;

import java.util.Optional;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> addBookmark(Bookmark bookmark, PrincipalUser principalUser) {
        if (!bookmark.getUserId().equals(principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "권한이 없습니다.", null);
        }
        int result = bookmarkRepository.addBookmark(bookmark);
        if (result != 1) {
            return new ApiRespDto<>("failed", "북마크 추가 실패", null);
        }
        return new ApiRespDto<>("success", "북마크가 추가되었습니다.", bookmark);
    }

    public ApiRespDto<?> getBookmarkById(Integer bookmarkId) {
        Optional<Bookmark> optionalBookmark = bookmarkRepository.getBookmarkById(bookmarkId);
        return optionalBookmark.isPresent()
                ? new ApiRespDto<>("success", "조회 성공", optionalBookmark.get())
                : new ApiRespDto<>("failed", "북마크가 존재하지 않습니다.", null);
    }

    public ApiRespDto<?> getBookmarksByUserId(Integer userId, PrincipalUser principalUser) {
        if (!userId.equals(principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "권한이 없습니다.", null);
        }
        return new ApiRespDto<>("success", "조회 성공", bookmarkRepository.getBookmarksByUserId(userId));
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> removeBookmarkById(Integer bookmarkId, PrincipalUser principalUser) {
        Optional<Bookmark> optionalBookmark = bookmarkRepository.getBookmarkById(bookmarkId);
        if (optionalBookmark.isEmpty()) {
            return new ApiRespDto<>("failed", "북마크가 존재하지 않습니다.", null);
        }
        if (!optionalBookmark.get().getUserId().equals(principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "삭제 권한이 없습니다.", null);
        }
        int result = bookmarkRepository.removeBookmarkById(bookmarkId);
        return result == 1
                ? new ApiRespDto<>("success", "북마크가 삭제되었습니다.", null)
                : new ApiRespDto<>("failed", "북마크 삭제 실패", null);
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> toggleBookmark(Integer estimateId, PrincipalUser principalUser) {
        Integer userId = principalUser.getUserId();

        Optional<Bookmark> existing = bookmarkRepository.findByUserAndEstimate(userId, estimateId);
        if (existing.isPresent()) {
            // 이미 북마크가 있으면 삭제
            bookmarkRepository.removeByUserAndEstimate(userId, estimateId);
            return new ApiRespDto<>("removed", "북마크가 해제되었습니다.", null);
        } else {
            // 없으면 추가
            Bookmark bookmark = Bookmark.builder()
                    .estimateId(estimateId)
                    .userId(userId)
                    .build();
            bookmarkRepository.addBookmark(bookmark);
            return new ApiRespDto<>("added", "북마크가 추가되었습니다.", bookmark);
        }
    }
}
