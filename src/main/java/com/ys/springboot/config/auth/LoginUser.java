package com.ys.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// @Target: annotation이 생성될 수 있는 위치를 지정한다
// @interface: 현제 파일을 annotation class로 지정 (LoginUser라는 이름을 가진 annotation이 생성되었다고 보면 된다)

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {

}
