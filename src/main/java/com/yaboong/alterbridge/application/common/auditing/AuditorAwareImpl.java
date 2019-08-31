package com.yaboong.alterbridge.application.common.auditing;

import org.springframework.data.domain.AuditorAware;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Created by yaboong on 2019-08-31
 */
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    @Nonnull
    public Optional<String> getCurrentAuditor() {
        return Optional.of("admin");
    }
}
