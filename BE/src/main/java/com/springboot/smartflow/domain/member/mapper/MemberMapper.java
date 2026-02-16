package com.springboot.smartflow.domain.member.mapper;

import com.springboot.smartflow.entity.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.Optional;

@Mapper // 이 어노테이션이 있어야 스프링이 XML과 연결해줍니다.
public interface MemberMapper {

    // 이메일로 사용자 조회
    Optional<User> findByEmail(String email);

    // 사용자 등록 (회원가입)
    void save(User user);

    // (선택) ID로 사용자 조회
    Optional<User> findById(Long userId);
}