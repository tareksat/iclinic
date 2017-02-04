from flask import json
from flask import request
from flask.ext.restful import Resource

from src.models.categoryModel import CategoryModel


class GetPatient(Resource):
    @staticmethod
    def get(category_name):
        patient = CategoryModel.CATEGORIES[category_name].get_patient()
        return {"patient": patient}


class GetPatientByNumber(Resource):
    @staticmethod
    def post():
        data = request.get_json()
        category_name = data['category_name']
        patient_number = data['patient_number']
        patient = CategoryModel.CATEGORIES[category_name].get_selected_patient(patient_number)
        return {"patient": patient}


class Reset(Resource):
    @staticmethod
    def get():
        CategoryModel.reset()



