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

import com.example.mail.DatabaseLayer.Interfaces.LogRepository;
import com.example.mail.DatabaseLayer.Interfaces.UserRepository;
import com.example.mail.DatabaseLayer.Model.User;
import com.example.mail.LogicalLayer.Letter.LetterLogical;
import com.example.mail.LogicalLayer.Letter.LetterClass.Letter;
import com.example.mail.LogicalLayer.Log.LogService;
import com.example.mail.LogicalLayer.User.UserException.UserNotFoundException;
import com.example.mail.LogicalLayer.User.UserException.UserWithThisEmailAlreadyExistException;

@RestController
public class LetterController {

    private final UserRepository userRepository;
    private final LogRepository logRepository;

    LetterController(UserRepository userRepository, LogRepository logRepository)
    {
        this.userRepository = userRepository;
        this.logRepository = logRepository;
    }

    @PostMapping("/send_email/{sender_email}/{user_to_send_id}")
    ResponseEntity<Object> sendEmail(@RequestBody Letter letter, @PathVariable String sender_email, @PathVariable Long user_to_send_id) {

        try
        {
            if(userRepository.findByEmail(sender_email) == null) {
                throw new IllegalArgumentException( new UserNotFoundException());
            }

            Optional<User> userToSend = userRepository.findById(user_to_send_id);

            if(userToSend.isPresent())
            {
                Message message = new MimeMessage(LetterLogical.getSession());
                message.setFrom(new InternetAddress(LetterLogical.mainEmail));
                message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(userToSend.get().getEmail()));
                message.setSubject("Subject: " + letter.getSubject());
                message.setText("To: " + userToSend.get().getEmail());
                message.setText("Body: \n" +
                    letter.getBody() + "\n\t" +
                        "User name: " + userToSend.get().getUsername() + "\n\t" +
                        "Creation date and time: " + userToSend.get().getCreatedOn());
                
                LogService.log(userRepository, logRepository, "REST", sender_email);
                
                Transport.send(message);
                
                return ResponseEntity.ok("\"success\": \"email has been sent to " + userToSend.get().getEmail() + "\"");
            }
            else
            {
                throw new NoSuchElementException( new UserNotFoundException(user_to_send_id));
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
        catch(IllegalArgumentException exception)
        {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
