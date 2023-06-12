/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ua.com.owu.autoria_springboot.services;

import java.math.*;
import java.util.*;
import org.slf4j.*;
import org.springframework.stereotype.*;
import ua.com.owu.autoria_springboot.dao.*;
import ua.com.owu.autoria_springboot.models.*;
/**
 *
 * @author HP
 */
@Service

public class CurrencyService {
    private final CurrencyRateRepository currencyRateRepository;
    private final PrivatBankCurrencyService privatBankCurrencyService;
    private  final AdRepository adRepository;
    private static final Logger logger = LoggerFactory.getLogger(CurrencyService.class);

    public CurrencyService(CurrencyRateRepository currencyRateRepository, PrivatBankCurrencyService privatBankCurrencyService, AdRepository adRepository) {
        this.currencyRateRepository = currencyRateRepository;
        this.privatBankCurrencyService = privatBankCurrencyService;
        this.adRepository = adRepository;
    }

  

    public CurrencyRate getCurrencyRatesFromPrivatBank() {
        BigDecimal usdRate = privatBankCurrencyService.getUsdExchangeRate();
        BigDecimal eurRate = privatBankCurrencyService.getEurExchangeRate();

        CurrencyRate currencyRate = currencyRateRepository.findByCurrencyCode("USD");
        if (currencyRate != null) {
            currencyRate.setExchangeRate(usdRate);
        } else {
            CurrencyRate newCurrencyRate = new CurrencyRate("USD", "Dollar", usdRate);
            currencyRateRepository.save(newCurrencyRate);
        }
            currencyRateRepository.save(currencyRate);

        currencyRate = currencyRateRepository.findByCurrencyCode("EUR");
        if (currencyRate != null) {
            currencyRate.setExchangeRate(eurRate);
        } else {
            CurrencyRate newCurrencyRate = new CurrencyRate("EUR", "Euro", eurRate);
            currencyRateRepository.save(newCurrencyRate);
        }
            currencyRateRepository.save(currencyRate);

        return currencyRate;
    }

