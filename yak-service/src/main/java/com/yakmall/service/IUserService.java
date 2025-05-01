package com.yakmall.service;

import com.yak.common.domain.dto.LoginFormDTO;
import com.yak.common.domain.dto.UserRegisterDTO;
import com.yak.common.domain.vo.UserLoginVO;
import com.yak.common.result.Result;

public interface IUserService {
    /**
     * 登陆
     * @param loginFormDTO
     * @return
     */
    Result<UserLoginVO> login(LoginFormDTO loginFormDTO);

    Result<Void> register(UserRegisterDTO userRegisterDTO);
}
