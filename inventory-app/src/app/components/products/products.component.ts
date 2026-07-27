import {
  ChangeDetectionStrategy,
  Component,
  inject,
  OnInit,
  signal
} from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { TableModule,TableLazyLoadEvent } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';

import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-products',
  imports: [ FormsModule, CurrencyPipe, TableModule,InputTextModule, ButtonModule],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProductsComponent {

  readonly productService = inject(ProductService);
  private routing: Router = inject(Router);

  readonly searchValue = signal('');

  private currentSort = 'name,asc';

  onLazyLoad(event: TableLazyLoadEvent): void {
    const first = event.first ?? 0;
    const rows = event.rows ?? 10;

    const page = Math.floor(first / rows);

    const sortField = Array.isArray(event.sortField)
      ? event.sortField[0]
      : event.sortField;

    const sortDirection =
      event.sortOrder === -1 ? 'desc' : 'asc';

    const sort = sortField
      ? `${sortField},${sortDirection}`
      : 'name,asc';

    this.productService.loadProducts(
      page,
      rows,
      sort
    );
  }

    onSearchChange(value: string): void {
    this.searchValue.set(value.trim());

    this.productService.loadProducts(
      0,
      this.productService.pageSize(),
      this.currentSort,
      this.searchValue()
    );
  }

  clearSearch(): void {
    this.searchValue.set('');

    this.productService.loadProducts(
      0,
      this.productService.pageSize(),
      this.currentSort,
      ''
    );
  }

  public goHome(): void {
    this.routing.navigate(['home'])
  }
  
}
