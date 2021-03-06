package io.nyarosys.gateway.filter;

import io.nyarosys.gateway.config.GatewayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ShopFilter extends AbstractGatewayFilterFactory<GatewayConfig> {
    public ShopFilter() {
        super(GatewayConfig.class);
    }

    @Override
    public GatewayFilter apply(final GatewayConfig config) {
        return (exchange, chain) -> {
            log.info("## ShopFilter baseMessage: {}", config.getBaseMessage());
            if(config.isPreLogger()) {
                log.info(">> ShopFilter Start: {}", exchange.getRequest());
            }

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if(config.isPostLogger()) {
                    log.info("<< ShopFilter End: {}", exchange.getResponse());
                }
            }));
        };
    }
}
