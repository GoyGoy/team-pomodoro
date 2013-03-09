from django.conf.urls import patterns, include, url
from django.contrib import admin

from teams.views import HomeView

admin.autodiscover()

urlpatterns = patterns('',
    url(r'^$', HomeView.as_view(), name='home'),
    url(r'^accounts/', include('profiles.urls')),
    url(r'^admin/', include(admin.site.urls)),
)
