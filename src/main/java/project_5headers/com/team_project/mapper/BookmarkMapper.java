package project_5headers.com.team_project.mapper;

import project_5headers.com.team_project.entity.Bookmark;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BookmarkMapper {

    int addBookmark(Bookmark bookmark);
    Optional<Bookmark> getBookmarkById(Integer bookmarkId);
    List<Bookmark> getBookmarksByUserId(Integer userId);
    int removeBookmarkById(Integer bookmarkId);
}
