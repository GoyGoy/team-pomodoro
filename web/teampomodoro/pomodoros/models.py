from django.contrib.auth.models import User
from django.db import models
from django.utils import timezone
from django.utils.encoding import smart_unicode

from teams.models import Team
from pomodoros.constants import RECORD_TYPE_POMODORO, RECORD_TYPES


class Pomodoro(models.Model):
    """
    Holds pomodoros
    """
    user = models.ForeignKey(User, related_name="pomodoros")
    team = models.ForeignKey(Team, related_name="pomodoros")
    date_start = models.DateTimeField(default=timezone.now)
    date_end = models.DateTimeField(blank=True, null=True)
    is_broken = models.BooleanField()
    broke_cause = models.TextField(blank=True, null=True)
    record_type = models.IntegerField(default=RECORD_TYPE_POMODORO,
                                      choices=RECORD_TYPES)

    def __unicode__(self):
        return smart_unicode("%s -> %s " % (self.team.name,
                                            self.user.username))
