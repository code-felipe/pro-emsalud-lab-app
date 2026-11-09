
import { Routes } from '@angular/router';

// =====================================================
// TESTS
// =====================================================
import { TestsComponentList } from './catalog/test/page/list/test-list.component';
import { TestFormComponent } from './catalog/test/page/form/form.component';
import { TestDetailComponent } from './catalog/test/page/detail/detail.component';

// =====================================================
// TEST CATEGORIES
// =====================================================
import { TestCategoryList } from './catalog/test-category/page/list/test-category-list.component';
import { TestCategoryFormComponent } from './catalog/test-category/page/form/form.component';

// =====================================================
// PATIENTS
// =====================================================
import { PatientComponentList } from './patient/page/list/patient-list.component';
import { PatientFormComponent } from './patient/page/form/form.component';
import { PatientDetailComponent } from './patient/page/detail/detail.component';
import { PatientResultsComponent } from './patient/pages/results/patient-result.component';
import { PatientReportComponent } from './patient/pages/report/patient-report.component';
import { PatientHistoryComponent } from './patient/pages/history/patient-history.component';

// =====================================================
// ORDERS
// =====================================================
import { OrderFormComponent } from './order/page/form/form.component';
import { OrderComponentList } from './order/page/list/order-list.component';
import { OrderReportView } from './order/page/report-view/order-report-view';

// =====================================================
// SIMPLE CHEMISTRY
// =====================================================
import { TestMenuComponent } from './order/page/detail/menu/test-menu.component';
import { SimpleChemistryList } from './order/page/detail/simple-chemistry/pages/list/simple-chemistry-list.component';
import { SimpleChemistryFormComponent } from './order/page/detail/simple-chemistry/pages/form/form.component';

// =====================================================
// TEST RESULTS
// =====================================================
import { TestResultComponentList } from './results/page/list/test-result-list.component';
import { TestResultFormComponent } from './results/page/form/form.component';

// =====================================================
// DOCTORS
// =====================================================
import { DoctorComponentList } from './doctor/page/list/doctor-list.component';
import { DoctorFormComponent } from './doctor/page/form/form.component';

// =====================================================
// HOME / DASHBOARD
// =====================================================
import { HomeComponent } from './home/home.component';
import { DashboardComponent } from './metrics/page/dashboard.component';

// =====================================================
// BACKUP / SETTINGS
// =====================================================
import { BackupConfigurationComponent } from './backup/page/backup-configuration.component';
import { SettingsComponent } from './settings/settings.component';
import { BackupComponent } from './backup/page/backup.component';

// =====================================================
// USERS
// =====================================================
import { UserComponentList } from './user/page/list/user-list.component';
import { UserFormComponent } from './user/page/form/form.component';
import { UserDetailComponent } from './user/page/detail/detail.component';

// =====================================================
// AUTH / LAYOUT
// =====================================================
import { LoginComponent } from './auth/components/login-form/login-form.component';
import { MainLayoutComponent } from './layout/main/main-layout.component';
import { AuthGuard } from './auth/auth.guard';
import { WidalReactionList } from './order/page/detail/reaction-widal/pages/list/widal-reaction-list.component';
import { WidalReactionDetailComponent } from './order/page/detail/reaction-widal/pages/detail/detail.component';
import { WidalReactionFormComponent } from './order/page/detail/reaction-widal/pages/form/form.component';
import { SimpleChemistryDetailComponent } from './order/page/detail/simple-chemistry/pages/detail/detail.component';
import { ParameterComponentList } from './form-parameter/parameter/pages/list/parameter-list.component';
import { ParameterFormComponent } from './form-parameter/parameter/pages/form/form.component';
import { UrinalysisComponentList } from './order/page/detail/urinalysis/pages/list/urinalysis-list.component';
import { UrinalysisFormComponent } from './order/page/detail/urinalysis/pages/form/form.component';
import { UrinalysisDetailComponent } from './order/page/detail/urinalysis/pages/detail/detail.component';
import { StoolComponentList } from './order/page/detail/stool/pages/list/stool-list.component';
import { StoolFormComponent } from './order/page/detail/stool/pages/form/form.component';
import { StoolDetailComponent } from './order/page/detail/stool/pages/detail/detail.component';
import { TorchComponentList } from './order/page/detail/torch/pages/list/torch-list.component';
import { TorchFormComponent } from './order/page/detail/torch/pages/form/form.component';
import { TorchDetailComponent } from './order/page/detail/torch/pages/detail/detail.component';

