package ru.ithub.ithub_space.dto;

import ru.ithub.ithub_space.model.PartnerEntity;

public record PartnerRequest(
        String companyName,
        String contactPerson,
        String email,
        String phone,
        String description,
        String logoUrl
) {
    public PartnerEntity toEntity() {
        PartnerEntity partner = new PartnerEntity();
        partner.setCompanyName(companyName);
        partner.setContactPerson(contactPerson);
        partner.setEmail(email);
        partner.setPhone(phone);
        partner.setDescription(description);
        partner.setLogoUrl(logoUrl);
        return partner;
    }
}
