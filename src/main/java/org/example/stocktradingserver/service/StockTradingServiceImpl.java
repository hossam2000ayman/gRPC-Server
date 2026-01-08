package org.example.stocktradingserver.service;

import com.example.grpc.StockRequest;
import com.example.grpc.StockResponse;
import com.example.grpc.StockTradingServiceInterfaceGrpc;
import io.grpc.stub.StreamObserver;
import org.example.stocktradingserver.entity.Stock;
import org.example.stocktradingserver.repository.StockRepository;
import org.springframework.grpc.server.service.GrpcService;

import java.time.format.DateTimeFormatter;
import java.util.List;
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
    public void subscribeStockPrice(StockRequest request, StreamObserver<StockResponse> responseObserver) {
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
}
