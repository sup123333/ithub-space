package ru.ithub.ithub_space.dto;

import ru.ithub.ithub_space.model.PartnerEntity;

public record PartnerResponse(
        Long id,
        String companyName,
        String contactPerson,
        String email,
        String phone,
        String description,
        String logoUrl
) {
    public static PartnerResponse from(PartnerEntity partner) {
        return new PartnerResponse(
                partner.getId(),
                partner.getCompanyName(),
                partner.getContactPerson(),
                partner.getEmail(),
                partner.getPhone(),
                partner.getDescription(),
                partner.getLogoUrl()
        );
    }
}
