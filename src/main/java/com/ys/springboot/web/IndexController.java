package com.ys.springboot.web;

import com.ys.springboot.config.auth.dto.SessionUser;
import com.ys.springboot.service.posts.PostsService;

import com.ys.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        // Model은 서버 template engine에서 사용 가능한 객체를 저장할 수 있다
        // 여기서는 postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달한다
        model.addAttribute("posts", postsService.findAllDesc());

        // 세션에 저장된 값이 있을 때만 model에 userName으로 등록한다
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        // mustache에 의해서 index 파일의 경로와 index.mustache 처럼 확장자가 자동으로 등록된다
        // src/main/resources/templates/index.mustache로 전환된다
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

}
