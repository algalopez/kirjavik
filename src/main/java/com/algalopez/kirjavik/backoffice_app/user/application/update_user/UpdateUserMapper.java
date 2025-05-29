package com.algalopez.kirjavik.backoffice_app.user.application.update_user;

import com.algalopez.kirjavik.backoffice_app.user.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface UpdateUserMapper {

  User mapToDomain(UpdateUserCommand command);
}
