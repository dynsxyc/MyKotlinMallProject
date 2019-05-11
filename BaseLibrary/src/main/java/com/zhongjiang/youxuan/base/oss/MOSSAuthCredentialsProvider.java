package com.zhongjiang.youxuan.base.oss;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.common.OSSConstants;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.common.utils.IOUtils;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jingdan on 2017/11/15.
 * Authentication server issued under the agreement of the official website agreement, you can directly use the provider
 */

public class MOSSAuthCredentialsProvider extends OSSFederationCredentialProvider {

    private String mAuthServerUrl;
    private AuthDecoder mDecoder;

    public MOSSAuthCredentialsProvider(String authServerUrl) {
        this.mAuthServerUrl = authServerUrl;
    }

    /**
     * set auth server url
     *
     * @param authServerUrl
     */
    public void setAuthServerUrl(String authServerUrl) {
        this.mAuthServerUrl = authServerUrl;
    }

    /**
     * set response data decoder
     *
     * @param decoder
     */
    public void setDecoder(AuthDecoder decoder) {
        this.mDecoder = decoder;
    }

    @Override
    public OSSFederationToken getFederationToken() throws ClientException {
        OSSFederationToken authToken;
        String authData;
        try {
            URL stsUrl = new URL(mAuthServerUrl);
            HttpURLConnection conn = (HttpURLConnection) stsUrl.openConnection();
            conn.setConnectTimeout(10000);
            InputStream input = conn.getInputStream();
            authData = IOUtils.readStreamAsString(input, OSSConstants.DEFAULT_CHARSET_NAME);
            if (mDecoder != null) {
                authData = mDecoder.decode(authData);
            }
            JSONObject jsonObj = new JSONObject(authData);
            int statusCode = jsonObj.getInt("status");
            if (statusCode == 1) {
                JSONObject dataObj = jsonObj.getJSONObject("data");
                String ak = dataObj.getString("AccessKeyId");
                String sk = dataObj.getString("AccessKeySecret");
                String token = dataObj.getString("SecurityToken");
                String expiration = dataObj.getString("Expiration");
                authToken = new OSSFederationToken(ak, sk, token, expiration);
            } else {
                String errorCode = jsonObj.getString("status");
                String errorMessage = jsonObj.getString("showMessage");
                throw new ClientException("ErrorCode: " + errorCode + "| ErrorMessage: " + errorMessage);
            }
            return authToken;
        } catch (Exception e) {
            throw new ClientException(e);
        }
    }

    public interface AuthDecoder {
        String decode(String data);
    }
}
