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
package tech.ordinaryroad.upms.facade.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tech.ordinaryroad.commons.core.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.mybatis.utils.PageUtils;
import tech.ordinaryroad.upms.dto.SysUserDTO;
import tech.ordinaryroad.upms.entity.SysUserDO;
import tech.ordinaryroad.upms.facade.ISysUserFacade;
import tech.ordinaryroad.upms.mapstruct.SysUserMapStruct;
import tech.ordinaryroad.upms.request.*;
import tech.ordinaryroad.upms.service.SysUserService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mjz
 * @date 2021/10/27
 */
@RequiredArgsConstructor
@Component
public class SysUserFacadeImpl implements ISysUserFacade {

    private final SysUserService sysUserService;
    private final SysUserMapStruct objMapStruct;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<SysUserDTO> create(SysUserSaveRequest request) {
        Result<SysUserDTO> validResult = checkValid(request);
        if (validResult != null) {
            return validResult;
        }

        SysUserDO sysUserDO = objMapStruct.transfer(request);
        String password = sysUserDO.getPassword();
        if (StrUtil.isBlank(password)) {
            // 默认密码123456
            sysUserDO.setPassword(passwordEncoder.encode("123456"));
        } else {
            if (StrUtil.length(password) < 6 || StrUtil.length(password) > 16) {
                return Result.fail("密码长度 6-16");
            }
        }
        return Result.success(objMapStruct.transfer(sysUserService.createSelective(sysUserDO)));
    }

    @Override
    public Result<SysUserDTO> findById(SysUserQueryRequest request) {
        SysUserDO sysUserDO = objMapStruct.transfer(request);
        SysUserDO byId = sysUserService.findById(sysUserDO);
        if (Objects.nonNull(byId)) {
            return Result.success(objMapStruct.transfer(byId));
        }
        return Result.fail(StatusCode.DATA_NOT_EXIST);
    }

    @Override
    public Result<List<SysUserDTO>> findAll(SysUserQueryRequest request) {
        SysUserDO sysUserDO = objMapStruct.transfer(request);

        List<SysUserDO> all = sysUserService.findAll(sysUserDO);
        List<SysUserDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());

