package admin.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 自定义鉴权失败回调
 * @author: 李明
 * @company: 朴新教育
 * @version:
 * @date: 2019/7/9 19:54
 */
public class TokenAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException {
        //设置返回状态码 403-无权限
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,e.getMessage());
    }
}
