package com.shuoye.video.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

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
 * @ClassName AnimeInfo
 * @create 2021-10-17 10:50
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Entity
public class AnimeInfo {
	@PrimaryKey
	private Integer id;
	/**
	 * 地区
	 */
	private String region;
	/**
	 * 动画种类
	 */
	private String genre;
	/**
	 * 动画名称
	 */
	private String name;
	/**
	 * 原版名称
	 */
	private String originalName;
	/**
	 * 其他名称
	 */
	private String otherNames;
	/**
	 * 字母索引
	 */
	private String letter;
	/**
	 * 原作
	 */
	private String originalAuthor;
	/**
	 * 制作公司
	 */
	private String productionCompany;
	/**
	 * 首播时间
	 */
	private String premiereTime;
	/**
	 * 播放状态
	 */
	private String status;
	/**
	 * 剧情类型
	 */
	private List<Tag> tags;
	/**
	 * 视频清晰度
	 */
	private String videoResolution;
	/**
	 * 资源类型
	 */
	private String resource;
	/**
	 * 新番标题
	 */
	private String newTitle;
	/**
	 * 更新时间
	 */
	private Long updateTime;
	/**
	 * 推荐星级
	 */
	private Integer recommendStar;
	/**
	 * 封面
	 */
	private String cover;
	/**
	 * 封面图小
	 */
	private String coverSmall;
	/**
	 * 简介
	 */
	private String introduction;
	/**
	 * 系列名
	 */
	private String seriesName;
	/**
	 * 系列
	 */
	private List<AnimeInfo> series;
	/**
	 * 官方网站
	 */
	private String officialWebsite;
	/**
	 * 网盘资源
	 */
	private String networkDisk;
	/**
	 * 首播年份
	 */
	private String year;
	/**
	 * 首播季度
	 */
	private String season;
	/**
	 * 热度
	 */
	private Integer rankCount;
	/**
	 * 收藏数
	 */
	private Integer collectCount;
	/**
	 * 评论数
	 */
	private Integer commentCount;
	/**
	 * 播放链接
	 */
	private String players;
}
