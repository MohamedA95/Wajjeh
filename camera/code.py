#please do not forget to export GOOGLE_APPLICATION_CREDENTIALS="/home/user/Downloads/[FILE_NAME].json" 
from cv2 import *
import sys, base64,json,firebase_admin
from google.cloud import automl_v1beta1
from google.cloud.automl_v1beta1.proto import service_pb2
from firebase_admin import db
from firebase_admin import credentials
cred = credentials.Certificate("wajjah2-bb770-firebase-adminsdk-hh20y-9b6f0a44db.json")
firebase_admin.initialize_app(cred, {'databaseURL':'https://wajjah2-bb770.firebaseio.com/'})
def get_image():
 cam = VideoCapture(0)
 for i in range(15):
  temp=cam.read()
 retval, im = cam.read()
 del(cam)
 return im

def scv(floor, status):
    ref = db.reference('floors')
    ref.update({floor: status})

def get_prediction(content, project_id, model_id):
  prediction_client = automl_v1beta1.PredictionServiceClient()
  name = 'projects/{}/locations/us-central1/models/{}'.format(project_id, model_id)
  payload = {'image': {'image_bytes': content }}
  params = {}
  request = prediction_client.predict(name, payload, params)
  return request  # waits till request is returned

while True:
 f=input("Enter floor number>")
 img=get_image()
 file="image.jpg"
 cv2.imwrite(file,img)
 with open(file,'rb') as ff:
   content=ff.read()
 res=get_prediction(content, 'gra-pro' ,  'ICN978699579344462926')
 rest=res.payload[0].display_name
 print(f+' state '+rest)
 scv(f,rest)

