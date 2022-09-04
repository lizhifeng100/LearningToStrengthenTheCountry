package com.lizhi.service.impl;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lizhi.common.UrlConstants;
import com.lizhi.repository.UserRepository;
import com.lizhi.send.Send;
import com.lizhi.service.Login;
import com.lizhi.service.UrlRpc;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: 荔枝
 * @date: 2022/9/3 16 37
 * @description:
 */
@Service
public class LoginImpl implements Login {

    private static final Logger logger = LoggerFactory.getLogger(LoginImpl.class);

    @Resource
    private UrlRpc urlRpc;

    @Resource
    private UserRepository userRepository;

    @Override
    public void login(String key) {
        logger.info("登陆开始====================================");
        //1、获取二维码
        String sign = urlRpc.getSign();
        String qrData = urlRpc.getQrData();
        String loginUrl = StringUtils.replace(UrlConstants.CODE_LINK, "{qr_data}", qrData);
        QrConfig config = new QrConfig();
        config.setErrorCorrection(ErrorCorrectionLevel.H).setWidth(300).setHeight(300);
        QrCodeUtil.generate(loginUrl, config, FileUtil.file("d:/qrcode.jpg"));
        String imageBase64 = QrCodeUtil.generateAsBase64(loginUrl, config, ImgUtil.IMAGE_TYPE_PNG);
        userRepository.saveUser(sign, qrData, imageBase64);
        // sendMessage.send(imageBase64, key);

        String secret = urlRpc.confirmLogin(qrData);
        if (StringUtils.isNotEmpty(secret)) {
            //登陆成功，然后通过检测
            Boolean aBoolean = urlRpc.secureCheck(sign, secret);
            if (Boolean.TRUE.equals(aBoolean)) {
                logger.info("安全测试成功登陆成功====================================");
                return;
            }
        }
        logger.info("登陆失败====================================");
    }


}
