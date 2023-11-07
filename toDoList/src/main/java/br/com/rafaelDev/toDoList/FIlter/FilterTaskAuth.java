package br.com.rafaelDev.toDoList.FIlter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.rafaelDev.toDoList.User.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter{

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                var servletPath = request.getServletPath();
                //VERIFICO QUAL FOI O CAMINHO DA ROTA
                if((servletPath.equals("/tasks/registerTask")) || (servletPath.equals("/tasks/listarTasks"))){
                    //PEGAR A AUTENTICAÇÃO( USUÁRIO E SENHA)
                    var authorization = request.getHeader("Authorization");
                    
                    //RETIRA PARTE INDESEJÁVEL DA STRING
                    var user_psw = authorization.substring("Basic".length()).trim();
                    //DECODADA A NOVA STRING
                    byte[] authDecode = Base64.getDecoder().decode(user_psw);
        
                    //CONVERTE ARRAY DE BYTES PARA STRING
                    var authString = new String(authDecode);
        
                    //RETIRANDO PARTE INDESEJÁVEL DA STRING E ATRIBUINDO AS VARIAVEIS FINAIS
                    String[] credencials = authString.split(":");
                    String username = credencials[0];
                    String password = credencials[1];

                    //VALIDAR USUÁRIO
                    var user = this.userRepository.findByUsername(username);
                    if(user == null){
                        response.sendError(401);
                    }else{
                        //VALIDAR SENHA
                        var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                        if(passwordVerify.verified){
                            request.setAttribute("idUsuario", user.getId());
                            filterChain.doFilter(request, response);
                        }else response.sendError(401);
                    }
                }else filterChain.doFilter(request, response);             
    }
}
