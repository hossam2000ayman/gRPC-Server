package org.example.stocktradingserver.service;

import com.example.grpc.*;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.example.stocktradingserver.entity.Stock;
import org.example.stocktradingserver.repository.StockRepository;
import org.springframework.grpc.server.service.GrpcService;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@GrpcService //so this class will be exposed as a gRPC service by gRPC server
public class StockTradingServiceImpl extends StockTradingServiceInterfaceGrpc.StockTradingServiceInterfaceImplBase {

    private final StockRepository stockRepository;

    public StockTradingServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void getStockPrice(StockRequest request, StreamObserver<StockResponse> responseObserver) {
        // StreamObserver is the guy who play role of sending response back to client
        // super.getStockPrice(request, responseObserver);
        Stock stock = stockRepository.findBySymbol(request.getStockSymbol());

        StockResponse response = StockResponse.newBuilder()
                .setStockSymbol(stock.getSymbol())
                .setPrice(stock.getPrice().doubleValue())
                .setTimestamp(stock.getLastUpdated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void subscribeStockPrice(Empty request, StreamObserver<StockResponse> responseObserver) {
        List<Stock> stocks = stockRepository.findAll();
        stocks.forEach(stock -> {
            try {
                StockResponse response = StockResponse.newBuilder()
                        .setStockSymbol(stock.getSymbol())
                        .setPrice(stock.getPrice().doubleValue())
                        .setTimestamp(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(stock.getLastUpdated()))
                        .build();

                responseObserver.onNext(response);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                responseObserver.onError(e);
            }
        });
    }

    @Override
    public StreamObserver<StockOrder> bulkStockOrder(StreamObserver<OrderSummary> responseObserver) {
        final int[] totalOrders = {0};
        final double[] totalAmount = {0.0};
        final int[] successCount = {0};
        String[] possibleCurrencies = {"USD", "EUR", "SAR", "EGP", "AED"};
        return new StreamObserver<StockOrder>() {
            @Override
            public void onNext(StockOrder stockOrder) {
                totalOrders[0]++;
                totalAmount[0] += stockOrder.getQuantity() * stockOrder.getPrice();

                Stock stock = new Stock();
                stock.setSymbol(stockOrder.getStockSymbol());
                stock.setPrice(BigDecimal.valueOf(stockOrder.getPrice()));
                stock.setCurrency(possibleCurrencies[new Random().nextInt(possibleCurrencies.length - 1)]);
                stock.setOrderType(stockOrder.getOrderType());

                stockRepository.save(stock);
                successCount[0]++;

            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Server unable to process the request: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Client completed sending orders");
                OrderSummary summary = OrderSummary.newBuilder()
                        .setTotalOrders(totalOrders[0])
                        .setTotalAmount(totalAmount[0])
                        .setSuccessCount(successCount[0])
                        .build();

                //how to send summary response back to client
                responseObserver.onNext(summary);
                responseObserver.onCompleted();
            }
        };
    }
}
