import uuid

from src.common.database import Database
from src.models.patientModel import PatientModel


class CategoryModel(object):
    CATEGORIES = {}
    
    def __init__(self, name, start, end, current_patient=0, last_patient=0, model_type='p', _id=None):

        self.name = name
        self.start = start
        self.end = end
        self.current_patient = current_patient
        self.last_patient = last_patient
        self.model_type = model_type
        self._id = uuid.uuid4().hex if _id is None else _id
        self.waiting_list = []

    def json(self):

        return {
            "_id":self._id,
            "name": self.name,
            "start": self.start,
            "end":self.end,
            "current_patient": self.current_patient,
            "last_patient": self.last_patient,
            "model_type": self.model_type
        }

    @classmethod
    def load_all(cls):
        CategoryModel.CATEGORIES.clear()
        for category in [cls(**cat) for cat in Database.load('categories')]:
            CategoryModel.CATEGORIES.update({category.name: category})

    @classmethod
    def find_by_name(cls, name):
        try:
            return cls(**Database.find_by_name('categories', query={"name": name}))
        except:
            return

    def insert(self):
        Database.insert(collection='categories', data=self.json())
        CategoryModel.load_all()

    def update(self):
        Database.update(collection='categories', query={"_id": self._id}, data=self.json())
        CategoryModel.load_all()

    @staticmethod
    def delete(_id):
        Database.delete(collection='categories', query={"_id": _id})
        CategoryModel.load_all()

    def add_new_patient(self):
        if self.last_patient == self.end:
            self.last_patient = self.start
        elif self.last_patient == 0:
            self.last_patient = self.start
        else:
            self.last_patient += 1

        if self.model_type == 'p':
            self.waiting_list.append(PatientModel(self.last_patient, 'p'))
        else:
            self.waiting_list.append(PatientModel(self.last_patient, 'c'))

        return int(self.last_patient)

    def change_patient_status(self, patient_number):
        for patient in self.waiting_list:
            if patient.number == patient_number:
                patient.status = 'c'

    def get_patient(self):
        for patient in self.waiting_list:
            if patient.status == 'c':
                self.waiting_list.remove(patient)
                self.current_patient = patient.number
                return int(patient.number)

    def get_selected_patient(self, number):
        for patient in self.waiting_list:
            if patient.number == number:
                self.current_patient = number
                self.waiting_list.remove(patient)
                return int(patient.number)
    @staticmethod
    def reset():
        for key in CategoryModel.CATEGORIES:
            CategoryModel.CATEGORIES[key].waiting_list = []
            CategoryModel.CATEGORIES[key].current_patient = 0
            CategoryModel.CATEGORIES[key].last_patient = 0
