package project_5headers.com.team_project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project_5headers.com.team_project.entity.Bookmark;
import project_5headers.com.team_project.mapper.BookmarkMapper;

import java.util.List;
import java.util.Optional;

@Repository
public class BookmarkRepository {

    @Autowired
    private BookmarkMapper bookmarkMapper;

    public int addBookmark(Bookmark bookmark){
        return bookmarkMapper.addBookmark(bookmark);
    }

    public Optional<Bookmark> getBookmarkById(Integer bookmarkId){
        return bookmarkMapper.getBookmarkById(bookmarkId);
    }

    public List<Bookmark> getBookmarksByUserId(Integer userId){
        return bookmarkMapper.getBookmarksByUserId(userId);
    }

    public int removeBookmarkById(Integer bookmarkId){
        return bookmarkMapper.removeBookmarkById(bookmarkId);
    }
}
