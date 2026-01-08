package com.facebookapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facebookapp.dto.ReactionDTO.AddReaction;
import com.facebookapp.service.ReactionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/reaction")
public class ReactionController {
    
    private final ReactionService reactionService;

    public ReactionController(ReactionService reactionService){
        this.reactionService=reactionService;
    }

    @PostMapping("/addReaction")
    public ResponseEntity<?> addReaction(@RequestBody AddReaction reaction) {
        reactionService.addReaction(reaction);
        return ResponseEntity.ok().build();
    }
    
}
