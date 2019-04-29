package com.icecream.server.service;

import com.icecream.server.entity.Article;
import com.icecream.server.exception.RSSException;
import com.icecream.server.rss.ObjectFactory;
import com.icecream.server.rss.TRss;
import com.icecream.server.rss.TRssChannel;
import com.icecream.server.rss.TRssItem;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@Service
public class RssServiceImpl implements RssService {

    private List<Article> getItems(Source source) throws RSSException {
        List<Article> list = new ArrayList<>();
        System.out.println("crawling...");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            JAXBElement<TRss> jaxbElement = unmarshaller.unmarshal(source, TRss.class);
            TRss rss = jaxbElement.getValue();

            List<TRssChannel> channel = rss.getChannel();
            for (TRssChannel chanel : channel) {
                System.out.println("There is a channel");
                List<TRssItem> items = chanel.getItem();
                for (TRssItem rssItem : items) {
                    System.out.println("There is an item");
                    Article rssFeedEntryEntity = new Article();
                    rssFeedEntryEntity.setTitle(rssItem.getTitle());
                    rssFeedEntryEntity.setDescription(rssItem.getDescription());
                    rssFeedEntryEntity.setLink(rssItem.getLink());
                    Date pubDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
                            .parse(rssItem.getPubDate());
                    rssFeedEntryEntity.setPublishedTime(pubDate);
                    list.add(rssFeedEntryEntity);
                }
            }

        } catch (JAXBException | ParseException e) {
            throw new RSSException(e);
        }
        System.out.println("finsh crawling, return list");
        return list;
    }

    public List<Article> getItems(File file) throws RSSException {
        return getItems(new StreamSource(file));
    }

    public List<Article> getItems(String url) throws RSSException {
        return getItems(new StreamSource(url));
    }

}
