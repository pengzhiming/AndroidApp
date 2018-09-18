package com.yl.merchat.component.net;

import android.text.TextUtils;

import com.yl.merchat.YLApplication;

import java.io.IOException;
import java.io.InputStream;

/**
 * 证书provider
 * Created by zm on 2018/9/9.
 */

public class CertificateProvider {

    /**
     * 根据URL的地址来判断证书的加载
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static InputStream[] getCertificateStreams(String url) throws IOException {
        String cer_name = null;
        if (url.startsWith("")) {
            cer_name = "";
        }
        if (!TextUtils.isEmpty(cer_name)) {
            return new InputStream[]{YLApplication.getContext().getAssets().open(cer_name)};
        } else {
            return null;
        }
    }
}
