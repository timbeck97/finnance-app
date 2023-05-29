import { RouterModule, Routes } from "@angular/router"
import { StudyComponent } from "./study/study.component"
import { InfoComponent } from "./info/info.component"
import { ModuleWithProviders } from "@angular/core"
import { LoginComponent } from "./login/login.component"
import { FinancesComponent } from "./finances/finances.component"

const APP_ROUTES: Routes = [
    {path: 'study', component: StudyComponent},
    {path: '', redirectTo: '/study', pathMatch: 'full'},
    {path:'info', component: InfoComponent},
    {path:'login', component: LoginComponent},
    {path:'finances', component: FinancesComponent}
]

export const routing:ModuleWithProviders<RouterModule>=RouterModule.forRoot(APP_ROUTES);