package org.example.springboot.web;

import org.example.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController //컨트롤러를 JSON을 반환하는 컨트롤러로 만듦
public class HelloController {
    @GetMapping("/hello") //HTTP Method인 GET 요청을 받을 수 있는 API를 만듦
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount) {
//외부에서 API로 넘긴 파라미터를 가져오는 어노테이션. 어노테이션 바로 옆에 선언한 것이 외부에서 들어올 때의 이름, 그 뒤에 선언한 것이 저장되는 파라미터.
        return new HelloResponseDto(name, amount);
    }
}
