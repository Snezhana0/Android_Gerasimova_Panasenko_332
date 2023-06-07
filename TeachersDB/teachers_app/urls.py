from rest_framework import routers

from .views import ChairsViewSet, TeachersViewSet, PostsViewSet, FacultiesViewSet


router = routers.DefaultRouter()
router.register(r'chairs', ChairsViewSet)
router.register(r'teachers', TeachersViewSet)
router.register(r'posts', PostsViewSet)
router.register(r'faculties', FacultiesViewSet)
urlpatterns = [
    *router.urls,
]
