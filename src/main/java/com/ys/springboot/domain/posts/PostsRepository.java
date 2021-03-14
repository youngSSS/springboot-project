package com.ys.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

// Dao (Data Access Object)
// DB Layer 접근자이다
// 단순히 interface를 생성 후, JpaRepository<Entity Class, PK Type>을 상속하면 기본적인 CRUD 메소드가 자동 생성된다

public interface PostsRepository extends JpaRepository<Posts, Long>{
}
