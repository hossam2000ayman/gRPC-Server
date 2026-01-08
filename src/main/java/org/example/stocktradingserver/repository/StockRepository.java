package org.example.stocktradingserver.repository;

import org.example.stocktradingserver.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findBySymbol(String symbol);
}
