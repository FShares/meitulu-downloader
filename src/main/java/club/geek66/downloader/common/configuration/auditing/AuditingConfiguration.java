package club.geek66.downloader.common.configuration.auditing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.ZonedDateTime;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(/*auditorAwareRef = "userProvider",*/ dateTimeProviderRef = "dateTimeProvider")
public class AuditingConfiguration {

	/*@Bean
	public AuditorAware<Long> userProvider() {
		return () -> {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserPrincipal) {
				return Optional.of(((UserPrincipal) authentication.getPrincipal()).getIndex());
			}
			return Optional.empty();
		};
	}*/

	@Bean
	public DateTimeProvider dateTimeProvider() {
		return () -> Optional.of(ZonedDateTime.now());
	}

}