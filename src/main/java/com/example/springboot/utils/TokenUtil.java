package com.example.springboot.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springboot.entity.User;
import com.example.springboot.service.IUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Component
public class TokenUtil {

    private static IUserService staticIUserService;


    @Resource
    private IUserService userService;


    @PostConstruct
    public void setUserService(){
        staticIUserService = userService;
    }


    /**
     * 生成token
     */
    public static String genToken(String userId,String sign){
        String token="";
        token= JWT.create().withAudience(userId)//userId保存到token当中作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))// 两小时后过期
                .sign(Algorithm.HMAC256(sign));
        return token;
    }


    /**
     * 获取当前登录用户的信息
     */
    public static User getCurrentUser(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if(StrUtil.isNotBlank(token)){
                String userId = JWT.decode(token).getAudience().get(0);
                return staticIUserService.getById(Integer.parseInt(userId));
            }

        }catch (Exception e) {
            return null;
        }
        return null;
    }
}
