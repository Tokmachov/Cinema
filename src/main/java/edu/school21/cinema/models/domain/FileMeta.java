package edu.school21.cinema.models.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileMeta {
    private String name;
    private long sizeKb;
    private String mime;
}
