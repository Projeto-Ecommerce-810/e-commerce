package br.com.letscode.shop.infra.filter;

import br.com.letscode.shop.util.TokenUtil;
import br.com.letscode.shop.domain.service.UsuarioDetalheService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AutorizacaoFilter extends OncePerRequestFilter {

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UsuarioDetalheService usuarioDetalheService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException
    {
         final String requestTokenHeader= request.getHeader("Authorization");

         String jwt = null;
         String usuario = null;

         if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
             jwt = requestTokenHeader.substring(7);


             try{
                 usuario = tokenUtil.getUsernameFromToken(jwt);
             }catch(IllegalArgumentException e){
                 System.out.println("Não foi possível obter token.");
             }catch (ExpiredJwtException e){
                 System.out.println("Token expirado.");
             }
         }else{
             logger.warn("Token não é um Bearer");
         }

         if (usuario != null && SecurityContextHolder.getContext().getAuthentication() == null){
             UserDetails userDetails = usuarioDetalheService.loadUserByUsername(usuario);

             if (tokenUtil.validateToken(jwt, userDetails)){
                 UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                 userToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                 SecurityContextHolder.getContext().setAuthentication(userToken);
             }
         }

         filterChain.doFilter(request, response);
    }
}
