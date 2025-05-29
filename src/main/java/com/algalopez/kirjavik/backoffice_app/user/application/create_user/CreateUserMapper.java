package com.algalopez.kirjavik.backoffice_app.user.application.create_user;

import com.algalopez.kirjavik.backoffice_app.user.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface CreateUserMapper {

  User mapToDomain(CreateUserCommand command);
}
