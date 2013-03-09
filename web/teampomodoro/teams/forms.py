from django import forms
from django.utils.translation import ugettext_lazy as _
from teams.models import Team


class ChooseTeamForm(forms.Form):
    team = forms.ModelChoiceField(queryset=Team.objects.all(),
                                  label=_("Choose a team to work"))

    def __init__(self, *args, **kwargs):
        user = kwargs.pop("user", None)
        self.base_fields["team"].queryset = user.teams.all()
        super(ChooseTeamForm, self).__init__(*args, **kwargs)


class CreateTeamForm(forms.ModelForm):
    class Meta:
        model = Team
        exclude = ["owner"]
