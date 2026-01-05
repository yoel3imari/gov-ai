import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { UbCardDirective, UbCardHeaderDirective, UbCardTitleDirective, UbCardDescriptionDirective, UbCardContentDirective, UbCardFooterDirective } from '@/components/ui/card';
import { UbLabelDirective } from '@/components/ui/label';
import { UbInputDirective } from '@/components/ui/input';
import { UbButtonDirective } from '@/components/ui/button';
import { UbSeparatorDirective } from '@/components/ui/separator';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    UbCardDirective,
    UbCardHeaderDirective,
    UbCardTitleDirective,
    UbCardDescriptionDirective,
    UbCardContentDirective,
    UbCardFooterDirective,
    UbLabelDirective,
    UbInputDirective,
    UbButtonDirective,
    UbSeparatorDirective,
    RouterLink
  ],
  template: `
    <div class="flex min-h-screen items-center justify-center bg-gray-50 dark:bg-gray-900 p-4">
      <div ubCard class="w-full max-w-md">
        <div ubCardHeader class="space-y-1">
          <h3 ubCardTitle class="text-2xl font-bold tracking-tight">Sign in to your account</h3>
          <p ubCardDescription>Enter your email below to login to your account</p>
        </div>
        <div ubCardContent class="grid gap-4">
          <div class="grid gap-2">
            <label ubLabel for="email">Email</label>
            <input ubInput type="email" id="email" placeholder="m@example.com" />
          </div>
          <div class="grid gap-2">
            <label ubLabel for="password">Password</label>
            <input ubInput type="password" id="password" />
          </div>
          <button ubButton class="w-full" (click)="onLogin()">Sign In</button>
          
          <div class="relative">
            <div class="absolute inset-0 flex items-center">
              <div ubSeparator class="w-full"></div>
            </div>
            <div class="relative flex justify-center text-xs uppercase">
              <span class="bg-card px-2 text-muted-foreground">Or continue with</span>
            </div>
          </div>
          
          <div class="grid grid-cols-2 gap-6">
            <button ubButton variant="outline">Github</button>
            <button ubButton variant="outline">Google</button>
          </div>
        </div>
        <div ubCardFooter class="flex justify-center text-sm text-muted-foreground">
          Don't have an account? <a routerLink="/register" class="underline underline-offset-4 hover:text-primary ml-1">Sign up</a>
        </div>
      </div>
    </div>
  `,
})
export class LoginComponent {
  constructor(private router: Router) {}

  onLogin() {
    // Mock login logic
    this.router.navigate(['/chat']);
  }
}
