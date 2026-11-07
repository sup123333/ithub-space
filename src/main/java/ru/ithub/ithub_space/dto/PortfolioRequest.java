package ru.ithub.ithub_space.dto;

public record PortfolioRequest(
        String title,
        String description,
        String projectUrl,
        String imageUrl
) {
}
