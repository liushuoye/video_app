package com.shuoye.video.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 轮播图资源
 *
 * @author shuoy
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BannerData {
	private Integer aID;
	private String title;
	private String picUrl;
	private Long time;
}
