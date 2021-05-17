package com.ys.springboot.web;

import com.ys.springboot.service.portfolio.PortfolioService;
import com.ys.springboot.web.dto.PortfolioDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PortfolioApiController {

    private final PortfolioService portfolioService;

    @PostMapping("/api/portfolio")
    public Long save(@RequestBody PortfolioDto portfolioDto) {
        return portfolioService.save(portfolioDto);
    }

}
