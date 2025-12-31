package com.back.shared.post.dto;

import java.time.LocalDateTime;

import com.back.standard.modelType.CanGetModelTypeCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostDto implements CanGetModelTypeCode {
	private final int id;
	private final LocalDateTime createTime;
	private final LocalDateTime modifyTime;
	private final int authorId;
	private final String authorName;
	private final String title;
	private final String content;

	@Override
	public String getModelTypeCode() {
		return "Post";
	}
}
