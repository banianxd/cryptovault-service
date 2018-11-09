package com.mvc.cryptovault.app.controller;

import com.mvc.cryptovault.app.bean.vo.TokenDetailVO;
import com.mvc.cryptovault.app.bean.vo.TokenRatioVO;
import com.mvc.cryptovault.common.bean.vo.Result;
import com.mvc.cryptovault.common.swaggermock.SwaggerMock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

/**
 * 令牌相关
 *
 * @author qiyichen
 * @create 2018/11/7 11:02
 */

@Api(tags = "令牌相关")
@RequestMapping("token")
@RestController
public class TokenController extends BaseController {

    @ApiOperation("获取币种列表,需要传入时间戳,必须缓存.添加移除时本地记录并保存顺序.如果返回内容为空则代表无变化,否则需要刷新本地数据库(全量刷新).搜索时本地搜索")
    @GetMapping
    @SwaggerMock("${token.all}")
    public Result<TokenDetailVO> getTokens(@RequestParam(required = false) BigInteger timestamp) throws Exception {
        return mockResult;
    }

    @ApiOperation("获取币种比值,用于计算资产总值.以USDT为基础货币.建议缓存")
    @GetMapping("base")
    @SwaggerMock("${token.base}")
    public Result<TokenRatioVO> getBase() {
        return mockResult;
    }


}