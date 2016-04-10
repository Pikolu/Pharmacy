package com.pharmacy.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Article.
 */
@Entity
@Table(name = "article", indexes = {
        @Index(name = "article_number_index", columnList="articel_number")
})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "article")
public class Article implements Serializable{

    @Id
    private Long id;

    @Column(name = "name")
    @Field(index = FieldIndex.analyzed, type = FieldType.String)
    private String name;

    @Column(name = "sort_name")
    @Field(index = FieldIndex.not_analyzed, type = FieldType.String)
    private String sortName;

    @Size(max = 4000)
    @Column(name = "description", length = 4000)
    private String description;

    @NotNull
    @Column(name = "articel_number", nullable = false)
    private Integer articelNumber;

    @Column(name = "image_url")
    private String imageURL;

    @Column(name = "deep_link")
    private String deepLink;

    @Column(name = "key_words")
    private String keyWords;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "article_id")
    @Field(index = FieldIndex.not_analyzed, type = FieldType.Object)
    private List<Price> prices;

    @Field(index = FieldIndex.not_analyzed, type = FieldType.String)
    @Column(name = "packaging")
    private String packaging;

    @Column(name = "recipe")
    private Boolean recipe;

    @Column(name = "concentration")
    private Float concentration;

    @Column(name = "exported")
    private Boolean exported;

//    @LastModifiedDate
//    @Column(name = "last_updated")
//    @Field(type = FieldType.Date)
//    private ZonedDateTime lastUpdated = ZonedDateTime.now();
//
//    @CreatedDate
//    @Column(name = "creation_date")
//    @Field(type = FieldType.Date)
//    private ZonedDateTime creationDate = ZonedDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getArticelNumber() {
        return articelNumber;
    }

    public void setArticelNumber(Integer articelNumber) {
        this.articelNumber = articelNumber;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDeepLink() {
        return deepLink;
    }

    public void setDeepLink(String deepLink) {
        this.deepLink = deepLink;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public List<Price> getPrices() {
        if (prices == null) {
            prices = new ArrayList<>();
        }
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public Boolean getRecipe() {
        return recipe;
    }

    public void setRecipe(Boolean recipe) {
        this.recipe = recipe;
    }

    public Float getConcentration() {
        return concentration;
    }

    public void setConcentration(Float concentration) {
        this.concentration = concentration;
    }

    public Boolean getExported() {
        return exported;
    }

    public void setExported(Boolean exported) {
        this.exported = exported;
    }
//    public ZonedDateTime getLastUpdated() {
//        return lastUpdated;
//    }
//
//    public void setLastUpdated(ZonedDateTime lastUpdated) {
//        this.lastUpdated = lastUpdated;
//    }
//
//    public ZonedDateTime getCreationDate() {
//        return creationDate;
//    }
//
//    public void setCreationDate(ZonedDateTime creationDate) {
//        this.creationDate = creationDate;
//    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", description='" + description + "'" +
                ", articelNumber='" + articelNumber + "'" +
                ", imageURL='" + imageURL + "'" +
                ", deepLink='" + deepLink + "'" +
                ", keyWords='" + keyWords + "'" +
                '}';
    }
}
