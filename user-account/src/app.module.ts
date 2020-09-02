import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { ServeStaticModule} from '@nestjs/serve-static';
import { join } from 'path';
import {ConsulService} from "./consul/consul.service";

@Module({
  imports: [
    ServeStaticModule.forRoot({
      rootPath: join(__dirname, '..', 'client/dist'),
    }), // New
  ],
  controllers: [AppController],
  providers: [AppService, ConsulService],
})
export class AppModule {}
