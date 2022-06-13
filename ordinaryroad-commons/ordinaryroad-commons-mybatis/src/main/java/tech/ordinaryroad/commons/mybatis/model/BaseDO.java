/*
 * MIT License
 *
 * Copyright (c) 2021 苗锦洲
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package tech.ordinaryroad.commons.mybatis.model;

import com.alibaba.fastjson2.JSON;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DO基类
 *
 * @author mjz
 * @date 2021/9/3
 */
@Getter
@Setter
@MappedSuperclass
public class BaseDO implements Serializable {

    private static final long serialVersionUID = -5159871109171099140L;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * uuid主键
     */
    @Id
    private String uuid;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 创建者OR账号
     */
    private String createBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新者OR账号
     */
    private String updateBy;

    /**
     * 起始时间
     */
    @Transient
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Transient
    private LocalDateTime endTime;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
