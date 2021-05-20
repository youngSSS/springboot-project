package com.ys.springboot.service.portfolio;

import com.ys.springboot.domain.portfolio.Portfolio;
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
    public Long save(PortfolioDto requestDto) {
        return portfolioRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(PortfolioDto requestDto, Long id) {
        try {
            Portfolio portfolio = portfolioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

            portfolio.update(requestDto.getAboutMe(), requestDto.getProfile(), requestDto.getContact(), requestDto.getSkill(), requestDto.getProject());
            return id;
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }

    public PortfolioDto findById(Long id) {
        try {
            Portfolio entity = portfolioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 포트폴리오는 존재하지 않습니다. id = " + id));

            return new PortfolioDto(entity);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Transactional
    public Long delete(Long id) {
        try {
            Portfolio portfolio = portfolioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 포트폴리오는 존재하지 않습니다. id = " + id));

            portfolioRepository.delete(portfolio);
            return id;
        }
        catch (IllegalArgumentException e) {
            return null;
        }

    }
}
