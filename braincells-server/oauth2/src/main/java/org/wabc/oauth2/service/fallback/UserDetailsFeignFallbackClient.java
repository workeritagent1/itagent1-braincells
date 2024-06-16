package org.wabc.authorization.service.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.wabc.authorization.service.UserDetailsFeignClient;
import org.wabc.commons.result.ApiResult;
import org.wabc.commons.result.ResultCode;

/**
 * @author haoxr
 * @createTime 2021/4/24 21:30
 */
@Component
@Slf4j
public class UserDetailsFeignFallbackClient implements UserDetailsFeignClient {

    @Override
    public ApiResult getByUsername(String username) {
        log.error("feign远程调用系统用户服务异常后的降级方法");
        return ApiResult.failed(ResultCode.INTERNAL_SERVER_ERROR);
    }
}
