package com.example.service;

import java.util.*;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

/**
 * This service handles all the business logic related to messages:
 * creating messages, retrieving them, updating text, deleting by ID,
 * and listing messages written by a specific user.
 */
@Service
public class MessageService {

    // Inject the message repository to interact with the message table
    @Autowired
    private MessageRepository messageRepository;

    // Inject the account repository to verify that the user exists
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Creates a new message if it's valid:
     * - message text is not null or blank
     * - message text is under 255 characters
     * - postedBy refers to an existing user
     */
    public ResponseEntity<?> creatMessage(Message message){
        if (message == null ||
            message.getMessageText() == null ||
            message.getMessageText().isBlank() ||
            message.getMessageText().length() > 255 ||
            !accountRepository.existsById(message.getPostedBy())) {

            return ResponseEntity.status(400).build(); // Invalid input
        }

        Message messageSaved = messageRepository.save(message);
        return ResponseEntity.ok(messageSaved); // Return the saved message with ID
    }

    /**
     * Returns all messages in the database.
     * If none exist, returns an empty list.
     */
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> messagesExist = messageRepository.findAll();
        return ResponseEntity.ok(messagesExist);
    }

    /**
     * Returns a specific message by its ID.
     * If found, return it. If not, return 200 OK with no body.
     */
    public ResponseEntity<?> getMessageById(Integer messageId){
        Optional<Message> messagesExist = messageRepository.findById(messageId);
        if (messagesExist.isPresent()) {
            return ResponseEntity.ok(messagesExist);
        } else {
            return ResponseEntity.ok().build();
        }
    }

    /**
     * Deletes a message if it exists.
     * Always returns 200 OK. If deleted, return 1; if not found, return empty.
     */
    public ResponseEntity<?> deleteMessage(Integer messageId){
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return ResponseEntity.ok(1); // 1 row deleted
        } else {
            return ResponseEntity.ok().build(); // No message to delete
        }
    }

    /**
     * Updates the text of an existing message.
     * Only works if:
     * - message exists
     * - new text is valid (not blank, under 255 chars)
     * Returns 1 if successful, or 400 if failed.
     */
    public ResponseEntity<?> updateMessage(Integer messageId, String messageText){
        Optional<Message> exsisteMessage = messageRepository.findById(messageId);
        if (exsisteMessage.isEmpty() ||
            messageText == null ||
            messageText.isBlank() ||
            messageText.length() > 255) {

            return ResponseEntity.status(400).build(); // Invalid update
        }

        exsisteMessage.get().setMessageText(messageText);
        messageRepository.save(exsisteMessage.get());
        return ResponseEntity.ok(1); // 1 row updated
    }

    /**
     * Returns all messages posted by a specific user (accountId).
     * If the user has no messages, returns an empty list.
     */
    public ResponseEntity<List<Message>> getAllMessegaesUser(Integer accountId){
        List<Message> messagesExist = messageRepository.findByPostedBy(accountId);
        return ResponseEntity.ok(messagesExist);
    }
}