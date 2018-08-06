package br.edu.ifpb.atividade_oauth;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author laerton
 */
@WebServlet(name= "Start", urlPatterns = {"/start"})
public class WebStart extends HttpServlet{
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // recupera o id do cliente repassado como paramentro
        String client_id = request.getParameter("client_id");
        String client_sec = request.getParameter("client_sec");
        
        request.getSession().setAttribute("client_id",client_id);
        request.getSession().setAttribute("client_sec",client_sec);
        
        //Munda a URL com o id recolhido anteriormente
        String uri = "https://github.com/login/oauth/authorize?scope=repo&client_id=" + client_id; 
        //Redireciona ao endereço repassado
        response.sendRedirect(uri); 
    }
}
