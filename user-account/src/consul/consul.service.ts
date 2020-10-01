import {Injectable, OnApplicationBootstrap, OnApplicationShutdown} from '@nestjs/common';
import consul from 'consul';

@Injectable()
export class ConsulService implements OnApplicationShutdown, OnApplicationBootstrap {

	onApplicationShutdown(): void {
		consul.agent.service.deregister('mf-user-account', function(err) {
			if (err) throw err;
		});
	}

	onApplicationBootstrap(): void {
		consul.agent.join('127.0.0.1', function(err) {
			if (err) throw err;

			consul.agent.service.register('mf-user-account', function(err) {
				if (err) throw err;
			});
		});
	}
}
