package com.ys.springboot.web;

import com.google.gson.JsonObject;
import com.ys.springboot.service.portfolio.PortfolioService;
import com.ys.springboot.web.dto.PortfolioDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PortfolioApiController {

    private final PortfolioService portfolioService;

    @PostMapping("/api/portfolio")
    public String save(@RequestBody PortfolioDto requestDto) {
        JsonObject response = new JsonObject();

        portfolioService.save(requestDto);

        response.addProperty("code", 200);
        response.addProperty("data", "");
        response.addProperty("message", "포트폴리오 등록 성공");

        return response.toString();
    }

    @PutMapping("/api/portfolio/{id}")
    public String update(@RequestBody PortfolioDto requestDto, @PathVariable Long id) {
        JsonObject response = new JsonObject();

        Long updatedPortfolioId = portfolioService.update(requestDto, id);

        if (updatedPortfolioId == null) {
            response.addProperty("code", 500);
            response.addProperty("data", "");
            response.addProperty("message", "해당 게시물을 찾을 수 없습니다.");
        }
        else {
            response.addProperty("code", 200);
            response.addProperty("data", "");
            response.addProperty("message", "포트폴리오 업데이트 성공");
        }

        return response.toString();
    }

    @GetMapping("/api/portfolio/{id}")
    public String findById(@PathVariable Long id) {
        JsonObject response = new JsonObject();
        JsonObject data = new JsonObject();

        PortfolioDto portfolioDto = portfolioService.findById(id);

        if (portfolioDto == null) {
            response.addProperty("code", 500);
            response.addProperty("data", "");
            response.addProperty("message", "해당 게시물을 찾을 수 없습니다.");
        }
        else {
            data.addProperty("aboutMe", portfolioDto.getAboutMe());
            data.addProperty("profile", portfolioDto.getProfile());
            data.addProperty("contact", portfolioDto.getContact());
            data.addProperty("skill", portfolioDto.getSkill());
            data.addProperty("project", portfolioDto.getProject());

            response.addProperty("code", 200);
            response.add("data", data);
            response.addProperty("message", "");
        }

        return response.toString();
    }

    @DeleteMapping("/api/portfolio/{id}")
    public String delete(@PathVariable Long id) {
        JsonObject response = new JsonObject();

        Long deletedPortfolioId = portfolioService.delete(id);

        if (deletedPortfolioId == null) {
            response.addProperty("code", 500);
            response.addProperty("data", "");
            response.addProperty("message", "해당 게시물을 찾을 수 없습니다.");
        }
        else {
            response.addProperty("code", 200);
            response.addProperty("data", "");
            response.addProperty("message", "포트폴리오 삭제 성공");
        }

        return response.toString();
    }


}
