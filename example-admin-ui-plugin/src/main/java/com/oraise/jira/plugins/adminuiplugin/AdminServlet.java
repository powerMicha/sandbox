package com.oraise.jira.plugins.adminuiplugin;

import com.atlassian.sal.api.auth.LoginUriProvider;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.templaterenderer.TemplateRenderer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;


/**
 * Created with IntelliJ IDEA.
 * User: malbers
 * Date: 24.09.12
 * Time: 14:55
 * To change this template use File | Settings | File Templates.
 */
public class AdminServlet extends HttpServlet {

    private final UserManager userManager;

    private final LoginUriProvider loginUriProvider;

    private final TemplateRenderer renderer;


    public AdminServlet(UserManager userManager, LoginUriProvider loginUriProvider, TemplateRenderer renderer){
        this.userManager = userManager;
        this.loginUriProvider = loginUriProvider;
        this.renderer = renderer;
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        String username = userManager.getRemoteUsername(request);
        if(username == null || !userManager.isSystemAdmin(username)){
            redirectToLogin(request, response);
            return;
        }

        response.setContentType("text/html;charset=utf-8");
        renderer.render("admin.vm", response.getWriter());
    }

    private void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(loginUriProvider.getLoginUri(getUri(request)).toASCIIString());
    }

    private URI getUri(HttpServletRequest request) {
        StringBuffer builder = request.getRequestURL();
        if (request.getQueryString() != null)
        {
            builder.append("?");
            builder.append(request.getQueryString());
        }
        return URI.create(builder.toString());
    }



}
