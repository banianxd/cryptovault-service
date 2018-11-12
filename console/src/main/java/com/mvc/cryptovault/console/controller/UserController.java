package com.mvc.cryptovault.console.controller;

import com.mvc.cryptovault.common.bean.vo.Result;
import com.mvc.cryptovault.common.bean.AppUser;
import com.mvc.cryptovault.console.common.BaseController;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import static com.mvc.cryptovault.common.constant.RedisConstant.TEST;
/**
 * @author qiyichen
 * @create 2018/11/12 14:35
 */
@RequestMapping("user")
public class UserController extends BaseController {

    @GetMapping("username")
    public Result<AppUser> getByUsername(@RequestParam String username) {
        String key = TEST;
        String result = (String) redisTemplate.opsForHash().get(key, key);
        AppUser user = null;
        if (null == result) {
            user = appUserService.findBy("cellphone", username);
            if (null == user) {
                //用户不存在则保存空串,防止缓存穿透
                redisTemplate.opsForHash().put(key, key, "");
                redisTemplate.expire(key, 10, TimeUnit.MINUTES);
            } else {
                redisTemplate.opsForHash().put(key, key, user.getId());
            }
        } else if (!"".equals(result)) {
            //如果存在值且不为空,则用户存在,直接获取
            user = appUserService.findById(NumberUtils.createBigInteger(result));
        }
        return new Result<>(user);
    }

    @GetMapping("{id}")
    public Result<AppUser> getUserById(@PathVariable BigInteger id){
        return new Result(appUserService.findById(id));
    }


}