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

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import tech.ordinaryroad.commons.core.lang.Argument;
import tech.ordinaryroad.commons.mybatis.service.BaseService;
import tech.ordinaryroad.upms.dao.SysRequestPathDAO;
import tech.ordinaryroad.upms.entity.SysRequestPathDO;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author mjz
 * @date 2021/11/7
 */
@Service
public class SysRequestPathService extends BaseService<SysRequestPathDAO, SysRequestPathDO> {

    public Optional<SysRequestPathDO> findByPath(String path) {
        Example example = Example.builder(SysRequestPathDO.class)
                .where(Sqls.custom().andEqualTo("path", path))
                .build();
        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public Optional<SysRequestPathDO> findByPathName(String pathName) {
        Example example = Example.builder(SysRequestPathDO.class)
                .where(Sqls.custom().andEqualTo("pathName", pathName))
                .build();
        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public List<SysRequestPathDO> findAll(SysRequestPathDO sysRequestPathDO) {
        Sqls sqls = Sqls.custom();

        String path = sysRequestPathDO.getPath();
        if (Argument.isNotBlank(path)) {
            sqls.andLike("path", "%" + path + "%");
        }
        String pathName = sysRequestPathDO.getPathName();
        if (Argument.isNotBlank(pathName)) {
            sqls.andLike("pathName", "%" + pathName + "%");
        }

        return super.dao.selectByExample(Example.builder(SysRequestPathDO.class).where(sqls).build());
    }

    public List<SysRequestPathDO> findAllByPermissionUuids(List<String> permissionUuids) {
        if (CollUtil.isEmpty(permissionUuids)) {
            return Collections.emptyList();
        }
        Example example = Example.builder(SysRequestPathDO.class)
                .where(Sqls.custom().andIn("permissionUuid", permissionUuids))
                .build();
        return super.dao.selectByExample(example);
    }
}
