package com.wanted.auth.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginMemberResDto {
    private String accessToken;
}
