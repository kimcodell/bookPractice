package org.example.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

//JpaRepository<Entity 클래스, PK타입>을 상속하면 기본적인 CRUD 메소드가 자동 생성. @Repository 추가 필요X.
//Entity 클래스와 Entity Repository는 함께 위치해야 함. -> 따라서 같은 패키지로 묶어서 관리
public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
