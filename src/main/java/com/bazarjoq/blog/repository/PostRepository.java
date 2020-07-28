package com.bazarjoq.blog.repository;

import com.bazarjoq.blog.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {

}
