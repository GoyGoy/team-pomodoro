from datetime import datetime
from django.conf.urls import url
from django.contrib.auth.models import User

from tastypie import fields
from tastypie.resources import ModelResource
from tastypie.authentication import BasicAuthentication

from pomodoros.models import Pomodoro
from teams.models import Team


class UserResource(ModelResource):
    teams = fields.ToManyField('api.resources.TeamResource', 'teams', full=True, null=True)
    pomodoros = fields.ToManyField('api.resources.PomodoroResource', 'pomodoros', full=True, null=True)

    class Meta:
        queryset = User.objects.all()
        authentication = BasicAuthentication()
        resource_name = 'auth'
        fields = ('id', 'username', 'first_name', 'last_name', 'email', 'pomodoros', 'teams')

    def get_object_list(self, request):
        qs = super(UserResource, self).get_object_list(request)
        return qs.filter(id=request.user.id)

    def override_urls(self):
        return [
            url(r"^(?P<resource_name>%s)/$" % self._meta.resource_name, self.wrap_view('dispatch_detail'),
                name="user_dispatch_detail"), ]


class TeamResource(ModelResource):

    class Meta:
        queryset = Team.objects.all()
        authentication = BasicAuthentication()
        resource_name = 'teams'
        fields = ('name', 'owner', 'members', 'pomodoro_time', 'long_break_time', 'short_break_time')

    def get_object_list(self, request):
        qs = super(TeamResource, self).get_object_list(request)
        if request.user and request.user.is_authenticated():
            return qs.filter(members__in=[request.user.id])
        return qs.none


class PomodoroResource(ModelResource):

    class Meta:
        queryset = Pomodoro.objects.filter(date_end__gte=datetime.now())
        resource_name = 'pomodoros'
        fields = ('user', 'team', 'date_start', 'date_end', 'record_type')



