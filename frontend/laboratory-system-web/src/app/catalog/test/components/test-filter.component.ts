export interface TestFilter {
  name?: string;
  priceMin?: number;
  priceMax?: number;
  active?: boolean;
  testCategoryName?: string;
  createdAtAfter?: string;
  createdAtBefore?: string;
  updatedAtAfter?: string;
  updatedAtBefore?: string;
}
