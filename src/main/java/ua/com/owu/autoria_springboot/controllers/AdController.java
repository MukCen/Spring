package ua.com.owu.autoria_springboot.controllers;


import java.math.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ua.com.owu.autoria_springboot.models.*;
import ua.com.owu.autoria_springboot.services.*;


@RestController
@RequestMapping("/ads")
public class AdController {
 private final PrivatBankCurrencyService privatBankCurrencyService;

  private final AdService adService;
//  private final AdViewService adViewService;
    
    public AdController(AdService adService, ua.com.owu.autoria_springboot.services.PrivatBankCurrencyService privatBankCurrencyService) {
        this.adService = adService;
//      this.adViewService = null;
     this.privatBankCurrencyService = privatBankCurrencyService;
    }

// 1    Save
    @PostMapping("/saveAd")
public ResponseEntity<Ad> saveAd(@RequestBody Ad ad) {
    try {
        BigDecimal uahRate = privatBankCurrencyService.getUsdExchangeRate();
        BigDecimal eurRate = privatBankCurrencyService.getEurExchangeRate();

        adService.calculateCurrency(ad, uahRate, eurRate);
        adService.saveAd(ad);
         return ResponseEntity.ok(ad);
    } catch (Exception e) {
        // Обробка винятків та логування помилок
        System.out.println("Сталася помилка при збереженні оголошення: " + e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}


//     @PostMapping("/saveAd")
//    public ResponseEntity<String> saveAd(@RequestBody Ad ad) {
//  try {
//        adService.calculateCurrency(ad);
//        return ResponseEntity.ok("Ad created successfully");
//    } catch (Exception e) {
//        // Обробка винятків та логування помилок
//        System.out.println("Сталася помилка при створенні оголошення: " + e.getMessage());
//        e.printStackTrace();
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create ad");
//    }
//}

    
// 1        Get Id

//  1        Get Id + increment       
@GetMapping("/{Id}")
public ResponseEntity<Ad> getAdByIdAndIncrementViews(@PathVariable("Id") Integer Id) {
    Ad ad = adService.getAdById(Id);
    if (ad != null) {
        adService.incrementViews(Id);
        return ResponseEntity.ok(ad);
    }
    return ResponseEntity.notFound().build();
}
    @GetMapping("/{Id}/views/day")
    public ResponseEntity<Integer> getAdViewsForDay(@PathVariable("Id") Integer Id) {
        int adViewsForDay = adService.getAdViewsForDay(Id);
        return ResponseEntity.ok(adViewsForDay);
    }

    @GetMapping("/{Id}/views/week")
    public ResponseEntity<Integer> getAdViewsForWeek(@PathVariable("Id") Integer Id) {
        int adViewsForWeek = adService.getAdViewsForWeek(Id);
        return ResponseEntity.ok(adViewsForWeek);
    }

    @GetMapping("/{Id}/views/month")
    public ResponseEntity<Integer> getAdViewsForMonth(@PathVariable("Id") Integer Id) {
        int adViewsForMonth = adService.getAdViewsForMonth(Id);
        return ResponseEntity.ok(adViewsForMonth);
    }
    @DeleteMapping("/{id}/delete")
public ResponseEntity<String> deleteAdById(@PathVariable("id") Integer id) {
    try {
        adService.deleteById(id);
        return ResponseEntity.ok("Оголошення з ID " + id + " було успішно видалено");
    } catch (Exception e) {
        // Обробка винятків та логування помилок
        System.out.println("Сталася помилка при видаленні оголошення: " + e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Сталася помилка при видаленні оголошення");
    }
}

    
}

    
//            1   

//@GetMapping("/api/adViewsByWeek/Id}")
//public ResponseEntity<Map<LocalDate, Integer>> getAdViewsByWeek(@PathVariable("Id") Integer Id,
//        @RequestParam("startDate") LocalDate startDate,
//        @RequestParam("endDate") LocalDate endDate) {
//    Map<LocalDate, Integer> adViewsByWeek = new LinkedHashMap<>();
//
//    Ad ad = adService.getAdById(Id);
//    if (ad != null) {
//        List<AdView> adViews = ad.getAdViews();
//
//        for (AdView adView : adViews) {
//            LocalDateTime viewedAt = adView.getViewedAt();
//            LocalDate viewDate = viewedAt.toLocalDate();
//            int weekOfYear = viewDate.get(java.time.temporal.WeekFields.ISO.weekOfYear());
//
//            if (viewDate.isAfter(startDate) && viewDate.isBefore(endDate)) {
//                adViewsByWeek.put(viewDate, adViewsByWeek.getOrDefault(viewDate, 0) + 1);
//            }
//        }
//    }
//
//    return ResponseEntity.ok(adViewsByWeek);
//}
//
//
//    @GetMapping("/adViews/month/{Id}")
//    public ResponseEntity<Map<YearMonth, Integer>> getAdViewsByMonth(
//            @PathVariable("Id") Integer Id,
//            @RequestParam("startDate") YearMonth startDate,
//            @RequestParam("endDate") YearMonth endDate) {
//        Map<YearMonth, Integer> adViewsByMonth = adService.getAdViewsByMonth(Id, startDate, endDate);
//        return new ResponseEntity<>(adViewsByMonth, HttpStatus.OK);
//    }
//
//    // Перегляд Оголошення
//
//  
//@GetMapping("/api/ads/Id}/views")
//public ResponseEntity<String> viewAd(@PathVariable("Id") Integer Id) {
//    // Отримати оголошення за його ідентифікатором
//    Ad ad = adService.getAdById( Id);
//    
//    if (ad != null) {
//        // Збільшити кількість переглядів оголошення
//        ad.incrementViews();
//        
//        // Створити об'єкт AdView і задати значення
//        AdView adView = new AdView();
//        adView.setAd(ad);
//        adView.setViewedAt(LocalDateTime.now());
//        
//        // Зберегти інформацію про перегляд
//        adService.saveAdView(adView);
//        
//        // Повернути відповідь з кодом 200 OK та повідомленням про успішний перегляд
//        return ResponseEntity.ok("Ad viewed successfully");
//    } else {
//        // Якщо оголошення не знайдено, повернути відповідь з кодом 404 Not Found
//        return ResponseEntity.notFound().build();
//    }
//}


