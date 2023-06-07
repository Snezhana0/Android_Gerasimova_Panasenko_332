from rest_framework import serializers
from teachers_app.models import Teachers, Chairs, Posts, Faculties


class TeachersSerializer(serializers.ModelSerializer):
    class Meta:
        model = Teachers
        fields = '__all__'


class ChairsSerializer(serializers.ModelSerializer):
    class Meta:
        model = Chairs
        fields = '__all__'


class PostsSerializer(serializers.ModelSerializer):
    class Meta:
        model = Posts
        fields = '__all__'


class FacultiesSerializer(serializers.ModelSerializer):
    class Meta:
        model = Faculties
        fields = '__all__'
