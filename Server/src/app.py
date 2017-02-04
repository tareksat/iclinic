from flask import Flask
from flask import render_template
from flask_restful import Api

from src.common.database import Database
from src.resources.admin import *
from src.resources.category import *
from src.resources.clinic import *
from src.resources.front_desk import *

app = Flask(__name__)
app.secret_key = 'application'
api = Api(app)


@app.before_first_request
def init():
    Database.initialize()
    CategoryModel.load_all()

api.add_resource(LoadCategoryNames, '/load_cat_names')
api.add_resource(LoadCategoryData, '/load_cat_Data/<string:name>')
api.add_resource(AddNewPatient, '/add_new_patient/<string:name>')
api.add_resource(ChangePatientStatus, '/change_patient_status')
api.add_resource(GetPatient, '/get_patient/<string:category_name>')
api.add_resource(GetPatientByNumber, '/get_selected_patient')
api.add_resource(Admin, '/admin')
api.add_resource(Reset, '/reset')
api.add_resource(LoadCategoryWaitingList, '/load_waiting_lists/<string:name>')


@app.route('/intec')
def get():
    cat_list = []
    for key in CategoryModel.CATEGORIES:
        cat_list.append((CategoryModel.CATEGORIES[key]))
    return render_template('categories.html', cats=cat_list)