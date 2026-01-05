import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UbAvatarDirective, UbAvatarFallbackDirective } from '@/components/ui/avatar';
import { UbButtonDirective } from '@/components/ui/button';

interface Message {
  id: string;
  content: string;
  sender: 'user' | 'ai';
  timestamp: Date;
}

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, UbAvatarDirective, UbAvatarFallbackDirective, UbButtonDirective],
  template: `
    <div class="flex h-screen bg-background overflow-hidden">


      <!-- Main Chat Area -->
      <div class="flex-1 flex flex-col min-w-0 relative">


        <!-- Messages -->
        <div class="flex-1 overflow-y-auto p-4 md:p-10 space-y-8">


          @for (msg of messages(); track msg.id) {
            <div class="flex gap-4 max-w-3xl mx-auto" [class.flex-row-reverse]="msg.sender === 'user'">
              <div ubAvatar class="h-8 w-8 mt-1 shrink-0" [class.invisible]="msg.sender === 'user'">
                <div ubAvatarFallback class="bg-primary text-primary-foreground">AI</div>
              </div>
              
              <div 
                class="flex-1 space-y-2"
                [class.text-right]="msg.sender === 'user'"
              >
                <div class="font-semibold text-sm opacity-90 mb-1">
                  {{ msg.sender === 'user' ? 'You' : 'Gov AI' }}
                </div>
                <div 
                  class="prose dark:prose-invert text-sm leading-relaxed"
                  [class.bg-muted]="msg.sender === 'user'"
                  [class.p-3]="msg.sender === 'user'"
                  [class.rounded-lg]="msg.sender === 'user'"
                  [class.inline-block]="msg.sender === 'user'"
                >
                  {{ msg.content }}
                </div>
              </div>

              <div ubAvatar class="h-8 w-8 mt-1 shrink-0" [class.invisible]="msg.sender === 'ai'">
                <div ubAvatarFallback>ME</div>
              </div>
            </div>
          }
        </div>

        <!-- Input Area -->
        <div 
          class="p-4 bg-background border-t md:border-t-0 md:bg-transparent md:absolute md:left-0 md:right-0 md:pb-10 md:pt-0 pointer-events-none transition-all duration-500 ease-in-out"
          [class.top-1/2]="messages().length === 0"
          [class.-translate-y-1/2]="messages().length === 0"
          [class.bottom-0]="messages().length > 0"
        >
          <div class="max-w-3xl mx-auto pointer-events-auto">
            <div class="relative flex items-end gap-2 bg-background border rounded-xl shadow-[0_0_15px_rgba(0,0,0,0.1)] dark:shadow-[0_0_15px_rgba(255,255,255,0.1)] p-4 focus-within:shadow-[0_0_20px_rgba(var(--primary),0.3)] transition-shadow duration-300 animate-rainbow-border focus-within:animate-none">
              
              <textarea 
                class="flex-1 bg-transparent border-0 focus:ring-0 resize-none max-h-32 py-2.5 text-base min-h-[3rem] outline-none"
                placeholder="Message Gov AI..." 
                rows="1"
                (keydown.enter)="handleKeydown($event)"
                (input)="autoResize($event)"
              ></textarea>
              <button ubButton size="icon" class="shrink-0 h-10 w-10 rounded-lg">
                ➤
              </button>
            </div>
            <div class="text-center text-xs text-muted-foreground mt-2">
              AI can make mistakes. Check important info.
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
})
export class ChatComponent {
  messages = signal<Message[]>([]);

  handleKeydown(event: Event) {
    const keyboardEvent = event as KeyboardEvent;
    if (keyboardEvent.key === 'Enter' && !keyboardEvent.shiftKey) {
      event.preventDefault();
      const textarea = event.target as HTMLTextAreaElement;
      this.sendMessage(textarea.value);
      textarea.value = '';
      textarea.style.height = 'auto';
    }
  }

  autoResize(event: Event) {
    const textarea = event.target as HTMLTextAreaElement;
    textarea.style.height = 'auto';
    textarea.style.height = textarea.scrollHeight + 'px';
  }

  sendMessage(content: string) {
    const value = content.trim();
    if (!value) return;

    this.messages.update((msgs) => [
      ...msgs,
      {
        id: Date.now().toString(),
        content: value,
        sender: 'user',
        timestamp: new Date(),
      },
    ]);

    // Mock reply
    setTimeout(() => {
      this.messages.update((msgs) => [
        ...msgs,
        {
          id: Date.now().toString(),
          content:
            'I am processing your request. As an AI assistant, I can help analyze documents, draft content, or answer questions.',
          sender: 'ai',
          timestamp: new Date(),
        },
      ]);
    }, 1000);
  }
}
