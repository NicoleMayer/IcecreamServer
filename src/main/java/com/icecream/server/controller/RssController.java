package com.icecream.server.controller;

import com.icecream.server.entity.RssFeed;
import com.icecream.server.entity.User;
import com.icecream.server.service.RssFeedService;
import com.icecream.server.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class RssController {
  private UserService userService;

  private RssFeedService rssFeedService;

  public RssController(UserService userService, RssFeedService rssFeedService) {
    this.userService = userService;
    this.rssFeedService = rssFeedService;
  }

  @GetMapping(value = "/user/aaa")
  public String testCrawl() {
    User user = userService.findByPhoneNumber("12345678910");
    rssFeedService.saveAll(user.getRssFeedEntities().get(1));
    return "ok?";
  }

  //TODO 没有测试
  @PostMapping(path = "/user/{uid}/addChannel")
  public String subscribeChannel(RssFeed rssFeedEntity, String phoneNumber) {
    rssFeedService.save(rssFeedEntity, phoneNumber);
    return "save channel succeed";
  }

  //TODO 没有测试 这里没有写针对哪个用户删除？？？？？
  @PostMapping(path = "/user/{uid}/deleteChannel")
  public String deleteChannel(@PathVariable Long id) {
    RssFeed rssFeedEntity = rssFeedService.findOne(id);
    rssFeedService.delete(rssFeedEntity);
    return "delet channel succeed";
  }
}
