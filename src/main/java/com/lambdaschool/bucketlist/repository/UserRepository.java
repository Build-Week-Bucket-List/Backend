package com.lambdaschool.bucketlist.repository;

        import com.lambdaschool.bucketlist.models.Item;
        import com.lambdaschool.bucketlist.models.User;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.CrudRepository;
        import org.springframework.data.repository.query.Param;

        import java.util.List;

public interface UserRepository extends CrudRepository<User, Long>
{
    User findByUsername(String username);

    @Query(value = "SELECT u.username, i.* FROM users u JOIN items i ON u.userid = i.userid WHERE u.username = :user", nativeQuery = true)
    List<Item> findByUsernameLike(@Param("user") String username);

    @Query(value = "SELECT username FROM users WHERE LOWER(username) LIKE %:search%", nativeQuery = true)
    List<String> findLikeUsername(@Param("search") String searchname);
//     List<User> findLikeUsername();

}
