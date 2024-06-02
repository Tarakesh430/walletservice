package com.crypto.wallet.controller;

import com.crypto.wallet.dto.ExchangeDto;
import com.crypto.wallet.entity.Exchange;
import com.crypto.wallet.response.ApiResponse;
import com.crypto.wallet.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/exchange")
@RequiredArgsConstructor
public class ExchangeResource {
    private final Logger logger = LoggerFactory.getLogger(ExchangeResource.class);
    private final ExchangeService exchangeService;

    @GetMapping("/{exchange}")
    public ResponseEntity<ApiResponse<ExchangeDto>> getExchange(@PathVariable("exchange") String exchange) throws Exception {
        logger.info("Get Exchange Details for {}", exchange);
        return ResponseEntity.ok(ApiResponse.success("Exchange Details Retrieved",
                exchangeService.getExchangeDetails(exchange)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ExchangeDto>>> getExchanges(@RequestParam(value = "type", required = false) String type,
                                                                       @RequestParam(value = "isActive", required = false) Boolean isActive) {
        logger.info("Get filtered Exchanges");
        return ResponseEntity.ok(ApiResponse.success("Exchanges Retrieved Successfully",
                exchangeService.getExchanges(type, isActive)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ExchangeDto>> createExchange(@RequestBody ExchangeDto exchangeDto) {
        logger.info("Save Exchange {}",exchangeDto);
        return ResponseEntity.ok(ApiResponse.success("Exchanges Created Successfully",
                exchangeService.saveExchange(exchangeDto)));
    }


}
