package org.openhab.binding.gruenbeckcloud.internal.communication;

import java.net.HttpCookie;

import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.FormContentProvider;
import org.eclipse.jetty.util.Fields;
import org.openhab.binding.gruenbeckcloud.internal.communication.model.ClientSettings;
import org.openhab.binding.gruenbeckcloud.internal.communication.model.Token;
import org.openhab.binding.gruenbeckcloud.internal.communication.model.WebsocketToken;

/**
 * The {@link UrlBuilder} class holds all data about the gruenbeck cloud request urls, as well as
 * the needed headers.
 *
 * @author Mario Aerni - Initial contribution
 */
public class UrlBuilder {
    private final String sdVersion = "2020-08-03";
    private final CodeChallenge codeChallenge = new CodeChallenge();

    public UrlBuilder() {
    }

    /**
     * TODO: maybe we need to call this in case of an connection exception in the {@link
     * GruenbeckConnector}
     */
    public void refreshCodeChallenge() {
        codeChallenge.refresh();
    }

    /** 1. Call */
    public String clientPreparationUrl() {
        return "https://gruenbeckb2c.b2clogin.com/a50d35c1-202f-4da7-aa87-76e51a3098c6/b2c_1a_signinup/oauth2/v2.0/authorize?"
                + "state=NjkyQjZBQTgtQkM1My00ODBDLTn3MkYtOTZCQ0QyQkQ2NEE5" + "&client_info=1" + "&x-client-Ver=0.8.0"
                + "&prompt=select_account" + "&response_type=code" + "&code_challenge_method=S256"
                + "&x-app-name=Gr%C3%BCnbeck" + "&x-client-OS=14.3" + "&x-app-ver=1.2.1"
                + "&scope=https%3A%2F%2Fgruenbeckb2c.onmicrosoft.com%2Fiot%2Fuser_impersonation+openid+profile+offline_access"
                + "&x-client-SKU=MSAL.iOS" + "&code_challenge=" + codeChallenge.getHash() + "&x-client-CPU=64"
                + "&client-request-id=F2929DED-2C9D-49F5-A0F4-31215427667C"
                + "&redirect_uri=msal5a83cc16-ffb1-42e9-9859-9fbf07f36df8%3A%2F%2Fauth"
                + "&client_id=5a83cc16-ffb1-42e9-9859-9fbf07f36df8" + "&haschrome=1" + "&return-client-request-id=true"
                + "&x-client-DM=iPhone";
    }

    /** 2. Call */
    public String loginPreparationUrl(ClientSettings clientSettings) {
        return "https://gruenbeckb2c.b2clogin.com" + clientSettings.getTenant() + "/SelfAsserted?tx="
                + clientSettings.getTransId() + "&p=" + clientSettings.getPolicy();
    }

    public void addLoginPreparationHeader(Request request, ClientSettings clientSettings) {
        request.agent(
                "Mozilla/5.0 (iPhone; CPU iPhone OS 12_4_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.1.2 Mobile/15E148 Safari/604.1");
        request.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        request.header("X-CSRF-TOKEN", clientSettings.getCsrf());
        request.header("Accept", "application/json, text/javascript, */*; q=0.01");
        request.header("X-Requested-With", "XMLHttpRequest");
        request.header("Origin", "https://gruenbeckb2c.b2clogin.com");

        String referer = "https://gruenbeckb2c.b2clogin.com/a50d35c1-202f-4da7-aa87-76e51a3098c6/b2c_1a_signinup/oauth2/v2.0/authorize?"
                + "state=NjkyQjZBQTgtQkM1My00ODBDLTn3MkYtOTZCQ0QyQkQ2NEE5" + "&client_info=1" + "&x-client-Ver=0.8.0"
                + "&prompt=select_account" + "&response_type=code" + "&code_challenge_method=S256"
                + "&x-app-name=Gr%C3%BCnbeck" + "&x-client-OS=14.3" + "&x-app-ver=1.2.1"
                + "&scope=https%3A%2F%2Fgruenbeckb2c.onmicrosoft.com%2Fiot%2Fuser_impersonation+openid+profile+offline_access"
                + "&x-client-SKU=MSAL.iOS" + "&code_challenge=" + "PkCmkmlW_KomPNfBLYqzBAHWi10TxFJSJsoYbI2bfZE"
                + "&x-client-CPU=64" + "&client-request-id=F2929DED-2C9D-49F5-A0F4-31215427667C"
                + "&redirect_uri=msal5a83cc16-ffb1-42e9-9859-9fbf07f36df8%3A%2F%2Fauth"
                + "&client_id=5a83cc16-ffb1-42e9-9859-9fbf07f36df8" + "&haschrome=1" + "&return-client-request-id=true"
                + "&x-client-DM=iPhone";

        request.header("Referer", referer);
    }

