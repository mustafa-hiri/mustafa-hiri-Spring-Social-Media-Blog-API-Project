package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.entity.Account;
import com.example.entity.Message;
import java.util.List;

@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    // POST localhost:8080/register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Account account) {
        Object result = accountService.register(account);

        if (result instanceof Integer) {
            int code = (Integer) result;
            if (code == 400) return ResponseEntity.badRequest().build();
            if (code == 409) return ResponseEntity.status(409).build();
        }

        return ResponseEntity.ok(result);
    }

    // POST localhost:8080/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        Account result = accountService.login(account);
        if (result == null) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
        return ResponseEntity.ok(result);
    }

    // POST localhost:8080/messages
    @PostMapping("/messages")
    public ResponseEntity<?> creatMessage(@RequestBody Message message) {
        Object result = messageService.creatMessage(message);
        if (result == null) {
            return ResponseEntity.badRequest().build(); // Invalid message
        }
        return ResponseEntity.ok(result);
    }

    // GET localhost:8080/messages
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    // GET localhost:8080/messages/{messageId}
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable Integer messageId) {
        Object result = messageService.getMessageById(messageId);
        if (result == null) {
            return ResponseEntity.ok().build(); // No message found
        }
        return ResponseEntity.ok(result);
    }

    // DELETE localhost:8080/messages/{messageId}
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable Integer messageId) {
        Object result = messageService.deleteMessage(messageId);
        if (result == null) {
            return ResponseEntity.ok().build(); // Nothing deleted
        }
        return ResponseEntity.ok(result); // Return 1
    }

    // PATCH localhost:8080/messages/{messageId}
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> updateMessage(@PathVariable Integer messageId, @RequestBody Message message) {
        Object result = messageService.updateMessage(messageId, message.getMessageText());
        if (result instanceof Boolean && !(Boolean) result) {
            return ResponseEntity.badRequest().build(); // Invalid update
        }
        return ResponseEntity.ok(result); // Return 1
    }

    // GET localhost:8080/accounts/{accountId}/messages
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<?> getAllMessegaesUser(@PathVariable Integer accountId) {
        List<Message> messages = messageService.getAllMessegaesUser(accountId);
        return ResponseEntity.ok(messages);
    }
}