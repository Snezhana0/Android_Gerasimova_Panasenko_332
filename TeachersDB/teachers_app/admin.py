from django.contrib import admin

from teachers_app.models import Chairs, Teachers, Posts, Faculties

admin.site.register(Chairs)
admin.site.register(Posts)
admin.site.register(Teachers)
admin.site.register(Faculties)
