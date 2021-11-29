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
package tech.ordinaryroad.gateway.service;

import tech.ordinaryroad.commons.core.constant.CacheConstants;

import javax.validation.constraints.NotNull;

/**
 * @author mjz
 * @date 2021/11/27
 */
public interface ICaptchaService {

    void checkValid(@NotNull String key, @NotNull String code);

    /**
     * 生成登录base64图片验证码
     *
     * @param orNumber OR账号，key的一部分 {@link CacheConstants#CAPTCHA_LOGIN_KEY}
     * @return base64格式的图片
     */
    String generateLoginCaptcha(@NotNull String orNumber);

    /**
     * 校验登录验证码
     *
     * @param orNumber OR账号，key的一部分 {@link CacheConstants#CAPTCHA_LOGIN_KEY}
     * @param code     用户输入的验证码
     */
    void checkLoginCaptcha(@NotNull String orNumber, @NotNull String code);

    /**
     * 生成注册纯文字验证码
     *
     * @param email 邮箱，key的一部分 {@link CacheConstants#CAPTCHA_REGISTER_KEY}
     * @return code 生成的验证码
     */
    String generateRegisterCaptcha(@NotNull String email);

    /**
     * 校验注册验证码
     *
     * @param email 邮箱，key的一部分 {@link CacheConstants#CAPTCHA_REGISTER_KEY}
     * @param code  用户输入的验证码
     */
    void checkRegisterCaptcha(@NotNull String email, @NotNull String code);

}
