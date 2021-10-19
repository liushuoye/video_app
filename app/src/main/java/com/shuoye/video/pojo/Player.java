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
 * @ClassName Player
 * @create 2021-10-17 11:11
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Player {
	private Integer id;
	private String from;
	private String url;
}
