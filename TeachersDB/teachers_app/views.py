from rest_framework import viewsets

from teachers_app.models import Teachers, Chairs, Posts, Faculties
from teachers_app.serializers import TeachersSerializer, ChairsSerializer, PostsSerializer, FacultiesSerializer


class TeachersViewSet(viewsets.ModelViewSet):
    queryset = Teachers.objects.all().order_by('id')
    serializer_class = TeachersSerializer


class ChairsViewSet(viewsets.ModelViewSet):
    queryset = Chairs.objects.all().order_by('id')
    serializer_class = ChairsSerializer


class PostsViewSet(viewsets.ModelViewSet):
    queryset = Posts.objects.all().order_by('id')
    serializer_class = PostsSerializer


class FacultiesViewSet(viewsets.ModelViewSet):
    queryset = Faculties.objects.all().order_by('id')
    serializer_class = FacultiesSerializer
