package com.example.mail.ControllerLayer.Letters;

import java.util.List;
import java.util.Optional;

import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mail.DatabaseLayer.Interfaces.CronRepository;
import com.example.mail.DatabaseLayer.Interfaces.UserRepository;
import com.example.mail.DatabaseLayer.Model.Cron;
import com.example.mail.DatabaseLayer.Model.User;
import com.example.mail.LogicalLayer.Letter.LetterLogical;
import com.example.mail.LogicalLayer.Letter.LetterClass.Letter;
import com.example.mail.LogicalLayer.Letter.LetterClass.LetterAndCron;
import com.example.mail.LogicalLayer.Letter.LetterException.CronNotFoundException;
import com.example.mail.LogicalLayer.Letter.LetterException.LetterIsValidCronException;
import com.example.mail.LogicalLayer.Letter.LetterException.ScheduleDontUpdatedException;
import com.example.mail.LogicalLayer.User.UserLogical;
import com.example.mail.LogicalLayer.User.UserException.UserNotFoundException;
import com.example.mail.LogicalLayer.User.UserException.UserWithThisEmailAlreadyExistException;

@RestController
public class CronController {

    private final UserRepository userRepository;
    private final CronRepository cronRepository;

    CronController(UserRepository userRepository, CronRepository cronRepository)
    {
        this.userRepository = userRepository;
        this.cronRepository = cronRepository;
    }

    @PostMapping("/create_cron/{sender_email}/{schedule_name}")
    public ResponseEntity<Object> createCron(@RequestBody LetterAndCron letterAndCron, @PathVariable String sender_email, @PathVariable String schedule_name) {

        try
        {
            if(userRepository.findByEmail(sender_email) == null) {
                throw new IllegalArgumentException( new UserNotFoundException());
            }

            if(CronExpression.isValidExpression(letterAndCron.getCron().getExpression()))
            {
                LetterLogical.callUserRepository(userRepository);
                LetterLogical.cronScheduleTask(letterAndCron, sender_email, schedule_name);
                return ResponseEntity.ok(cronRepository.save(letterAndCron.getCron()));
            }
            else
            {
                return ResponseEntity.badRequest().body( new LetterIsValidCronException(letterAndCron.getCron().getExpression()));
            }
        }
        catch(IllegalArgumentException exception)
        {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
        catch(SchedulerException exception)
        {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping("/update_cron/{cron_id}")
    public ResponseEntity<Object> updateCron(@RequestBody Cron newCron, @PathVariable Long cron_id) {

        try
        {
            Optional<Cron> oldCron = cronRepository.findById(cron_id);
            
            if(LetterLogical.isCronUpdated(newCron, oldCron.get()))
            {
                return ResponseEntity.ok(oldCron
                    .map(cron -> {
                        cron.setExpression(newCron.getExpression());
                        return cronRepository.save(cron);
                    }));
            }
            else
            {
                return ResponseEntity.badRequest().body( new ScheduleDontUpdatedException() ); 
            }
        }
        catch(Exception exception)
        {
            return ResponseEntity.badRequest().body( new CronNotFoundException(cron_id));
        }
    }

    @DeleteMapping("/delete_cron/{cron_id}")
    void deleteCron(@PathVariable Long cron_id) {

        try
        {
            Optional<Cron> cron = cronRepository.findById(cron_id); 
            if(LetterLogical.isCronDeleted(cron.get()))
            {
                cronRepository.deleteById(cron_id);
            }
        }
        catch(SchedulerException exception)
        {
            exception.printStackTrace();
        }
        catch(Exception exception)
        {
            new CronNotFoundException(cron_id).printStackTrace();
        }
    }

    @GetMapping("/get_crons/{page}")
    List<Cron> getCrons(@PathVariable int page) {

        return cronRepository.findAll(LetterLogical.getPageable(page)).toList();
    }

}
