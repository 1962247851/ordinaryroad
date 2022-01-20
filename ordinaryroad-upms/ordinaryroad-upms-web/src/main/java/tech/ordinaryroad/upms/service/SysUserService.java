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
package tech.ordinaryroad.upms.service;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ordinaryroad.commons.mybatis.service.BaseService;
import tech.ordinaryroad.upms.dao.SysUserDAO;
import tech.ordinaryroad.upms.entity.SysUserDO;
import tech.ordinaryroad.upms.entity.SysUsersRolesDO;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mjz
 * @date 2021/10/27
 */
@Service
public class SysUserService extends BaseService<SysUserDAO, SysUserDO> {

    @Autowired
    private SysUsersRolesService sysUsersRolesService;

    public Optional<SysUserDO> findByEmail(String email) {
        Example example = Example.builder(SysUserDO.class)
                .where(Sqls.custom().andEqualTo("email", email))
                .build();
        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public Optional<SysUserDO> findByUsername(String username) {
        Example example = Example.builder(SysUserDO.class)
                .where(Sqls.custom().andEqualTo("username", username))
                .build();
        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public Optional<SysUserDO> findByOrNumber(String orNumber) {
        Example example = Example.builder(SysUserDO.class)
                .where(Sqls.custom().andEqualTo("orNumber", orNumber))
                .build();
        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public List<SysUserDO> findAll(SysUserDO sysUserDO) {
        Sqls sqls = Sqls.custom();

        String email = sysUserDO.getEmail();
        if (StrUtil.isNotBlank(email)) {
            sqls.andLike("email", "%" + email + "%");
        }

        String username = sysUserDO.getUsername();
        if (StrUtil.isNotBlank(username)) {
            sqls.andLike("username", "%" + username + "%");
        }

        String orNumber = sysUserDO.getOrNumber();
        if (StrUtil.isNotBlank(orNumber)) {
            sqls.andLike("orNumber", "%" + orNumber + "%");
        }

        return super.dao.selectByExample(Example.builder(SysUserDO.class).where(sqls).build());
    }

    public List<SysUserDO> findAllByRoleUuid(String roleUuid) {
        List<SysUsersRolesDO> allByRoleUuid = sysUsersRolesService.findAllByRoleUuid(roleUuid);
        List<String> userUuids = allByRoleUuid.stream().map(SysUsersRolesDO::getUserUuid).collect(Collectors.toList());
        return this.findIds(SysUserDO.class, userUuids);
    }

    public Integer selectCount() {
        return super.dao.selectCount(null);
    }

}
