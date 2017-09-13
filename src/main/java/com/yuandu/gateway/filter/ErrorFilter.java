package com.yuandu.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.yuandu.common.exceptions.YuanduBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

public class ErrorFilter extends ZuulFilter {

    Logger log = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();
        log.error("this is a ErrorFilter : ", throwable);

        if(throwable.getCause() instanceof YuanduBaseException){
            YuanduBaseException exception = (YuanduBaseException)throwable.getCause();
            ctx.set("error.exception", exception.getCause());
            ctx.set("error.status_code", exception.getCode());
            ctx.set("error.message", exception.getMsg());
            return null;
        }

        ctx.set("error.exception", throwable.getCause());
        ctx.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        ctx.set("error.message", "网关api服务异常");
        return null;
    }
}
