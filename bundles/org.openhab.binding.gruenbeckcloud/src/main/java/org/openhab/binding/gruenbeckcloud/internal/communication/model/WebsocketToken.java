package org.openhab.binding.gruenbeckcloud.internal.communication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The {@link WebsocketToken} class holds the websocket access token.
 *
 * @author Mario Aerni - Initial contribution
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebsocketToken {
    private String url;
    private String accessToken;

    public WebsocketToken() {
    }

    public WebsocketToken(String url, String accessToken) {
        this.url = url;
        this.accessToken = accessToken;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
