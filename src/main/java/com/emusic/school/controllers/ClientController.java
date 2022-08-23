package com.emusic.school.controllers;

import com.emusic.school.dtos.ClientDTO;
import com.emusic.school.dtos.ClientReviewDTO;
import com.emusic.school.models.Client;
import com.emusic.school.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.emusic.school.Utils.Utils.deleteToken;
import static com.emusic.school.Utils.Utils.generateToken;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;


    @GetMapping("/clients")
    public List<ClientDTO> getClients(){return clientService.getClientsDTO();}

    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) throws MessagingException, UnsupportedEncodingException {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()){
            return new ResponseEntity<>("Complete all the fields.", HttpStatus.FORBIDDEN);
        }

        if (clientService.getClientByEmail(email) != null){
            return new ResponseEntity<>("Email in use.", HttpStatus.FORBIDDEN);
        }

        if(!email.contains("@")){
            return new ResponseEntity<>("Email invalid.", HttpStatus.FORBIDDEN);
        }


        Client newClient = new Client(firstName,lastName,email,passwordEncoder.encode(password),true,true);


        String randomCode = generateToken(64);
        newClient.setToken(randomCode);
        newClient.setVerified(false);


        clientService.saveClient(newClient);
        sendVerificationEmail(newClient);
        return new ResponseEntity<>("Successfully registered.", HttpStatus.CREATED);
    }

    private void sendVerificationEmail(Client newClient) throws MessagingException, UnsupportedEncodingException {
        String toAddress = newClient.getEmail();
        String fromAddress = "e.music.school.verified@gmail.com";
        String senderName = "E Music School";
        String subject = "Please verify you registration";
        String content = "<h2 style=\"color:black; font-family:Poppins, sans-serif; \">Hi [[name]]</h2>"
                + "<p style=\"color:black; font-family:Poppins, sans-serif; \"> Please click the link below to verify your registration: </p>"
                + "<img src=\"https://i.imgur.com/I3EMJb4.png\" alt=\"logEMusicSchool\"/> <br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\" style=\"color:red;\">VERIFY YOUR ACCOUNT </a></h3>"
                + "<h4 style=\"color:black; font-family:Poppins, sans-serif; \"> THANKS YOU FOR REGISTERING, LET'S ROCK!</h4>"
                ;

        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]",newClient.getFirstName() + " " +  " " + newClient.getLastName());
        String verifyURL = "http://localhost:8080/activateClient.html?token=" + newClient.getToken();

        content = content.replace("[[URL]]",verifyURL);

        helper.setText(content,true);
        javaMailSender.send(message);
    }


    @PatchMapping("/activateClient/{token}")
    private ResponseEntity<Object> activateClient (@PathVariable String token){
        Client client =  clientService.getClientToken(token);

        if(client == null){
            return new ResponseEntity<>("Token invalid",HttpStatus.FORBIDDEN);
        }

        client.setVerified(true);
        client.deleteToken();
        deleteToken(token);
        clientService.saveClient(client);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/client/current")
    public ClientDTO getCurrent(Authentication authentication){
        Client client = clientService.getClientByEmail(authentication.getName());
        return new ClientDTO(client);
    }

    @GetMapping("/clients/courseReview")
    public List<ClientReviewDTO>  getClientsReviews (){
        return clientService.getClientsReviewDTO();
    }

}
