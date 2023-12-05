package org.openhab.binding.gruenbeckcloud.internal.communication.model;

import org.eclipse.jetty.client.api.ContentResponse;

/**
 * The {@link ClientSettings} class extracts the relevant data for communication with the Gruenbeck
 * Cloud out of the first client preparation call.
 *
 * @author Mario Aerni - Initial contribution
 */
public class ClientSettings {
    private String csrf;
    private String transId;
    private String policy;
    private String tenant;

    public ClientSettings(ContentResponse response) {
        processResponse(response);
    }

    private void processResponse(ContentResponse response) {
        String resultString = response.getContentAsString();

        int start;
        int end;

        // csrf
        start = resultString.indexOf("csrf") + 7;
        end = resultString.indexOf(",", start) - 1;
        csrf = resultString.substring(start, end);

        // transId
        start = resultString.indexOf("transId") + 10;
        end = resultString.indexOf(",", start) - 1;
        transId = resultString.substring(start, end);

        // policy
        start = resultString.indexOf("policy") + 9;
        end = resultString.indexOf(",", start) - 1;
        policy = resultString.substring(start, end);

        // tenant
        start = resultString.indexOf("tenant") + 9;
        end = resultString.indexOf(",", start) - 1;
        tenant = resultString.substring(start, end);
    }

    public String getCsrf() {
        return csrf;
    }

    public String getTransId() {
        return transId;
    }

    public String getPolicy() {
        return policy;
    }

    public String getTenant() {
        return tenant;
    }
}
