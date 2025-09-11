package project_5headers.com.team_project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import project_5headers.com.team_project.entity.Bookmark;
//import org.apache.ibatis.annotations.Mapper;

import java.util.List;
//import java.util.Optional;

@Mapper
public interface BookmarkMapper {

    int addBookmark(@Param("bookmark") Bookmark bookmark);
//    Optional<Bookmark> getBookmarkById(Integer bookmarkId);
    Bookmark getBookmarkById(@Param("bookmarkId") Integer bookmarkId);
    List<Bookmark> getBookmarksByUserId(@Param("userId") Integer userId);
    int removeBookmarkById(@Param("bookmarkId") Integer bookmarkId);
}
