package com.sparta.springauth.food;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;


@Component
@Primary // 같은 타입의 Bean이 여러개 있더라도, 이 것이 적용된 Bean객체를 적용해 준다.

public class Chicken implements Food {
    @Override
    public void eat() {
        System.out.println("치킨을 먹습니다.");
    }
}