    public CurrencyRate getCurrencyRatesFromDatabase() {
        CurrencyRate currencyRate = currencyRateRepository.findTopByOrderByTimestampDesc();
        return currencyRate;
    }
    public CurrencyRate getCurrencyRateByCurrencyCode(String currencyCode) {
    // Отримати об'єкт CurrencyRate з бази даних за заданим кодом валюти
    CurrencyRate currencyRate = currencyRateRepository.findByCurrencyCode(currencyCode);
    return currencyRate;
}

public void updateCurrencyRates() {
    try {
        // Отримати актуальний курс валют з ПриватБанку
        BigDecimal usdRate = privatBankCurrencyService.getUsdExchangeRate();
        BigDecimal eurRate = privatBankCurrencyService.getEurExchangeRate();

        logger.info("usdRate: " + usdRate);
        logger.info("eurRate: " + eurRate);

        // Оновити курс валюти USD в базі даних
        CurrencyRate usdCurrencyRate = currencyRateRepository.findByCurrencyCode("USD");
        if (usdCurrencyRate != null) {
            usdCurrencyRate.setExchangeRate(usdRate);
        } else {
            usdCurrencyRate = new CurrencyRate("USD", "Dollar", usdRate);
        }
        currencyRateRepository.save(usdCurrencyRate);

        logger.info("usdCurrencyRate: " + usdCurrencyRate);

        // Оновити курс валюти EUR в базі даних
        CurrencyRate eurCurrencyRate = currencyRateRepository.findByCurrencyCode("EUR");
        if (eurCurrencyRate != null) {
            eurCurrencyRate.setExchangeRate(eurRate);
        } else {
            eurCurrencyRate = new CurrencyRate("EUR", "Euro", eurRate);
        }
        currencyRateRepository.save(eurCurrencyRate);

        logger.info("eurCurrencyRate: " + eurCurrencyRate);

        // Отримати всі оголошення
        List<Ad> ads = adRepository.findAll();

        logger.info("ads: " + ads);

        // Оновити ціни оголошень у відповідних валютах
        for (Ad ad : ads) {
            BigDecimal priceUah = ad.getPriceUah();

            if (priceUah != null && usdCurrencyRate != null) {
                BigDecimal priceUsd = priceUah.multiply(usdCurrencyRate.getExchangeRate());
                ad.setPriceUsd(priceUsd);
            }

            if (priceUah != null && eurCurrencyRate != null) {
                BigDecimal priceEur = priceUah.multiply(eurCurrencyRate.getExchangeRate());
                ad.setPriceEur(priceEur);
            }

            adRepository.save(ad);
        }
    } catch (Exception e) {
        // Обробка винятків та логування помилок
        System.out.println("Сталася помилка при оновленні курсів валют: " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Помилка при оновленні курсів валют");
    }
}
}

//    public void updateCurrencyRates() {
//        // Отримати актуальний курс валют з ПриватБанку
//        BigDecimal usdRate = privatBankCurrencyService.getUsdExchangeRate();
//        BigDecimal eurRate = privatBankCurrencyService.getEurExchangeRate();
//
//        logger.info("usdRate: " + usdRate);
//        logger.info("eurRate: " + eurRate);
//
//        // Оновити курс валюти USD в базі даних
//        CurrencyRate usdCurrencyRate = currencyRateRepository.findByCurrencyCode("USD");
//        if (usdCurrencyRate != null) {
//            usdCurrencyRate.setExchangeRate(usdRate);
//        } else {
//            usdCurrencyRate = new CurrencyRate("USD", "Dollar", usdRate);
//        }
//        currencyRateRepository.save(usdCurrencyRate);
//
//        logger.info("usdCurrencyRate: " + usdCurrencyRate);
//
//        // Оновити курс валюти EUR в базі даних
//        CurrencyRate eurCurrencyRate = currencyRateRepository.findByCurrencyCode("EUR");
//        if (eurCurrencyRate != null) {
//            eurCurrencyRate.setExchangeRate(eurRate);
//        } else {
//            eurCurrencyRate = new CurrencyRate("EUR", "Euro", eurRate);
//        }
//        currencyRateRepository.save(eurCurrencyRate);
//
//        logger.info("eurCurrencyRate: " + eurCurrencyRate);
//
//        // Отримати всі оголошення
//        List<Ad> ads = adRepository.findAll();
//
//        logger.info("ads: " + ads);
//
//        // Оновити ціни оголошень у відповідних валютах
//        for (Ad ad : ads) {
//            BigDecimal priceUah = ad.getPriceUah();
//
//            if (usdCurrencyRate != null) {
//                BigDecimal priceUsd = priceUah.multiply(usdCurrencyRate.getExchangeRate());
//                ad.setPriceUsd(priceUsd);
//            }
//
//            if (eurCurrencyRate != null) {
//                BigDecimal priceEur = priceUah.multiply(eurCurrencyRate.getExchangeRate());
//                ad.setPriceEur(priceEur);
//            }
//
//            adRepository.save(ad);
//        }
//    }



//     public void updateCurrencyRates() {
////    CurrencyRate currencyRate = getCurrencyRatesFromPrivatBank();
//CurrencyRate currentCurrencyRate = getCurrencyRatesFromPrivatBank();
//
//    // Оновлення цін оголошень у відповідних валютах
//    List<Ad> ads = adRepository.findAll();
//    for (Ad ad : ads) {
//        BigDecimal priceUah = ad.getPriceUah();
////        CurrencyRate currencyRate = getCurrencyRateByCurrencyCode(ad.getCurrencyCode());
//    CurrencyRate currencyRate = currentCurrencyRate;
//
//        if (currencyRate != null) {
//            BigDecimal priceInCurrency = priceUah.multiply(currencyRate.getExchangeRate());
//            ad.setPriceInCurrency(priceInCurrency);
//            adRepository.save(ad);
//        }
//
//
//    }
//     }
     
     
