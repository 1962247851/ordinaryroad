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

package tech.ordinaryroad.im.entity;


import lombok.Getter;
import lombok.Setter;
import tech.ordinaryroad.commons.mybatis.model.BaseDO;

import javax.persistence.Column;
import javax.persistence.Table;


/**
 * 单聊即时消息表
 *
 * @author mjz
 * @date 2022/1/20
 **/
@Getter
@Setter
@Table(name = "im_msg")
public class ImMsgDO extends BaseDO {

    private static final long serialVersionUID = -111925592197699160L;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 消息id
     */
    private String msgId;

    /**
     * 消息内容
     */
    private String payload;

    /**
     * 消息类型
     */
    private String bizType;

    /**
     * 是否已读
     */
    @Column(name = "`read`")
    private Boolean read;

    /**
     * 是否已撤回
     */
    private Boolean recalled;

    /**
     * 接收者
     */
    private String toOrNumber;

    /**
     * MIMC APP ID
     */
    private String appId;

}
