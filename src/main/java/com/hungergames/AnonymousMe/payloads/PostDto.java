package com.hungergames.AnonymousMe.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PostDto {

	private int postId;
	private String title;
	private String Content;
}