    public void addLoginPreparationFields(Request request, String email, String password) {
        Fields fields = new Fields();
        fields.add("request_type", "RESPONSE");
        fields.add("signInName", email);
        fields.add("password", password);
        request.content(new FormContentProvider(fields));
    }

    /** 3. Call */
    public String loginUrl(ClientSettings clientSettings) {
        return "https://gruenbeckb2c.b2clogin.com" + clientSettings.getTenant()
                + "/api/CombinedSigninAndSignup/confirmed?csrf_token=" + clientSettings.getCsrf() + "&tx="
                + clientSettings.getTransId() + "&p=" + clientSettings.getPolicy();
    }

    public void addLoginHeader(Request request, ClientSettings clientSettings) {
        request.cookie(new HttpCookie("x-ms-cpim-csrf", clientSettings.getCsrf()));
        request.agent(
                "Mozilla/5.0 (iPhone; CPU iPhone OS 12_4_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.1.2 Mobile/15E148 Safari/604.1");
        request.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    }

    /** 4. Call */
    public String initialTokenUrl(ClientSettings clientSettings) {
        return "https://gruenbeckb2c.b2clogin.com" + clientSettings.getTenant() + "/oauth2/v2.0/token";
    }

    public void addInitialTokenFields(Request request, String code) {
        Fields fields = new Fields();
        fields.add("client_info", "1");
        fields.add("scope",
                "https://gruenbeckb2c.onmicrosoft.com/iot/user_impersonation openid profile offline_access");
        fields.add("code", code);
        fields.add("grant_type", "authorization_code");
        fields.add("code_verifier", codeChallenge.getResult());
        fields.add("redirect_uri", "msal5a83cc16-ffb1-42e9-9859-9fbf07f36df8://auth");
        fields.add("client_id", "5a83cc16-ffb1-42e9-9859-9fbf07f36df8");
        request.content(new FormContentProvider(fields));
    }

    /** 5. Call */
    public String refreshTokenUrl(ClientSettings clientSettings) {
        return "https://gruenbeckb2c.b2clogin.com" + clientSettings.getTenant() + "/oauth2/v2.0/token";
    }

    public void addRefreshTokenFields(Request request, Token token) {
        Fields fields = new Fields();
        fields.add("client_info", "1");
        fields.add("scope",
                "https://gruenbeckb2c.onmicrosoft.com/iot/user_impersonation openid profile offline_access");
        fields.add("grant_type", "refresh_token");
        fields.add("refresh_token", token.getRefreshToken());
        fields.add("client_id", "5a83cc16-ffb1-42e9-9859-9fbf07f36df8");
        request.content(new FormContentProvider(fields));
    }

    public void addTokenHeaders(Request request) {
        request.agent("Gruenbeck/354 CFNetwork/1209 Darwin/20.2.0");
        request.header("Host", "gruenbeckb2c.b2clogin.com");
        request.header("x-client-SKU", "MSAL.iOS");
        request.header("Accept", "application/json");
        request.header("x-client-OS", "14.3");
        request.header("x-app-name", "Grünbeck");
        request.header("x-client-CPU", "64");
        request.header("x-app-ver", "1.2.0");
        request.header("Accept-Language", "de-de");
        request.header("Accept-Encoding", "br, gzip, deflate");
        request.header("client-request-id", "F2929DED-2C9D-49F5-A0F4-31215427667C");
        request.header("x-client-Ver", "0.8.0");
        request.header("x-client-DM", "iPhone");
        request.header("return-client-request-id", "true");
        request.header("cache-control", "no-cache");
        request.header("Connection", "keep-alive");
        request.header("x-ms-PkeyAuth", "1.0");
        request.header("Content-Type", "application/x-www-form-urlencoded");
    }

    /* retrieve device list from cloud */
    public String deviceListUrl() {
        return "https://prod-eu-gruenbeck-api.azurewebsites.net/api/devices?api-version=" + sdVersion;
    }

    /** retrieve additional information about the device */
    public String deviceInformationUrl(String deviceId) {
        return deviceDataUrlBase(deviceId, "parameters");
    }

