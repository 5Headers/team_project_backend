package project_5headers.com.team_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project_5headers.com.team_project.dto.ApiRespDto;
import project_5headers.com.team_project.entity.Bookmark;
import project_5headers.com.team_project.repository.BookmarkRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> addBookmark(Bookmark bookmark) {
        try {
            int result = bookmarkRepository.addBookmark(bookmark);
            if (result != 1) {
                return new ApiRespDto<>("failed", "북마크 추가 실패", null);
            }
            return new ApiRespDto<>("success", "북마크가 추가되었습니다.", bookmark);
        } catch (Exception e) {
            return new ApiRespDto<>("failed", "서버 오류: " + e.getMessage(), null);
        }
    }

    public ApiRespDto<?> getBookmarkById(Integer bookmarkId) {
        if (bookmarkId == null || bookmarkId <= 0) {
            return new ApiRespDto<>("failed", "유효하지 않은 ID", null);
        }
        Optional<Bookmark> optionalBookmark = bookmarkRepository.getBookmarkById(bookmarkId);
        if (optionalBookmark.isEmpty()) {
            return new ApiRespDto<>("failed", "북마크가 존재하지 않습니다.", null);
        }
        return new ApiRespDto<>("success", "조회 성공", optionalBookmark.get());
    }

    public ApiRespDto<?> getBookmarksByUserId(Integer userId) {
        if (userId == null || userId <= 0) {
            return new ApiRespDto<>("failed", "유효하지 않은 사용자 ID", null);
        }
        List<Bookmark> list = bookmarkRepository.getBookmarksByUserId(userId);
        return list.isEmpty()
                ? new ApiRespDto<>("failed", "북마크가 없습니다.", null)
                : new ApiRespDto<>("success", "조회 성공", list);
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> removeBookmarkById(Integer bookmarkId) {
        if (bookmarkId == null || bookmarkId <= 0) {
            return new ApiRespDto<>("failed", "유효하지 않은 ID", null);
        }
        Optional<Bookmark> optionalBookmark = bookmarkRepository.getBookmarkById(bookmarkId);
        if (optionalBookmark.isEmpty()) {
            return new ApiRespDto<>("failed", "삭제할 북마크가 없습니다.", null);
        }
        try {
            int result = bookmarkRepository.removeBookmarkById(bookmarkId);
            if (result != 1) {
                return new ApiRespDto<>("failed", "북마크 삭제 실패", null);
            }
            return new ApiRespDto<>("success", "삭제 성공", null);
        } catch (Exception e) {
            return new ApiRespDto<>("failed", "서버 오류: " + e.getMessage(), null);
        }
    }
}
