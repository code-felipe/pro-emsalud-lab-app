import { Pipe, PipeTransform } from '@angular/core';
import {
  TEST_METHOD_LABELS,
  TestMethod,
} from '../../catalog/test/components/test.model';

@Pipe({
  name: 'testMethods',
  standalone: true,
})
export class TestMethodsPipe implements PipeTransform {
  transform(methods: TestMethod[] | null | undefined, separator = ', '): string {
    if (!methods || methods.length === 0) {
      return '';
    }

    return methods.map((m) => TEST_METHOD_LABELS[m] ?? m).join(separator);
  }
}