package org.openhab.binding.gruenbeckcloud.internal.communication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link CodeChallenge} class holds the logic to create a
 * valid token to connect to the gruenbeck cloud login site. .
 *
 * @author Mario Aerni - Initial contribution
 */
public class CodeChallenge {
    private final Logger logger = LoggerFactory.getLogger(CodeChallenge.class);

    private String hash;
    private String result;

    public CodeChallenge() {
        refresh();
    }

    public void refresh() {
        logger.info("refresh code challenge");
        hash = "";
        result = "";
        char[] chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        int charLength = chars.length;
        while (Objects.equals(hash, "") || hash.contains("+") || hash.contains("/") || hash.contains("=")
                || result.contains("+") || result.contains("/")) {
            result = "";
            for (int i = 64; i > 0; --i) {
                result += chars[(int) Math.floor(Math.random() * charLength)];
            }
            result = Base64.getEncoder().encodeToString(result.getBytes());
            result = result.replaceAll("=", "");

            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance("SHA-256");
                digest.update(result.getBytes());
                hash = Base64.getEncoder().encodeToString(digest.digest());
                logger.debug("hash: {}", hash);
                logger.debug("result: {}", result);
                hash = hash.substring(0, hash.length() - 1);
                logger.debug("hash: {}", hash);

            } catch (NoSuchAlgorithmException e) {
                logger.error("algorithm not available", e);
            }
        }
    }

    public String getHash() {
        return hash;
    }

    public String getResult() {
        return result;
    }
}
