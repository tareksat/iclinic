
from src.common.database import Database
from src.models.categoryModel import *

Database.initialize()

cat1 = CategoryModel(name='x', start=1, end=10)
cat2 = CategoryModel(name='y', start=11, end=20)
cat3 = CategoryModel(name='z', start=21, end=30)

cat1.insert()
cat2.insert()
cat3.insert()
