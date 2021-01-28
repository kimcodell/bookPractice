package org.example.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springboot.domain.BaseTimeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter //lombok의 getter 자동 생성 기능.
@NoArgsConstructor //lombok의 생성자 자동 생성 기능.
@Entity //테이블과 링크될 클래스임을 선언. Entity 클래스에는 절대 Setter 메소드를 만들지 않음. 필드의 값 변경이 필요하면 목적과 의도를 명확히 나타내는 메소드를 추가해야 함.
public class Posts extends BaseTimeEntity {

    @Id //PRIMARY KEY(PK)임을 명시. 참고로 PK는 Long 타입이 가장 적절함.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK 생성 규칙 명시. 현재 옵션이 auto_increment.
    private Long id;

    @Column(length = 500, nullable = false) //선언하지 않아도 기본적으로 해당 클래스의 필드는 모두 칼럼이 되지만 기본값 외 추가 옵션을 넣기 위해 사용.
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder //생성자와 유사한 역할. 그러나 생성자와 달리 어떤 필드에 어떤 값을 채워야 하는지 명확히 지정 가능.해당 클래스의 빌더 패턴 클래스를 생성
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
