package com.t_systems.webstore.interceptor;

import com.t_systems.webstore.model.Cart;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Component
public class SessionInterceptor extends HandlerInterceptorAdapter
{
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception
    {
        HttpSession session = request.getSession();

        if (session.getAttribute("cart") == null)
        {
            Cart cart = new Cart();
            cart.setProducts(new ArrayList<>());
            session.setAttribute("cart",cart);
        }
        return true;
    }
}
