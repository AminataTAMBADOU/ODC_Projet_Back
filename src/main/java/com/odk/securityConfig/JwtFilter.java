package com.odk.securityConfig;

import com.odk.Entity.Jwt;
import com.odk.Service.Interface.Service.UtilisateurService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtFilter extends OncePerRequestFilter {

    private final UtilisateurService utilisateurService;
    private final JwtService jwtService;

    public JwtFilter(UtilisateurService utilisateurService, JwtService jwtService) {
        this.utilisateurService = utilisateurService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token;
        Jwt tokenDansLaBDD = null;
        String username = null;
        boolean isTokenExpired = true;

        // Bearer eyJhbGciOiJIUzI1NiJ9.eyJub20iOiJBY2hpbGxlIE1CT1VHVUVORyIsImVtYWlsIjoiYWNoaWxsZS5tYm91Z3VlbmdAY2hpbGxvLnRlY2gifQ.zDuRKmkonHdUez-CLWKIk5Jdq9vFSUgxtgdU1H2216U
        final String authorization = request.getHeader("Authorization");
        if(authorization != null && authorization.startsWith("Bearer ")){
            token = authorization.substring(7);
            System.out.println("JWTFilter: Token reçu -> " + token);

            //Verifie que le token existe dans la BDD
            tokenDansLaBDD = this.jwtService.tokenByValue(token);
            if (tokenDansLaBDD==null) {
                System.out.println("JWTFilter: Token introuvable dans la BDD !");
            } else {
            //Verifie que le token existe dans la BDD
            isTokenExpired = jwtService.isTokenExpired(token);
            username = jwtService.extractUsername(token);
            System.out.println("JWTFilter: Username extrait -> " + username);
            System.out.println("JWTFilter: Token expiré ? -> " + isTokenExpired);
            }
        }

    if(!isTokenExpired && tokenDansLaBDD.getUtilisateur().getEmail().equals(username)&& SecurityContextHolder.getContext().getAuthentication() == null) {
            //Changer l'utilisateur et mettre dans le conteste
            UserDetails userDetails = utilisateurService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // 🔹 TEST : afficher l’utilisateur authentifié
                System.out.println("JWTFilter: UTILISATEUR AUTHENTIFIÉ = " + username);
             } else {
                System.out.println("JWTFilter: PAS D'AUTHENTIFICATION, token expiré ou invalide");
                System.out.println("Controller: USER CONNECTÉ = " + username);

            // Transformer les rôles en authorities avec le préfixe ROLE_
                    //List<SimpleGrantedAuthority> authorities = userDetails.getAuthorities().stream()
                            //.map(a -> new SimpleGrantedAuthority("ROLE_" + a.getAuthority()))
                            //.collect(Collectors.toList());

                    //UsernamePasswordAuthenticationToken authenticationToken =
                            //new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

                    //SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
