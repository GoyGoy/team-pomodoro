from django.contrib.auth.models import User
from django.db import models
from django.utils.encoding import smart_unicode
from django.utils.translation import ugettext_lazy as _


class Team(models.Model):
    """
    Holds teams
    """
    name = models.CharField(max_length=255)
    owner = models.ForeignKey(User, related_name="owner")
    members = models.ManyToManyField(User, related_name="teams")
    pomodoro_time = models.PositiveIntegerField(_("Pomodoro Time"))
    long_break_time = models.PositiveIntegerField(_("Long Break Time"))
    short_break_time = models.PositiveIntegerField(_("Short Break Time"))

    def __unicode__(self):
        return smart_unicode(self.name)
