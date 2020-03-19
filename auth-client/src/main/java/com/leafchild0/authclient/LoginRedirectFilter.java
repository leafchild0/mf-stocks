package com.leafchild0.authclient;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

/**
 * Has to redirect request of it's for login
 * @author victor
 * @date 09.03.2020
 */
@Component
public class LoginRedirectFilter extends ZuulFilter {

	@Override
	public String filterType() {

		return "pre";
	}

	@Override
	public int filterOrder() {

		return 5 - 1;
	}

	@Override
	public boolean shouldFilter() {

		RequestContext ctx = RequestContext.getCurrentContext();
		return ctx.getRequest().getRequestURI().endsWith("/login");
	}

	@Override
	public Object run() {

		// Perform actual redirection
		return null;
	}
}
