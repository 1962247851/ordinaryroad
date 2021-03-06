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
package tech.ordinaryroad.upms.web.controller;

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.api.ISysDictItemApi;
import tech.ordinaryroad.upms.dto.SysDictItemDTO;
import tech.ordinaryroad.upms.facade.ISysDictItemFacade;
import tech.ordinaryroad.upms.request.SysDictItemQueryRequest;
import tech.ordinaryroad.upms.request.SysDictItemSaveRequest;

import java.util.List;

/**
 * @author mjz
 * @date 2022/1/5
 */
@RequiredArgsConstructor
@RestController
public class SysDictItemController implements ISysDictItemApi {

    private final ISysDictItemFacade sysDictItemFacade;

    @Override
    public Result<SysDictItemDTO> create(@RequestBody @Validated SysDictItemSaveRequest request) {
        return sysDictItemFacade.create(request);
    }

    @Override
    public Result<Boolean> delete(@RequestBody @Validated BaseDeleteRequest request) {
        return sysDictItemFacade.delete(request);
    }

    @Override
    public Result<SysDictItemDTO> update(@RequestBody @Validated SysDictItemSaveRequest request) {
        return sysDictItemFacade.update(request);
    }

    @Override
    public Result<SysDictItemDTO> findById(@RequestBody SysDictItemQueryRequest request) {
        return sysDictItemFacade.findById(request);
    }

    @Override
    public Result<SysDictItemDTO> detail(@RequestBody SysDictItemQueryRequest request) {
        return sysDictItemFacade.detail(request);
    }

    @Override
    public Result<List<SysDictItemDTO>> findAll(@RequestBody SysDictItemQueryRequest request) {
        return sysDictItemFacade.findAll(request);
    }

    @Override
    public Result<List<SysDictItemDTO>> findAllByForeignColumn(@RequestBody SysDictItemQueryRequest request) {
        return sysDictItemFacade.findAllByForeignColumn(request);
    }

    @Override
    public Result<PageInfo<SysDictItemDTO>> list(@RequestBody SysDictItemQueryRequest request) {
        return sysDictItemFacade.list(request);
    }
}
