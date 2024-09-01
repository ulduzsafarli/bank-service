package java.az.bankservice.util.specifications;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.az.bankservice.entities.UserProfile;
import java.az.bankservice.model.users.profile.UserProfileFilterDto;

import static java.az.bankservice.util.specifications.SpecificationUtil.isEqual;
import static java.az.bankservice.util.specifications.SpecificationUtil.likeIgnoreCase;


@UtilityClass
public class UserProfileSpecifications {

    public static Specification<UserProfile> getUserProfileSpecification(UserProfileFilterDto userProfileFilterDto) {
        return Specification.<UserProfile>where(
                        likeIgnoreCase("firstName", userProfileFilterDto.getFirstName()))
                .and(likeIgnoreCase("lastName", userProfileFilterDto.getLastName()))
                .and(isEqual("birthDate", userProfileFilterDto.getBirthDate()))
                .and(isEqual("phoneNumber", userProfileFilterDto.getPhoneNumber()));
    }
}
