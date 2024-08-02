package com.github.common.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.github.common.authorization.UserDetailLoadService;
import com.github.common.context.UserDetailContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author Gudao
 * @since 2024/7/31
 */
@RequiredArgsConstructor
@Component
public class UserDetailInterceptor implements HandlerInterceptor {

	private final UserDetailLoadService userDetailLoadService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		UserDetailContext.set(userDetailLoadService.load(Long.valueOf((String) StpUtil.getLoginId())));
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		UserDetailContext.remove();
	}

}
