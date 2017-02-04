from flask import request
from flask.ext.restful import Resource

from src.models.categoryModel import CategoryModel


class AddNewPatient(Resource):
    @staticmethod
    def post(name):
        c_list = []
        p_list = []
        waitinglist = CategoryModel.CATEGORIES[name].waiting_list
        for patient in waitinglist:
            if patient.status == 'c':
                c_list.append(patient.number)
            else:
                p_list.append(patient.number)

        cat = CategoryModel.CATEGORIES[name].add_new_patient()
        return {
            "last_patient": cat,
            "waiting_patients": (len(p_list) + len(c_list))
        }


class ChangePatientStatus(Resource):
    @staticmethod
    def post():
        data = request.get_json()
        patient_number = data['patient_number']
        category = data['category']
        CategoryModel.CATEGORIES[category].change_patient_status(patient_number)
