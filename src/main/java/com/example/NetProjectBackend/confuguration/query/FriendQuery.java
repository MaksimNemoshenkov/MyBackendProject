package com.example.NetProjectBackend.confuguration.query;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:query.properties")
public class FriendQuery {
    @Value("${friend.insert}")
    private String insert;

    @Value("${friend.update_status}")
    private String update;

    @Value("${friend.delete}")
    private String delete;

    @Value("${friend.select_friend_id}")
    private String selectFriendId;

    @Value("${friend.select_request}")
    private String selectRequest;
}
