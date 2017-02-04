import pymongo


class Database(object):

    URL = "mongodb://127.0.0.1:27017"
    DATABASE = None

    @staticmethod
    def initialize():
        client = pymongo.MongoClient(Database.URL)
        Database.DATABASE = client['clinic_db']

    @staticmethod
    def load(collection):
        return [category for category in Database.DATABASE[collection].find({})]

    @staticmethod
    def find_by_name(collection, query):
        cat = Database.DATABASE[collection].find_one(query)
        return  cat

    @staticmethod
    def update(collection, query, data):
        Database.DATABASE[collection].update(query, data, upsert=False)

    @staticmethod
    def insert(collection, data):
        Database.DATABASE[collection].insert(data)
        return data

    @staticmethod
    def delete(collection, query):
        Database.DATABASE[collection].remove(query)
