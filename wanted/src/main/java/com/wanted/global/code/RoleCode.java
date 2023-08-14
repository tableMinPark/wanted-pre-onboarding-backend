package com.wanted.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleCode {
    USER("USER", "일반회원"),
    ADMIN("ADMIN", "관리자");

    public final String code;
    public final String name;
}
