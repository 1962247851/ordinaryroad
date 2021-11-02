package tech.ordinaryroad.commons.core.base.request.save;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 新增/更新请求抽象类
 *
 * @author mjz
 * @date 2021/9/3
 */
@Getter
@Setter
public abstract class BaseSaveRequest implements IBaseSaveRequest {

    private static final long serialVersionUID = -879891710040240181L;

    /**
     * 主键uuid
     */
    @ApiModelProperty("主键uuid")
    private String uuid;

}