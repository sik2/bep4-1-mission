package com.back.boundedContext.post.domain;

import java.util.ArrayList;
import java.util.List;

import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.post.dto.PostDto;
import com.back.shared.post.event.PostCommentCreatedEvent;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "POST_POST")
@NoArgsConstructor
@Getter
public class Post extends BaseIdAndTime {

	@ManyToOne(fetch = FetchType.LAZY)
	private PostMember author;
	private String title;
	@Column(columnDefinition = "LONGTEXT")
	private String content;
	@OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	private List<PostComment> comments = new ArrayList<>();

	public Post(PostMember author, String title, String content) {
		this.author = author;
		this.title = title;
		this.content = content;
	}

	public PostDto toDto() {
		return new PostDto(
			getId(),
			getCreateTime(),
			getModifyTime(),
			author.getId(),
			author.getNickName(),
			title,
			content
		);
	}

	public PostComment addComment(PostMember author, String content) {
		PostComment postComment = new PostComment(this, author, content);

		comments.add(postComment);

		publishEvent(new PostCommentCreatedEvent(postComment.toDto()));

		return postComment;
	}

	public boolean hasComments() {
		return !comments.isEmpty();
	}


}
