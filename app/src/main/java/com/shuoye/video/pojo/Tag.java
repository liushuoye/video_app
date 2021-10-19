package com.shuoye.video.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * TODO
 *
 * @author shuoye
 * @program video
 * @ClassName Tag
 * @create 2021-10-17 22:46
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Tag {
	private Integer id;
	private Integer animeInfoId;
	private String name;
}
