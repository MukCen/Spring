package ua.com.owu.autoria_springboot.services;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import java.io.*;
import java.math.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

@Service
public class PrivatBankCurrencyService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public PrivatBankCurrencyService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
//   2    PrivatBankCurrencyService----Start
    public BigDecimal getUsdExchangeRate() {
        String url = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
        String responseBody = restTemplate.getForObject(url, String.class);

        // Парсинг JSON-відповіді та отримання курсу Долара
        BigDecimal usdRate = parseUsdExchangeRate(responseBody);

        return usdRate;
    }

    public BigDecimal getEurExchangeRate() {
        String url = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
        String responseBody = restTemplate.getForObject(url, String.class);

        // Парсинг JSON-відповіді та отримання курсу Євро
        BigDecimal eurRate = parseEurExchangeRate(responseBody);

        return eurRate;
    }

    private BigDecimal parseUsdExchangeRate(String responseBody) {
        // Парсинг JSON-відповіді та отримання курсу Долара
        try {
            JsonFactory jsonFactory = objectMapper.getFactory();
            JsonParser jsonParser = jsonFactory.createParser(responseBody);
            List<Map<String, Object>> responseList = objectMapper.readValue(jsonParser, List.class);

            Map<String, Object> usdCurrency = responseList.stream()
                    .filter(currency -> currency.get("ccy").equals("USD"))
                    .findFirst()
                    .orElse(null);

            if (usdCurrency != null) {
                return new BigDecimal(usdCurrency.get("buy").toString());
            } else {
                throw new RuntimeException("Unable to parse USD exchange rate");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON response", e);
        }
    }

    private BigDecimal parseEurExchangeRate(String responseBody) {
        // Парсинг JSON-відповіді та отримання курсу Євро
        try {
            JsonFactory jsonFactory = objectMapper.getFactory();
            JsonParser jsonParser = jsonFactory.createParser(responseBody);
            List<Map<String, Object>> responseList = objectMapper.readValue(jsonParser, List.class);

            Map<String, Object> eurCurrency = responseList.stream()
                    .filter(currency -> currency.get("ccy").equals("EUR"))
                    .findFirst()
                    .orElse(null);

            if (eurCurrency != null) {
                return new BigDecimal(eurCurrency.get("buy").toString());
            } else {
                throw new RuntimeException("Unable to parse EUR exchange rate");
            }
        } catch (IOException e
) {
throw new RuntimeException("Failed to parse JSON response", e);
}
}
    //   2    PrivatBankCurrencyService----End
}