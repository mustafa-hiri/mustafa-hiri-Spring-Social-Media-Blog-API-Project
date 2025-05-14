package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Message;
import java.util.*;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    // This finds all messages that were posted by a specific user (by their account ID)
    List<Message> findByPostedBy(Integer accountId);
}