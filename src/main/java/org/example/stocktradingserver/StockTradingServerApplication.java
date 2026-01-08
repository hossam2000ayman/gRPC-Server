package org.example.stocktradingserver;

import org.example.stocktradingserver.entity.Stock;
import org.example.stocktradingserver.repository.StockRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication

public class StockTradingServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockTradingServerApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(StockRepository stockRepository) {
        return args -> {
            System.out.println("Stock Trading Server is running...");
            Stock stock1 = new Stock();
            stock1.setSymbol("AAPL");
            stock1.setPrice(BigDecimal.valueOf(175.5));
            stockRepository.save(stock1);

            Stock stock2 = new Stock();
            stock2.setSymbol("GOOGL");
            stock2.setPrice(BigDecimal.valueOf(2800.75));
            stockRepository.save(stock2);

            Stock stock3 = new Stock();
            stock3.setSymbol("AMZN");
            stock3.setPrice(BigDecimal.valueOf(3400));
            stockRepository.save(stock3);
        };
    }

}
