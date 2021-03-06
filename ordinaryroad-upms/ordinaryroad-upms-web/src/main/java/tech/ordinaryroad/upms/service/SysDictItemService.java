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
import org.springframework.stereotype.Service;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.mybatis.service.BaseService;
import tech.ordinaryroad.upms.dao.SysDictItemDAO;
import tech.ordinaryroad.upms.entity.SysDictItemDO;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author mjz
 * @date 2022/1/5
 */
@Service
public class SysDictItemService extends BaseService<SysDictItemDAO, SysDictItemDO> {

    public List<SysDictItemDO> findAll(SysDictItemDO sysDictItemDO, BaseQueryRequest baseQueryRequest) {
        WeekendSqls<SysDictItemDO> sqls = WeekendSqls.custom();

        String dictUuid = sysDictItemDO.getDictUuid();
        if (StrUtil.isNotBlank(dictUuid)) {
            sqls.andEqualTo(SysDictItemDO::getDictUuid, dictUuid);
        }
        String label = sysDictItemDO.getLabel();
        if (StrUtil.isNotBlank(label)) {
            sqls.andLike(SysDictItemDO::getLabel, "%" + label + "%");
        }
        String value = sysDictItemDO.getValue();
        if (StrUtil.isNotBlank(value)) {
            sqls.andLike(SysDictItemDO::getValue, "%" + value + "%");
        }
        String remark = sysDictItemDO.getRemark();
        if (StrUtil.isNotBlank(remark)) {
            sqls.andLike(SysDictItemDO::getRemark, "%" + value + "%");
        }

        Example.Builder exampleBuilder = Example.builder(SysDictItemDO.class).where(sqls);

        return super.findAll(baseQueryRequest, sqls, exampleBuilder);
    }

    public List<SysDictItemDO> findAllByDictUuid(String dictUuid) {
        if (StrUtil.isBlank(dictUuid)) {
            return Collections.emptyList();
        }
        Example example = Example.builder(SysDictItemDO.class)
                .where(Sqls.custom().andEqualTo("dictUuid", dictUuid))
                .build();
        return super.dao.selectByExample(example);
    }

    public Optional<SysDictItemDO> findByDictUuidAndLabel(String dictUuid, String label) {
        if (StrUtil.isBlank(dictUuid) || StrUtil.isBlank(label)) {
            return Optional.empty();
        }

        Sqls custom = Sqls.custom().andEqualTo("dictUuid", dictUuid)
                .andEqualTo("label", label);

        Example example = Example.builder(SysDictItemDO.class).where(custom).build();
        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public Optional<SysDictItemDO> findByDictUuidAndValue(String dictUuid, String value) {
        if (StrUtil.isBlank(dictUuid) || StrUtil.isBlank(value)) {
            return Optional.empty();
        }

        Sqls custom = Sqls.custom().andEqualTo("dictUuid", dictUuid)
                .andEqualTo("value", value);

        Example example = Example.builder(SysDictItemDO.class).where(custom).build();
        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public Optional<SysDictItemDO> findByDictIdAndId(SysDictItemDO sysDictItemDO) {
        final String dictUuid = sysDictItemDO.getDictUuid();
        if (StrUtil.isBlank(dictUuid)) {
            return Optional.empty();
        }
        WeekendSqls<SysDictItemDO> sqlsAnd = WeekendSqls.custom();
        sqlsAnd.andEqualTo(SysDictItemDO::getDictUuid, dictUuid);

        WeekendSqls<SysDictItemDO> sqlsOr = WeekendSqls.custom();
        final String uuid = sysDictItemDO.getUuid();
        if (StrUtil.isNotBlank(uuid)) {
            sqlsOr.orEqualTo(SysDictItemDO::getUuid, uuid);
        }
        final String label = sysDictItemDO.getLabel();
        if (StrUtil.isNotBlank(label)) {
            sqlsOr.orEqualTo(SysDictItemDO::getLabel, label);
        }
        final String value = sysDictItemDO.getValue();
        if (StrUtil.isNotBlank(value)) {
            sqlsOr.orEqualTo(SysDictItemDO::getValue, value);
        }

        return Optional.ofNullable(super.dao.selectOneByExample(Example.builder(SysDictItemDO.class).where(sqlsAnd).andWhere(sqlsOr).build()));
    }
}
