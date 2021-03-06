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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.api.ISysFileApi;
import tech.ordinaryroad.upms.dto.SysFileDTO;
import tech.ordinaryroad.upms.facade.ISysFileFacade;
import tech.ordinaryroad.upms.request.SysFileQueryRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mjz
 * @date 2022/1/11
 */
@RequiredArgsConstructor
@RestController
public class SysFileController implements ISysFileApi {

    private final ISysFileFacade sysFileFacade;

    @Override
    public Result<String> upload(@RequestParam String clientId, @RequestParam String clientSecret, @RequestPart MultipartFile file) {
        return sysFileFacade.upload(clientId, clientSecret, file);
    }

    @Override
    public void download(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) Boolean showInline) {
        sysFileFacade.download(request, response, showInline);
    }

    @Override
    public Result<PageInfo<SysFileDTO>> list(@RequestBody SysFileQueryRequest request) {
        return sysFileFacade.list(request);
    }

}
