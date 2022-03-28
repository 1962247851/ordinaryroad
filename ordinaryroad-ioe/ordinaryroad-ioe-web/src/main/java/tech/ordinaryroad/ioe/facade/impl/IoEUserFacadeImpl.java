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
package tech.ordinaryroad.ioe.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.ordinaryroad.commons.core.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.ioe.api.dto.IoEUserDTO;
import tech.ordinaryroad.ioe.api.request.IoEUserQueryRequest;
import tech.ordinaryroad.ioe.api.request.IoEUserSaveRequest;
import tech.ordinaryroad.ioe.entity.IoEUserDO;
import tech.ordinaryroad.ioe.facade.IIoEUserFacade;
import tech.ordinaryroad.ioe.mapstruct.IoEUserMapStruct;
import tech.ordinaryroad.ioe.service.IoEUserService;

import java.util.Optional;

/**
 * @author mjz
 * @date 2022/3/25
 */
@RequiredArgsConstructor
@Component
public class IoEUserFacadeImpl implements IIoEUserFacade {

    private final IoEUserService ioEUserService;
    private final IoEUserMapStruct objMapStruct;

    @Override
    public Result<IoEUserDTO> create(IoEUserSaveRequest request) {
        // 唯一性校验
        IoEUserDO ioEUserDO = objMapStruct.transfer(request);
        if (ioEUserService.findByUniqueColumn(ioEUserDO).isPresent()) {
            return Result.fail(StatusCode.DATA_ALREADY_EXIST);
        }

        return Result.success(objMapStruct.transfer(ioEUserService.createSelective(ioEUserDO)));
    }

    @Override
    public Result<IoEUserDTO> findByUniqueColumn(IoEUserQueryRequest request) {
        final Optional<IoEUserDO> optional = ioEUserService.findByUniqueColumn(objMapStruct.transfer(request));
        return optional.map(eUserDO -> Result.success(objMapStruct.transfer(eUserDO))).orElseGet(() -> Result.fail(StatusCode.DATA_NOT_EXIST));
    }

}