package com.epam.learnspringsecurity.service;

import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.stream.IntStream;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service that works with unique link address
 */
@Service
public class UniqueLinkAddressService {

    private static final int MAX_STRING_SYMBOLS_NUMBER = 8;

    @Value("${hostname}")
    private String hostname;

    @Value("${scheme}")
    private String scheme;

    @Value("${secrets.path}")
    private String secretsPath;

    @Value("${port}")
    private String port;

    private static final int MAX_UPPER_BOUND = 6;

    /**
     * Gets link with unique link address identifier
     *
     * @param uniqueAddressIdentifier unique address identifier
     * @return link with unique link address identifier
     * @throws URISyntaxException
     */
    public String getAddressWithUniqueLinkAddressIdentifier(String uniqueAddressIdentifier) throws URISyntaxException {
        URIBuilder builder = new URIBuilder();
        builder.setScheme(scheme);
        builder.setHost(hostname);
        builder.setPort(Integer.parseInt(port));
        builder.setPath(secretsPath + uniqueAddressIdentifier);
        return builder.build().toString();
    }

    /**
     * Gets generated unique link address identifier
     *
     * @return generated unique link address identifier
     */
    public String generateUniqueLinkAddressIdentifier() {
        StringBuilder generatedString = new StringBuilder();
        String algorithm = new SecureRandom().getAlgorithm();
        try {
            final SecureRandom secureRandom = SecureRandom.getInstance(algorithm);
            IntStream.range(0, MAX_STRING_SYMBOLS_NUMBER).forEach(number -> generatedString.append(secureRandom.nextInt(MAX_UPPER_BOUND)));
            return generatedString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

}
