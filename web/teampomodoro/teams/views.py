from django.views.generic import TemplateView

from profiles.mixins import LoginRequiredMixin


class HomeView(LoginRequiredMixin, TemplateView):
    template_name = 'index.html'
