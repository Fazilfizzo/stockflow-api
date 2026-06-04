package com.fizoind.stockflow_api.pricing;

import io.github.bucket4j.Bandwidth;

import java.time.Duration;

public enum PricingPlan {
    FREE {
        Bandwidth getLimit() {
            return
                    Bandwidth.builder()
                            .capacity(5)
                            .refillIntervally(5, Duration.ofMinutes(1))
                            .build();
        }
    },
    BASIC {
        Bandwidth getLimit() {
            return
            Bandwidth.builder()
                    .capacity(10)
                    .refillIntervally(10, Duration.ofMinutes(1))
                    .build();
        }
    },
    PROFESSIONAL {
        Bandwidth getLimit() {
            return
                    Bandwidth.builder()
                            .capacity(20)
                            .refillIntervally(20, Duration.ofMinutes(1))
                            .build();
        }
    };

    static PricingPlan resolvePlanFromApiKey(String apiKey) {
        if (apiKey.startsWith("FREE-")) {
            return FREE;
        } else if (apiKey.startsWith("PX-001-")) {
            return PROFESSIONAL;
        } else if (apiKey.startsWith("BX001-")) {
            return BASIC;
        }
        return FREE;
    }

    abstract Bandwidth getLimit();
}
