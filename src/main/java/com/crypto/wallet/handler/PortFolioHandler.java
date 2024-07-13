package com.crypto.wallet.handler;

import com.common.library.response.cryptotradeapi.PortFolioResponse;
import com.crypto.wallet.entity.ValidationKey;
import com.crypto.wallet.utils.CommonConstants;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClient;

import java.text.MessageFormat;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PortFolioHandler {
    private static final Logger logger = LoggerFactory.getLogger(PortFolioHandler.class);
    private final RestClient restClient;
    private static final String TARGET_URL = "/v1/cryptotrade/portfolio?exchange={0}";
    @Value("${crypto.trade.api.baseUrl}")
    private String baseUrl;
    public List<PortFolioResponse> process(ValidationKey validationKey, String exchangeName) throws Exception {
        logger.info("Get Port Folio Details for the exchangeName {}",exchangeName);
        List<PortFolioResponse> response = null;
        try{
             response = restClient.get().uri(getTargetUrl(exchangeName))
                    .header(CommonConstants.API_KEY_HEADER, validationKey.getApiKey())
                    .header(CommonConstants.SECRET_KEY_HEADER, validationKey.getSecretKey())
                    .retrieve().body(new ParameterizedTypeReference<>() {
                    });
            logger.info("Successfully Retrieved PortFolio Details for the exchange Name {}",exchangeName);
        }catch(Exception ex){
            logger.error("Exception in getting the Port Folio Details",ex);
            throw new Exception("Exception in getting the Port Folio Details");
        }
        if (CollectionUtils.isEmpty(response) ) {
            logger.info("Invalid Response while getting the Order Details");
            throw new Exception("Invalid Response while getting the Order Details");
        }
        return response;
    }
    
    private String getTargetUrl(String exchangeName){
        return baseUrl.concat(MessageFormat.format(TARGET_URL,exchangeName));
    }

}
