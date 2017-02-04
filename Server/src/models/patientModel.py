

class PatientModel(object):

    def __init__(self, number, status):
        self.number = number
        self.status = status

    def json(self):
        return {
            "number": self.number,
            "status": self.status
        }
