package com.ys.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Dao (Data Access Object)
// DB Layer 접근자이다
// 단순히 interface를 생성 후, JpaRepository<Entity Class, PK Type>을 상속하면 기본적인 CRUD 메소드가 자동 생성된다

public interface PostsRepository extends JpaRepository<Posts, Long>{

    // 아래와 같이 SpringDataJpa에서 제공하지 않는 메소드를 쿼리로 작성하여 사용할 수도 있다
    // 실제 아래 코드는 SpringDataJpa에서 제공하는 기본 메소드로 해결 가능하지만, @Query가 가독성이 훨씬 좋으니 선택하여 사용하자
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();

}
