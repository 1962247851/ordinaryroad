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
package tech.ordinaryroad.push.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.push.api.IPushApi;
import tech.ordinaryroad.push.facade.impl.AndroidPushFacadeImpl;
import tech.ordinaryroad.push.facade.impl.EmailPushFacadeImpl;
import tech.ordinaryroad.push.request.AndroidPushRequest;
import tech.ordinaryroad.push.request.EmailPushRequest;

/**
 * @author mjz
 * @date 2021/11/27
 */
@RequiredArgsConstructor
@RestController
public class PushController implements IPushApi {

    private final EmailPushFacadeImpl emailPushFacade;
    private final AndroidPushFacadeImpl androidPushFacade;

    @Override
    public Result<?> email(@RequestBody @Validated EmailPushRequest request) {
        return emailPushFacade.send(request);
    }

    @Override
    public Result<?> android(@RequestBody @Validated AndroidPushRequest request) {
        return androidPushFacade.send(request);
    }

}
