package com.agp.geek.enums;

import lombok.Getter;

import java.util.List;

@Getter
public enum TagType {
    ANIME("Anime"),
    MANGA("Manga"),
    SERIE("Série"),
    DESENHO("Desenho");

    private final String description;

    TagType(String description) {
        this.description = description;
    }

    public static List<TagType> getTagsFromStrings(List<String> tagsStr) {
        return tagsStr.stream().map(TagType::valueOf).toList();
    }
}
