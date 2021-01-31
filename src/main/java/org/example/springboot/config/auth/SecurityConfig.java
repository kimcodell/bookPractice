package org.example.springboot.config.auth;

import lombok.RequiredArgsConstructor;
import org.example.springboot.domain.user.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //스프링 시큐리티 설정을 활성화.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable() //h2-console 화면을 사용하기 위해 해당 옵션들을 disable함.
                .and()
                    .authorizeRequests() //url별 권한 관리를 설정하는 옵션의 시작점. 이게 선언되야만 antMatcher 옵션을 사용할 수 있음.
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    //권한 관리 대상을 지정하는 옵션. url, http 메소드 별로 관리 가능. (위) 지정된 url은 permitAll() 옵션으로 전체 열람 권한을 줌. (아래) 지정된 주소를 가진 API는 user권한을 가진 사람만 열람 가능.
                    .anyRequest().authenticated() //설정된 값들 이외 나머지 url. 여기에 authenticated()을 추가해 나머지 url은 인증된 사용자만 허용.
                .and()
                    .logout()
                        .logoutSuccessUrl("/") //로그아웃 기능에 대한 설정의 진입점. 로그아웃 성공시 "/"주소로 이동.
                .and()
                    .oauth2Login() //oauth2기능에 대한 설정의 진입점.
                        .userInfoEndpoint() //로그인 성공시 이후 사용자 정보를 가져올 때의 설정을 담당.
                            .userService(customOAuth2UserService); //소셜 로그인 성공 시 후속 조치를 진행할 인터페이스의 구현체를 등록. 리소스 서버(즉, 소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고 싶은 기능을 명시할 수 있음.
    }
}
