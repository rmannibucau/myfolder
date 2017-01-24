import {NgModule} from '@angular/core';
import {BrowserModule } from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {HttpModule} from '@angular/http';

import {Tree} from './components/tree/tree.component';
import {TreeService} from './components/tree/tree.service';

import {App} from './app';
import {routes} from './app.routes';

@NgModule({
    bootstrap: [App],
    declarations: [
      App,
      Tree
    ],
    providers: [
      TreeService
    ],
    imports: [
      BrowserModule,
      FormsModule,
      HttpModule,
      RouterModule.forRoot(routes, { useHash: true })
    ]
})
export class AppModule {
}
