package com.example.mail.ControllerLayer.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.mail.DatabaseLayer.Interfaces.LogRepository;
import com.example.mail.DatabaseLayer.Interfaces.UserRepository;
import com.example.mail.DatabaseLayer.Model.Log;
import com.example.mail.DatabaseLayer.Model.User;
import com.example.mail.LogicalLayer.Log.LogService;
import com.example.mail.LogicalLayer.Log.LogClass.Count;
import com.example.mail.LogicalLayer.Log.LogClass.UserStatistics;
import com.example.mail.LogicalLayer.User.UserLogical;

@RestController
public class LogController {

    private final LogRepository logRepository;
    private final UserRepository userRepository;

    LogController(LogRepository logRepository, UserRepository userRepository)
    {
        this.logRepository = logRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/get_logs/{page}")
    List<UserStatistics> getLogs(@PathVariable int page)
    {
        List<UserStatistics> userStatistics = new ArrayList<>();
        List<User> users = userRepository.findAll(UserLogical.getPageableForStatistics(page)).toList();
        for(User user : users)
        {
            userStatistics.add(new UserStatistics(user.getUsername(), user.getEmail(), LogService.countRestCronLetter(user.getLogs()), LogService.getFirstLetterDateTime(user.getLogs()), LogService.getLastLetterDateTime(user.getLogs())));
        }

        Collections.sort(userStatistics, new Comparator<UserStatistics>() {
            @Override
            public int compare(UserStatistics statistics1, UserStatistics statistics2) {
                int totalCountU1 = statistics1.getCount().getRest() + statistics1.getCount().getCron();
                int totalCountU2 = statistics2.getCount().getRest() + statistics2.getCount().getCron();
                return totalCountU2 - totalCountU1;
            }
        });

        return userStatistics;
    }
}
