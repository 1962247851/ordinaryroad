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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tech.ordinaryroad.commons.core.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.mybatis.utils.PageUtils;
import tech.ordinaryroad.upms.dto.SysRoleDTO;
import tech.ordinaryroad.upms.entity.SysRoleDO;
import tech.ordinaryroad.upms.entity.SysUsersRolesDO;
import tech.ordinaryroad.upms.facade.ISysRoleFacade;
import tech.ordinaryroad.upms.mapstruct.SysRoleMapStruct;
import tech.ordinaryroad.upms.request.SysRoleQueryRequest;
import tech.ordinaryroad.upms.request.SysRoleSaveRequest;
import tech.ordinaryroad.upms.request.SysUserRolesSaveRequest;
import tech.ordinaryroad.upms.service.SysRoleService;
import tech.ordinaryroad.upms.service.SysUsersRolesService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mjz
 * @date 2021/11/3
 */
@RequiredArgsConstructor
@Component
public class SysRoleFacadeImpl implements ISysRoleFacade {

    private final SysRoleService sysRoleService;
    private final SysRoleMapStruct objMapStruct;
    private final SysUsersRolesService sysUsersRolesService;

    @Override
    public Result<SysRoleDTO> create(SysRoleSaveRequest request) {

        // 唯一性校验
        String roleName = request.getRoleName();
        Optional<SysRoleDO> byRoleName = sysRoleService.findByRoleName(roleName);
        if (byRoleName.isPresent()) {
            return Result.fail(StatusCode.NAME_ALREADY_EXIST);
        }
        String roleCode = request.getRoleCode();
        Optional<SysRoleDO> byRoleCode = sysRoleService.findByRoleCode(roleCode);
        if (byRoleCode.isPresent()) {
            return Result.fail(StatusCode.CODE_ALREADY_EXIST);
        }

        SysRoleDO sysRoleDO = objMapStruct.transfer(request);
        return Result.success(objMapStruct.transfer(sysRoleService.createSelective(sysRoleDO)));
    }

    @Override
    public Result<Boolean> delete(BaseDeleteRequest request) {
        return Result.success(sysRoleService.delete(request.getUuid()));
    }

    @Override
    public Result<SysRoleDTO> update(SysRoleSaveRequest request) {
        String uuid = request.getUuid();
        if (StrUtil.isBlank(uuid)) {
            return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
        }

        SysRoleDO byId = sysRoleService.findById(uuid);
        if (Objects.isNull(byId)) {
            return Result.fail(StatusCode.DATA_NOT_EXIST);
        }

        String newRoleName = request.getRoleName();
        String roleName = byId.getRoleName();
        if (!Objects.equals(newRoleName, roleName)) {
            if (sysRoleService.findByRoleName(newRoleName).isPresent()) {
                return Result.fail(StatusCode.NAME_ALREADY_EXIST);
            }
        }
        String newRoleCode = request.getRoleCode();
        String roleCode = byId.getRoleCode();
        if (!Objects.equals(newRoleCode, roleCode)) {
            if (sysRoleService.findByRoleCode(newRoleCode).isPresent()) {
                return Result.fail(StatusCode.CODE_ALREADY_EXIST);
            }
        }

        SysRoleDO transfer = objMapStruct.transfer(request);
        return Result.success(objMapStruct.transfer(sysRoleService.updateSelective(transfer)));
    }

    @Override
    public Result<SysRoleDTO> findById(SysRoleQueryRequest request) {
        SysRoleDO sysRoleDO = objMapStruct.transfer(request);
        SysRoleDO byId = sysRoleService.findById(sysRoleDO);
        if (Objects.nonNull(byId)) {
            return Result.success(objMapStruct.transfer(byId));
        }
        return Result.fail(StatusCode.DATA_NOT_EXIST);
    }

    @Override
    public Result<SysRoleDTO> findByUniqueColumn(SysRoleQueryRequest request) {
        Optional<SysRoleDO> optional = Optional.empty();
        String roleCode = request.getRoleCode();
        String roleName = request.getRoleName();
        if (StrUtil.isNotBlank(roleCode)) {
            optional = sysRoleService.findByRoleCode(roleCode);
        }
        if (!optional.isPresent() && StrUtil.isNotBlank(roleName)) {
            optional = sysRoleService.findByRoleName(roleName);
        }
        return optional.map(data -> Result.success(objMapStruct.transfer(data))).orElseGet(Result::fail);
    }

