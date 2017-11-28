package uk.ac.qub.csc3045.api.setup;

import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.Sprint;
import uk.ac.qub.csc3045.api.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UnitTestObjectGenerator {

    private static Random rand = new Random();

    public static Sprint generateSprint() {
        long id = rand.nextInt(1000);

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusWeeks(2);
        Sprint sprint = new Sprint("Test Sprint " + id, startDate, endDate);
        sprint.setId(id);

        return sprint;
    }

    public static List<Sprint> generateSprintList(int size) {
        List<Sprint> sprints = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            sprints.add(generateSprint());
        }

        return sprints;
    }

    public static Project generateProject() {
        long id = rand.nextInt(1000);

        Project project = new Project();
        project.setId(id);
        project.setName("Test Project " + id);
        project.setDescription("Test Project Description " + id);

        return project;
    }

    public static User generateUser() {
        long id = rand.nextInt(1000);

        User user = new User();
        user.setId(id);
        user.setForename("Test User Forename " + id);
        user.setSurname("Test User Surname " + id);
        user.setEmail("TestUserEmail" + id + "@email.com");

        return user;
    }

    public static List<User> generateUserList(int size) {
        List<User> users = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            users.add(generateUser());
        }

        return users;
    }
}