package ua.com.owu.autoria_springboot.controllers;


import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ua.com.owu.autoria_springboot.models.*;
import ua.com.owu.autoria_springboot.services.*;


@RestController
@RequestMapping("/ads")
public class AdController {
 
  @Autowired  
  private final AdService adService;
//  private final AdViewService adViewService;
    
    public AdController(AdService adService) {
        this.adService = adService;
//      this.adViewService = null;
    }

// 1    Save

     @PostMapping("/saveAd")
    public ResponseEntity<Ad> saveAd(@RequestBody Ad ad) {
        Ad savedAd = adService.saveAd(ad);
        return ResponseEntity.ok(savedAd);
    }
    
// 1        Get Id

//    @GetMapping("/api/{Id}")
//public ResponseEntity<Ad> getAdById(@PathVariable("Id") Integer Id) {
//    Ad ad = adService.getAdById( Id);
//    if (ad != null) {
//        return ResponseEntity.ok(ad);
//    } else {
//        return ResponseEntity.notFound().build();
//    }
//}
//    1        Get Id + increment       
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


