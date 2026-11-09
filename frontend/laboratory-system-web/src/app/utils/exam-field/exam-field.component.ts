export interface ExamField<T extends string = string> {
  name: T;
  label: string;
  type: 'text' | 'radio' | 'select';
  options?: string[];
  section?: string;
  order: number;
}