package uk.ac.qub.csc3045.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.qub.csc3045.api.mapper.UserStoryMapper;
import uk.ac.qub.csc3045.api.model.UserStory;

import java.util.Comparator;
import java.util.List;

@Service
public class UserStoryService {

    private UserStoryMapper userStoryMapper;

    @Autowired
    public UserStoryService(UserStoryMapper userStoryMapper) {
        this.userStoryMapper = userStoryMapper;
    }

    public List<UserStory> updateBacklogOrder(List<UserStory> backlog) {
        for (int i = 0; i < backlog.size(); i++) {
            userStoryMapper.updateUserStoryIndex(backlog.get(i).getId(), i);
        }

        backlog.sort(Comparator.comparing(UserStory::getIndex));

        return backlog;
    }
}
