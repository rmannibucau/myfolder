import {enableProdMode} from "@angular/core";
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import {disableDebugTools} from '@angular/platform-browser';

import {AppModule} from './app/app.module';

const ENV_PROVIDERS = [];
if (process.env.compileEnv != 'dev') { // prod
  disableDebugTools();
  enableProdMode();
}

platformBrowserDynamic()
  .bootstrapModule(AppModule);
