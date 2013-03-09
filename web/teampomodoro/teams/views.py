from django.shortcuts import redirect
from django.views.generic import TemplateView

from profiles.mixins import LoginRequiredMixin
from teams.forms import ChooseTeamForm, CreateTeamForm


class HomeView(LoginRequiredMixin, TemplateView):
    template_name = 'index.html'

    def get_context_data(self, **kwargs):
        choose_team_form = ChooseTeamForm(user=self.request.user)
        create_team_form = CreateTeamForm()
        return {
            "choose_team": choose_team_form,
            "create_team": create_team_form,
        }

    def post(self, request):
        form = CreateTeamForm(request.POST)
        if form.is_valid():
            form.instance.owner = request.user
            form.save()
            return redirect("home")
