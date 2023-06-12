/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ua.com.owu.autoria_springboot.models;

import jakarta.persistence.*;
import java.math.*;
import java.time.*;
import java.util.*;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "currency_rate")
public class CurrencyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency_code", nullable = false)
    private String currencyCode;
    @Column(name = "currency_name", nullable = false)
    private String currencyName;
    @Column(name = "exchange_rate", nullable = false)
    private BigDecimal exchangeRate;
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    // Конструктори, геттери та сеттери

    public CurrencyRate() {
        this.timestamp = LocalDateTime.now();
    }

    public CurrencyRate(String currencyCode, String currencyName, BigDecimal exchangeRate) {
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.exchangeRate = exchangeRate;
        this.timestamp = LocalDateTime.now();
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.currencyCode);
        hash = 89 * hash + Objects.hashCode(this.currencyName);
        hash = 89 * hash + Objects.hashCode(this.exchangeRate);
        hash = 89 * hash + Objects.hashCode(this.timestamp);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CurrencyRate other = (CurrencyRate) obj;
        if (!Objects.equals(this.currencyCode, other.currencyCode)) {
            return false;
        }
        if (!Objects.equals(this.currencyName, other.currencyName)) {
            return false;
        }
        if (!Objects.equals(this.exchangeRate, other.exchangeRate)) {
            return false;
        }
        return Objects.equals(this.timestamp, other.timestamp);
    }
    
    
}


