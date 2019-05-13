package com.icecream.server.service.rssfeed;

import com.icecream.server.dao.ArticleRepository;
import com.icecream.server.dao.RssFeedRepository;
import com.icecream.server.dao.UserRepository;
import com.icecream.server.entity.Article;
import com.icecream.server.entity.RssFeed;
import com.icecream.server.entity.User;
import com.icecream.server.exception.RSSException;
import com.icecream.server.rss.ObjectFactory;
import com.icecream.server.rss.TRss;
import com.icecream.server.rss.TRssChannel;
import com.icecream.server.rss.TRssItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class RssFeedServiceImpl implements RssFeedService {

    @Autowired
    private RssFeedRepository rssFeedRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public boolean addChannel(RssFeed rssFeedEntity, User user) {
        System.out.println("!!! get url  "+rssFeedEntity.getUrl());
        RssFeed exist_rss_feed = rssFeedRepository.findByUrl(rssFeedEntity.getUrl());
        System.out.println("!!! exist rss feed "+exist_rss_feed);
        if (exist_rss_feed == null || exist_rss_feed.getUserEntities().contains(user)){ //alreay have the feed
            return false;
        }

        if (user.getRssFeedEntities().add(exist_rss_feed))
        {
            System.out.println("save the new channel");
            userRepository.save(user);
            System.out.println("crawl some articles for the new channel");
            addArticles(rssFeedRepository.findByUrl(rssFeedEntity.getUrl()));
            return true;
        }
        return false;
    }

    @Override
    public void addArticles(RssFeed rssFeedEntity) {
        try {
            List<Article> articles = crawlArticles(rssFeedEntity.getUrl());
            articles.forEach(entry -> {
                Article savedArticles =
                        articleRepository.findByRssFeedEntityAndLink(rssFeedEntity, entry.getLink()); //no repeat articles
                if (savedArticles == null) {
                    entry.setRssFeedEntity(rssFeedEntity);
                    articleRepository.save(entry);
                }
            });
        } catch (RSSException e) {
            System.out.println("Could not save the channel");
        }
    }

    @Scheduled(cron = "0 5/10 0 * *") //TODO 定时任务
    public void reloadChannels() {
        rssFeedRepository.findAll().stream().forEach(this::addArticles);
    }

    @Override
    public boolean deleteChannel(RssFeed rssFeed, User user) {
        for (RssFeed rssFeedEntity: user.getRssFeedEntities()) {
            if(rssFeedEntity.getId() == rssFeed.getId()) {
                user.getRssFeedEntities().remove(rssFeedEntity);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    private List<Article> crawlArticles(Source source) throws RSSException {
        List<Article> list = new ArrayList<>();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            JAXBElement<TRss> jaxbElement = unmarshaller.unmarshal(source, TRss.class);
            TRss rss = jaxbElement.getValue();

            List<TRssChannel> channel = rss.getChannel();
            for (TRssChannel chanel : channel) {
                List<TRssItem> items = chanel.getItem();
                for (TRssItem rssItem : items) {
                    Article article = new Article();
                    article.setTitle(rssItem.getTitle());
                    article.setDescription(rssItem.getDescription());
                    article.setLink(rssItem.getLink());
                    Date pubDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
                            .parse(rssItem.getPubDate());
                    article.setPublishedTime(pubDate);
                    list.add(article);
                }
            }

        } catch (JAXBException | ParseException e) {
            throw new RSSException(e);
        }
        return list;
    }

    @Override
    public List<Article> crawlArticles(String url) throws RSSException {
        return this.crawlArticles(new StreamSource(url));
    }

    @Override
    public Optional<RssFeed> findById(long id){
        return rssFeedRepository.findById(id);
    }
}

