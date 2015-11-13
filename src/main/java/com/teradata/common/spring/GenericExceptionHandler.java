package com.teradata.common.spring;

import com.teradata.common.bean.http.Response;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * Created by alex on 15/8/19.
 */
public class GenericExceptionHandler implements HandlerExceptionResolver {
    private MappingJackson2JsonView mappingJackson2JsonView = new MappingJackson2JsonView();

    public ModelAndView resolveException(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        if ("true".equals(httpServletRequest.getHeader("debug")))
            modelAndView.addObject(new Response(Response.RESULT.ERROR,e.getMessage()));
        else
            modelAndView.addObject(new Response(Response.RESULT.ERROR,"系统异常，请联系管理员"));
        modelAndView.setView(mappingJackson2JsonView);
        return modelAndView;
    }
}
