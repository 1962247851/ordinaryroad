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
package tech.ordinaryroad.upms.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.constants.ServiceNameCons;
import tech.ordinaryroad.upms.dto.SysUserDTO;
import tech.ordinaryroad.upms.request.SysUserQueryRequest;
import tech.ordinaryroad.upms.request.SysUserSaveRequest;
import tech.ordinaryroad.upms.request.SysUserUpdatePasswordRequest;
import tech.ordinaryroad.upms.request.SysUserUpdateUsernameRequest;

import java.util.List;

/**
 * @author mjz
 * @date 2021/10/27
 */
@Api(value = "用户API")
@FeignClient(name = ServiceNameCons.SERVICE_NAME)
public interface ISysUserApi {

    @PostMapping(value = "/user/create")
    Result<SysUserDTO> create(@Validated @RequestBody SysUserSaveRequest request);

    @PostMapping(value = "/user/findById")
    Result<SysUserDTO> findById(@Validated @RequestBody SysUserQueryRequest request);

    @PostMapping(value = "/user/findAll")
    Result<List<SysUserDTO>> findAll(@Validated @RequestBody SysUserQueryRequest request);

    @PostMapping(value = "/user/list")
    Result<PageInfo<SysUserDTO>> list(@Validated @RequestBody SysUserQueryRequest request);

    @PostMapping(value = "/user/update")
    Result<SysUserDTO> update(@Validated @RequestBody SysUserSaveRequest request);

    @PostMapping(value = "/user/delete")
    Result<Boolean> delete(@Validated @RequestBody BaseDeleteRequest request);


    @PostMapping(value = "/user/updateUsername")
    Result<Boolean> updateUsername(@Validated @RequestBody SysUserUpdateUsernameRequest request);

    @PostMapping(value = "/user/updatePassword")
    Result<Boolean> updatePassword(@Validated @RequestBody SysUserUpdatePasswordRequest request);

}