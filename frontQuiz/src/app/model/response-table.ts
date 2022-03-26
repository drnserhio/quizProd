export class ResponseTable<T> {
  content: T[] = [];
  sort!: string;
  column!: string;
  currentPage!: number;
  totalPage!: number;
  allItems!: number;
}
