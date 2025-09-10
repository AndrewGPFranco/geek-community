package com.agp.geek.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum TagType {
    ANIME("Anime"),
    MANGA("Manga"),
    SERIE("Série"),
    DESENHO("Desenho"),
    GAME("Jogo");

    private final String description;

    TagType(String description) {
        this.description = description;
    }

    public static List<TagType> getTagsFromStrings(List<String> tagsStr) {
        List<TagType> tags = new ArrayList<>(tagsStr.size());

        for (String tag : tagsStr) {
            switch (tag) {
                case "Anime":
                    tags.add(ANIME);
                    break;
                case "Manga":
                    tags.add(MANGA);
                    break;
                case "Série":
                    tags.add(SERIE);
                    break;
                case "Desenho":
                    tags.add(DESENHO);
                    break;
                case "Jogo":
                    tags.add(GAME);
                    break;
            }
        }

        return tags;
    }
}
