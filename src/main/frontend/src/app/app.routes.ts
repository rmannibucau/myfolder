import {Tree} from './components/tree/tree.component';

export const routes = [
  {path: '', component: Tree},
  {path: '**', component: Tree}
];
