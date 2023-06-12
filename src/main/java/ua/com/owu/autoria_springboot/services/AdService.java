package ua.com.owu.autoria_springboot.services;


import java.math.*;
import java.time.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import ua.com.owu.autoria_springboot.dao.*;
import ua.com.owu.autoria_springboot.models.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
@Service
//@AllArgsConstructor
public class AdService {
    private AdRepository adRepository;
//    private ua.com.owu.autoria_springboot.dao.AdViewRepository adViewRepository;
    private CurrencyService currencyService;
    private PrivatBankCurrencyService privatBankCurrencyService;
     @Autowired
    public AdService(AdRepository adRepository, CurrencyService currencyService, PrivatBankCurrencyService privatBankCurrencyService) {
        this.adRepository = adRepository;
        this.currencyService = currencyService;
        this.privatBankCurrencyService = privatBankCurrencyService;
    }
  
    // 1  Інші методи сервісу
    
    // ...save
     // Зберегти оголошення
     public Ad saveAd(Ad ad) {
    try {
              // Перевірка на null перед множенням
        if (ad.getPriceUah() == null) {
            throw new IllegalArgumentException("PriceUah не може бути null");
        }
        Ad savedAd = adRepository.save(ad);

        // Оновити курси валют
        currencyService.updateCurrencyRates();

        return savedAd;
    } catch (Exception e) {
        // Обробка винятків і повідомлення про помилку
         System.out.println("Сталася помилка при збереженні оголошення: " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Помилка при збереженні оголошення");
    }
}
public void calculateCurrency(Ad ad, BigDecimal uahRate, BigDecimal eurRate) {
    try {
        BigDecimal priceUah = ad.getPriceUah();
        BigDecimal priceUsd = priceUah.divide(uahRate, RoundingMode.HALF_UP);
        BigDecimal priceEur = priceUah.divide(eurRate, RoundingMode.HALF_UP);

        ad.setPriceUsd(priceUsd);
        ad.setPriceEur(priceEur);
        ad.setCurrencyCode("UAH");
    } catch (Exception e) {
        // Обробка винятків та логування помилок
        System.out.println("Сталася помилка при розрахунку валют для оголошення: " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Помилка при розрахунку валют для оголошення");
    }
}



//     ,,,,,  get byId  +  Increment
     public Ad getAdById(Integer Id) {
        return adRepository.findById(Id).orElse(null);
    }
        public void incrementViews(Integer Id) {
            Ad ad = adRepository.findById(Id).orElse(null);
            if (ad != null) {
                AdView adView = new AdView();
                adView.setAd(ad);
                adView.setViewedAt(LocalDateTime.now());
                ad.getAdViews().add(adView);
                adRepository.save(ad);
            }
        }
//        ,,,,For Day  Week Month --- Get View---Carent
        public int getAdViewsForDay(Integer Id) {
            Ad ad = adRepository.findById(Id).orElse(null);
            if (ad != null) {
                return (int) ad.getAdViews().stream()
                        .filter(adView -> adView.getViewedAt().toLocalDate().isEqual(LocalDate.now()))
                        .count();
            }
            return 0;
        }

        public int getAdViewsForWeek(Integer Id) {
            Ad ad = adRepository.findById(Id).orElse(null);
            if (ad != null) {
                LocalDate startDate = LocalDate.now().minusDays(7);
                return (int) ad.getAdViews().stream()
                        .filter(adView -> adView.getViewedAt().toLocalDate().isAfter(startDate))
                        .count();
            }
            return 0;
        }

        public int getAdViewsForMonth(Integer Id) {
            Ad ad = adRepository.findById(Id).orElse(null);
            if (ad != null) {
                LocalDate startDate = LocalDate.now().minusMonths(1);
                return (int) ad.getAdViews().stream()
                        .filter(adView -> adView.getViewedAt().toLocalDate().isAfter(startDate))
                        .count();
            }
            return 0;
        }
            // Видалити оголошення за ідентифікатором
    public void deleteById(Integer id) {
        try {
            adRepository.deleteById(id);
        } catch (Exception e) {
            // Обробка винятків і повідомлення про помилку
            System.out.println("Сталася помилка при видаленні оголошення: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Помилка при видаленні оголошення");
        }
    }

    }

    //public void  saveAdView(AdView adView) {
    //        // Логіка для збереження об'єкта AdView у базі даних
    //        // Наприклад, використання adViewRepository для збереження
    //        
    //        adViewRepository.save(adView);
    //    }
    // 



//    public Map<LocalDate, Integer> getAdViewsByDay(Integer Id
//            , LocalDate startDate, LocalDate endDate) {
//        Map<LocalDate, Integer> adViewsByDay = new HashMap<>();
//        
//        Ad ad = adRepository.findById(Id
//        ).orElse(null);
//        if (ad != null) {
//            List<AdView> adViews = ad.getAdViews();
//            
//            for (AdView adView : adViews) {
//                LocalDateTime viewedAt = adView.getViewedAt();
//                LocalDate viewDate = viewedAt.toLocalDate();
//                
//                if (viewDate.isAfter(startDate) && viewDate.isBefore(endDate)) {
//                    adViewsByDay.put(viewDate, adViewsByDay.getOrDefault(viewDate, 0) + 1);
//                }
//            }
//        }
//        
//        return adViewsByDay;
//    }
//    
//
//
//public Map<YearMonth, Integer> getAdViewsByMonth(Integer Id
//        , YearMonth startYearMonth, YearMonth endYearMonth) {
//    Map<YearMonth, Integer> adViewsByMonth = new HashMap<>();
//    
//    Ad ad = adRepository.findById(Id
//    ).orElse(null);
//    if (ad != null) {
//        List<AdView> adViews = ad.getAdViews();
//        
//        for (AdView adView : adViews) {
//            LocalDateTime viewedAt = adView.getViewedAt();
//            LocalDate viewDate = viewedAt.toLocalDate();
//            YearMonth yearMonth = YearMonth.from(viewDate);
//            
//            if (yearMonth.isAfter(startYearMonth) && yearMonth.isBefore(endYearMonth)) {
//                adViewsByMonth.put(yearMonth, adViewsByMonth.getOrDefault(yearMonth, 0) + 1);
//            }
//        }
//    }
//    
//    return adViewsByMonth;
//}


