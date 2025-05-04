package com.yakmall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yak.common.config.JwtProperties;
import com.yak.common.domain.dto.LoginFormDTO;
import com.yak.common.domain.dto.UserRegisterDTO;
import com.yak.common.domain.vo.UserLoginVO;
import com.yak.common.exception.BadRequestException;
import com.yak.common.exception.ForbiddenException;
import com.yak.common.exception.UserBadRequestException;
import com.yak.common.result.Result;
import com.yak.common.utils.JwtTool;
import com.yakmall.domain.po.UserBase;
import com.yakmall.enums.UserStatus;
import com.yakmall.mapper.UserMapper;
import com.yakmall.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserBase> implements IUserService {

    /**
     * 用于密码加密
     */
    private final PasswordEncoder passwordEncoder;

    private final JwtTool jwtTool;

    private final JwtProperties jwtProperties;

    @Autowired
    public UserServiceImpl(JwtProperties jwtProperties, JwtTool jwtTool, PasswordEncoder passwordEncoder) {
        this.jwtProperties = jwtProperties;
        this.jwtTool = jwtTool;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * 用户登陆 【大概耗时310ms】
     * @param loginFormDTO
     * @return
     */
    @Override
    public Result<UserLoginVO> login(LoginFormDTO loginFormDTO) {
        //1.数据校验
        String username = loginFormDTO.getUsername();
        String password = loginFormDTO.getPassword();
        //2.根据用户名或者手机号查询
        //TODO这里需要改成使用除了使用用户名等多种方法登陆
        UserBase user = lambdaQuery().eq(UserBase::getUsername, username).one();
        Assert.notNull(user, "用户名错误");

        //3.校验用户状态
        if(user.getStatus() == UserStatus.FROZEN){
            throw new ForbiddenException("用户被冻结");
        }

        //4.校验密码
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new UserBadRequestException("密码错误");
        }

        //5.生成token
        String token = jwtTool.createJWT(user.getId(), jwtProperties.getTokenTTL());

        //6.封装成一个VO返回
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setUsername(user.getUsername());
        userLoginVO.setToken(token);
        userLoginVO.setUserId(user.getId());

        new Result<>();
        return Result.success(userLoginVO);
    }

    /**
     * 用户注册
     * @param userRegisterDTO
     * @return
     */
    @Override
    public Result<Void> register(UserRegisterDTO userRegisterDTO) {
        //校验唯一性
        if(checkUsernameExists(userRegisterDTO.getUsername())){
            throw new UserBadRequestException("用户名已存在");
        }
        if (checkMobileExists(userRegisterDTO.getMobile())){
            throw new UserBadRequestException("该号码已注册过账户");
        }
        UserBase userBase = BeanUtil.toBean(userRegisterDTO, UserBase.class);

        userBase.setStatus(UserStatus.NORMAL);
        userBase.setCreateTime(LocalDateTime.now());
        userBase.setUpdateTime(LocalDateTime.now());
        userBase.setPassword(passwordEncoder.encode(userBase.getPassword()));


        try {
            save(userBase);
            log.info("用户注册成功：userid:{} username：{}", userBase.getId(), userBase.getUsername());
            return Result.success().msg("用户创建成功");
        } catch (Exception e) {
            log.error("注册失败：{}", e.getMessage());
            throw new BadRequestException("注册失败" + "---" + e.getMessage());
        }
    }
    // 检查用户名是否存在
    private boolean checkUsernameExists(String username) {
        return lambdaQuery().eq(UserBase::getUsername, username).exists();
    }

    // 检查手机号是否存在
    private boolean checkMobileExists(String mobile) {
        return lambdaQuery().eq(UserBase::getMobile, mobile).exists();
    }

}