    @Override
    public Result<List<SysRoleDTO>> findAllByIds(BaseQueryRequest request) {
        List<String> uuids = request.getUuids();
        if (CollUtil.isEmpty(uuids)) {
            return Result.success(Collections.emptyList());
        }
        List<SysRoleDO> all = sysRoleService.findIds(SysRoleDO.class, uuids);
        List<SysRoleDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());
        return Result.success(list);
    }

    @Override
    public Result<List<SysRoleDTO>> findAll(SysRoleQueryRequest request) {
        SysRoleDO sysRoleDO = objMapStruct.transfer(request);

        List<SysRoleDO> all = sysRoleService.findAll(sysRoleDO);
        List<SysRoleDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());

        return Result.success(list);
    }

    @Override
    public Result<PageInfo<SysRoleDTO>> list(SysRoleQueryRequest request) {
        PageHelper.offsetPage(request.getOffset(), request.getLimit());

        SysRoleDO sysRoleDO = objMapStruct.transfer(request);
        Page<SysRoleDO> all = (Page<SysRoleDO>) sysRoleService.findAll(sysRoleDO);

        PageInfo<SysRoleDTO> objectPageInfo = PageUtils.pageInfoDo2PageInfoDto(all, objMapStruct::transfer);

        return Result.success(objectPageInfo);
    }

    @Override
    public Result<List<SysRoleDTO>> findAllByUserUuid(SysRoleQueryRequest request) {
        String userUuid = request.getUserUuid();
        if (StrUtil.isBlank(userUuid)) {
            return Result.fail(StatusCode.PARAM_IS_BLANK);
        }
        // 根据用户uuid查询所有角色uuid
        List<SysUsersRolesDO> allByUserUuid = sysUsersRolesService.findAllByUserUuid(userUuid);
        // 根据角色uuid查询角色
        List<String> roleUuidList = allByUserUuid.stream().map(SysUsersRolesDO::getRoleUuid).collect(Collectors.toList());
        BaseQueryRequest baseQueryRequest = new BaseQueryRequest();
        baseQueryRequest.setUuids(roleUuidList);
        return this.findAllByIds(baseQueryRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> updateUserRoles(SysUserRolesSaveRequest request) {
        String userUuid = request.getUserUuid();

        // 最新的角色uuids
        List<String> roleUuids = request.getRoleUuids();

        // 本地的角色uuids
        List<SysUsersRolesDO> allByUserUuid = sysUsersRolesService.findAllByUserUuid(userUuid);

        // 需要新增的用户角色关联关系实体类
        List<SysUsersRolesDO> needInsertList = new ArrayList<>();
        // 需要删除的角色uuids
        List<String> needDeleteList = new ArrayList<>();

        roleUuids.forEach(roleUuid -> {
            // 判断是否需要新增
            boolean find = false;
            for (SysUsersRolesDO sysUsersRolesDO : allByUserUuid) {
                if (sysUsersRolesDO.getRoleUuid().equals(roleUuid)) {
                    find = true;
                    break;
                }
            }
            if (!find) {
                SysUsersRolesDO sysUsersRolesDO = new SysUsersRolesDO();
                sysUsersRolesDO.setUserUuid(userUuid);
                sysUsersRolesDO.setRoleUuid(roleUuid);
                needInsertList.add(sysUsersRolesDO);
            }
        });
        allByUserUuid.forEach(sysUsersRolesDO -> {
            // 最新的不存在，需要删除的
            if (!roleUuids.contains(sysUsersRolesDO.getRoleUuid())) {
                needDeleteList.add(sysUsersRolesDO.getUuid());
            }
        });

        if (CollUtil.isEmpty(needDeleteList) && CollUtil.isEmpty(needInsertList)) {
            return Result.success(false);
        } else {
            if (CollUtil.isNotEmpty(needDeleteList)) {
                sysUsersRolesService.deleteByIdList(SysUsersRolesDO.class, needDeleteList);
            }
            if (CollUtil.isNotEmpty(needInsertList)) {
                sysUsersRolesService.insertList(needInsertList);
            }
        }

        return Result.success(true);
    }

}
