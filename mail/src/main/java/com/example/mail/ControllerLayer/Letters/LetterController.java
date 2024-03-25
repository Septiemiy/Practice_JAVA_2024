package com.example.mail.ControllerLayer.Letters;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mail.DatabaseLayer.Interfaces.UserRepository;
import com.example.mail.DatabaseLayer.Model.User;
import com.example.mail.LogicalLayer.Letter.LetterLogical;
import com.example.mail.LogicalLayer.Letter.LetterClass.Letter;
import com.example.mail.LogicalLayer.User.UserException.UserNotFoundException;

@RestController
public class LetterController {

    private final UserRepository repository;

    LetterController(UserRepository repository)
    {
        this.repository = repository;
    }

    @PostMapping("/send_email/{user_id}")
    ResponseEntity<Object> sendEmail(@RequestBody Letter letter, @PathVariable Long user_id) {

        try
        {
            Optional<User> user = repository.findById(user_id);
            if(user.isPresent())
            {
                Message message = new MimeMessage(LetterLogical.getSession());
                message.setFrom(new InternetAddress(LetterLogical.mainEmail));
                message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(user.get().getEmail()));
                message.setSubject("Subject: " + letter.getSubject());
                message.setText("To: " + user.get().getEmail());
                message.setText("Body: \n" +
                    letter.getBody() + "\n\t" +
                        "User name: " + user.get().getUsername() + "\n\t" +
                        "Creation date and time: " + user.get().getCreatedOn());
                
                Transport.send(message);
                
                return ResponseEntity.ok("\"success\": \"email has been sent to " + user.get().getEmail() + "\"");
            }
            else
            {
                throw new NoSuchElementException( new UserNotFoundException(user_id));
            }
        }
        catch(NoSuchElementException exception)
        {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch(MessagingException exception)
        {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
