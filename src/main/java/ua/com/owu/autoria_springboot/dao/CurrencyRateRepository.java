/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ua.com.owu.autoria_springboot.dao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import ua.com.owu.autoria_springboot.models.*;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    CurrencyRate findByCurrencyCode(String currencyCode);
    CurrencyRate findTopByOrderByTimestampDesc();
}

