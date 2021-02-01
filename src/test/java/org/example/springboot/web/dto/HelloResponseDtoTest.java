package org.example.springboot.web.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트() {
        //Given
        String name = "test";
        int amount = 1000;

        //When
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //Then
        assertThat(dto.getName()).isEqualTo(name); //asserThat : assertj라는 테스트 검증 라이브러리의 검증 메소드. 검증하고자 하는 대상을 인자로 받음. 체이닝 지원.
        assertThat(dto.getAmount()).isEqualTo(amount); //isEqualTo : assertj의 동등 비교 메소드.
    }
}
