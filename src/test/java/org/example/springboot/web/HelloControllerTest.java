package org.example.springboot.web;

import org.example.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) //테스트 시 JUnit에 내장된 실행자 외 다른 실행자(지금은 SpringRunner)를 실행시킴. 즉, 스프링부트 테스트와 JUnit 사이 연결자 역할을 함.
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
) //@WebMvcTest 선언 시 @Controller, @ControllerAdvice 사용 가능. //SecurityConfig를 스캔대 상에서 제거. But, secure옵션은 사용하지 않는 것이 좋음 -> deprecated 됨.
public class HelloControllerTest {

    @Autowired //스프링이 관리하는 빈(Bean)을 주입 받음 //빈 : 반복적으로 코드를 재사용하기 위해 만들어진 클래스. 속성과 메서드로 구성되며, 데이터의 처리를 담당.
    private MockMvc mvc; //스프링 MVC 테스트의 시작점. 이를 통해 GET, POST 등에 대한 API 테스트 가능.

    @WithMockUser(roles="USER")
    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) //MocMVC를 통해 선언한 주소로 GET 요청을 보냄. 체이닝이 지원돼서 아래처럼 여러 검증기능 이어서 선언 가능.
                .andExpect(status().isOk()) //mvc.perform의 결과를 검증. HTTP 헤더의 Status를 검증. ex) 200, 404, 500, etc.
                .andExpect(content().string(hello)); //mvc.perform의 결과를 검증. HTTP 응답 본문의 내용을 검증.
    }

    @WithMockUser(roles="USER")
    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name", name) //API 테스트 시 사용될 요청 파라미터를 설정. String만 되므로 다른 타입은 valueOf로 변경해야 함.
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) //jsonPath : JSON 응답값을 필드별로 검증할 수 있는 메소드. $를 기준으로 필드명을 명시.
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
