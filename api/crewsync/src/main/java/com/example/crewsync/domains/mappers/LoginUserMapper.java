package com.example.crewsync.domains.mappers;

import org.apache.ibatis.annotations.Mapper;

import com.example.crewsync.auth.LoginUserDetails;

@Mapper
public interface LoginUserMapper {

    public LoginUserDetails identifyUser(String email);

}
