package com.bank.service.util.specifications;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import com.bank.service.entities.UserProfile;
import com.bank.service.model.users.profile.UserProfileFilterDto;


@UtilityClass
public class UserProfileSpecifications {

    public static Specification<UserProfile> getUserProfileSpecification(UserProfileFilterDto userProfileFilterDto) {
        return Specification.<UserProfile>where(
                        SpecificationUtil.likeIgnoreCase("firstName", userProfileFilterDto.getFirstName()))
                .and(SpecificationUtil.likeIgnoreCase("lastName", userProfileFilterDto.getLastName()))
                .and(SpecificationUtil.isEqual("birthDate", userProfileFilterDto.getBirthDate()))
                .and(SpecificationUtil.isEqual("phoneNumber", userProfileFilterDto.getPhoneNumber()));
    }
}
