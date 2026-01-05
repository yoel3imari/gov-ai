import { computed, Directive, input } from '@angular/core'

import { cn } from '@/lib/utils'

@Directive({
  selector: 'label[ubLabel]',
  standalone: true,
  host: {
    '[class]': 'computedClass()',
  },
})
export class UbLabelDirective {
  readonly class = input<string>()

  protected computedClass = computed(() =>
    cn(
      'text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70',
      this.class(),
    ),
  )
}
