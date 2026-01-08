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
            stock1.setSymbol("APPLE");
            stock1.setPrice(BigDecimal.valueOf(175.5));
            stockRepository.save(stock1);

            Stock stock2 = new Stock();
            stock2.setSymbol("GOOGLE");
            stock2.setPrice(BigDecimal.valueOf(2800.75));
            stockRepository.save(stock2);

            Stock stock3 = new Stock();
            stock3.setSymbol("AMAZON");
            stock3.setPrice(BigDecimal.valueOf(3400));
            stockRepository.save(stock3);

            Stock stock4 = new Stock();
            stock4.setSymbol("ANDROID");
            stock4.setPrice(BigDecimal.valueOf(3400));
            stockRepository.save(stock4);

            Stock stock5 = new Stock();
            stock5.setSymbol("IOS");
            stock5.setPrice(BigDecimal.valueOf(3400));
            stockRepository.save(stock5);

            Stock stock6 = new Stock();
            stock6.setSymbol("CONFLUENT");
            stock6.setPrice(BigDecimal.valueOf(3400));
            stockRepository.save(stock6);

            Stock stock7 = new Stock();
            stock7.setSymbol("CHATGPT");
            stock7.setPrice(BigDecimal.valueOf(3400));
            stockRepository.save(stock7);

            Stock stock8 = new Stock();
            stock8.setSymbol("LANGCHAIN");
            stock8.setPrice(BigDecimal.valueOf(3400));
            stockRepository.save(stock8);

            Stock stock9 = new Stock();
            stock9.setSymbol("OLLAMA");
            stock9.setPrice(BigDecimal.valueOf(3400));
            stockRepository.save(stock9);

            Stock stock10 = new Stock();
            stock10.setSymbol("REPLIT");
            stock10.setPrice(BigDecimal.valueOf(3400));
            stockRepository.save(stock10);

            Stock stock11 = new Stock();
            stock11.setSymbol("MINSTREL");
            stock11.setPrice(BigDecimal.valueOf(3400));
            stockRepository.save(stock11);
        };
    }

}
