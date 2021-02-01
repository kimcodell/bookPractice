package org.example.springboot.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest //별다른 설정없이 사용하면 H2 데이터베이스를 자동으로 실행.
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach //JUnit에서 단위 테스트가 끝날 때마다 수행되는 메소드 지정. 보통 전체 테스트를 수행할 때 테스트간 데이터 침범을 막기 위해 사용.
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //Given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder() //테이블 posts에 insert/update 쿼리를 실행. id값이 있으면 update, 없으면 insert.
                .title(title)
                .content(content)
                .author("kimminhyuk0719@gamil.com")
                .build());

        //When
        List<Posts> postsList = postsRepository.findAll(); //테이블 posts에 있는 모든 데이터를 조회해오는 메소드.

        //Then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2021,1,29,0,0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>>> createDate=" + posts.getCreatedDate() + ", modifiedDate=" + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}