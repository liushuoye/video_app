package com.shuoye.video.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 新番时间表资源
 *
 * @author shuoye
 * @program video
 * @ClassName TimeLine
 * @create 2021-10-16 18:36
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TimeLine {
	private Integer id;
	private String name;
	private String episodes;
	private String cover;
}
