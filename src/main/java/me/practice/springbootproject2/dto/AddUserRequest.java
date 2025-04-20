package me.practice.springbootproject2.dto;

import lombok.Getter;


@Getter
public class AddUserRequest {
    private String username;
    private String password;
}

// DTO 사용 이유
// - 필요한 정보를 선택적으로 전달해 민감한 정보 노출 방지, 효율성 증가
// - Entitiy 기준보다 DTO 기준으로 API를 설계할 경우 유연성 증가해서 유지보수 편리