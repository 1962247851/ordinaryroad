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

package tech.ordinaryroad.commons.core.quarkus.base.request.query;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;
import tech.ordinaryroad.commons.core.quarkus.base.request.BaseRequest;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 查询请求抽象类
 *
 * @author mjz
 * @date 2021/9/3
 */
@Getter
@Setter
public class BaseQueryRequest extends BaseRequest {

    private static final long serialVersionUID = -619594620770230224L;

    /**
     * 主键uuid
     */
    @SchemaProperty(name = "主键uuid")
    @FormParam("uuid")
    private String uuid;

    /**
     * 创建者OR账号
     */
    @SchemaProperty(name = "创建者OR账号")
    @FormParam("createBy")
    private String createBy;

    /**
     * 更新者OR账号
     */
    @SchemaProperty(name = "更新者OR账号")
    @FormParam("updateBy")
    private String updateBy;

    /**
     * 主键uuid列表
     */
    @SchemaProperty(name = "主键uuid列表")
    @FormParam("uuids")
    private List<String> uuids;

    /**
     * 个数
     */
    @SchemaProperty(name = "个数")
    @DefaultValue("20")
    @QueryParam("limit")
    private Integer limit = 20;

    /**
     * 偏移量
     */
    @SchemaProperty(name = "偏移量")
    @QueryParam("offset")
    private Integer offset = 0;

    /**
     * 升序字段列表
     */
    @SchemaProperty(name = "升序字段列表")
    @FormParam("orderBy")
    private List<String> orderBy;

    /**
     * 降序字段列表
     */
    @SchemaProperty(name = "降序字段列表")
    @FormParam("orderByDesc")
    private List<String> orderByDesc;

    /**
     * 起始时间
     */
    @SchemaProperty(name = "起始时间")
    @FormParam("startTime")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @SchemaProperty(name = "结束时间")
    @FormParam("endTime")
    private LocalDateTime endTime;

}
