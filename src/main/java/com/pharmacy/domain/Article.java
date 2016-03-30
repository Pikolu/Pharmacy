package com.pharmacy.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Article.
 */
@Entity
@Table(name = "article")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "article", type = "parent-entity")
public class Article extends BaseUUID {

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

    @OneToMany(cascade = CascadeType.ALL)
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

    @Override
    public String toString() {
        return "Article{" +
                "id=" + getId() +
                ", name='" + name + "'" +
                ", description='" + description + "'" +
                ", articelNumber='" + articelNumber + "'" +
                ", imageURL='" + imageURL + "'" +
                ", deepLink='" + deepLink + "'" +
                ", keyWords='" + keyWords + "'" +
                '}';
    }
}
