package com.algalopez.kirjavik.backoffice_app.user.application.get_user;

import com.algalopez.kirjavik.backoffice_app.user.domain.view.UserView;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface GetUserMapper {

  GetUserResponse mapToResponse(UserView user);
}
