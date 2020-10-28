import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { ServeStaticModule } from '@nestjs/serve-static';
import { ConsulModule } from 'nest-consul';
import { ConsulServiceModule } from 'nest-consul-service';
import { join } from 'path';

@Module({
  imports: [
    ServeStaticModule.forRoot({
      rootPath: join(__dirname, '..', 'client/dist'),
    }),
    ConsulModule.register({
      host: '127.0.0.1',
      port: 8500
    }),
    ConsulServiceModule.register({
      serviceId: 'node1',
      serviceName: 'user-account',
      port: 3004,
      consul: {
        discovery_host: 'localhost',
        health_check: {
          timeout: '1s',
          interval: '10s',
          route: '/health',
        },
        max_retry: 5,
        retry_interval: 3000,
      }
    }),
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
