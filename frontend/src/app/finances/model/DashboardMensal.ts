import { PanelTotais } from "./PanelTotais";
import { Serie } from "./Serie";

export interface DashboardMensal {
    barChartData: Serie[];
    totalGasto: PanelTotais;
    totalReceita: PanelTotais;
}