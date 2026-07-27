import { HttpClient, HttpParams } from '@angular/common/http';
import { computed, inject, Injectable, signal } from '@angular/core';
import { PageResponse } from '../utils/interfaces/pagination.interface';
import { Product } from '../utils/interfaces/response/product.interface';
import { finalize } from 'rxjs';

import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private readonly http = inject(HttpClient);

  private readonly apiUrl = `${environment.apiUrl}/products`;

  /*
   * Estado interno modificable.
   * Solo el servicio puede usar set() o update().
   */
  private readonly productsState = signal<Product[]>([]);
  private readonly loadingState = signal(false);
  private readonly errorState = signal<string | null>(null);
  private readonly totalRecordsState = signal(0);
  private readonly currentPageState = signal(0);
  private readonly pageSizeState = signal(10);
  private readonly totalPagesState = signal(0);

  /*
   * Signals de solo lectura.
   * solo se pueden leer, pero no modificarlos.
   */
  readonly products = this.productsState.asReadonly();
  readonly loading = this.loadingState.asReadonly();
  readonly error = this.errorState.asReadonly();
  readonly totalRecords = this.totalRecordsState.asReadonly();
  readonly currentPage = this.currentPageState.asReadonly();
  readonly pageSize = this.pageSizeState.asReadonly();
  readonly totalPages = this.totalPagesState.asReadonly();


  readonly hasProducts = computed(
    () => this.productsState().length > 0
  );

  readonly isEmpty = computed(
    () =>
      !this.loadingState() &&
      this.productsState().length === 0
  );

  loadProducts(
    page: number = 0,
    size: number = 10,
    sort: string = 'name,asc',
    search: string = ''
  ): void {

    this.loadingState.set(true);
    this.errorState.set(null);

    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', sort);

    if (search.trim()) {
      params = params.set('search', search.trim());
    }

    this.http
      .get<PageResponse<Product>>(this.apiUrl, { params })
      .pipe(
        finalize(() => {
          this.loadingState.set(false);
        })
      )
      .subscribe({
        next: (response:PageResponse<Product>) => {
          this.productsState.set(response.content);
          this.totalRecordsState.set(response.page.totalElements);
          this.currentPageState.set(response.page.number);
          this.pageSizeState.set(response.page.size);
          this.totalPagesState.set(response.page.totalPages);
        },
        error: (error) => {
          console.error(
            'Error al obtener los productos:',
            error
          );

          this.productsState.set([]);
          this.totalRecordsState.set(0);

          this.errorState.set(
            'No fue posible obtener los productos.'
          );
        }
      });
  }

  reloadProducts(): void {
    this.loadProducts(
      this.currentPageState(),
      this.pageSizeState()
    );
  }

  clearError(): void {
    this.errorState.set(null);
  }
}
