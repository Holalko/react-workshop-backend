package com.tieto.internship.reactworkshop;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@CrossOrigin("*")
public class PostController {

    List<Post> posts = Arrays.asList(
            new Post(UUID.randomUUID(), "Dogs, cats, animals", "Lorem ipsum dolor sit amet, consectetur adipisicing elit. A adipisci aspernatur aut autem consectetur, dolore, est eum fugiat impedit itaque labore nesciunt ratione recusandae, temporibus."),
            new Post(UUID.randomUUID(), "React is the best", "Lorem ipsum dolor sit amet, consectetur adipisicing elit. A adipisci aspernatur aut autem consectetur, dolore, est eum fugiat impedit itaque labore nesciunt ratione recusandae, temporibus."),
            new Post(UUID.randomUUID(), "New workshop", "Lorem ipsum dolor sit amet, consectetur adipisicing elit. A adipisci aspernatur aut autem consectetur, dolore, est eum fugiat impedit itaque labore nesciunt ratione recusandae, temporibus."),
            new Post(UUID.randomUUID(), "Test tile", "Lorem ipsum dolor sit amet, consectetur adipisicing elit. A adipisci aspernatur aut autem consectetur, dolore, est eum fugiat impedit itaque labore nesciunt ratione recusandae, temporibus.")
    );

    @GetMapping
    public Page<Post> getPosts(Pageable pageable) {
        return new PageImpl<>(posts.subList(pageable.getPageNumber() * pageable.getPageSize(), Math.min((pageable.getPageNumber() + 1) * pageable.getPageSize(), posts.size())), pageable, (long) posts.size());
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable UUID id) {
        return posts.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }


    @PostMapping
    public Post addPost(@RequestBody Post post){
        if(Math.random() < 0.1){
            throw new IllegalArgumentException("Random exception");
        }
        if(post != null && post.getId() != null && post.getBody() != null && post.getTitle() != null){
            posts.add(post);
            return post;
        }
        throw new IllegalArgumentException("Invalid  post");
    }

    @PutMapping("/{id}")
    public Post update(@PathVariable UUID id, @RequestBody Post post){
        posts = posts.stream().map(p -> {
            if(p.getId().equals(id)){
                return post;
            }
            return p;
        }).collect(Collectors.toList());
        return post;
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        posts = posts.stream().filter(p -> !p.getId().equals(id)).collect(Collectors.toList());
    }
}
