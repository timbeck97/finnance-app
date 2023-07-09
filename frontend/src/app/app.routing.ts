import { RouterModule, Routes } from "@angular/router"
import { StudyComponent } from "./study/study.component"
import { InfoComponent } from "./info/info.component"
import { ModuleWithProviders } from "@angular/core"
import { LoginComponent } from "./login/login.component"
import { FinancesComponent } from "./finances/finances.component"
import { AuthGuardService } from "./guards/auth-guard.service"
import { ConfiguracoesComponent } from "./finances/configuracoes/configuracoes.component"


const APP_ROUTES: Routes = [
    {path: 'study', component: StudyComponent},
    {path: '', redirectTo: '/study', pathMatch: 'full'},
    {path:'info', component: InfoComponent},
    {path:'login', component: LoginComponent},
    {path:'finances', component: FinancesComponent, canActivate: [AuthGuardService]},
    {path:'configuracoes', component: ConfiguracoesComponent, canActivate: [AuthGuardService]},
]

export const Routing:ModuleWithProviders<RouterModule>=RouterModule.forRoot(APP_ROUTES);