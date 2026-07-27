import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';



import { CardComponent } from '../../components/card/card.component';

@Component({
  selector: 'app-home',
  imports: [CardModule, ButtonModule, CardComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  private routing: Router = inject(Router);


  public navigationToUrl(path: string): void {
      this.routing.navigate([path]);
  }

}
