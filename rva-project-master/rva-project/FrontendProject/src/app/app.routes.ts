import { Routes } from '@angular/router';
import { BolnicaComponent } from './components/main/bolnica/bolnica';
import { DijagnozaComponent } from './components/main/dijagnoza/dijagnoza';
import { OdeljenjeComponent } from './components/main/odeljenje/odeljenje';
import { PacijentComponent } from './components/main/pacijent/pacijent';
import { AuthorComponent } from './components/utility/author/author';
import { AboutComponent } from './components/utility/about/about';
import { HomeComponent } from './components/utility/home/home';

export const routes: Routes = [
    {path:'bolnica', component:BolnicaComponent},
    {path:'dijagnoza', component:DijagnozaComponent},
    {path:'odeljenje', component:OdeljenjeComponent},
    {path:'pacijent', component:PacijentComponent},
    {path:'author', component:AuthorComponent},
    {path:'about', component:AboutComponent},
    {path:'', component:HomeComponent, pathMatch:"full"},

];