export const routes: Routes = [

  // =====================================================
  // LOGIN
  // =====================================================
  {
    path: 'login',
    component: LoginComponent
  },

  // =====================================================
  // MAIN LAYOUT
  // =====================================================
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [AuthGuard],

    children: [

      // =====================================================
      // HOME
      // =====================================================
      {
        path: '',
        component: HomeComponent
      },

      // =====================================================
      // TESTS
      // =====================================================
      {
        path: 'tests',
        component: TestsComponentList
      },
      {
        path: 'tests/form',
        component: TestFormComponent
      },
      {
        path: 'tests/form/:id',
        component: TestFormComponent
      },
      {
        path: 'tests/:id',
        component: TestDetailComponent
      },

      // =====================================================
      // TEST CATEGORIES
      // =====================================================
      {
        path: 'test-category-references',
        component: TestCategoryList
      },
      {
        path: 'test-category-references/form',
        component: TestCategoryFormComponent
      },
      {
        path: 'test-category-references/form/:id',
        component: TestCategoryFormComponent
      },

      // =====================================================
      // PATIENTS
      // =====================================================
      {
        path: 'patients',
        component: PatientComponentList
      },
      {
        path: 'patients/form',
        component: PatientFormComponent
      },
      {
        path: 'patients/form/:id',
        component: PatientFormComponent
      },

      // =====================================================
      // PATIENT DETAIL AND CHILD ROUTES
      // =====================================================
      {
        path: 'patients/:id',
        component: PatientDetailComponent,

        children: [

          // DEFAULT
          {
            path: '',
            redirectTo: 'results',
            pathMatch: 'full'
          },

          // PATIENT RESULTS
          {
            path: 'results',
            component: PatientResultsComponent
          },

          // PATIENT HISTORY
          {
            path: 'history',
            component: PatientHistoryComponent
          },

          // PATIENT REPORT
          {
            path: 'report',
            component: PatientReportComponent
          },

          // =================================================
          // ORDERS
          // =================================================

          // CREATE ORDER
          {
            path: 'orders/create',
            component: OrderFormComponent
          },

          // EDIT ORDER
          {
            path: 'orders/:id',
            component: OrderFormComponent
          },

          // ORDER LIST
          {
            path: 'orders',
            component: OrderComponentList
          },

          // ORDER REPORT - NEW VIEW
          {
            path: 'orders/:orderId/report',
            component: OrderReportView
          },

          // ORDER TEST MENU
          {
            path: 'orders/:orderId/test-menu',
            component: TestMenuComponent
          },
          //=== SIMPLE CHEMISTRY ===
          {
            path: 'orders/:orderId/simple-chemistry',
            component: SimpleChemistryList
          },

          {
            path: 'orders/:orderId/simple-chemistry/create',
            component: SimpleChemistryFormComponent
          },

          {
            path: 'orders/:orderId/simple-chemistry/:id',
            component: SimpleChemistryFormComponent
          },
            {
            path: 'orders/:orderId/simple-chemistry/:id/detail',
            component: SimpleChemistryDetailComponent
          },

          // === WIDAL REACTION ===
          {
            path: 'orders/:orderId/widal-reactions',
            component: WidalReactionList
          },
          {
            path: 'orders/:orderId/widal-reactions/create',
            component: WidalReactionFormComponent
          },
          {
            path: 'orders/:orderId/widal-reactions/:id',
            component: WidalReactionFormComponent
          },
          {
            path: 'orders/:orderId/widal-reactions/:id/detail',
            component: WidalReactionDetailComponent
          },
        
          //=== URINALYSIS ==
          {
            path: 'orders/:orderId/urinalysis',
            component: UrinalysisComponentList
          },
          {
            path: 'orders/:orderId/urinalysis/create',
            component: UrinalysisFormComponent
          },
          {
            path: 'orders/:orderId/urinalysis/:id',
            component: UrinalysisFormComponent
          },
          {
            path: 'orders/:orderId/urinalysis/:id/detail',
            component: UrinalysisDetailComponent
          },
          //=== STOOL ==
          {
            path: 'orders/:orderId/stools',
            component: StoolComponentList
          },
          {
            path: 'orders/:orderId/stools/create',
            component: StoolFormComponent
          },
          {
            path: 'orders/:orderId/stools/:id',
            component: StoolFormComponent
          },
          {
            path: 'orders/:orderId/stools/:id/detail',
            component: StoolDetailComponent
          },

          //=== TORCH ===
          {
            path: 'orders/:orderId/torchs',
            component: TorchComponentList
          },

          {
            path: 'orders/:orderId/torchs/create',
            component: TorchFormComponent
          },
          {
            path: 'orders/:orderId/torchs/:id',
            component: TorchFormComponent
          },
          {
            path: 'orders/:orderId/torchs/:id/detail',
            component: TorchDetailComponent
          },


          // =================================================
          // TEST RESULTS - VERSION 1.0.4
          // =================================================

          // {
          //   path: 'orders/:orderId/test-results',
          //   component: TestResultComponentList
          // },

          // {
          //   path: 'orders/:orderId/test-results/create',
          //   component: TestResultFormComponent
          // },

          {
            path: 'orders/:orderId/test-results/:id',
            component: TestResultFormComponent
          }

        ]
      },

      // =====================================================
      // DOCTORS
      // =====================================================
      {
        path: 'doctors',
        component: DoctorComponentList
      },
      {
        path: 'doctors/form',
        component: DoctorFormComponent
      },
      {
        path: 'doctors/form/:id',
        component: DoctorFormComponent
      },

      // =====================================================
      // METRICS
      // =====================================================
      {
        path: 'metrics',
        component: DashboardComponent
      },
      // =====================================================
      // PARAMETERS FOR FORMS
      // =====================================================
      {
        path: 'parameters',
        component: ParameterComponentList
      },
      {
        path: 'parameters/form',
        component: ParameterFormComponent
      },
       {
        path: 'parameters/form/:id',
        component: ParameterFormComponent
      },
   
      // =====================================================
      // SETTINGS
      // =====================================================
      {
        path: 'settings',
        component: SettingsComponent
      },

      // =====================================================
      // USERS
      // =====================================================
      {
        path: 'users',
        component: UserComponentList
      },
      {
        path: 'users/form',
        component: UserFormComponent
      },
      {
        path: 'users/form/:id',
        component: UserFormComponent
      },
      {
        path: 'users/view/:id',
        component: UserDetailComponent
      },

      // =====================================================
      // BACKUP
      // =====================================================
      {
        path: 'backup',
        component: BackupComponent
      },
      {
        path: 'backup/configuration',
        component: BackupConfigurationComponent
      }

    ]
  },

  // =====================================================
  // UNKNOWN ROUTES
  // =====================================================
  {
    path: '**',
    redirectTo: 'login'
  }

];
