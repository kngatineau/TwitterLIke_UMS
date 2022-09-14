package com.gatineau.TwitterLike_UMS.repositories;

import com.gatineau.TwitterLike_UMS.beans.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface UserRepository extends MongoRepository<User, String> {
    @Query("{name:'?0'}")
    User findUserByName(String name);

    @Query("{gitHubID:'?0'}")
    User findUserByGitHubID(String gitHubID);
}