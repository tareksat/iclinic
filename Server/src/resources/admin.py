from flask import json
from flask import request
from flask.ext.restful import Resource

from src.models.categoryModel import CategoryModel


class Admin(Resource):

    @staticmethod
    def post():
        data = request.get_json()
        name = data['name']
        start = data['start']
        end = data['end']
        model_type = data['model_type']
        flag = CategoryModel.find_by_name(name)
        if flag is None:
            cat = CategoryModel(name=name, start=start, end=end, model_type=model_type)
            cat.insert()
            return json.dumps({"Message": "True"})
        return json.dumps({"Message": "False"})

    @staticmethod
    def put():
        data = request.get_json()
        _id = data['id']
        name = data['name']
        start = data['start']
        end = data['end']
        model_type = data['model_type']
        cat = CategoryModel(_id=_id, name=name, start=start, end=end, model_type=model_type)
        cat.update()


    @staticmethod
    def delete():
        data = request.get_json()
        _id = data['id']
        name = data['name']
        start = data['start']
        end = data['end']
        model_type = data['model_type']
        cat = CategoryModel(_id=_id, name=name, start=start, end=end, model_type=model_type)
        cat.delete(_id)
        return json.dumps({"Message": "True"})


