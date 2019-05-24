
package com.icecream.server.rss;

import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * An item may represent a "story" -- much like a story in a newspaper or magazine;
 * if so its description is a synopsis of the story, and the link points to the full
 * story. An item may also be complete in itself, if so, the description contains the
 * text (entity-encoded HTML is allowed), and the link and title may be omitted.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tRssItem", propOrder = {
        "title",
        "description",
        "link",
        "category",
        "comments",
        "enclosure",
        "guid",
        "pubDate",
        "source",
        "any"
})
public class TRssItem {

  protected String title;
  protected String description;
  @XmlSchemaType(name = "anyURI")
  protected String link;
  protected TCategory category;
  @XmlSchemaType(name = "anyURI")
  protected String comments;
  protected TEnclosure enclosure;
  protected TGuid guid;
  protected String pubDate;
  protected TSource source;
  @XmlAnyElement(lax = true)
  protected List<Object> any;
  @XmlAnyAttribute
  private Map<QName, String> otherAttributes = new HashMap<QName, String>();

  /**
   * Gets the value of the title property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the value of the title property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setTitle(String value) {
    this.title = value;
  }

  /**
   * Gets the value of the description property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the value of the description property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setDescription(String value) {
    this.description = value;
  }

  /**
   * Gets the value of the link property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getLink() {
    return link;
  }

  /**
   * Sets the value of the link property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setLink(String value) {
    this.link = value;
  }

  /**
   * Gets the value of the category property.
   *
   * @return possible object is
   * {@link TCategory }
   */
  public TCategory getCategory() {
    return category;
  }

  /**
   * Sets the value of the category property.
   *
   * @param value allowed object is
   *              {@link TCategory }
   */
  public void setCategory(TCategory value) {
    this.category = value;
  }

  /**
   * Gets the value of the comments property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getComments() {
    return comments;
  }

  /**
   * Sets the value of the comments property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setComments(String value) {
    this.comments = value;
  }

  /**
   * Gets the value of the enclosure property.
   *
   * @return possible object is
   * {@link TEnclosure }
   */
  public TEnclosure getEnclosure() {
    return enclosure;
  }

  /**
   * Sets the value of the enclosure property.
   *
   * @param value allowed object is
   *              {@link TEnclosure }
   */
  public void setEnclosure(TEnclosure value) {
    this.enclosure = value;
  }

  /**
   * Gets the value of the guid property.
   *
   * @return possible object is
   * {@link TGuid }
   */
  public TGuid getGuid() {
    return guid;
  }

  /**
   * Sets the value of the guid property.
   *
   * @param value allowed object is
   *              {@link TGuid }
   */
  public void setGuid(TGuid value) {
    this.guid = value;
  }

  /**
   * Gets the value of the pubDate property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getPubDate() {
    return pubDate;
  }

  /**
   * Sets the value of the pubDate property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setPubDate(String value) {
    this.pubDate = value;
  }

  /**
   * Gets the value of the source property.
   *
   * @return possible object is
   * {@link TSource }
   */
  public TSource getSource() {
    return source;
  }

  /**
   * Sets the value of the source property.
   *
   * @param value allowed object is
   *              {@link TSource }
   */
  public void setSource(TSource value) {
    this.source = value;
  }

  /**
   * Gets the value of the any property.
   * Objects of the following type(s) are allowed in the list
   * {@link Element }
   * {@link Object }
   */
  public List<Object> getAny() {
    if (any == null) {
      any = new ArrayList<Object>();
    }
    return this.any;
  }

  /**
   * Gets a map that contains attributes that aren't bound to any typed property on this class.
   * the map is keyed by the name of the attribute and
   * the value is the string value of the attribute.
   * the map returned by this method is live, and you can add new attribute
   * by updating the map directly. Because of this design, there's no setter.
   *
   * @return always non-null
   */
  public Map<QName, String> getOtherAttributes() {
    return otherAttributes;
  }

}
