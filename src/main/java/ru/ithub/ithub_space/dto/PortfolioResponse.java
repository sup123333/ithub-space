package ru.ithub.ithub_space.dto;

import ru.ithub.ithub_space.model.PortfolioEntity;

public record PortfolioResponse(
        Long id,
        String title,
        String description,
        String projectUrl,
        String imageUrl,
        String studentName
) {
    public static PortfolioResponse from(PortfolioEntity portfolio) {
        String studentName = portfolio.getStudent() != null
                ? portfolio.getStudent().getFirstName() + " " + portfolio.getStudent().getLastName()
                : null;
        return new PortfolioResponse(
                portfolio.getId(),
                portfolio.getTitle(),
                portfolio.getDescription(),
                portfolio.getProjectUrl(),
                portfolio.getImageUrl(),
                studentName
        );
    }
}
