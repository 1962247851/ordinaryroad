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
package tech.ordinaryroad.ioe.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.ioe.api.api.IIoEDeviceApi;
import tech.ordinaryroad.ioe.api.dto.IoEDeviceDTO;
import tech.ordinaryroad.ioe.api.request.IoEDeviceDeleteRequest;
import tech.ordinaryroad.ioe.api.request.IoEDeviceSaveRequest;
import tech.ordinaryroad.ioe.facade.IIoEDeviceFacade;

/**
 * @author mjz
 * @date 2022/3/27
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class IoEDeviceController implements IIoEDeviceApi {

    private final IIoEDeviceFacade deviceFacade;

    @Override
    public Result<IoEDeviceDTO> create(@RequestBody @Validated IoEDeviceSaveRequest request) {
        return deviceFacade.create(request);
    }

    @Override
    public Result<IoEDeviceDTO> delete(@RequestBody @Validated IoEDeviceDeleteRequest request) {
        return deviceFacade.delete(request);
    }

}