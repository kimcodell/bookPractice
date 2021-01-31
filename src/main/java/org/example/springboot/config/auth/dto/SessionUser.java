package org.example.springboot.config.auth.dto;

import lombok.Getter;
import org.example.springboot.domain.user.User;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable{
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
//여기는 인증된 사용자 정보만을 필요로 함. 따라서 name, email, picture만 필드로 선언.
