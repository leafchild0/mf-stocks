import {Module} from '@nestjs/common';
import {ConsulModule} from 'nest-consul';
import {ConsulServiceModule} from "nest-consul-service";

@Module({
	imports: [
		ConsulModule.register({
			host: '127.0.0.1',
			port: 8500
		}),
		ConsulServiceModule.register({
			serviceId: 'mf-user-account',
			serviceName: 'mf-user-account',
			port: 3001,
			consul: {
				discovery_host: '127.0.0.1',
				health_check: {
					timeout: '1s',
					interval: '10s',
					route: '/health',
				},
				max_retry: 5,
				retry_interval: 3000,
			}
		})
	],
})
export class ConsulAppModule {
}
