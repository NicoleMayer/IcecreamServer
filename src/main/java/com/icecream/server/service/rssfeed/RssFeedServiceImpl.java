package com.icecream.server.service.rssfeed;

import com.icecream.server.dao.ArticleRepository;
import com.icecream.server.dao.RssFeedRepository;
import com.icecream.server.dao.UserRepository;
import com.icecream.server.entity.Article;
import com.icecream.server.entity.RssFeed;
import com.icecream.server.entity.User;
import com.icecream.server.exception.RssException;
import com.icecream.server.rss.ObjectFactory;
import com.icecream.server.rss.TRss;
import com.icecream.server.rss.TRssChannel;
import com.icecream.server.rss.TRssItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private transient RssFeedRepository rssFeedRepository;

  @Autowired
  private transient UserRepository userRepository;

  @Autowired
  private transient ArticleRepository articleRepository;

  private static final Logger logger = LoggerFactory.getLogger(RssFeedServiceImpl.class);

  @Override
  public boolean addChannel(RssFeed rssFeedEntity, User user) {
    RssFeed existRssFeed = rssFeedRepository.findByUrl(rssFeedEntity.getUrl());
    if (existRssFeed == null
            || existRssFeed.getUserEntities().contains(user)) { //alreay have the feed
      return false;
    }

    if (user.getRssFeedEntities().add(existRssFeed)) {
      userRepository.save(user);
      logger.info("crawl some articles for the new channel");
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
        Article savedArticle =
                articleRepository.findByRssFeedEntityAndLink(
                        rssFeedEntity, entry.getLink()); //no repeat articles
        if (savedArticle == null) {
          entry.setRssFeedEntity(rssFeedEntity);
          articleRepository.save(entry);
        }
      });
    } catch (RssException e) {
      logger.info("Could not save the channel");
    }
  }

  @Scheduled(cron = "0 5/10 0 * *") //TODO 定时任务
  public void reloadChannels() {
    rssFeedRepository.findAll().stream().forEach(this::addArticles);
  }

  @Override
  public boolean deleteChannel(RssFeed rssFeed, User user) {
    if (user.getRssFeedEntities().remove(rssFeed)) {
      userRepository.save(user);
      return true;
    }
    return false;
  }

  private List<Article> crawlArticles(Source source) throws RssException {
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
      throw new RssException(e);
    }
    return list;
  }

  @Override
  public List<Article> crawlArticles(String url) throws RssException {
    return this.crawlArticles(new StreamSource(url));
  }

  @Override
  public Optional<RssFeed> findById(long id) {
    return rssFeedRepository.findById(id);
  }
}

