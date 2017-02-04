from flask import json
from flask import request
from flask.ext.restful import Resource

from src.models.categoryModel import CategoryModel


class LoadCategoryNames(Resource):
    @staticmethod
    def get():
        cat_list = []
        for key in CategoryModel.CATEGORIES:
            cat_list.append(key)
        return cat_list


class LoadCategoryData(Resource):
    @staticmethod
    def get(name):
        c_list = []
        p_list = []
        waitinglist = CategoryModel.CATEGORIES[name].waiting_list
        for patient in waitinglist:
            if patient.status == 'c':
                c_list.append(patient.number)
            else:
                p_list.append(patient.number)

        return {
            "id": CategoryModel.CATEGORIES[name]._id,
            "last_patient": int(CategoryModel.CATEGORIES[name].last_patient),
            "current_patient": int(CategoryModel.CATEGORIES[name].current_patient),
            "waiting_p_list": len(p_list),
            "waiting_c_list": len(c_list)
        }


class LoadCategoryWaitingList(Resource):
    @staticmethod
    def get(name):
        c_list = []
        p_list = []
        waitinglist = CategoryModel.CATEGORIES[name].waiting_list
        for patient in waitinglist:
            if patient.status == 'c':
                c_list.append(int(patient.number))
            else:
                p_list.append(int(patient.number))

        return {
            "P-List": p_list,
            "C-List": c_list
        }



