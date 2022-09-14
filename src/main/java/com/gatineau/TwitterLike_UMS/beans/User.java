package com.gatineau.TwitterLike_UMS.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("user")
@Data
@NoArgsConstructor

public class User {

    @Id
    private String id;
    private String name;
    private String[] roles;
    private List<String> sub_ids = new ArrayList<>();
    private String gitHubID;
    private String authToken;

    private String userEmail;

    public User(String id, String name, String gitHubID, String authToken) {
        this.id = id;
        this.name = name;
        this.gitHubID = gitHubID;
        this.authToken = authToken;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}