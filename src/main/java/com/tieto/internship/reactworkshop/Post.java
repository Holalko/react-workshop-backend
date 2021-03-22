package com.tieto.internship.reactworkshop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Builder
public class Post {

    private UUID id;
    private String title;
    private String body;


}
