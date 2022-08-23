package com.emusic.school.configurations;

import com.emusic.school.models.Client;
import com.emusic.school.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private ClientService clientService;

    @Override
    public void init (AuthenticationManagerBuilder auth) throws Exception{

        auth.userDetailsService(inputName ->{
            Client client = clientService.getClientByEmail(inputName);

            if (client != null) {

                if (client.getEmail().equals("admin@admin.com")){
                    return new User(client.getEmail(), client.getPassword(),
                            AuthorityUtils.createAuthorityList("ADMIN"));
                } else {
                    if (client.isVerified()){
                        return new User(client.getEmail(), client.getPassword(),
                                AuthorityUtils.createAuthorityList("CLIENT"));
                    }
                    throw new UsernameNotFoundException("Unknown client:" + inputName);
                }
            }
            else {
                throw new UsernameNotFoundException("Unknown client:" + inputName);
            }
        });
    }

    @Bean
    public PasswordEncoder passwordEncoder(){return PasswordEncoderFactories.createDelegatingPasswordEncoder();}

}
