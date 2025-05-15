package com.example.service;

import java.util.*;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Creates a new message if it's valid.
     * Returns:
     * - the saved message if successful
     * - null if validation fails
     */
    public Object creatMessage(Message message) {
        if (message == null ||
            message.getMessageText() == null ||
            message.getMessageText().isBlank() ||
            message.getMessageText().length() > 255 ||
            !accountRepository.existsById(message.getPostedBy())) {
            return null;
        }

        Message messageSaved = messageRepository.save(message);
        return messageSaved;
    }

    /**
     * Returns all messages from the database.
     */
    public List<Message> getAllMessages() {
        List<Message> messagesExist = messageRepository.findAll();
        return messagesExist;
    }

    /**
     * Returns a message by ID if it exists, or null otherwise.
     */
    public Object getMessageById(Integer messageId) {
        Optional<Message> messagesExist = messageRepository.findById(messageId);
        if (messagesExist.isPresent()) {
            return messagesExist;
        } else {
            return null;
        }
    }

    /**
     * Deletes a message if it exists.
     * Returns:
     * - true if deleted
     * - false if it didn't exist
     */
    public Object deleteMessage(Integer messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1; // number of rows affected
        } else {
            return null;
        }
    }

    /**
     * Updates a message text if valid and exists.
     * Returns:
     * - true if updated
     * - false if invalid input or not found
     */
    public Object updateMessage(Integer messageId, String messageText) {
        Optional<Message> exsisteMessage = messageRepository.findById(messageId);
        if (exsisteMessage.isEmpty() ||
            messageText == null ||
            messageText.isBlank() ||
            messageText.length() > 255) {
            return false;
        }

        exsisteMessage.get().setMessageText(messageText);
        messageRepository.save(exsisteMessage.get());
        return 1; // number of rows updated
    }

    /**
     * Returns all messages posted by a specific user.
     */
    public List<Message> getAllMessegaesUser(Integer accountId) {
        List<Message> messagesExist = messageRepository.findByPostedBy(accountId);
        return messagesExist;
    }
}