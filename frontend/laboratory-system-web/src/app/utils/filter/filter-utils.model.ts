import { HttpParams } from '@angular/common/http';
import { ParamMap } from '@angular/router';
import { TestFilter } from '../../catalog/test/components/test-filter.component';
import { PatientFilter } from '../../patient/components/patient-filter.component';
import { OrderFilter } from '../../order/components/order-filter.component';
import { TestResultFilter } from '../../results/components/result-filter.component';


export class FilterUtils {
  static buildHttpParams(page: number, size: number, filter?: any, sort?: any): HttpParams {
    let params = new HttpParams().set('page', page).set('size', size);

    if (sort) {
      params = params.set('sort', sort);
    }
    if (filter) {
      Object.entries(filter).forEach(([key, value]) => {
        if (value !== undefined && value !== null && value !== '') {
          params = params.set(key, value.toString());
        }
      });
    }

    return params;
  }

  // static buildHttpParamsWithMachineId(
  //   machineId: number,
  //   page: number,
  //   size: number,
  //   filter?: any,
  // ): HttpParams {
  //   let params = new HttpParams().set('page', page).set('size', size);

  //   if (filter) {
  //     Object.entries(filter).forEach(([key, value]) => {
  //       if (value !== undefined && value !== null && value !== '') {
  //         params = params.set(key, value.toString());
  //       }
  //     });
  //   }

  //   return params;
  // }

  // issue
  // static buildHttpParamsWithIssueId(
  //   issuedId: number,
  //   page: number,
  //   size: number,
  //   filter?: any,
  // ): HttpParams {
  //   let params = new HttpParams().set('page', page).set('size', size);

  //   if (filter) {
  //     Object.entries(filter).forEach(([key, value]) => {
  //       if (value !== undefined && value !== null && value !== '') {
  //         params = params.set(key, value.toString());
  //       }
  //     });
  //   }

  //   return params;
  // }

  static cleanQueryParams(filter: any) {
    const params: any = {
      page: 0,
    };

    Object.entries(filter).forEach(([key, value]) => {
      params[key] = value !== undefined && value !== null && value !== '' ? value : null;
    });

    return params;
  }

static buildTestFilter(params: ParamMap): TestFilter {
  return {
    name: params.get('name') ?? undefined,

    priceMin: params.get('priceMin')
      ? +params.get('priceMin')!
      : undefined,

    priceMax: params.get('priceMax')
      ? +params.get('priceMax')!
      : undefined,

    active:
      params.get('active') !== null
        ? params.get('active') === 'true'
        : undefined,

    testCategoryName: params.get('testCategoryName') ?? undefined,

    createdAtAfter: params.get('createdAtAfter') ?? undefined,

    createdAtBefore: params.get('createdAtBefore') ?? undefined,

    updatedAtAfter: params.get('updatedAtAfter') ?? undefined,

    updatedAtBefore: params.get('updatedAtBefore') ?? undefined,
  };
}

static buildPatientFilter(params: ParamMap): PatientFilter {
  return {
    search: params.get('search') ?? undefined,

    gender: params.get('gender') ?? undefined,

    dateOfBirth: params.get('dateOfBirth') ?? undefined,

    createdAtAfter: params.get('createdAtAfter') ?? undefined,

    createdAtBefore: params.get('createdAtBefore') ?? undefined,

    updatedAtAfter: params.get('updatedAtAfter') ?? undefined,

    updatedAtBefore: params.get('updatedAtBefore') ?? undefined,
  };

}

static buildOrderFilter(params: ParamMap): OrderFilter{
  return{

    doctorName: params.get('doctorName') ?? undefined,

    status: params.get('status') ?? undefined,

    createdAtAfter: params.get('createdAtAfter') ?? undefined,

    createdAtBefore: params.get('createdAtBefore') ?? undefined,

    updatedAtAfter: params.get('updatedAtAfter') ?? undefined,

    updatedAtBefore: params.get('updatedAtBefore') ?? undefined,
  };
}

static buildTestResultFilter(params: ParamMap): TestResultFilter{
  return{

    priceMin: params.get('priceMin')
      ? +params.get('priceMin')!
      : undefined,

    priceMax: params.get('priceMax')
      ? +params.get('priceMax')!
      : undefined,


    search: params.get('search') ?? undefined,
    
    createdAtAfter: params.get('createdAtAfter') ?? undefined,

    createdAtBefore: params.get('createdAtBefore') ?? undefined,

    updatedAtAfter: params.get('updatedAtAfter') ?? undefined,

    updatedAtBefore: params.get('updatedAtBefore') ?? undefined,
  };
}



  // Isue
  // static buildIssueFilter(params: ParamMap): IssueFilter {
  //   return {
  //     id: params.get('id') ? +params.get('id')! : undefined,

  //     status: params.get('status') ? (params.get('status') as IssuesStatus) : undefined,

  //     reportedAtAfter: params.get('reportedAtAfter') ?? undefined,

  //     reportedAtBefore: params.get('reportedAtBefore') ?? undefined,

  //     updatedAtAfter: params.get('updatedAtAfter') ?? undefined,

  //     updatedAtBefore: params.get('updatedAtBefore') ?? undefined,

  //     createdBy: params.get('createdBy') ? +params.get('createdBy')! : undefined,

  //     gameMachineId: params.get('gameMachineId') ? +params.get('gameMachineId')! : undefined,

  //     user: params.get('user') ?? undefined,
  //   };
  // }

  // Repair Attempt
  // static buildRepairAttemptFilter(params: ParamMap): RepairAttemptFilter {
  //   return {
  //     id: params.get('id') ? +params.get('id')! : undefined,

  //     status: params.get('status') ? (params.get('status') as RepairStatus) : undefined,

  //     createdAtAfter: params.get('createdAtAfter') ?? undefined,

  //     createdAtBefore: params.get('createdAtBefore') ?? undefined,

  //     createdBy: params.get('createdBy') ? +params.get('createdBy')! : undefined,

  //     issuedId: params.get('issueId') ? +params.get('issueId')! : undefined,

  //     // user: params.get('user') ?? undefined
  //   };
  // }

}
