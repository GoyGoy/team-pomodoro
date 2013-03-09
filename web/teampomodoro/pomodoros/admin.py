from django.contrib import admin
from pomodoros.models import Pomodoro


class PomodoroAdmin(admin.ModelAdmin):
    list_display = ('user', 'team', 'date_start', 'date_end', 'record_type')
    list_editable = ('date_start', 'date_end')
    search_fields = ('user__username', 'team')
    list_filter = ('user', 'team', 'record_type')


admin.site.register(Pomodoro,PomodoroAdmin)