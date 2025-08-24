package com.agp.geek.enums;

import lombok.Getter;

import java.util.List;

@Getter
public enum TagType {
    ANIME,
    MANGA,
    SERIE,
    DESENHO;

    public static List<TagType> getTagsFromStrings(List<String> tagsStr) {
        return tagsStr.stream().map(TagType::valueOf).toList();
    }
}
