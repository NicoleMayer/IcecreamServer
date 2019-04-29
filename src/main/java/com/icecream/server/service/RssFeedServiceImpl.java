package com.icecream.server.service;

import com.icecream.server.controller.UserController;
import com.icecream.server.dao.ArticleRepository;
import com.icecream.server.dao.RssFeedRepository;
import com.icecream.server.dao.UserRepository;
import com.icecream.server.entity.Article;
import com.icecream.server.entity.RssFeed;
import com.icecream.server.entity.User;
import com.icecream.server.exception.RSSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;


@Service
@Transactional
public class RssFeedServiceImpl implements RssFeedService {

    @Autowired
    private RssFeedRepository rssFeedRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RssServiceImpl rssService;

    static final Logger logger = Logger.getLogger(String.valueOf(UserController.class));

    @Override
    public void save(RssFeed rssFeedEntity, String phoneNumber) {
        User userEntity = userRepository.findByPhoneNumber(phoneNumber);
        rssFeedEntity.setUserEntity(userEntity);
        rssFeedRepository.save(rssFeedEntity);
        saveAll(rssFeedEntity);
    }

    @Override
    public void saveAll(RssFeed rssFeedEntity) {
        try {
            List<Article> itemEntities = rssService.getItems(rssFeedEntity.getUrl());
            itemEntities.forEach(entry -> {
                Article savedRssFeedEntryEntity =
                        articleRepository.findByRssFeedEntityAndLink(rssFeedEntity, entry.getLink());
                if (savedRssFeedEntryEntity == null) {
                    entry.setRssFeedEntity(rssFeedEntity);
                    articleRepository.save(entry);
                }
            });
        } catch (RSSException e) {
            logger.warning("Could not save blog");
        }
    }

    @Scheduled(cron = "${pl.dmichalski.rss.core.service.scheduleCron}")
    public void reloadChannels() {
        rssFeedRepository.findAll().stream().forEach(this::saveAll);
    }

    @Override
    public RssFeed findOne(Long id) {
        return rssFeedRepository.findById(id).get();
    }


    @Override
//    @PreAuthorize("#blog.userEntity.name == authentication.name or hasRole('ROLE_ADMIN')") TODO
    public void delete(@P("blog") RssFeed rssFeedEntity) {
        rssFeedRepository.delete(rssFeedEntity);
    }
}

