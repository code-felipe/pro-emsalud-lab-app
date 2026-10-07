import { Routes } from '@angular/router';
import { TestsComponentList } from './catalog/test/page/list/test-list.component';
import { TestFormComponent } from './catalog/test/page/form/form.component';
import { TestDetailComponent } from './catalog/test/page/detail/detail.component';
import { TestCategoryList } from './catalog/test-category/page/list/test-category-list.component';
import { TestCategoryFormComponent } from './catalog/test-category/page/form/form.component';
import { PatientComponentList } from './patient/page/list/patient-list.component';
import { PatientFormComponent } from './patient/page/form/form.component';
import { PatientDetailComponent } from './patient/page/detail/detail.component';
import { PatientResultsComponent } from './patient/pages/results/patient-result.component';
import { PatientReportComponent } from './patient/pages/report/patient-report.component';
import { PatientHistoryComponent } from './patient/pages/history/patient-history.component';
import { OrderFormComponent } from './order/page/form/form.component';
import { DoctorComponentList } from './doctor/page/list/doctor-list.component';
import { DoctorFormComponent } from './doctor/page/form/form.component';
import { TestResultComponentList } from './results/page/list/test-result-list.component';
import { OrderComponentList } from './order/page/list/order-list.component';
import { TestResultFormComponent } from './results/page/form/form.component';
import { HomeComponent } from './home/home.component';
import { DashboardComponent } from './metrics/page/dashboard.component';
import { BackupConfigurationComponent } from './backup/page/backup-configuration.component';
import { SettingsComponent } from './settings/settings.component';
import { BackupComponent } from './backup/page/backup.component';

export const routes: Routes = [

    { path: '', component: HomeComponent },

    {path: 'tests', component: TestsComponentList},
    {path: 'tests/form', component: TestFormComponent},
    {path: 'tests/form/:id', component: TestFormComponent},
    {path: 'tests/:id', component: TestDetailComponent},

    // Test Category
    {path: 'test-category-references', component: TestCategoryList},
    {path: 'test-category-references/form', component: TestCategoryFormComponent},
    {path: 'test-category-references/form/:id', component: TestCategoryFormComponent},

    // Patient
    {path: 'patients', component: PatientComponentList},
    {path: 'patients/form', component: PatientFormComponent},
    {path: 'patients/form/:id', component: PatientFormComponent},
    // {path: 'patients/:id', component: PatientDetailComponent},

    {path: 'metrics', component: DashboardComponent},
        // Patient - Pages
    {
  path: 'patients/:id',
  component: PatientDetailComponent,
  children: [
    {
      path: '',
      redirectTo: 'results',
      pathMatch: 'full'
    },
    {
      path: 'results',
      component: PatientResultsComponent
    },
    {
      path: 'history',
      component: PatientHistoryComponent
    },
    {
      path: 'report',
      component: PatientReportComponent
    },
    
    {
      path: 'orders/create',
      component: OrderFormComponent
    },
     {
      path: 'orders/:id',
      component: OrderFormComponent
    },
    {
      path: 'orders/:orderId/test-results',
      component: TestResultComponentList
    },
    {
      path: 'orders/:orderId/test-results/create',
      component: TestResultFormComponent
    },
     {
      path: 'orders/:orderId/test-results/:id',
      component: TestResultFormComponent
    }

  ]
},

{path: 'doctors', component: DoctorComponentList},
{path: 'doctors/form', component: DoctorFormComponent},
{path: 'doctors/form/:id', component: DoctorFormComponent},

{ path: 'settings', component: SettingsComponent },

{ path: 'backup', component: BackupComponent },

{ path: 'backup/configuration', component: BackupConfigurationComponent },



];
