from django.db import models


class Faculties(models.Model):
    nameFaculty = models.CharField(max_length=128)
    shortNameFaculty = models.CharField(max_length=128)

    def __str__(self):
        return f'{self.shortNameFaculty}'


class Chairs(models.Model):
    nameChair = models.CharField(max_length=128)
    shortNameChair = models.CharField(max_length=128)
    idFaculty = models.ForeignKey(Faculties, on_delete=models.CASCADE, db_column='faculty_id')

    def __str__(self):
        return f'{self.idFaculty}'


class Posts(models.Model):
    namePost = models.CharField(max_length=128)

    def __str__(self):
        return f'{self.namePost}'


class Teachers(models.Model):
    idChair = models.ForeignKey(Chairs, on_delete=models.CASCADE, db_column='chair_id')
    idPost = models.ForeignKey(Posts, on_delete=models.CASCADE, db_column='post_id')
    firstName = models.CharField(max_length=128)
    secondName = models.CharField(max_length=128)
    lastName = models.CharField(max_length=128)
    phone = models.IntegerField()
    email = models.CharField(max_length=128)

    def __str__(self):
        return f'{self.idChair} {self.idPost}'
