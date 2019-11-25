package club.geek66.downloader.meitulu.foo.auditing;

/*
@Configuration
@EnableJpaAuditing(*/
/*auditorAwareRef = "userProvider",*//*
 dateTimeProviderRef = "dateTimeProvider")
public class AuditingConfiguration {

	@Bean
	public AuditorAware<Long> userProvider() {
		return () -> {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserPrincipal) {
				return Optional.of(((UserPrincipal) authentication.getPrincipal()).getIndex());
			}
			return Optional.empty();
		};
	}

	@Bean
	public DateTimeProvider dateTimeProvider() {
		return () -> Optional.of(ZonedDateTime.now());
	}

}*/
