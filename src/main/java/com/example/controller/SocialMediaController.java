package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.entity.Account;
import com.example.entity.Message;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */


 /**
 * This controller handles all the endpoints for the social media API.
 * It maps HTTP requests to the appropriate service layer methods to handle:
 * - user registration
 * - user login
 * - message creation, retrieval, update, and deletion
 * - getting all messages from a specific user
 */
@RestController
public class SocialMediaController {

    // Injects the account service which handles registration and login logic
    @Autowired
    private AccountService accountService;

    // Injects the message service which handles message-related operations
    @Autowired
    private MessageService messageService;

    // Registers a new account
    // POST localhost:8080/register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Account account){
        return accountService.register(account);
    }

    // Authenticates a user login
    // POST localhost:8080/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account){
        return accountService.login(account);
    }

    // Creates a new message
    // POST localhost:8080/messages
    @PostMapping("/messages")
    public ResponseEntity<?> creatMessage(@RequestBody Message message){
        return messageService.creatMessage(message);
    }

    // Retrieves all messages
    // GET localhost:8080/messages
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return messageService.getAllMessages();
    }

    // Retrieves a message by its ID
    // GET localhost:8080/messages/{messageId}
    @GetMapping("messages/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable Integer messageId){
        return messageService.getMessageById(messageId);
    }

    // Deletes a message by its ID
    // DELETE localhost:8080/messages/{messageId}
    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable Integer messageId){
        return messageService.deleteMessage(messageId);
    }

    // Updates the text of a message identified by its ID
    // PATCH localhost:8080/messages/{messageId}
    @PatchMapping("messages/{messageId}")
    public ResponseEntity<?> updateMessage(@PathVariable Integer messageId, @RequestBody Message message){
        return messageService.updateMessage(messageId, message.getMessageText());
    }

    // Retrieves all messages written by a specific user
    // GET localhost:8080/accounts/{accountId}/messages
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<?> getAllMessegaesUser(@PathVariable Integer accountId){
        return messageService.getAllMessegaesUser(accountId);
    }

}