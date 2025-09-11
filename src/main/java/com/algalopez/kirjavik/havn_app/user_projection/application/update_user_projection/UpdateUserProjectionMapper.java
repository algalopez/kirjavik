package com.algalopez.kirjavik.havn_app.user_projection.application.update_user_projection;

import com.algalopez.kirjavik.havn_app.user_projection.domain.model.UserProjection;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface UpdateUserProjectionMapper {

  UserProjection mapToDomain(UpdateUserProjectionCommand command);
}
