package ua.com.owu.autoria_springboot.models;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.time.*;
import java.util.*;
import lombok.*;

//package ua.com.owu.feb_2023_springboot.models;
//
//import com.fasterxml.jackson.annotation.JsonView;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.validation.constraints.*;
//import lombok.*;
//import ua.com.owu.feb_2023_springboot.views.Views;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@ToString
//@Entity
//
//public class Car {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @JsonView(value = {Views.Level1.class})
//    private int id;
//    @NotBlank(message = "model cannot be empty")
//    @Size(min = 2 ,max = 20, message = "model mast be from 2 to 20 chars")
//    @Size( max = 20, message = "model too long" )
//    @Pattern(regexp = "^[A-Za-z\s][A-Za-z0-9\s]*$", message = "model mast be [a-zA-Z_0-9]")
//
//    @JsonView(value = {Views.Level1.class,Views.Level2.class,Views.Level3.class})
//    private String model;
//    @NotBlank(message = "producer cannot be empty")
//
//    @JsonView(value = {Views.Level1.class,Views.Level2.class,Views.Level3.class})
//    private String producer;
//
//    @Min(value = 1, message ="power cannot be less then 1")
//    @Max(value = 1100, message = "power cannot be more then 1100")
//
//    @JsonView(value = {Views.Level1.class,Views.Level2.class})
//    private int power;
//
//    @JsonView(value = {Views.Level1.class})
//    private String photo;
//
//    public Car(String model, String producer, int power) {
//        this.model = model;
//        this.producer = producer;
//        this.power = power;
//    }
//}

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ad{
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Integer id;
    
    private String descr;
   
  @JsonIgnore  
  @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL)
    private List<AdView> adViews;

    public Ad(String descr) {
        this.descr = descr;
    }
   public void incrementViews() {
        if (adViews == null) {
            adViews = new ArrayList<>();
        }
        
        AdView adView = new AdView();
        adView.setAd(this);
        adView.setViewedAt(LocalDateTime.now());
        
        adViews.add(adView);
    }

    public Ad(String descr, List<AdView> adViews) {
        this.descr = descr;
        this.adViews = adViews;
    }
    
}
        
        