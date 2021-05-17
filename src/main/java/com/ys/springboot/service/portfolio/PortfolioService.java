package com.ys.springboot.service.portfolio;

import com.ys.springboot.domain.portfolio.PortfolioRepository;
import com.ys.springboot.web.dto.PortfolioDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    @Transactional
    public Long save(PortfolioDto portfolioDto) {
        return portfolioRepository.save(portfolioDto.toEntity()).getId();
    }

}
