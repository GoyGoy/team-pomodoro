from django.contrib import admin
from teams.models import Team


class TeamAdmin(admin.ModelAdmin):
    list_display = ('name', 'owner', 'pomodoro_time', 'short_break_time', 'long_break_time')
    list_editable = ('short_break_time', 'long_break_time')
    search_fields = ('name', 'owner__username', 'members__username')
    list_filter = ('owner', 'short_break_time', 'long_break_time')


admin.site.register(Team,TeamAdmin)