import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-card',
  imports: [],
  templateUrl: './card.component.html',
  styleUrl: './card.component.css'
})
export class CardComponent {

  @Input({ required: true })
  title!: string;

  @Input({ required: true })
  value!: string | number;

  @Input({ required: true })
  icon!: string;

  @Input({ required: true })
  footerText!: string;

  @Input()
  textColor = 'text-blue-600';

  @Input()
  footerColor = 'bg-blue-500';

  @Output()
  footerClick = new EventEmitter<void>();

  onFooterClick(): void {
    this.footerClick.emit();
  }

}
