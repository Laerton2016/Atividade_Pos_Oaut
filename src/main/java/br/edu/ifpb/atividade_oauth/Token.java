package br.edu.ifpb.atividade_oauth;




import java.io.IOException;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author laerton
 */
@WebServlet(name= "Token", urlPatterns = {"/token"})
public class Token extends HttpServlet{
     
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Guarando o code
        String code = request.getParameter("code");
        Client client = ClientBuilder.newClient();
        
        WebTarget root  =  client
                .target("https://github.com/login/oauth/access_token");
        
        String client_id = (String) request.getSession().getAttribute("client_id");
        String client_sec = (String) request.getSession().getAttribute("client_sec");
        
        //Repassando os paramentros 
        Form form = new Form("client_id", client_id)
                .param("client_secret", client_sec)
                .param("code", code)
                .param("state", "repo");
         
         
        Entity<Form> entity = Entity.form(form);
        //Criando a entidade apartir dos dados recolhidos pelo response
        Response post = root
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .post(entity);
        
        //Criando o objeto Jason com os dados 
        JsonObject json = post.readEntity(JsonObject.class);
        
        //Recolhendo dados do usuario
        WebTarget user = client.target("https://api.github.com/user");
        //Recolhe o token do Json
        String token = "token " + json.getString("access_token");
        
        JsonObject jsonUser = user
                .request()
                .header("Authorization", token)
                .get(JsonObject.class);
        
        //Printa os dados do Usuario requisitado
        response.getWriter().println(
                jsonUser
        );
    }
}
