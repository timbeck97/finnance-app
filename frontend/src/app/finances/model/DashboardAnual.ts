import { LinearChart } from "./LinearChart";
import { PanelTotais } from "./PanelTotais";
import { Serie } from "./Serie";

export interface DashboardAnual {
    name: string;
    lineChartDto: LinearChart[];
    barChartData: Serie[];
    totalGasto: PanelTotais;
    totalReceita: PanelTotais;
}