        return Result.success(list);
    }

    @Override
    public Result<PageInfo<SysUserDTO>> list(SysUserQueryRequest request) {
        PageHelper.offsetPage(request.getOffset(), request.getLimit());

        SysUserDO sysUserDO = objMapStruct.transfer(request);
        Page<SysUserDO> all = (Page<SysUserDO>) sysUserService.findAll(sysUserDO);

        PageInfo<SysUserDTO> objectPageInfo = PageUtils.pageInfoDo2PageInfoDto(all, objMapStruct::transfer);

        return Result.success(objectPageInfo);
    }

    @Override
    public Result<SysUserDTO> update(SysUserSaveRequest request) {
        // 更新，只允许管理员和开发者
        StpUtil.checkRoleOr("ADMIN", "DEVELOPER");
        SysUserDO sysUserDO = objMapStruct.transfer(request);
        return Result.success(objMapStruct.transfer(sysUserService.updateSelective(sysUserDO)));
    }

    @Override
    public Result<Boolean> delete(BaseDeleteRequest request) {
        // 删除，只允许管理员和开发者
        StpUtil.checkRoleOr("ADMIN", "DEVELOPER");
        return Result.success(sysUserService.delete(request.getUuid()));
    }

    @Override
    public Result<Boolean> updateUsername(SysUserUpdateUsernameRequest request) {
        // 获取当前登录用户
        String orNumber = StpUtil.getLoginIdAsString();
        Optional<SysUserDO> byOrNumber = sysUserService.findByOrNumber(orNumber);
        if (!byOrNumber.isPresent()) {
            return Result.fail(StatusCode.USER_ACCOUNT_NOT_EXIST);
        }
        String newUsername = request.getUsername();
        SysUserDO sysUserDO = byOrNumber.get();
        String username = sysUserDO.getUsername();
        if (newUsername.equals(username)) {
            return Result.success(false);
        }

        Optional<SysUserDO> byUsername = sysUserService.findByUsername(newUsername);
        if (byUsername.isPresent()) {
            return Result.fail(StatusCode.USERNAME_ALREADY_EXIST);
        }

        SysUserDO newSysUserDO = new SysUserDO();
        newSysUserDO.setUuid(sysUserDO.getUuid());
        newSysUserDO.setUsername(newUsername);
        if (sysUserService.doUpdateSelective(newSysUserDO)) {
            return Result.success(true);
        } else {
            return Result.fail();
        }
    }

    @Override
    public Result<Boolean> updateEmail(SysUserUpdateEmailRequest request) {
        // 获取当前登录用户
        String orNumber = StpUtil.getLoginIdAsString();
        Optional<SysUserDO> byOrNumber = sysUserService.findByOrNumber(orNumber);
        if (!byOrNumber.isPresent()) {
            return Result.fail(StatusCode.USER_ACCOUNT_NOT_EXIST);
        }
        String newEmail = request.getEmail();
        SysUserDO sysUserDO = byOrNumber.get();
        String email = sysUserDO.getEmail();
        if (newEmail.equals(email)) {
            return Result.success(false);
        }

        Optional<SysUserDO> byEmail = sysUserService.findByEmail(newEmail);
        if (byEmail.isPresent()) {
            return Result.fail(StatusCode.EMAIL_ALREADY_EXIST);
        }

        SysUserDO newSysUserDO = new SysUserDO();
        newSysUserDO.setUuid(sysUserDO.getUuid());
        newSysUserDO.setEmail(newEmail);
        if (sysUserService.doUpdateSelective(newSysUserDO)) {
            return Result.success(true);
        } else {
            return Result.fail();
        }
    }

    @Override
    public Result<Boolean> updatePassword(SysUserUpdatePasswordRequest request) {
        // 获取当前登录用户
        String orNumber = StpUtil.getLoginIdAsString();
        Optional<SysUserDO> byOrNumber = sysUserService.findByOrNumber(orNumber);
        if (!byOrNumber.isPresent()) {
            return Result.fail(StatusCode.USER_ACCOUNT_NOT_EXIST);
        }
        // 新旧密码是否相同
        String newPassword = request.getNewPassword();
        SysUserDO sysUserDO = byOrNumber.get();
        String password = sysUserDO.getPassword();
        if (password.equals(newPassword)) {
            Result.success(false);
        }

        // 旧密码是否匹配
        if (!passwordEncoder.matches(request.getPassword(), password)) {
            return Result.fail(StatusCode.USER_CREDENTIALS_ERROR);
        }

        SysUserDO newSysUserDO = new SysUserDO();
        newSysUserDO.setUuid(sysUserDO.getUuid());
        // 密码加密
        newSysUserDO.setPassword(passwordEncoder.encode(newPassword));
        return Result.success(sysUserService.doUpdateSelective(newSysUserDO));
    }

    @Override
    public Result<SysUserDTO> findByUniqueColumn(SysUserQueryRequest request) {
        Optional<SysUserDO> optional = Optional.empty();
        String orNumber = request.getOrNumber();
        String email = request.getEmail();
        String username = request.getUsername();
        if (StrUtil.isNotBlank(orNumber)) {
            optional = sysUserService.findByOrNumber(orNumber);
        }
        if (!optional.isPresent() && StrUtil.isNotBlank(email)) {
            optional = sysUserService.findByEmail(email);
        }
        if (!optional.isPresent() && StrUtil.isNotBlank(username)) {
            optional = sysUserService.findByUsername(username);
        }

        return optional.map(data -> Result.success(objMapStruct.transfer(data))).orElseGet(Result::fail);
    }

    @Override
    public Result<SysUserDTO> register(SysUserRegisterRequest request) {
        // 校验邮箱
        String email = request.getEmail();
        Optional<SysUserDO> byEmail = sysUserService.findByEmail(email);
        if (byEmail.isPresent()) {
            return Result.fail(StatusCode.EMAIL_ALREADY_EXIST);
        }
        SysUserDO sysUserDO = objMapStruct.transfer(request);
        // 密码加密
        sysUserDO.setPassword(passwordEncoder.encode(request.getPassword()));
        return Result.success(objMapStruct.transfer(sysUserService.createSelective(sysUserDO)));
    }

    @Nullable
    private Result<SysUserDTO> checkValid(SysUserSaveRequest request) {
        // 校验邮箱
        String email = request.getEmail();
        Optional<SysUserDO> byEmail = sysUserService.findByEmail(email);
        if (byEmail.isPresent()) {
            return Result.fail(StatusCode.EMAIL_ALREADY_EXIST);
        }
        // 校验用户名
        String username = request.getUsername();
        Optional<SysUserDO> byName = sysUserService.findByUsername(username);
        if (byName.isPresent()) {
            return Result.fail(StatusCode.USERNAME_ALREADY_EXIST);
        }
        return null;
    }
}
