package com.example.mail.LogicalLayer.Letter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.mail.DatabaseLayer.Interfaces.LogRepository;
import com.example.mail.DatabaseLayer.Interfaces.UserRepository;
import com.example.mail.DatabaseLayer.Model.Cron;
import com.example.mail.DatabaseLayer.Model.User;
import com.example.mail.LogicalLayer.Letter.LetterClass.Letter;
import com.example.mail.LogicalLayer.Letter.LetterClass.LetterAndCron;
import com.example.mail.LogicalLayer.Log.LogService;
import com.example.mail.LogicalLayer.User.UserException.UserNotFoundException;

public class LetterLogical {

    public static final String mainEmail = "your-email";
    private static final String password = "your-password";

    public static Session getSession() {
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mainEmail, password);
                }
            }); 
        
        return session;
    }

    public static void cronScheduleTask(LetterAndCron letterAndCron, String senderEmail, String scheduleName) throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        JobDetail jobDetail = JobBuilder.newJob(SendEmailJob.class)
                                        .withIdentity(scheduleName)
                                        .usingJobData("Subject", letterAndCron.getLetter().getSubject())
                                        .usingJobData("Body", letterAndCron.getLetter().getBody())
                                        .usingJobData("Email", senderEmail)
                                        .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                                         .withIdentity(letterAndCron.getCron().getExpression())
                                         .withSchedule(CronScheduleBuilder.cronSchedule(letterAndCron.getCron().getExpression()))
                                         .build();

        scheduler.scheduleJob(jobDetail, trigger);

        scheduler.start();
    }

    public static void sendEmailCron(String subject, String body, String email, UserRepository userRepository, LogRepository logRepository)
    {
        List<User> allUsers = userRepository.findAll();

        for(User user : allUsers)
        {
            try
            {
                Message message = new MimeMessage(LetterLogical.getSession());
                message.setFrom(new InternetAddress(mainEmail));
                message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmail()));
                message.setSubject("Subject: " + subject);
                message.setText("To: " + user.getEmail());
                message.setText("Body: \n" +
                    body + "\n\t" +
                        "User name: " + user.getUsername() + "\n\t" +
                        "Creation date and time: " + user.getCreatedOn());
                
                LogService.log(userRepository, logRepository, "CRON", email);

                Transport.send(message);
            }
            catch(MessagingException exception)
            {
                exception.printStackTrace();
            }
        }
    }

    public static class SendEmailJob implements Job {

        private static UserRepository userRepository;
        private static LogRepository logRepository;

        public static void setDependency(UserRepository userRepository, LogRepository logRepository) {
            SendEmailJob.userRepository = userRepository;
            SendEmailJob.logRepository = logRepository;
        }

        public SendEmailJob() {

        }

        public void execute(JobExecutionContext context) throws JobExecutionException {
            JobDataMap dataMap = context.getMergedJobDataMap();
            String subject = dataMap.getString("Subject");
            String body = dataMap.getString("Body");
            String email = dataMap.getString("Email");
            sendEmailCron(subject, body, email, userRepository, logRepository);
        }
    }

    public static void callUserLogRepository(UserRepository userRepository, LogRepository logRepository)
    {
        SendEmailJob.setDependency(userRepository, logRepository);
    }

    public static Boolean isCronUpdated(Cron cron, Cron oldCron) throws SchedulerException 
    {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        TriggerKey triggerKey = new TriggerKey(oldCron.getExpression());

        Trigger oldTrigger = scheduler.getTrigger(triggerKey);

        if (oldTrigger != null) {
            Trigger newTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(cron.getExpression())
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron.getExpression()))
                    .build();

            scheduler.rescheduleJob(triggerKey, newTrigger);
            return true;
        }

        return false;
    }

    public static Boolean isCronDeleted(Cron cron) throws SchedulerException
    {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            TriggerKey triggerKey = new TriggerKey(cron.getExpression());

            scheduler.unscheduleJob(triggerKey);

            return true;

        } catch (SchedulerException exception) {
            exception.printStackTrace();

            return false;
        }
    }

    public static Pageable getPageable(int pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber, 3);
        return pageable;
    }
}
