package project_5headers.com.team_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project_5headers.com.team_project.entity.Bookmark;
import project_5headers.com.team_project.repository.BookmarkRepository;
import project_5headers.com.team_project.dto.ApiRespDto;

import java.util.List;
import java.util.Optional;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Transactional
    public ApiRespDto<?> addBookmark(Bookmark bookmark){
        int result = bookmarkRepository.addBookmark(bookmark);
        if(result != 1) {
            return new ApiRespDto<>("failed","북마크 추가 실패",null);
        }
        return new ApiRespDto<>("success","북마크 추가 성공",bookmark);
    }

    public ApiRespDto<?> getBookmarkById(Integer bookmarkId){
        Optional<Bookmark> bookmark = bookmarkRepository.getBookmarkById(bookmarkId);
        return bookmark.map(b -> new ApiRespDto<>("success","조회 성공",b))
                .orElseGet(() -> new ApiRespDto<>("failed","조회 실패",null));
    }

    public ApiRespDto<?> getBookmarksByUserId(Integer userId){
        List<Bookmark> list = bookmarkRepository.getBookmarksByUserId(userId);
        return new ApiRespDto<>("success","목록 조회 성공",list);
    }

    @Transactional
    public ApiRespDto<?> removeBookmarkById(Integer bookmarkId){
        int result = bookmarkRepository.removeBookmarkById(bookmarkId);
        if(result != 1) return new ApiRespDto<>("failed","삭제 실패",null);
        return new ApiRespDto<>("success","삭제 성공",null);
    }
}
