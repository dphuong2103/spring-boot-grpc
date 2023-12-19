package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.services.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
public class UserController {
    private UserServiceImpl userService;

    @GetMapping
    @RequestMapping("{id}/black-list")
    public boolean isUserBlackList(@PathVariable("id") String id){
        return userService.isUserBlackList(id);
    }

    @GetMapping
    @RequestMapping("get-user-stream")
    public void getUserStream(){
        userService.getUserStream();
    }

    @GetMapping
    @RequestMapping("send-user-stream")
    public void sendUserStream(){
        userService.sendUserStream();
    }
}
