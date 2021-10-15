package com.shuoye.video.utils.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 返回结果封装
 *
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Response<T> {
    private int code; // 返回的code
    private T data; // 具体的数据结果
    private String msg; // message 可用来返回接口的说明
}
