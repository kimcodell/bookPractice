package org.example.springboot.config.auth.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.springboot.domain.user.Role;
import org.example.springboot.domain.user.User;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    // of(): OAuth2에서 반환하는 사용자 정보는 Map이기 때문에 값 하나 하나를 변환해야 함.
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> reponse = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .name((String) reponse.get("name"))
                .email((String) reponse.get("email"))
                .picture((String) reponse.get("profile_image"))
                .attributes(reponse)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    //User 엔티티를 생성. OAuthAttributes에서 엔티티를 생성하는 시점은 첫 가입 때임.
    //가입할 때 기본 권한을 게스트로 주기 위해 role 빌더에는 Role.GUEST를 사용.
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