    /** retrieve statistics from the device */
    public String deviceStatisticsUrl(String deviceId) {
        return deviceDataUrlBase(deviceId, null);
    }

    /** base url for device information and statistics */
    private String deviceDataUrlBase(String deviceId, String endpoint) {
        return "https://prod-eu-gruenbeck-api.azurewebsites.net/api/devices/" + deviceId
                + (endpoint != null ? "/" + endpoint : "") + "/?api-version=" + sdVersion;
    }

    public void addDeviceHeader(Request request, Token token) {
        request.agent("Gruenbeck/354 CFNetwork/1209 Darwin/20.2.0");
        request.header("Host", "prod-eu-gruenbeck-api.azurewebsites.net");
        request.header("Accept", "application/json, text/plain, */*");
        request.header("Authorization", "Bearer " + token.getAccessToken());
        request.header("Accept-Language", "de-de");
        request.header("cache-control", "no-cache");
    }

    /** opens the websocket connection to gruenbeck cloud */
    public String websocketNegotiationUrl() {
        return "https://prod-eu-gruenbeck-api.azurewebsites.net/api/realtime/negotiate";
    }

    public void addWebsocketNegotiationHeader(Request request, Token token) {
        request.agent("Gruenbeck/354 CFNetwork/1209 Darwin/20.2.0");
        request.header("Content-Type", "text/plain;charset=UTF-8");
        request.header("Origin", "file://");
        request.header("Accept", "*/*");
        request.header("Accept-Language", "de-de");
        request.header("Authorization", "Bearer " + token.getAccessToken());
        request.header("cache-control", "no-cache");
        request.header("X-Requested-With", "XMLHttpRequest");
    }

    /** returns the websocket id url */
    public String getWebsocketIdUrl() {
        return "https://prod-eu-gruenbeck-signalr.service.signalr.net/client/negotiate?hub=gruenbeck";
    }

    public void addWebsocketIdHeader(Request request, WebsocketToken websocketToken) {
        request.agent("Gruenbeck/349 CFNetwork/1197 Darwin/20.0.0");
        request.header("content-Type", "text/plain;charset=UTF-8");
        request.header("accept", "*/*");
        request.header("Accept-Language", "de-de");
        request.header("Authorization", "Bearer " + websocketToken.getAccessToken());
        request.header("X-Requested-With", "XMLHttpRequest");
        request.header("Upgrade", "websocket");
        request.header("Host", "prod-eu-gruenbeck-signalr.service.signalr.net");
    }

    public String websocketUrl(String connectionId, WebsocketToken websocketToken) {
        return "wss://prod-eu-gruenbeck-signalr.service.signalr.net/client/?hub=gruenbeck&id=" + connectionId
                + "&access_token=" + websocketToken.getAccessToken();
    }

    public String enterRealtimeUrl(String deviceId) {
        return "https://prod-eu-gruenbeck-api.azurewebsites.net/api/devices/" + deviceId
                + "/realtime/enter?api-version=" + sdVersion;
    }

    public String refreshRealtimeUrl(String deviceId) {
        return "https://prod-eu-gruenbeck-api.azurewebsites.net/api/devices/" + deviceId
                + "/realtime/refresh?api-version=" + sdVersion;
    }

    public String leaveRealtimeUrl(String deviceId) {
        return "https://prod-eu-gruenbeck-api.azurewebsites.net/api/devices/" + deviceId
                + "/realtime/leave?api-version=" + sdVersion;
    }

    public void addRealtimeHeader(Request request, Token token) {
        request.agent("Gruenbeck/354 CFNetwork/1209 Darwin/20.2.0");
        request.header("Host", "prod-eu-gruenbeck-api.azurewebsites.net");
        request.header("Accept", "application/json, text/plain, */*");
        request.header("Accept-Language", "de-de");
        request.header("Authorization", "Bearer " + token.getAccessToken());
    }

    public String pushParameterUrl(String deviceId) {
        return "https://prod-eu-gruenbeck-api.azurewebsites.net/api/devices/" + deviceId + "/parameters?api-version="
                + sdVersion;
    }

    public String requestRegenerationUrl(String deviceId) {
        return "https://prod-eu-gruenbeck-api.azurewebsites.net/api/devices/" + deviceId + "/regenerate?api-version="
                + sdVersion;
    }

    public void addPushParameterHeader(Request request, Token token) {
        addRealtimeHeader(request, token);
        request.header("Content-Type", "application/json");
    }
}
