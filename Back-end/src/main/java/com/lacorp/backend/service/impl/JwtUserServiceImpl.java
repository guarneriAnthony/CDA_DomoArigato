package com.lacorp.backend.service.impl;

import com.lacorp.backend.execption.AccountExistsException;
import com.lacorp.backend.model.UserRepositoryModel;
import com.lacorp.backend.repository.RoleRepository;
import com.lacorp.backend.repository.UserRepository;
import com.lacorp.backend.service.JwtUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class JwtUserServiceImpl implements JwtUserService {

    private final String signingKey;
    @Autowired
    AuthenticationConfiguration authenticationConfiguration;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public JwtUserServiceImpl(@Value("${jwt.signing.key}") String signingKey) {
        this.signingKey = signingKey;
    }

    @Override
    public UserDetails save(String username, String password, String email) throws AccountExistsException {
        UserDetails existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            throw new AccountExistsException();
        }
        UserRepositoryModel userRepositoryModel = new UserRepositoryModel(username, passwordEncoder.encode(password), email, List.of(roleRepository.getRoleByName("USER")));
        userRepository.save(userRepositoryModel);
        return userRepositoryModel;
    }

    @Override
    public Authentication authenticate(String username, String password) throws Exception {
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationConfiguration
                .getAuthenticationManager()
                .authenticate(authentication);
    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserRepositoryModel userRepositoryModel = userRepository.findByUsername(login);
        if (userRepositoryModel == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return userRepositoryModel;
    }

    @Override
    public UserDetails getUserFromJwt(String jwt) {
        String username = getUsernameFromToken(jwt);
        return loadUserByUsername(username);
    }

    private String getUsernameFromToken(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    @Override
    public String generateJwtForUser(UserDetails user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 1000 * 60 * 60);
        return Jwts
                .builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact();
    }
    // il faudra changer la clé
    public String generateJwtForHue(UserDetails user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 1000 * 60 * 60);
        return Jwts
                .builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact();
    }

}
