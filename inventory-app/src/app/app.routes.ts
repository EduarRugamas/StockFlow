import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: '',
        pathMatch: 'full',
        redirectTo: 'home'
    },
    {
        path: '',
        loadComponent: () => import('./components/layout/layout.component').then(it => it.LayoutComponent),
        children: [
            {
                path: 'home',
                loadComponent: () => import('./pages/home/home.component').then(it => it.HomeComponent)
            },
            {
                path: 'products',
                loadComponent: () => import('./components/products/products.component').then(it => it.ProductsComponent)
            },
            {
                path: 'movements',
                loadComponent: () => import('./components/movements/movements.component').then(it => it.MovementsComponent)
            },
            {
                path: 'alerts',
                loadComponent: () => import('./components/alerts/alerts.component').then(it => it.AlertsComponent)
            }
        ]
    }

];
