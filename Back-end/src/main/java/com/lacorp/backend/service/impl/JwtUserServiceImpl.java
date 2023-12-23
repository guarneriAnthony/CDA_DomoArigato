package com.lacorp.backend.service.impl;

import com.lacorp.backend.execption.AccountExistsException;
import com.lacorp.backend.model.User;
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

    @Autowired
    AuthenticationConfiguration authenticationConfiguration;
    @Value("${jwt.signing.key}")
    private String signingKey;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User save(String username, String password, String email) throws AccountExistsException {
        User existingUsername = userRepository.findByUsername(username);
        User existingEmail = userRepository.findByEmail(email);

        if (existingUsername != null || existingEmail != null) {
            throw new AccountExistsException();
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setRoles(List.of(roleRepository.getRoleByName("USER")));

        return userRepository.save(user);
    }

    @Override
    public Authentication authenticate(String username, String password) throws Exception {
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationConfiguration
                .getAuthenticationManager()
                .authenticate(authentication);
    }

    @Override
    public User loadUserByUsername(String login) throws UsernameNotFoundException {
        User userByUsername = userRepository.findByUsername(login);
        User userByEmail = userRepository.findByEmail(login);

        if (userByUsername == null && userByEmail == null) {
            throw new UsernameNotFoundException("User not found");
        }

        //Si userByUsername n'est pas null, on renvoie userByUsername sinon on renvoie userByEmail
        return userByUsername != null ? userByUsername : userByEmail;
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
        Date expiryDate = new Date(now.getTime() + 1000 * 60 * 60 * 24);
        return Jwts
                .builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact();
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public String generateJwtForHue(UserDetails user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 1000 * 60);
        return Jwts
                .builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact();
    }


}
