import { computed, Directive, input } from '@angular/core'

import { cn } from '@/lib/utils'

@Directive({
  selector: 'div[ubAvatar]',
  standalone: true,
  host: {
    '[class]': 'computedClass()',
  },
})
export class UbAvatarDirective {
  readonly class = input<string>()

  protected computedClass = computed(() =>
    cn('relative flex h-10 w-10 shrink-0 overflow-hidden rounded-full', this.class()),
  )
}

@Directive({
  selector: 'img[ubAvatarImage]',
  standalone: true,
  host: {
    '[class]': 'computedClass()',
  },
})
export class UbAvatarImageDirective {
  readonly class = input<string>()

  protected computedClass = computed(() => cn('aspect-square h-full w-full', this.class()))
}

@Directive({
  selector: 'div[ubAvatarFallback]',
  standalone: true,
  host: {
    '[class]': 'computedClass()',
  },
})
export class UbAvatarFallbackDirective {
  readonly class = input<string>()

  protected computedClass = computed(() =>
    cn(
      'flex h-full w-full items-center justify-center rounded-full bg-muted',
      this.class(),
    ),
  )
}
