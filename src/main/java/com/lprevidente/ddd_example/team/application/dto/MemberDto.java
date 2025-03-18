package com.lprevidente.ddd_example.team.application.dto;

import com.lprevidente.ddd_example.user.api.UserIdDto;

public interface MemberDto extends UserIdDto {

  String getFirstName();

  String getLastName();
}
