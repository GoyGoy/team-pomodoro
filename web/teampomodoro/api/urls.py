from django.conf.urls import patterns, include
from api.resources import UserResource, TeamResource, PomodoroResource

user_handler = UserResource()
team_handler = TeamResource()
pomodoro_handler = PomodoroResource()

urlpatterns = patterns('',
                       (r'^', include(user_handler.urls)),
                       (r'^', include(team_handler.urls)),
                       (r'^', include(pomodoro_handler.urls)),
                       )
